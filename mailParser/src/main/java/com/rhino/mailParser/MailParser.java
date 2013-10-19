package com.rhino.mailParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.rhino.mailParser.data.UserData;
import com.rhino.mailParser.data.UserDataDAO;
import com.rhino.mailParser.lineParser.AddressParser;
import com.rhino.mailParser.lineParser.AmountParser;
import com.rhino.mailParser.lineParser.DateParser;
import com.rhino.mailParser.lineParser.LineParserInterface;

public class MailParser implements MailParserInterface {
	private static Logger logger = Logger.getLogger(MailParser.class);

	private static HashMap<String, LineParserInterface> parsers = new HashMap<String, LineParserInterface>();
	private static Set<String> parsersKeys = null;
	private SessionFactory sessionFactory;
	
	static {
		AmountParser amountParser = new AmountParser("Amount");
		parsers.put("Amount", amountParser);
		parsers.put("amount", amountParser);
		parsers.put("balance is", amountParser);

		DateParser dateParser = new DateParser("Date");
		parsers.put("Date:", dateParser);
		parsers.put("date:", dateParser);

		AddressParser httpParser = new AddressParser("http://");
		parsers.put("http", httpParser);
		AddressParser httpsParser = new AddressParser("https://");
		parsers.put("https", httpsParser);
		
		parsersKeys = parsers.keySet();
	}

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
			UserData data = new UserData();
			data.setUserID(user);
			try {
				BufferedReader br = new BufferedReader(new FileReader(f));
				String line;
				while ((line = br.readLine()) != null) {
					for (String word : parsersKeys) {
						if (line.contains(word)) {
							LineParserInterface parser = parsers.get(word);
							parser.parse(line,data);							
						}
					}
				}
				br.close();
				if(data.getAmount()>0){
					userDataDAO.save(data);
				}
			} catch (Exception e) {
				logger.error(e, e);
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