package com.sbs.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Entity;
import javax.persistence.Table;



@Entity
@IdClass(ExtPK.class)
@Table (name = "extpayrequest")
public class ExtPayRequest {
	@Id
	@Column(name = "merchant")
	private String merchant;
	
	@Column(name = "requeststatus")
	private int requestStatus;
	@Id
	@Column(name = "user")
	private String username;
	
	public String getMerchant() {
		return merchant;
	}
	public void setMerchant(String merchant) {
		this.merchant= merchant;
	}
	public int getRequestStatus() {
		return requestStatus;
	}
	public void setRequestStatus(int requestStatus) {
		this.requestStatus = requestStatus;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	

}

