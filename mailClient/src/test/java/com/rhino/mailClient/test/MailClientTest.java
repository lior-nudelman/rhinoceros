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
		mailClient.readAccount("rhino.test.email@gmail.com","ya29.AHES6ZSdMCCDOzG-fUre7n0p46WGZZzAULGleYNe58s-Gus", "C:/tmp/rhino/",new Date(System.currentTimeMillis()-(24L*60L*60L*1000L*365L)),"aaa");

		// IMAP host for yahoo.
		// store.connect("imap.mail.yahoo.com", "<username>", "<password>"); 

	}

}
