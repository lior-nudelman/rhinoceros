package com.rhino.mailParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Date;
import java.util.LinkedList;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.rhino.mailParser.betterParser.AddressWordParser;
import com.rhino.mailParser.betterParser.AmountWordParser;
import com.rhino.mailParser.betterParser.AmountWordParserMustHaveSkipWord;
import com.rhino.mailParser.betterParser.DateWordParser;
import com.rhino.mailParser.betterParser.WordParserInterface;
import com.rhino.mailParser.data.DataTypeDAO;
import com.rhino.mailParser.data.UserData;
import com.rhino.mailParser.data.UserDataDAO;

public class BetterMailParser  implements MailParserInterface{

	private static Logger logger = Logger.getLogger(BetterMailParser.class);
	private SessionFactory sessionFactory;
	private static Map<String,String> dataType = null;
	private Object SYNC_OBJ = new Object();
	
	//The user is the email address
	public void readAccount(String host, String user, String password,
			String path, Date date,String sysUser) throws Exception {
		readMap();
		path = path + "/" + sysUser +"/"+ user + "/" + "Inbox";
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
		UserDataDAO userDataDAO = new UserDataDAO();
		userDataDAO.setSession(session);
		Transaction tx = session.beginTransaction();
		tx.begin();

		for (File f : files) {
			if(!f.getName().endsWith("txt")){
				logger.info("cannot parse file "+f.getName());
				continue;
			}
			logger.info("File: " + f.getAbsolutePath());
			LinkedList<WordParserInterface> parsers = new LinkedList<WordParserInterface>();
			parsers.add(new AmountWordParser(new String[]{"of","is","for","us"},new String[]{"amount","balance","total","payment"}));
			parsers.add(new AmountWordParserMustHaveSkipWord(new String[]{"is"},new String[]{"account"}));
			parsers.add(new AddressWordParser(null,new String[]{"http","https"}));
			parsers.add(new DateWordParser(null,new String[]{"date"},"EEEEEEEEEEE MMMMMMMMMMMM dd yyyy",4));
			parsers.add(new DateWordParser(null,new String[]{"date"},"MMMMMMMMMMMM dd yyyy",3));
			UserData data = new UserData();
			data.setUserID(sysUser);
			try {
				BufferedReader br = new BufferedReader(new FileReader(f));
				String subject = br.readLine();
				String from = br.readLine();
				String messageId = br.readLine();
				data.setMessageFrom(from);
				data.setMessageTitle(subject);
				data.setMessageIndex(Integer.parseInt(messageId));
				String line;
				while ((line = br.readLine()) != null) {
					line = line.replaceAll("<", " ");
					String[] words = line.split(" ");
					for(String word:words){
						word = word.trim();
						if(word.length()<2){
							continue;
						}
						LinkedList<String> splitWords = split(word);
						for(String splitWord:splitWords){
							for(WordParserInterface parser:parsers){
								parser.parse(splitWord, data);
							}
						}
					}
				}
				br.close();
			} catch (Exception e) {
				logger.error(e, e);
			}
			if(data.getAmount()>-1){
				try{
					String type = dataType.get(data.getFrom());
					data.setType(type);
					if(data.getFrom().equals("NULL")){
						data.setFrom(data.getMessageFrom());
					}
					userDataDAO.save(data);
				}catch(Exception e){
					logger.error(e,e);
				}
			}
		}
		tx.commit();
		session.close();
	}

	public LinkedList<String> split(String word){
		char c[] = word.toCharArray();
		int x=0;
		LinkedList<String> tmp = new LinkedList<String>();
		for(int i=0;i<c.length-1;i++){
			if(c[i]=='.' || c[i+1] =='.'){
				continue;
			}
			if((c[i]>='!' && c[i]<'A' && c[i+1]>='A' && c[i+1]<='z') || (c[i]>='A' && c[i]<='z' && c[i+1]>='!' && c[i+1]<'A')){
				String s = word.substring(x, i+1);
				if(s.length()>1){
					tmp.add(s);
				}
				x=i+1;
			}
		}
		if(x>0){
			String s = word.substring(x, word.length());
			if(s.length()>1){
				tmp.add(s);
			}
		}else{
			tmp.add(word);
		}
		return tmp;
	}
	
	private void readMap(){
		if (dataType !=null){
			return;
		}
		synchronized (SYNC_OBJ) {
			if (dataType !=null){
				return;
			}
			Session session = sessionFactory.openSession();
			DataTypeDAO userDataDAO = new DataTypeDAO();
			userDataDAO.setSession(session);
			Transaction tx = session.beginTransaction();
			tx.begin();
			dataType = userDataDAO.getTypeMap();
			tx.commit();
			session.close();
		}
	}
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}


}
