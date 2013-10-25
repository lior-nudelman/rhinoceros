package com.rhino.userAttributesServic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.rhino.userAttributesServic.data.UserAttributeType;
import com.rhino.userAttributesServic.data.UserDataImpl;
import com.rhino.userAttributesServic.data.UserDataInterface;
import com.rhino.userAttributesServic.db.UserProperties;
import com.rhino.userAttributesServic.db.UserPropertiesDao;
import com.rhino.userAttributesServic.db.UserStatus;
import com.rhino.userAttributesServic.db.UserStatusDao;

public class UserManagerImpl implements UserManagerInterface {

	private SessionFactory sessionFactory;
	private static String[] types = { "MAIL", "PARSER" };

	/* (non-Javadoc)
	 * @see com.rhino.userAttributesServic.UserManagerInterface#addUser(com.rhino.userAttributesServic.data.UserDataInterface)
	 */
	public boolean addUser(UserDataInterface user) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		tx.begin();
		long date = System.currentTimeMillis();
		String userID = user.getID();
		Map<UserAttributeType, String> attributes = user.getUserAttributes();
		if (attributes != null) {
			UserPropertiesDao userPropertiesDAO = new UserPropertiesDao();
			userPropertiesDAO.setSession(session);
			Set<UserAttributeType> keys = attributes.keySet();

			for (UserAttributeType key : keys) {
				UserProperties userProperties = new UserProperties();
				userProperties.setDate(date);
				userProperties.setValue(attributes.get(key));
				userProperties.setType(key.getStringCode());
				userProperties.setUserID(userID);
				userPropertiesDAO.save(userProperties);
			}
		}
		UserStatusDao userStatusDAO = new UserStatusDao();
		userStatusDAO.setSession(session);
		for (String type : types) {
			UserStatus userStatus = new UserStatus();
			userStatus.setDataSourceType(type);
			userStatus.setDate(0);
			userStatus.setOnProcess(false);
			userStatus.setUserID(userID);
			userStatus.setWorkingDate(0);
			userStatusDAO.save(userStatus);
		}
		tx.commit();
		session.close();
		return true;
	}

	/* (non-Javadoc)
	 * @see com.rhino.userAttributesServic.UserManagerInterface#getUser()
	 */
	public UserDataInterface getUser(String id) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		tx.begin();

		UserDataInterface user = new UserDataImpl();
		user.setID(id);
		UserPropertiesDao userPropertiesDAO = new UserPropertiesDao();
		userPropertiesDAO.setSession(session);
		List<UserProperties> userPropertiesList = userPropertiesDAO.getByUserID(id);
		HashMap<UserAttributeType, String> userPropertiesMap = new HashMap<UserAttributeType, String>();
		if(userPropertiesList.size() ==0){
			return null;
		}
		for(UserProperties v:userPropertiesList){
			userPropertiesMap.put(UserAttributeType.valueOfStringCode(v.getType()), v.getValue());
		}
		user.setUserAttributes(userPropertiesMap);
		
		tx.commit();
		session.close();

		return user;
	}

	/* (non-Javadoc)
	 * @see com.rhino.userAttributesServic.UserManagerInterface#deleteUser(com.rhino.userAttributesServic.data.UserDataInterface)
	 */
	public UserDataInterface deleteUser(String id) {
		return null;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
