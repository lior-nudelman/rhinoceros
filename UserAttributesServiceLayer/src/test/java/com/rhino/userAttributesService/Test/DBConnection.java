package com.rhino.userAttributesService.Test;


import java.io.IOException;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.rhino.userAttributesServic.UserAttributesServiceManagerImpl;
import com.rhino.userAttributesServic.data.UserDataInterface;

public class DBConnection {

	private static Logger logger = Logger.getLogger(DBConnection.class);
	
	@Test
	public void test() throws ClassNotFoundException, IOException {
		
		String sql = "select v from UserStatus v where v.dataSourceType = 'MAIL' and v.onProcess = false order by v.date";
		//String sql = "select v from UserStatus v , UserStatus v1 where v.dataSourceType='PARSER' and v1.dataSourceType='MAIL' and v.userID=v1.userID and v.date>v1.date and v1.onProcess=false order by v.date";

		@SuppressWarnings("resource")
		ApplicationContext appCtx = new ClassPathXmlApplicationContext("classpath:configs/userAttributesServiceLayer.xml");
		UserAttributesServiceManagerImpl service = appCtx.getBean("userAttributesServiceManager",UserAttributesServiceManagerImpl.class);
		Collection<UserDataInterface> job = service.getNextJob(sql, 10);
		logger.info("job size="+job.size());
	}

}
