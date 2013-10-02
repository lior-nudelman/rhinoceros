package com.rhino.userAttributesServic.data;

import java.util.HashMap;
import java.util.Map;

public class UserDataImpl implements UserDataInterface {

	private Map<UserAttributeType, String> attributes = new HashMap<UserAttributeType, String>();
	private Object data = null;
	private String id = null;
	
	public Map<UserAttributeType, String> getUserAttributes() {
		return attributes;
	}

	public void setUserAttributes(Map<UserAttributeType, String> attributes) {
		this.attributes = attributes;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getID() {
		return id;
	}

	public void setID(String id) {
		this.id = id;
	}

}
