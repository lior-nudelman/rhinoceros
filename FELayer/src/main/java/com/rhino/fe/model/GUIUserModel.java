package com.rhino.fe.model;

public class GUIUserModel {
	//textbox
	String userName;
	String address;
	String emailPassword;
	//password
	String password;
	String confirmPassword;
	
	
	
	//hidden value
	String secretValue;
	
	boolean firstTime = false;
	
	public String getSecretValue() {
		return secretValue;
	}
	public void setSecretValue(String secretValue) {
		this.secretValue = secretValue;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public boolean isFirstTime() {
		return firstTime;
	}
	public void setFirstTime(boolean firstTime) {
		this.firstTime = firstTime;
	}
	public String getEmailPassword() {
		return emailPassword;
	}
	public void setEmailPassword(String emailPassword) {
		this.emailPassword = emailPassword;
	}
	
	
}