package com.rhino.mailParser.test;

import static org.junit.Assert.*;


import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.rhino.mailParser.MailParser;

public class MailParserTest {

	@Test
	public void test() throws Exception {
		ApplicationContext appCtx = new ClassPathXmlApplicationContext("classpath:configs/mailParser.xml");
		MailParser parser = appCtx.getBean("mailParser",MailParser.class);

		parser.readAccount(null, "rhino.test.email@gmail.com", null, "C:/tmp/rhino/", null);
	
	}

}
