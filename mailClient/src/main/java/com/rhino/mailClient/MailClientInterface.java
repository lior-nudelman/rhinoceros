package com.rhino.mailClient;

import java.util.Date;

import javax.mail.MessagingException;

public interface MailClientInterface {
	
	public void readAccount(String host, String user, String password,String path, Date date) throws MessagingException;
}