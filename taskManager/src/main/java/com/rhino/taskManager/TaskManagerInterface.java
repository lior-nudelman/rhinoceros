package com.rhino.taskManager;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import com.rhino.taskManager.agent.TaskAgentInterface;
import com.rhino.taskManager.error.TypeNotFoundException;
import com.rhino.userAttributesServic.data.UserDataInterface;

public interface TaskManagerInterface {
	
	public Collection<UserDataInterface> getNextJob(String type) throws TypeNotFoundException, IOException, ClassNotFoundException;

	public void finishworkingOnJob(String type,Collection<UserDataInterface> job) throws TypeNotFoundException, IOException;

	public Map<String, TaskAgentInterface> getAgentMap() ;

	public void setAgentMap(Map<String, TaskAgentInterface> workersMap) ;

}
