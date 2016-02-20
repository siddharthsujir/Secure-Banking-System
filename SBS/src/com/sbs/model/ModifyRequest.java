package com.sbs.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;

@Entity
@Table (name = "modifyrequest")
public class ModifyRequest {
	
	@Id
	@Column(name = "username")
	private String username;
	
	@Column(name = "request_status")
	private boolean requestStatus;
	
	
	
	
	public boolean isRequestStatus() {
		return requestStatus;
	}
	public void setRequestStatus(boolean requestStatus) {
		this.requestStatus = requestStatus;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	

}
