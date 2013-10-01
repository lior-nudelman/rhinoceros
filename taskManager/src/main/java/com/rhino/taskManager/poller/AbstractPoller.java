package com.rhino.taskManager.poller;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;

import org.apache.log4j.Logger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.rhino.taskManager.TaskManagerInterface;
import com.rhino.taskManager.error.TypeNotFoundException;
import com.rhino.userAttributesServic.data.UserDataInterface;

public abstract class AbstractPoller implements TaskPollerInterface{

	private static Logger logger = Logger.getLogger(AbstractPoller.class);

	private TaskManagerInterface taskManager;

	private Poller poller = null;
	private boolean running = true;
	private long waitingTimeIfNoData = 10000L;
	private JobProcessorInterface processor;
	private ThreadPoolTaskExecutor taskExecutor;
    private int corePoolSize;
    private int maxPoolSize;
    
	public void init() {
		taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(getCorePoolSize());
		taskExecutor.setMaxPoolSize(getMaxPoolSize());
		taskExecutor.setQueueCapacity(0);
		taskExecutor.setRejectedExecutionHandler(new java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy());
		taskExecutor.afterPropertiesSet();
		
		poller = new Poller();
		new Thread(poller).start();
	}

	public void process(Collection<UserDataInterface> job){
		for(UserDataInterface worker:job){
		    taskExecutor.execute(new  Process(worker));
		}
	}

	public class Process implements Runnable{

	    UserDataInterface worker;
	    public Process(UserDataInterface worker){
	    	this.worker = worker;
	    }
	    
	    public void run(){
		String userID = worker.getID();
		try{
			Object data =getProcessor().process(userID,worker.getUserAttributes(),worker.getData());
			worker.setData(data);
			Collection<UserDataInterface> job = new LinkedList<UserDataInterface>();
			job.add(worker);

			taskManager.finishworkingOnJob(getType(), job);
		}catch(Exception e){
		    logger.error("ERROR for verite ID "+userID);
		    logger.error(e,e);
		}
	    }
	    
	}
	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public long getWaitingTimeIfNoData() {
		return waitingTimeIfNoData;
	}

	public void setWaitingTimeIfNoData(long waitingTimeIfNoData) {
		this.waitingTimeIfNoData = waitingTimeIfNoData;
	}

	public JobProcessorInterface getProcessor() {
		return processor;
	}

	public void setProcessor(JobProcessorInterface processor) {
		this.processor = processor;
	}

	
	public int getCorePoolSize() {
		return corePoolSize;
	}


	public void setCorePoolSize(int corePoolSize) {
		this.corePoolSize = corePoolSize;
	}


	public int getMaxPoolSize() {
		return maxPoolSize;
	}


	public void setMaxPoolSize(int maxPoolSize) {
		this.maxPoolSize = maxPoolSize;
	}


	class Poller implements Runnable {
		public void run() {
			logger.info("Start working on " + getType());
			while (running) {
				try {
					while (running) {
						try {
							Thread.sleep(waitingTimeIfNoData);
						} catch (InterruptedException e) {
							logger.error(e, e);
						}

						Collection<UserDataInterface> job= null;
						try{
						    job = taskManager.getNextJob(getType());
						}
						catch(Error e){
						    logger.error(e,e);
						}
						if (job == null || job.size() == 0) {
							logger.info("No data to " + getType()
									+ " process going to wait "
									+ waitingTimeIfNoData + " mil");
						} else {
							try{
								process(job);
							}
							catch(Error e){
								logger.error(e,e);
								logger.error("problam calling to "+getType());
							}
						}
					}
				} catch (TypeNotFoundException e) {
					logger.error(e, e);
				} catch (IOException e) {
					logger.error(e, e);
				} catch (ClassNotFoundException e) {
					logger.error(e, e);
				}
			}
		}
	}

	public abstract String getType();
}
