package com.sbs.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.Column;
@Entity
@Table (name = "accountinfo")
public class AccountInfo {
	
	@Column(name = "username")
	private String username;
	
	@Id
	@Column(name = "accountNumber")
	private int accountNumber;
	
	@Column(name = "accountType")
	private String accountType;
	
	@Column(name = "accountbalance")
	private double accountbalance;
	
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public double getAccountbalance() {
		return accountbalance;
	}

	public void setAccountbalance(double accountbalance) {
		this.accountbalance = accountbalance;
	}
	
}