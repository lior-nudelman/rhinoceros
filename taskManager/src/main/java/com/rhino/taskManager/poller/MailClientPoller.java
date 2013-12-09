package com.rhino.taskManager.poller;

import org.apache.log4j.Logger;

public class MailClientPoller extends AbstractPoller {

	private static Logger logger = Logger.getLogger(MailClientPoller.class);
	
	private final String type = "MAIL";
	@Override
	public String getType() {
		logger.debug("type is "+type);
		return type;
	}

}
