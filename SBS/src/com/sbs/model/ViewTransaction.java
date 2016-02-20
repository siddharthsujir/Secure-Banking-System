package com.sbs.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.Column;
@Entity
@Table (name = "viewtransaction")
public class ViewTransaction {
	
	@Id
	@GeneratedValue
	@Column(name = "TransId")
	private int TransId;
	
	@Column(name = "TranType")
	private String type;
	
	@Column(name = "date")
	private long date;
	
	@Column(name = "acc_id")
	private int acc_id;
	
	@Column(name = "username")
	private String username;
	
	@Column(name = "amount")
	private double amount;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	public int getTransId() {
		return TransId;
	}
	
	public void setTransId(int TransId) {
		this.TransId = TransId;
	}
	public int getAccid() {
		return acc_id;
	}	
	public void setAccid(int acc_id) {
		this.acc_id = acc_id;
	}	
	public long getDate() {
		return date;
	}
	public void setDate(long date) {
		this.date = date;
	}

}

