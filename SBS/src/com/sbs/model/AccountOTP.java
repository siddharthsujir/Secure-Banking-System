package com.sbs.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class AccountOTP {

	@Id
	private String otpid;
	private String username;
	private String password;
	private long expirytime;
	private String email;
	public String getOtpid() {
		return otpid;
	}
	public void setOtpid(String otpid) {
		this.otpid = otpid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public long getExpirytime() {
		return expirytime;
	}
	public void setExpirytime(long expirytime) {
		this.expirytime = expirytime;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
}
