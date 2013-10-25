package com.rhino.mailParser;

import java.util.Date;


public interface MailParserInterface {

	public void readAccount(String host, String user, String password,String path, Date date,String sysUser) throws Exception;

}
