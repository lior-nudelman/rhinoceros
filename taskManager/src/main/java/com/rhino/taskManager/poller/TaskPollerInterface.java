package com.rhino.taskManager.poller;

import java.util.Collection;

import com.rhino.userAttributesServic.data.UserDataInterface;

public interface TaskPollerInterface {
	public abstract void process(Collection<UserDataInterface> job);
}
