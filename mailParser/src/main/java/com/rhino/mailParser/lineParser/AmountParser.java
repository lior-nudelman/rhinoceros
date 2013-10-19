package com.rhino.mailParser.lineParser;

import com.rhino.mailParser.data.UserData;


public class AmountParser implements LineParserInterface {

	String name = null;
	public AmountParser(String name){
		this.name = name;
	}
	
	public void parse(String line,UserData data) {
		int start = line.indexOf("$") + 1;
		int end = line.indexOf(" ", start);
		if (end < 0) {
			end = line.length();
		}
		String amount = line.substring(start, end).trim();
		if (amount.endsWith(".")){
			amount = amount.substring(0,amount.length()-1);
		}
		try{
			float f = Float.parseFloat(amount);
			data.setAmount(f);
		}catch(Exception e){
			
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
}
