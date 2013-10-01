package com.rhino.taskManager.agent;

import java.io.IOException;
import java.util.Collection;

import com.rhino.userAttributesServic.UserAttributesServiceManagerInterface;
import com.rhino.userAttributesServic.data.UserDataInterface;

public interface TaskAgentInterface {

	public Collection<UserDataInterface> getNextJob() throws IOException, ClassNotFoundException;
	public void finishWorkingOnJob(Collection<UserDataInterface> job) throws IOException;	
	public void setUserService(UserAttributesServiceManagerInterface userService);

}
