package com.rhino.mailParser.test;


import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.rhino.mailParser.BetterMailParser;

public class MailParserTest {

	@Test
	public void test() throws Exception {
		@SuppressWarnings("resource")
		ApplicationContext appCtx = new ClassPathXmlApplicationContext("classpath:configs/mailParser.xml");
		//MailParser parser = appCtx.getBean("mailParser",MailParser.class);
		BetterMailParser parser = appCtx.getBean("betterMailParser",BetterMailParser.class);
		parser.readAccount(null, "rhino.test.email@gmail.com", null, "C:/tmp/rhino/", null,"aaa");
		//System.out.println(parser.split("Shipping:$13.99Tax:$37.38Total:$649.37"));
		//System.out.println(parser.split("hello"));
		//System.out.println(parser.split("Bill Amount:          $68.89<br>"));
		//System.out.println(parser.split("http://www.mass.gov/rmv/feedback/index.htm"));
	}

}
