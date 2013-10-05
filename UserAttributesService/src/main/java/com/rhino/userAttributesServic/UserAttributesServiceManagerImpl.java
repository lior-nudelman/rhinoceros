package com.rhino.userAttributesServic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

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

public class UserAttributesServiceManagerImpl implements
		UserAttributesServiceManagerInterface {

	private SessionFactory sessionFactory;
	
	public Collection<UserDataInterface> getNextJob(String sql, int size)
			throws IOException, ClassNotFoundException {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		tx.begin();
		UserStatusDao userStatusDAO = new UserStatusDao();
		userStatusDAO.setSession(session);
		UserPropertiesDao userPropertiesDAO = new UserPropertiesDao();
		userPropertiesDAO.setSession(session);
		List<UserStatus> users = userStatusDAO.getJob(sql, size);
		List<UserDataInterface> job = new ArrayList<UserDataInterface>(users.size());
		for (UserStatus user : users) {
			UserDataInterface data = new UserDataImpl();
			String userID = user.getUserID();
			data.setID(userID);
			Object vdata = userStatusDAO.getData(user.getData());
			data.setData(vdata);
			List<UserProperties> userPropertiesList = userPropertiesDAO.getByUserID(userID);
			HashMap<UserAttributeType, String> userPropertiesMap = new HashMap<UserAttributeType, String>();
			for(UserProperties v:userPropertiesList){
				userPropertiesMap.put(UserAttributeType.valueOfStringCode(v.getType()), v.getValue());
			}
			data.setUserAttributes(userPropertiesMap);
			job.add(data);
		}
		tx.commit();
		session.close();
		return job;
	}


	public void finishworkingOnJob(String type,
			Collection<UserDataInterface> job) throws IOException {
		long time = System.currentTimeMillis();
		Session session = sessionFactory.openSession();   
		Transaction tx = session.beginTransaction();
		tx.begin();
		UserStatusDao userStatusDAO = new UserStatusDao();
		userStatusDAO.setSession(session);
		userStatusDAO.updateJob(type, job,false,time);
		tx.commit();
		session.close();
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	
}
