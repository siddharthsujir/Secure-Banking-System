package com.sbs.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;

import com.sbs.service.TransactionService;

public class TransactionValidator {
  
	@Autowired
	TransactionService transactionService;
	
    public boolean validateFromAccountType(String fromAccount){
     return(fromAccount.equals("checking")||fromAccount.equals("savings"));
    }
    
    public boolean validateToAccount(String accountNo){
        boolean status=false;
        String user_pattern="[0-9]{1,10}";
        Pattern pattern=Pattern.compile(user_pattern);
  		Matcher matcher=pattern.matcher(accountNo);
  		if(matcher.matches()&& !(accountNo.equals(null))){
  			status=true;
  			/*if(!transactionService.checkifAccountExists(Integer.parseInt(accountNo)))
  				status=false;*/
  		}
  		else{
  			status=false;
  		}
  		
        return status;
      }
   
    public boolean validateAmount(String amount){
        boolean status=false;
        if (amount.matches("[0-9]{1,13}(\\.[0-9]*)?"))
        {
  			status=true;
  		}
  		else{
  			status=false;
  		}
        return status;
      }
	
	
	public boolean validateDescription(String Username){
	      boolean status=false;
	      String user_pattern="^[a-zA-Z0-9]{3,25}$";
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

}
