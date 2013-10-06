package com.rhino.userAttributesServic;

import com.rhino.userAttributesServic.data.UserDataInterface;

public interface UserManagerInterface {

	public abstract boolean addUser(UserDataInterface user);

	public abstract UserDataInterface getUser(String id);

	public abstract UserDataInterface deleteUser(String id);

}