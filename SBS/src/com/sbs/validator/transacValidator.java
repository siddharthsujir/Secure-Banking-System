package com.sbs.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class transacValidator {

	public boolean validateTransactionId(long Tid){
	      boolean status=false;
	      String val=Tid+"";
	      System.out.println(val);
	      String user_pattern="^[0-9]{10}$";
	      Pattern pattern=Pattern.compile(user_pattern);
			Matcher matcher=pattern.matcher(val);
			if(matcher.matches()){
				status=true;
			}
			else{
				status=false;
			}
	      return status;
	    }
	public boolean validateTransactionStatus(String Status){
	      boolean status=false;
	      String user_pattern="^[a-zA-Z]{3,}$";
	      Pattern pattern=Pattern.compile(user_pattern);
			Matcher matcher=pattern.matcher(Status);
			if(matcher.matches()){
				status=true;
			}
			else{
				status=false;
			}
	      return status;
	    }
	public boolean validateFromAccount(int facont){
	      boolean status=false;
	      String val=facont+"";
	      String user_pattern="^[0-9]{4,11}$";
	      Pattern pattern=Pattern.compile(user_pattern);
			Matcher matcher=pattern.matcher(val);
			if(matcher.matches()){
				status=true;
			}
			else{
				status=false;
			}
	      return status;
	    }
	public boolean validateToAccount(int facont){
	      boolean status=false;
	      String val=facont+"";
	      String user_pattern="^[0-9]{4,11}$";
	      Pattern pattern=Pattern.compile(user_pattern);
			Matcher matcher=pattern.matcher(val);
			if(matcher.matches()){
				status=true;
			}
			else{
				status=false;
			}
	      return status;
	    }
	public boolean validateAmount(double facont){
	      boolean status=false;
	      String val=facont+"";
	      String user_pattern="[0-9]{1,10}(\\.[0-9]*)?";
	      Pattern pattern=Pattern.compile(user_pattern);
			Matcher matcher=pattern.matcher(val);
			if(matcher.matches()){
				status=true;
			}
			else{
				status=false;
			}
	      return status;
	}
	public boolean validateDiscription(String Desc){
	      boolean status=false;
	     
	      String user_pattern="^[a-zA-Z]{3,25}$";
	      Pattern pattern=Pattern.compile(user_pattern);
			Matcher matcher=pattern.matcher(Desc);
			if(matcher.matches()){
				status=true;
			}
			else{
				status=false;
			}
	      return status;
	}
}
