package com.rhino.userAttributesServic.data;

import java.util.Map;

public interface UserDataInterface {
	public Map<UserAttributeType, String> getUserAttributes();
	
	public void setUserAttributes(Map<UserAttributeType,String> attributes);
	
	public Object getData();
	
	public void setData(Object data);
	
	public String getID();
	
	public void setID(String id);
}
