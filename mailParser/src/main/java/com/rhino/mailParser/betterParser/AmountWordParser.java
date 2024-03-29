package com.rhino.mailParser.betterParser;

import org.apache.log4j.Logger;

import com.rhino.mailParser.data.UserData;

public class AmountWordParser implements WordParserInterface {

	private static Logger logger = Logger.getLogger(AmountWordParser.class);
	private String[] skipWords = null;
	private String[] acceptWords = null;
	
	boolean trigger = false;
	boolean done = false;
	
	public AmountWordParser(String[] skipWords,String[] acceptWords){
		this.acceptWords = acceptWords;
		this.skipWords = skipWords;
	}

	public void parse(String word, UserData data) {
		if(done){
			return;
		}
		String tword = trim(word);
		if(trigger ){
			String[] retStr =skipWords;//{"of","is","for"};
			for(String str : retStr){
				if(tword.toLowerCase().equals(str)){
					return;
				}
			}
		}
		if(trigger){
			try{
				String[] ss= tword.split(",");
				String x ="";
				for(String s:ss){
					x=x+s;
				}
				float f = Float.parseFloat(x);
				data.setAmount(f);
			}catch(Exception e){
				trigger = false;
				return;
			}

			logger.info("amount = "+tword);
			done = true;
			return;
		}
		tword = tword.toLowerCase();
		String[] retStr =acceptWords;//{"amount","balance","total"};
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
