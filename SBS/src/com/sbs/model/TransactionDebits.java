package com.sbs.model;



import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import java.util.Date;

import javax.persistence.Column;
@Entity
@Table (name = "transactiondebits")
public class TransactionDebits {
	
	@Id
	
	@Column(name = "transactionId")
	private long transaction_id;
	
	
	
	public long getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(long transaction_id) {
		this.transaction_id = transaction_id;
	}

	

	public int getToAccount() {
		return toAccount;
	}

	public void setToAccount(int toAccount) {
		this.toAccount = toAccount;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	

	

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getTransactionStatus() {
		return transactionStatus;
	}

	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

	@Column(name = "toAccountNumber")
	private int toAccount;
	
	@Column(name = "amount")
	private double amount;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "transactiontime", columnDefinition="DATETIME", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date transactiontime;
	


	public Date getTransactiontime() {
		return transactiontime;
	}

	public void setTransactiontime(Date transactiontime) {
		this.transactiontime = transactiontime;
	}

	@Column(name = "transactiontype")
	private String transactionType;
	
	@Column(name = "transactionstatus")
	private String transactionStatus;
	
	
	
}