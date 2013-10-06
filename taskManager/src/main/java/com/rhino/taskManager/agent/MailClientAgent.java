package com.rhino.taskManager.agent;


public class MailClientAgent extends AbstractAgent {


	@Override
	public String getType() {
		return "MAIL";
	}

	@Override
	public String getSQL() {
		return "select v from UserStatus v where v.dataSourceType = 'MAIL' and v.onProcess = false order by v.date";
	}

}
