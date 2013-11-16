package com.rhino.mailClient.test;

import static org.junit.Assert.*;

import java.util.Date;

import javax.mail.MessagingException;

import org.junit.Test;

import com.rhino.mailClient.MailClient;

public class MailClientTest {

	@Test
	public void test() throws Exception {
		MailClient mailClient = new MailClient();
		//mailClient.readAccount("imap.gmail.com", "rhino.test.email@gmail.com","mokavanil", "C:/tmp/rhino/",new Date(System.currentTimeMillis()-(24L*60L*60L*1000L*365L)),"aaa");
		mailClient.readAccount("rhino.test.email@gmail.com","1/rral8nlUFZSfo3nL_xwdIV35EG0PaXzP9rIv0Q9hIAc", "C:/tmp/rhino/",new Date(System.currentTimeMillis()-(24L*60L*60L*1000L*365L)),"aaa");

		// IMAP host for yahoo.
		// store.connect("imap.mail.yahoo.com", "<username>", "<password>"); 

	}

}
