package com.rhino.taskManager.poller;

import java.util.Map;

import com.rhino.userAttributesServic.data.UserAttributeType;

public interface JobProcessorInterface {
	public Object process(String mainVeriteID,Map<UserAttributeType, String> attributes,Object veriteData); 
}
