package com.rhino.mailParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Date;
import java.util.LinkedList;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.rhino.mailParser.betterParser.AddressWordParser;
import com.rhino.mailParser.betterParser.AmountWordParser;
import com.rhino.mailParser.betterParser.DateWordParser;
import com.rhino.mailParser.betterParser.WordParserInterface;
import com.rhino.mailParser.data.UserData;
import com.rhino.mailParser.data.UserDataDAO;

public class BetterMailParser  implements MailParserInterface{

	private static Logger logger = Logger.getLogger(BetterMailParser.class);
	private SessionFactory sessionFactory;
	
	public void readAccount(String host, String user, String password,
			String path, Date date) throws Exception {
		path = path + "/" + user + "/" + "Inbox";
		File file = new File(path); // need to be recursive
		if (!file.exists()) {
			logger.error("The path " + path + " do not exist");
			return;
		}
		if (!file.isDirectory()) {
			logger.error("The path " + path + " is not a folder");
			return;
		}

		File[] files = file.listFiles();
		if (files.length == 0) {
			logger.error("The path " + path + " contain 0 files");
			return;
		}
		
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		tx.begin();
		UserDataDAO userDataDAO = new UserDataDAO();
		userDataDAO.setSession(session);
		for (File f : files) {
			logger.info("File: " + f.getAbsolutePath());
			LinkedList<WordParserInterface> parsers = new LinkedList<WordParserInterface>();
			parsers.add(new AmountWordParser(new String[]{"of","is","for"},new String[]{"amount","balance","total"}));
			parsers.add(new AmountWordParser(new String[]{"is"},new String[]{"account"}));
			parsers.add(new AddressWordParser(null,new String[]{"http://","https://"}));
			parsers.add(new DateWordParser(null,new String[]{"date"}));
			
			UserData data = new UserData();
			data.setUserID(user);
			try {
				BufferedReader br = new BufferedReader(new FileReader(f));
				String line;
				while ((line = br.readLine()) != null) {
					line = line.replaceAll("<", " ");
					String[] words = line.split(" ");
					for(String word:words){
						word = word.trim();
						if(word.length()<2){
							continue;
						}
						for(WordParserInterface parser:parsers){
							parser.parse(word, data);
						}
					}
				}
			} catch (Exception e) {
				logger.error(e, e);
			}
			if(data.getAmount()>-1){
				userDataDAO.save(data);
			}
		}
		tx.commit();
		session.close();
		
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}


}
