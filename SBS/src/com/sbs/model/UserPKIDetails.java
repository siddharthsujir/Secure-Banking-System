package com.sbs.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class UserPKIDetails {
	
	@Id
	private String userid;
	private String username;
	private String certpassword;
	private byte[] pubkey;
	
	
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getCertpassword() {
		return certpassword;
	}
	public void setCertpassword(String certpassword) {
		this.certpassword = certpassword;
	}
	public byte[] getPubkey() {
		return pubkey;
	}
	public void setPubkey(byte[] pubkey) {
		this.pubkey = pubkey;
	}
	

}
