package com.sbs.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validate {
  
    public boolean validateUsername(String Username){
      boolean status=false;
      String user_pattern="^[a-zA-Z0-9]{3,}$";
      Pattern pattern=Pattern.compile(user_pattern);
		Matcher matcher=pattern.matcher(Username);
		if(matcher.matches()){
			status=true;
		}
		else{
			status=false;
		}
      return status;
    }
    public boolean validateAddress(String Username){
        boolean status=false;
        String user_pattern="^[a-zA-Z0-9#.,\\s\\s]{3,200}$";
        Pattern pattern=Pattern.compile(user_pattern);
  		Matcher matcher=pattern.matcher(Username);
  		if(matcher.matches()){
  			status=true;
  		}
  		else{
  			status=false;
  		}
        return status;
      }
    public boolean validateFirstname(String Fname){
        boolean status=false;
        String user_pattern="^[a-zA-Z]{2,}$";
        Pattern pattern=Pattern.compile(user_pattern);
  		Matcher matcher=pattern.matcher(Fname);
  		if(matcher.matches()){
  			status=true;
  		}
  		else{
  			status=false;
  		}
        return status;
      }
    public boolean validateLastname(String Lname){
        boolean status=false;
        String user_pattern="^[a-zA-Z]{2,}$";
        Pattern pattern=Pattern.compile(user_pattern);
  		Matcher matcher=pattern.matcher(Lname);
  		if(matcher.matches()){
  			status=true;
  		}
  		else{
  			status=false;
  		}
        return status;
      }
    public boolean validatePhno(String phno){
        boolean status=false;
        String user_pattern="^[0-9]{10}$";
        Pattern pattern=Pattern.compile(user_pattern);
  		Matcher matcher=pattern.matcher(phno);
  		if(matcher.matches()){
  			status=true;
  		}
  		else{
  			status=false;
  		}
        return status;
      }
	public boolean validateEmail(String email){
		boolean status=false;
        String Email_pattern="^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		Pattern pattern=Pattern.compile(Email_pattern);
		Matcher matcher=pattern.matcher(email);
		if(matcher.matches()){
			status=true;
		}
		else{
			status=false;
		}
        return status;
	}
	
	public boolean validateTransactionId(String phno){
        boolean status=false;
        String user_pattern="^[0-9]{10}$";
        Pattern pattern=Pattern.compile(user_pattern);
  		Matcher matcher=pattern.matcher(phno);
  		if(matcher.matches()){
  			status=true;
  		}
  		else{
  			status=false;
  		}
        return status;
      }
	
	public boolean validateRole(String role){
		if(role.equals(null)||role.equals(""))
			return false;
		if(role.equals("ROLE_USER")||role.equals("ROLE_MERCHANT"))
			return true;
		return false;
	}
	
	public boolean validateCorrectRole(String role){
		if(role.equals(null)||role.equals(""))
			return false;
		if(role.equals("Reg")||role.equals("sysmgr")||role.equals("admin"))
			return true;
		return false;
	}
	


}
