package com.rhino.mailParser.lineParser;


public class AmountParser implements LineParserInterface {

	public Object parse(String line) {
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
			Float.parseFloat(amount);
		}catch(Exception e){
			return null;
		}
		return amount;
	}

}
