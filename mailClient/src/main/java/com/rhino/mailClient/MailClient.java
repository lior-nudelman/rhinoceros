package com.rhino.mailClient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
import javax.mail.search.ComparisonTerm;
import javax.mail.search.ReceivedDateTerm;
import javax.mail.search.SearchTerm;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson.JacksonFactory;
import com.rhino.mailClient.oauth2.OAuth2Authenticator;
import com.sun.mail.imap.IMAPStore;

public class MailClient implements MailClientInterface {
	private static Logger logger = Logger.getLogger(MailClient.class);

	private static final String CLIENT_ID = "159872143583.apps.googleusercontent.com";
	private static final String CLIENT_SECRET = "x2hF3tBFnfmlzELIRUlmGWdv";

	private static final JsonFactory JSON_FACTORY = new JacksonFactory();
	private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

	public void readAccount(String email,String oauthToken, String path, Date date,String sysUser) throws Exception {
		GoogleCredential credentials = new GoogleCredential.Builder()
	    .setClientSecrets(CLIENT_ID, CLIENT_SECRET)
	    .setJsonFactory(JSON_FACTORY).setTransport(HTTP_TRANSPORT).build()
	    .setRefreshToken(oauthToken).setAccessToken(null);	
		credentials.refreshToken();
		oauthToken=credentials.getAccessToken();
		logger.info("Refresh token: "+oauthToken);

		
		Properties props = System.getProperties();
		props.setProperty("mail.store.protocol", "imaps");
		
		IMAPStore imapStore = OAuth2Authenticator.connectToImap("imap.gmail.com", 993, email, oauthToken, true);
		Folder inbox = imapStore.getFolder("Inbox");// need to read all folders

		readFolder(inbox, path + "/" + sysUser +"/"+ email + "/", date);
	}

	public void readAccount(String host, String user, String password,
			String path, Date date,String sysUser) throws MessagingException {
		Properties props = System.getProperties();
		props.setProperty("mail.store.protocol", "imaps");

		Session session = Session.getDefaultInstance(props, null);
		Store store = session.getStore("imaps");
		store.connect(host, user, password);
		Folder inbox = store.getFolder("Inbox");// need to read all folders

		readFolder(inbox, path + "/" + sysUser +"/"+ user + "/", date);
	}

	private void readFolder(Folder folder, String path, Date date)
			throws MessagingException {
		folder.open(Folder.READ_ONLY);
		showAllMails(folder, path, date);
	}

	private void showAllMails(Folder folder, String path, Date date) {
		try {
			SearchTerm newerThan = new ReceivedDateTerm(ComparisonTerm.GT, date);
			Message msg[] = folder.search(newerThan);
			logger.info("MAILS: " + msg.length + " from " + folder
					+ " to path " + path + " date filter " + date);
			showMessages(msg, path + "/" + folder.getName() + "/");

		} catch (MessagingException e) {
			logger.error(e, e);
		}
	}

	private void showMessages(Message msg[], String path) {
		createFolder(path);
		long id = System.currentTimeMillis();
		for (Message message : msg) {
			try {

				int index = message.getMessageNumber();
				String subject = message.getSubject();
				Address[] address = message.getFrom();
				String phisicalFromAddress ="";
				if(address.length>0){
					InternetAddress iaddress =(InternetAddress)address[0];
					phisicalFromAddress = iaddress.getAddress();
				}
				String meassageIndex = ""+message.getMessageNumber();
				Multipart multipart = (Multipart) message.getContent();

				StringBuilder sb = new StringBuilder();
				sb.append(subject).append("\n");
				sb.append(phisicalFromAddress).append("\n");
				sb.append(meassageIndex).append("\n");
				
				for (int i = 0; i < multipart.getCount(); i++) {
					BodyPart bodyPart = multipart.getBodyPart(i);
					savePart(bodyPart,sb,path + "/" + id + "-" + index);
				}
				sb.append("\n</BODY>\n");
				writeFile(path + "/" + id + "-" + index + ".txt", sb.toString());

			} catch (Exception e) {
				logger.error(e, e);
			}
		}
	}

	private void savePart(BodyPart bodyPart,StringBuilder sb,String path) throws FileNotFoundException, MessagingException, IOException{
		if(bodyPart.getContent() instanceof Multipart){
			Multipart multipart = (Multipart)bodyPart.getContent();
			for (int i = 0; i < multipart.getCount(); i++) {
				BodyPart bodyPartT = multipart.getBodyPart(i);
				savePart(bodyPartT,sb,path +"-"+ i);
			}
			return;
		}
		if ( !StringUtils.isNotBlank(bodyPart.getFileName())) {
			String content = bodyPart.getContent().toString();
			sb.append(content);

		} else {
			if(!bodyPart.getFileName().endsWith("pdf")){
				return;
			}
			InputStream is = bodyPart.getInputStream();
			File f = new File(path  + "."+ bodyPart.getFileName());
			FileOutputStream fos = new FileOutputStream(f);
			byte[] buf = new byte[4096];
			int bytesRead;
			while ((bytesRead = is.read(buf)) != -1) {
				fos.write(buf, 0, bytesRead);
			}
			fos.close();
		}
	}
	private void createFolder(String path) {
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
	}

	private void writeFile(String path, String content) throws IOException {
		File f = new File(path);
		FileOutputStream fos = new FileOutputStream(f);
		fos.write(content.getBytes());
		fos.flush();
		fos.close();
	}
}