package com.rhino.mailParser.lineParser;

import com.rhino.mailParser.data.UserData;

public interface LineParserInterface {
	public void parse(String line,UserData data);
	public String getName();
}
