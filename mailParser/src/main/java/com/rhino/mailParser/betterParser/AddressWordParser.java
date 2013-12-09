package com.rhino.mailParser.betterParser;

import org.apache.log4j.Logger;

import com.rhino.mailParser.data.UserData;

public class AddressWordParser implements WordParserInterface {

	private static Logger logger = Logger.getLogger(AddressWordParser.class);
	private String[] acceptWords = null;

	boolean trigger = false;
	boolean done = false;
	int counter =0;
	public AddressWordParser(String[] skipWords,String[] acceptWords){
		this.acceptWords = acceptWords;
	}

	public void parse(String word, UserData data) {
		if(done){
			return;
		}
		if(counter ==1){
			String address = word;
			if(address.length()<8){
				counter=0;
				trigger = false;
				return;
			}
			data.setFrom(address);
			logger.info("address = "+word);
			done = true;
			return;
		}
		if(trigger){
			counter+=1;
			return;
		}
		String tword = trim(word);
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
