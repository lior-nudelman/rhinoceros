package com.rhino.taskManager.util;

import java.util.LinkedList;

public class TimeFrameCounter {
	private long timeFrame;
	LinkedList<Pair> container = new LinkedList<Pair>(); 
	public TimeFrameCounter(long timeFrame){
		this.timeFrame = timeFrame;
	}
	
	public void add(int value){
		container.addLast(new Pair(value,System.currentTimeMillis()));
		clean();
	}
	
	public int getValue(){
		clean();
		int acc=0;
		for(Pair pair:container){
			acc+=pair.value;
		}
		return acc;
	}
	
	private void clean(){
		if(container.isEmpty()){
			return;
		}
		Pair pair = null;
		do{
			
			pair = container.getFirst();
			if(System.currentTimeMillis()-pair.date>timeFrame){
				pair = container.removeFirst();
			}
			else{
				pair = null;
			}
		}while(pair !=null && !container.isEmpty());
	}
	
	class Pair{
		public int value;
		public long date;
		public Pair(int value,long date){
			this.date = date;
			this.value = value;
		}
	}
}
