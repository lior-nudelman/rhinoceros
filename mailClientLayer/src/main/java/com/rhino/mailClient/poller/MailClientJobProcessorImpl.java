package com.rhino.mailClient.poller;

import java.util.Date;
import java.util.Map;

import javax.mail.MessagingException;

import org.apache.log4j.Logger;

import com.rhino.mailClient.MailClientInterface;
import com.rhino.taskManager.poller.JobProcessorInterface;
import com.rhino.userAttributesServic.data.UserAttributeType;

public class MailClientJobProcessorImpl implements JobProcessorInterface {

	private static Logger logger = Logger.getLogger(MailClientJobProcessorImpl.class);
	
	private MailClientInterface mailClient;
	
	public Object process(String mainVeriteID,
			Map<UserAttributeType, String> attributes, Object veriteData) {
		MailClientInterface mailClient = getMailClient();
		String host = attributes.get(UserAttributeType.MAIL_HOST);
		String user = attributes.get(UserAttributeType.MAIL_USERNAME);
		String password = attributes.get(UserAttributeType.MAIL_PASSWORD);
		String path = attributes.get(UserAttributeType.FOLDER_PATH);
		String dateS = attributes.get(UserAttributeType.DATE);
		String token = attributes.get(UserAttributeType.MAIL_TOKEN);
		Date date = new Date(Long.parseLong(dateS));
		if(veriteData != null && veriteData instanceof Date){
			date = (Date)veriteData;
		}
		try {
			if(token != null){
					mailClient.readAccount(user,token, path,date,mainVeriteID);
			}else{
				mailClient.readAccount(host, user, password, path, date,mainVeriteID);
			}
		} catch (Exception e) {
			logger.error(e,e);
		}
		return new Date();
	}

	public MailClientInterface getMailClient() {
		return mailClient;
	}

	public void setMailClient(MailClientInterface mailClient) {
		this.mailClient = mailClient;
	}

}
