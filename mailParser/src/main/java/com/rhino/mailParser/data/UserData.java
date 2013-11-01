package com.rhino.mailParser.data;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author hagar
 *
 */
@Entity
@Table(name="USER_DATA")
public class UserData {
	private static SimpleDateFormat  format = new SimpleDateFormat("MM/dd/yyyy");
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(name="USER_ID")
	String userID = null;
	@Column(name="TRANSACTION_FROM")
	String from = null;
	@Column(name="AMOUNT")
	float amount=-1;
	@Column(name="TRANSACTION_DATE")
	long date=-1;
	@Column(name="TRANSACTION_TYPE")
	String type = null;
	@Column(name="MESSAGE_INDEX")
	int messageIndex = 0;
	@Column(name="MESSAGE_TITLE")
	String messageTitle = null;
	@Column(name="MESSAGE_FROM")
	String messageFrom = null;	
	
	@Transient
	int duplicationCounter = 1;
	
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		if(this.amount>0){
			return;
		}
		this.amount = amount;
	}
	public long getDate() {
		return date;
	}
	public void setDate(long date) {
		if(this.date>0){
			return;
		}
		this.date = date;
	}
	public String getFrom() {
		if (from == null){
			return "NULL";
		}
		return from;
	}
	public void setFrom(String from) {
		if(this.from == null){
			this.from = from;
		}
	}	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public int getMessageIndex() {
		return messageIndex;
	}
	public void setMessageIndex(int messageIndex) {
		this.messageIndex = messageIndex;
	}
	public String getMessageTitle() {
		return messageTitle;
	}
	public void setMessageTitle(String messageTitle) {
		this.messageTitle = messageTitle;
	}
	
	public String getMessageFrom() {
		return messageFrom;
	}
	public void setMessageFrom(String messageFrom) {
		this.messageFrom = messageFrom;
	}
	@Override
	public String toString() {
		return "ParserData [userID=" + userID + ", from=" + from + ", amount="
				+ amount + ", date=" + date + "]";
	}	
	
	public String getFormatedDate(){
		return format.format(new Date(getDate()));
	}
	public int getDuplicationCounter() {
		return duplicationCounter;
	}
	public void setDuplicationCounter(int duplicationCounter) {
		this.duplicationCounter = duplicationCounter;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(amount);
		result = prime * result + (int) (date ^ (date >>> 32));
		result = prime * result + ((from == null) ? 0 : from.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserData other = (UserData) obj;
		if (Float.floatToIntBits(amount) != Float.floatToIntBits(other.amount))
			return false;
		if (date != other.date)
			return false;
		if (from == null) {
			if (other.from != null)
				return false;
		} else if (!from.equals(other.from))
			return false;
		return true;
	}
	
	
}
