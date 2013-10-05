package com.rhino.userAttributesServic.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="VERITE_STATUS")
public class UserStatus {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name="VERITE_ID")
	private String userID;
	
	@Column(name="DATE")
	private long date;
	
	@Column(name="ON_PROCESS")
	private boolean onProcess;
	
	@Column(name="DATA_SOURCE_TYPE")
	private String dataSourceType;

	@Column(name="DATA")
	private String data;

	@Column(name="WORKING_DATE")
	private long workingDate;

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}

	public boolean isOnProcess() {
		return onProcess;
	}

	public void setOnProcess(boolean onProcess) {
		this.onProcess = onProcess;
	}

	public String getDataSourceType() {
		return dataSourceType;
	}

	public void setDataSourceType(String dataSourceType) {
		this.dataSourceType = dataSourceType;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public long getWorkingDate() {
		return workingDate;
	}

	public void setWorkingDate(long workingDate) {
		this.workingDate = workingDate;
	}

}
