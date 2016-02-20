package com.sbs.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.sbs.service.PasswordService;

@Service
public class PasswordValidator {

	@Autowired
	PasswordService passwordService;
	
	@Transactional
	public boolean validateUsername(String username){
		
		
		if(username.equals(null)||username.isEmpty()||username.equals(""))
			return false;
		
		String user_pattern="^[a-zA-Z0-9]{3,}$";
		Pattern pattern=Pattern.compile(user_pattern);
		Matcher matcher=pattern.matcher(username);
		if(!matcher.matches())
			return false;
		
		return passwordService.verifyUser(username);
		//return false;
	}
	
	public boolean validateOTP(String otp){
		
		if(otp.equals(null)||otp.isEmpty()||otp.equals(""))
			return false;
		
		if(otp.length()!=8)
			return false;
		
		String user_pattern="^[a-zA-Z0-9]{3,}$";
		Pattern pattern=Pattern.compile(user_pattern);
		Matcher matcher=pattern.matcher(otp);
		if(!matcher.matches())
			return false;
		
		return true;
	}
	
	public boolean validatePassword(String password,String confirmPassword){
		
		if(password.equals(null)||password.isEmpty()||password.equals(""))
			return false;
		
		if(password.length()<=8)
			return false;
		
		if(confirmPassword.equals(null)||confirmPassword.isEmpty()||confirmPassword.equals(""))
			return false;
		
		if(confirmPassword.length()<=8)
			return false;
		
		String password_pattern="((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,20})";
		Pattern pattern=Pattern.compile(password_pattern);
		
		Matcher matcher=pattern.matcher(password);
		if(!matcher.matches())
			return false;
		
		matcher=pattern.matcher(confirmPassword);
		if(!matcher.matches())
			return false;
		
		if(!password.equals(confirmPassword))
			return false;
		
		
		
		return true;
	}
}