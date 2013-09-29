package com.rhino.mailClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.FlagTerm;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class MailClient {
	private static Logger logger = Logger.getLogger(MailClient.class); 

	public void readAccount(String host,String user,String password,String path) throws MessagingException{
		Properties props = System.getProperties();
		props.setProperty("mail.store.protocol", "imaps");

		Session session = Session.getDefaultInstance(props, null);
		Store store = session.getStore("imaps");
		store.connect(host, user,password);
		Folder inbox = store.getFolder("Inbox");//need to read all folders
		
		readFolder(inbox, path+"/"+user+"/");
	}
	
	private void readFolder(Folder folder,String path) throws MessagingException{
		folder.open(Folder.READ_ONLY);
		showAllMails(folder,path);
	}
	
	private void showUnreadMails(Folder folder,String path) {
		try {
			FlagTerm ft = new FlagTerm(new Flags(Flags.Flag.SEEN), false);
			Message msg[] = folder.search(ft);
			showMessages(msg,path+"/"+folder.getName()+"/");
		} catch (MessagingException e) {
			System.out.println(e.toString());
		}
	}

	private void showAllMails(Folder folder,String path) {
		try {
			Message msg[] = folder.getMessages();
			showMessages(msg,path+"/"+folder.getName()+"/");

		} catch (MessagingException e) {
			System.out.println(e.toString());
		}
	}

	private void showMessages(Message msg[], String path) {
		System.out.println("MAILS: " + msg.length);
		createFolder(path);
		for (Message message : msg) {
			try {

				List<File> attachments = new ArrayList<File>();
				Multipart multipart = (Multipart) message.getContent();

				for (int i = 0; i < multipart.getCount(); i++) {
					BodyPart bodyPart = multipart.getBodyPart(i);
					if (!Part.ATTACHMENT.equalsIgnoreCase(bodyPart
							.getDisposition())
							&& !StringUtils.isNotBlank(bodyPart.getFileName())) {
						continue; // dealing with attachments only
					}
					InputStream is = bodyPart.getInputStream();
					File f = new File(path + bodyPart.getFileName());
					FileOutputStream fos = new FileOutputStream(f);
					byte[] buf = new byte[4096];
					int bytesRead;
					while ((bytesRead = is.read(buf)) != -1) {
						fos.write(buf, 0, bytesRead);
					}
					fos.close();
					attachments.add(f);
				}

			} catch (Exception e) {
				System.out.println("No Information");
			}
		}
	}
	
	private void createFolder(String path){
		File file = new File(path);
		if(!file.exists()){
			file.mkdirs();
		}
	}
}