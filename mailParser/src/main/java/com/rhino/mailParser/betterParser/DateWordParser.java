package com.rhino.mailParser.betterParser;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.log4j.Logger;

import com.rhino.mailParser.data.UserData;

public class DateWordParser implements WordParserInterface {

	private static Logger logger = Logger.getLogger(DateWordParser.class);
	private String[] skipWords = null;
	private String[] acceptWords = null;
	private String buffer = "";
	private int counter =0;
	boolean trigger = false;
	boolean done = false;
	private static SimpleDateFormat  format = new SimpleDateFormat("EEEEEEEEEEE MMMMMMMMMMMM dd yyyy",Locale.US);
	
	public DateWordParser(String[] skipWords,String[] acceptWords){
		this.acceptWords = acceptWords;
		this.skipWords = skipWords;
	}

	public void parse(String word, UserData data) {
		if(done){
			return;
		}
		String tword = trim(word);
		if(trigger && skipWords != null){
			String[] retStr =skipWords;
			for(String str : retStr){
				if(tword.toLowerCase().equals(str)){
					return;
				}
			}
		}
		if(trigger){
			buffer = buffer + tword+" ";
			
			counter++;
			if(counter ==4){
				try{
					buffer = buffer.trim();
					Date date = format.parse(buffer);
					data.setDate(date.getTime());
				}catch(Exception e){
					trigger = false;
					buffer ="";
					counter =0;
					return;
				}
				
				logger.info("Date = "+buffer);
				done = true;
			}
			return;
		}
		tword = tword.toLowerCase();
		String[] retStr =acceptWords;
		for(String str : retStr){
			if(tword.equals(str)){
				trigger = true;
				return;
			}
		}
		trigger = false;
	}

    public String trim(String s) {
    	int count = s.length();
        int len = count;
        int st = 0;
        
        byte[] val = s.getBytes();    /* avoid getfield opcode */

        while ((st < len) && (! (val[ st] >= 48 && val[ st] <= 57) ) && (! (val[ st] >= 65 && val[ st] <= 127))) {
            st++;
        }
        while ((st < len) && (! (val[ len-1] >= 48 && val[ len-1] <= 57) ) && (! (val[ len-1] >= 65 && val[ len-1] <= 127))) {
            len--;
        }
        return ((st > 0) || (len < count)) ? s.substring(st, len) : s;
    }
}
