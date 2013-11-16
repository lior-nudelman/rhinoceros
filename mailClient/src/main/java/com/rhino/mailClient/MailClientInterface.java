package com.rhino.mailClient;

import java.util.Date;

import javax.mail.MessagingException;

public interface MailClientInterface {
	
	public void readAccount(String host, String user, String password,String path, Date date,String sysUser) throws MessagingException;
	public void readAccount(String email,String oauthToken, String path, Date date,String sysUser) throws Exception;
}