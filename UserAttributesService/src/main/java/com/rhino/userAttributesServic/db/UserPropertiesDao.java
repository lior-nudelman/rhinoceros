package com.rhino.userAttributesServic.db;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class UserPropertiesDao {
	private Session session;

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
		
	}

	public List<UserProperties> getByUserID(String userID){
		Criteria criteria = session.createCriteria(UserProperties.class);
		criteria.add(Restrictions.ilike("userID", userID));
		@SuppressWarnings("unchecked")
		List<UserProperties> result = criteria.list();
		return result;
	}

	public List<UserProperties> getByTypeAndValue(String type, String value){
		Criteria criteria = session.createCriteria(UserProperties.class);
		criteria.add(Restrictions.ilike("type", type));
		criteria.add(Restrictions.ilike("value", "%"+value+"%"));
		@SuppressWarnings("unchecked")
		List<UserProperties> result = criteria.list();
		return result;
	}

	public UserProperties getByTypeAndID(String type, String id){
		Criteria criteria = session.createCriteria(UserProperties.class);
		criteria.add(Restrictions.ilike("type", type));
		criteria.add(Restrictions.ilike("userID", id));
		@SuppressWarnings("unchecked")
		List<UserProperties> result = criteria.list();
		if(result==null || result.size() ==0){
			return null;
		}
		return result.get(0);
	}

	public Map<String ,UserProperties> getByID(String id){
		HashMap <String ,UserProperties> map = new HashMap<String ,UserProperties>();
		Criteria criteria = session.createCriteria(UserProperties.class);
		criteria.add(Restrictions.ilike("userID", id));
		@SuppressWarnings("unchecked")
		List<UserProperties> result = criteria.list();
		if(result==null || result.size() ==0){
			return map;
		}
		for(UserProperties user:result){
			map.put(user.getType(), user);
		}
		return map;
	}
	public void update(UserProperties userProperties){
		session.saveOrUpdate(userProperties);
	}
	
	public void save(UserProperties userProperties){
		session.save(userProperties);
	}

}
