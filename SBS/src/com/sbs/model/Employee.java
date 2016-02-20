package com.sbs.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Employee {
	@Id
	  private String username;
	  private String fname;
	  private String lname;
	  private String desname;
	  private String address;
	  private String phno;
	  private String Email;
	  public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
	  public String getEmail() {
		 	return Email;
		}
		public void setEmail(String email) {
			Email = email;
		}
		public String getFname() {
			return fname;
		}
		public void setFname(String fname) {
			this.fname = fname;
		}
		public String getLname() {
			return lname;
		}
		public void setLname(String lname) {
			this.lname = lname;
		}
		public String getDesname() {
			return desname;
		}
		public void setDesname(String desname) {
			this.desname = desname;
		}
		public String getAddress() {
			return address;
		}
		public void setAddress(String address) {
			this.address = address;
		}
		public String getPhno() {
			return phno;
		}
		public void setPhno(String phno) {
			this.phno = phno;
		}

}
