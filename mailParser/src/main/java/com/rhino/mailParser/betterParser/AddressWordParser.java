package com.rhino.mailParser.betterParser;

import org.apache.log4j.Logger;

import com.rhino.mailParser.data.UserData;

public class AddressWordParser implements WordParserInterface {

	private static Logger logger = Logger.getLogger(AddressWordParser.class);
	private String[] skipWords = null;
	private String[] acceptWords = null;

	boolean done = false;
	
	public AddressWordParser(String[] skipWords,String[] acceptWords){
		this.acceptWords = acceptWords;
		this.skipWords = skipWords;
	}

	public void parse(String word, UserData data) {
		if(done){
			return;
		}
		String tword = trim(word);
		String[] retStr =acceptWords;//{"amount","balance","total"};
		for(String str : retStr){
			int index = tword.indexOf(str);
			if(index>-1){
				tword = tword.substring(index+str.length(),tword.length());
				int x = tword.indexOf("/");
				if( x>0){
					tword = tword.substring(0,x);
				}
				logger.info("address = "+tword);
				data.setFrom(tword);
				done = true;
				return;
			}
		}		

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
