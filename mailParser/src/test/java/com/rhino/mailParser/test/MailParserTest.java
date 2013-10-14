package com.rhino.mailParser.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.rhino.mailParser.MailParser;

public class MailParserTest {

	@Test
	public void test() throws Exception {
		MailParser parser = new MailParser();
		parser.readAccount(null, "rhino.test.email@gmail.com", null, "C:/tmp/rhino/", null);
	}

}
