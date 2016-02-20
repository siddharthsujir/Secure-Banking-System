package com.sbs.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table
public class AuthorizationRequest {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long requestno;
	private String employeename;
	private String customername;
	private int requeststatus;
	
	public String getEmployeename() {
		return employeename;
	}
	public void setEmployeename(String employeename) {
		this.employeename = employeename;
	}
	public String getCustomername() {
		return customername;
	}
	public void setCustomername(String customername) {
		this.customername = customername;
	}
	public int getRequeststatus() {
		return requeststatus;
	}
	public void setRequeststatus(int requeststatus) {
		this.requeststatus = requeststatus;
	}
	
	

}
