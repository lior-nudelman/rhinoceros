package com.rhino.userAttributesServic;

import java.io.IOException;
import java.util.Collection;

import com.rhino.userAttributesServic.data.UserDataInterface;

public interface UserAttributesServiceManagerInterface {

	public abstract Collection<UserDataInterface> getNextJob(String sql, int size) throws IOException, ClassNotFoundException;
	public abstract void finishworkingOnJob(String type, Collection<UserDataInterface> job) throws IOException;

}
