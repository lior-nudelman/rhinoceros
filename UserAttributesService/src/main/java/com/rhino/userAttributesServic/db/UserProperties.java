package com.rhino.userAttributesServic.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="USER_PROPERTIES")
public class UserProperties {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name="USER_ID")
	private String veriteID;
	
	@Column(name="TYPE")
	private String type;
	
	@Column(name="VALUE")
	private String value;
	
	@Column(name="UPDATE_DATE")
	private long date;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getVeriteID() {
		return veriteID;
	}

	public void setVeriteID(String veriteID) {
		this.veriteID = veriteID;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}

}
