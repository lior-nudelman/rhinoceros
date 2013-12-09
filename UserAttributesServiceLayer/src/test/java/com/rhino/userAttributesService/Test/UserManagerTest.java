package com.rhino.userAttributesService.Test;


import java.util.Map;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.rhino.userAttributesServic.UserManagerImpl;
import com.rhino.userAttributesServic.UserManagerInterface;
import com.rhino.userAttributesServic.data.UserAttributeType;
import com.rhino.userAttributesServic.data.UserDataImpl;

public class UserManagerTest {

	@Test
	public void test() {
		@SuppressWarnings("resource")
		ApplicationContext appCtx = new ClassPathXmlApplicationContext("classpath:configs/userAttributesServiceLayer.xml");
		UserManagerInterface manager = appCtx.getBean("userManager",UserManagerImpl.class);
		UserDataImpl user = new UserDataImpl();
		user.setID("rhino.test.email@gmail.com");
		Map<UserAttributeType, String> map = user.getUserAttributes();
		map.put(UserAttributeType.MAIL, "rhino.test.email@gmail.com");
		map.put(UserAttributeType.MAIL_PASSWORD, "mokavanil");
		map.put(UserAttributeType.MAIL_USERNAME, "rhino.test.email@gmail.com");
		map.put(UserAttributeType.FOLDER_PATH, "C:/tmp/rhino/");
		map.put(UserAttributeType.MAIL_HOST, "imap.gmail.com");
		map.put(UserAttributeType.DATE, ""+(System.currentTimeMillis()-(24*60*60*1000)));
		manager.addUser(user);
	}

}
