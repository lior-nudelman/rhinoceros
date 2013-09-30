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

import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.ComparisonTerm;
import javax.mail.search.ReceivedDateTerm;
import javax.mail.search.SearchTerm;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class MailClient {
	private static Logger logger = Logger.getLogger(MailClient.class);

	public void readAccount(String host, String user, String password,
			String path, Date date) throws MessagingException {
		Properties props = System.getProperties();
		props.setProperty("mail.store.protocol", "imaps");

		Session session = Session.getDefaultInstance(props, null);
		Store store = session.getStore("imaps");
		store.connect(host, user, password);
		Folder inbox = store.getFolder("Inbox");// need to read all folders

		readFolder(inbox, path + "/" + user + "/", date);
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
				List<File> attachments = new ArrayList<File>();
				Multipart multipart = (Multipart) message.getContent();

				for (int i = 0; i < multipart.getCount(); i++) {
					BodyPart bodyPart = multipart.getBodyPart(i);
					if (!Part.ATTACHMENT.equalsIgnoreCase(bodyPart
							.getDisposition())
							&& !StringUtils.isNotBlank(bodyPart.getFileName())) {
						String content = bodyPart.getContent().toString();
						if (content.length() > 1024) {
							content = content.substring(0, 1024);
						}
						String data = "<SUBJECT>\n" + subject
								+ "\n</SUBJECT>\n<BODY>\n" + content + "\n</BODY>\n";
						writeFile(path + "/" + id + "-" + index + ".txt", data);

					} else {
						InputStream is = bodyPart.getInputStream();
						File f = new File(path + "/" + id + "-" + index + "."+ bodyPart.getFileName());
						FileOutputStream fos = new FileOutputStream(f);
						byte[] buf = new byte[4096];
						int bytesRead;
						while ((bytesRead = is.read(buf)) != -1) {
							fos.write(buf, 0, bytesRead);
						}
						fos.close();
						attachments.add(f);
					}
				}

			} catch (Exception e) {
				logger.error(e, e);
			}
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