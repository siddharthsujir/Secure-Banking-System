package com.sbs.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class EmpReqUser {
	@Id
   String intuser;
   String extuser;
   int request;
public String getIntuser() {
	return intuser;
}
public void setIntuser(String intuser) {
	this.intuser = intuser;
}
public String getExtuser() {
	return extuser;
}
public void setExtuser(String extuser) {
	this.extuser = extuser;
}
public int getRequest() {
	return request;
}
public void setRequest(int request) {
	this.request = request;
}
   
}
