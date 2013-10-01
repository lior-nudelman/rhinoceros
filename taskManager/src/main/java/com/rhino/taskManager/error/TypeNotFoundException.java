package com.rhino.taskManager.error;

public class TypeNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public TypeNotFoundException(String message){
		super(message);
	}

}