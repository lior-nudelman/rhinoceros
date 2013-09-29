package com.rhino.mailClient.test;

import static org.junit.Assert.*;

import javax.mail.MessagingException;

import org.junit.Test;

import com.rhino.mailClient.MailClient;

public class MailClientTest {

	@Test
	public void test() throws MessagingException {
		MailClient mailClient = new MailClient();
		mailClient.readAccount("imap.gmail.com", "rhino.test.email@gmail.com","mokavanil", "C:/tmp/rhino/");
		// IMAP host for yahoo.
		// store.connect("imap.mail.yahoo.com", "<username>", "<password>"); 

	}

}
