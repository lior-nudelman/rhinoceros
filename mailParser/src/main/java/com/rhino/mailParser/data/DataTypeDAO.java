package com.rhino.mailParser.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;

public class DataTypeDAO {
	private Session session;

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
		
	}

	public Map<String,String> getTypeMap(){
		HashMap<String, String> map = new HashMap<String, String>();
		Criteria criteria = session.createCriteria(DataType.class);
		@SuppressWarnings("unchecked")
		List<DataType> list = criteria.list();
		for(DataType dataType:list){
			map.put(dataType.getAddress(), dataType.getName());
		}
		return map;
	}
}
