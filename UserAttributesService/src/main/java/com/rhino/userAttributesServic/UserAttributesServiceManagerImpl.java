package com.rhino.userAttributesServic;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;

import com.rhino.userAttributesServic.data.UserAttributeType;
import com.rhino.userAttributesServic.data.UserDataImpl;
import com.rhino.userAttributesServic.data.UserDataInterface;

public class UserAttributesServiceManagerImpl implements
		UserAttributesServiceManagerInterface {

	public Collection<UserDataInterface> getNextJob(String sql, int size)
			throws IOException, ClassNotFoundException {

		Collection<UserDataInterface> ret = new LinkedList<UserDataInterface>();
		UserDataInterface user = new UserDataImpl();
		ret.add(user);
		user.setID("rhino.test.email@gmail.com");
		Map<UserAttributeType, String> map = user.getUserAttributes();
		map.put(UserAttributeType.MAIL, "rhino.test.email@gmail.com");
		map.put(UserAttributeType.MAIL_PASSWORD, "mokavanil");
		map.put(UserAttributeType.MAIL_USERNAME, "rhino.test.email@gmail.com");
		map.put(UserAttributeType.FOLDER_PATH, "C:/tmp/rhino/");
		map.put(UserAttributeType.MAIL_HOST, "imap.gmail.com");
		map.put(UserAttributeType.DATE, ""+(System.currentTimeMillis()-(24*60*60*1000)));
		return ret;
	}

	public void finishworkingOnJob(String type,
			Collection<UserDataInterface> job) throws IOException {
		// TODO Auto-generated method stub

	}

}
