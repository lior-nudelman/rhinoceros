package com.rhino.taskManager;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import com.rhino.taskManager.agent.TaskAgentInterface;
import com.rhino.taskManager.error.TypeNotFoundException;
import com.rhino.userAttributesServic.data.UserDataInterface;

public class TaskManagerImpl implements TaskManagerInterface{
	
	private Map<String, TaskAgentInterface> agentMap ;

	public Collection<UserDataInterface> getNextJob(String type) throws TypeNotFoundException, IOException, ClassNotFoundException{
		TaskAgentInterface worker = agentMap.get(type);
		if(worker !=null){
			return worker.getNextJob();
		}
		Set<String> keys = agentMap.keySet();
		throw new TypeNotFoundException("The type "+type+" did not found in the workers list!!!("+keys+")");
	}

	public void finishworkingOnJob(String type,Collection<UserDataInterface> job) throws TypeNotFoundException, IOException{
		TaskAgentInterface worker = agentMap.get(type);
		if(worker !=null){
			worker.finishWorkingOnJob(job);
			return;
		}
		Set<String> keys = agentMap.keySet();
		throw new TypeNotFoundException("The type "+type+" did not found in the workers list!!!("+keys+")");
	}

	public Map<String, TaskAgentInterface> getAgentMap() {
		return agentMap;
	}

	public void setAgentMap(Map<String, TaskAgentInterface> workersMap) {
		this.agentMap = workersMap;
	}
	
}
