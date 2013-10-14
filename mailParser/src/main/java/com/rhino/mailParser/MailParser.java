package com.rhino.mailParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

import org.apache.log4j.Logger;

import com.rhino.mailParser.lineParser.AmountParser;
import com.rhino.mailParser.lineParser.LineParserInterface;

public class MailParser implements MailParserInterface{
	private static Logger logger = Logger.getLogger(MailParser.class);
	
	private static HashMap<String, LineParserInterface> parsers = new HashMap<String, LineParserInterface>();
	private static Set<String> parsersKeys = null;

	static{
		AmountParser amountParser = new AmountParser();
		parsers.put("Amount", amountParser);
		parsers.put("amount", amountParser);
		parsers.put("balance is", amountParser);
		parsersKeys = parsers.keySet();
	}
	
	public void readAccount(String host, String user, String password,String path, Date date) throws Exception{
		path = path+"/"+user+"/"+"Inbox";
		File file = new File(path); //need to be recursive
		if(!file.exists()){
			logger.error("The path "+path+" do not exist");
			return;
		}
		if(!file.isDirectory()){
			logger.error("The path "+path+" is not a folder");
			return;
		}
		
		File[] files = file.listFiles();
		if(files.length ==0){
			logger.error("The path "+path+" contain 0 files");
			return;
		}
		
		for(File f:files){
			logger.info("File: "+f.getAbsolutePath());
			try{
			BufferedReader br = new BufferedReader(new FileReader(f));
			String line;
			while ((line = br.readLine()) != null) {
			   for(String word:parsersKeys){
				   if(line.contains(word)){
					   LineParserInterface parser = parsers.get(word);
					   Object value = parser.parse(line);
					   logger.info(word+"="+value);
				   }
			   }
			}
			br.close();
			}catch(Exception e){
				logger.error(e,e);
			}
		}
	}

}
