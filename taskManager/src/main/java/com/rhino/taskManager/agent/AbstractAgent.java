package com.rhino.taskManager.agent;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;

import org.apache.log4j.Logger;

import com.rhino.taskManager.util.TimeFrameCounter;
import com.rhino.userAttributesServic.UserAttributesServiceManagerInterface;
import com.rhino.userAttributesServic.data.UserDataInterface;

public abstract class AbstractAgent implements TaskAgentInterface {

	private static Logger logger = Logger.getLogger(AbstractAgent.class);

	private UserAttributesServiceManagerInterface userService;
	private int totalSize;
	private int accessSize;
	private long timeFrame;
	private TimeFrameCounter limmiter;

	public void init() {
		limmiter = new TimeFrameCounter(getTimeFrame());
	}

	public void setUserService(
			UserAttributesServiceManagerInterface veriteService) {
		this.userService = veriteService;
	}

	public synchronized Collection<UserDataInterface> getNextJob()
			throws IOException, ClassNotFoundException {
		if (limmiter.getValue() < getTotalSize()) {
			String sql = getSQL();
			Collection<UserDataInterface> job = null;
			if (sql.length() < 1) {
				job = new LinkedList<UserDataInterface>();
				logger.info("provide job to type " + getType() + " job size="
						+ job.size());
				limmiter.add(1);
				return job;
			}
			job = userService.getNextJob(sql, getAccessSize());
			if (job == null) {
				logger.error("Recived a null job isted of a valid one for type "
						+ getType());
				return null;
			}
			logger.info("provide job to type " + getType() + " job size="
					+ job.size());
			limmiter.add(job.size());
			return job;
		}
		logger.info("nothing to provide for type " + getType()
				+ " need to wait ");
		return null;
	}

	public synchronized void finishWorkingOnJob(
			Collection<UserDataInterface> job) throws IOException {
		String sql = getSQL();
		if (sql == null || sql.length() < 1) {
			return; // no need to update if there is no SQL
		}
		userService.finishworkingOnJob(getType(), job);
	}

	public int getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}

	public int getAccessSize() {
		return accessSize;
	}

	public void setAccessSize(int accessSize) {
		this.accessSize = accessSize;
	}

	public long getTimeFrame() {
		return timeFrame;
	}

	public void setTimeFrame(long timeFrame) {
		this.timeFrame = timeFrame;
	}

	public abstract String getType();

	public abstract String getSQL();
}
