package com.rhino.mailParser.data;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;



public class UserDataDAO {
	private Session session;

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
		
	}

	public List<UserData> getByUserID(String userID){
		Criteria criteria = session.createCriteria(UserData.class);
		criteria.add(Restrictions.ilike("userID", userID));
		@SuppressWarnings("unchecked")
		List<UserData> result = criteria.list();
		return result;
	}

	public void update(UserData userData){
		session.saveOrUpdate(userData);
	}
	
	public void save(UserData userData){
		session.save(userData);
	}

}
