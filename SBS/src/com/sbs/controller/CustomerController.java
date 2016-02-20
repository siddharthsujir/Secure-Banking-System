package com.sbs.controller;

import java.security.Principal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.sbs.service.OTPService;
import com.sbs.service.PasswordService;
import com.sbs.service.RegUserService;
import com.sbs.service.SystemLogService;
import com.sbs.service.TransactionAuthorizationService;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sbs.model.AccountInfo;
import com.sbs.model.SystemLog;
import com.sbs.validator.PasswordValidator;
import com.sbs.validator.TransactionValidator;

import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;

import com.sbs.model.Transaction;
import com.sbs.service.TransactionService;

@Controller

public class CustomerController {
	@Autowired
	SessionFactory sessionFactory;

	@Autowired
	RegUserService reguserserv;
	
	@Autowired
	TransactionService transactionService;
	
	@Autowired
	OTPService otpService;
	
	@Autowired
	PasswordService passwordService;
	
	@Autowired
	SystemLogService systemLogService;

	@RequestMapping(value = "/transfer", method = RequestMethod.POST)
	@Transactional
	public ModelAndView transferfunds(Principal principal) {
		Session session = sessionFactory.getCurrentSession();
		final String currentUser = principal.getName();
		ModelAndView model = new ModelAndView();
		List<Integer> userlist = new ArrayList<Integer>();
		// List<RegUser> reguser = session.createQuery(
		// "select from sbs.RegUser where accountNumber is not null").list();

		List<AccountInfo> reguser = reguserserv.getRegUsers(currentUser);
		for (AccountInfo reg : reguser)
			userlist.add(reg.getAccountNumber());

		System.out.println(userlist);
		model.addObject("regusers", userlist);
		model.setViewName("transferfunds");
		return model;

	}

	@RequestMapping(value = "/creditfunds", method = RequestMethod.POST)
	@Transactional
	public ModelAndView creditfunds() {

		ModelAndView model = new ModelAndView();
		model.setViewName("creditfunds");
		return model;

	}

	@RequestMapping(value = "/updatecredit", method = RequestMethod.POST)
	@Transactional
	public ModelAndView updatecredit(HttpServletRequest request, Principal principal) {

		//Session session = sessionFactory.getCurrentSession();
		
		/*ReCaptchaImpl reCaptchaImpl=new ReCaptchaImpl();
		reCaptchaImpl.setPrivateKey("6Le45g8TAAAAACgF-9UadBnF9bVRbu1qGyOzvmuq");
		String challenge = request.getParameter("g-recaptcha-challenge");
		String uresponse = request.getParameter("g-recaptcha-response");		
		String remoteAddr = request.getRemoteAddr();
		
		System.out.println("\n\n\nRecaptcha "+remoteAddr+" "+challenge+" "+uresponse+"\n\n\n");
		
		ReCaptchaResponse reCaptchaResponse = reCaptchaImpl.checkAnswer(remoteAddr, challenge, uresponse);
		if (reCaptchaResponse.isValid()) {
	         System.out.println("Answer was entered correctly!");
	       } else {
	    	   System.out.println("Answer is wrong");
	       }*/
		
		final String currentUser = principal.getName();
		ModelAndView model = new ModelAndView();
		TransactionValidator transVal = new TransactionValidator();
		String validAmount = request.getParameter("creditamount").toLowerCase();
		String validToAccountType = request.getParameter("accounttype").toLowerCase();
		// double amount =
		// Double.parseDouble(request.getParameter("creditamount"));
		// String accounttype = request.getParameter("accounttype");
		boolean bool_Amount, bool_toAccountType;
		bool_toAccountType = false;
		bool_Amount = transVal.validateAmount(validAmount);
		String description = request.getParameter("description").toLowerCase();
		boolean bool_Description = transVal.validateDescription(description);
		System.out.println("\n\n\n\n\ndesc "+bool_Description+"\n\n\n\n");

		if (!(validToAccountType == null))
			bool_toAccountType = transVal.validateFromAccountType(validToAccountType);

		if (bool_Amount && bool_toAccountType&&bool_Description) {

			double amount = Double.parseDouble(validAmount);
			String accounttype = validToAccountType;
			double actualbal1 = reguserserv.getBalance(currentUser, accounttype);

			if(amount==0.0){
				model.addObject("error", "Please Enter some amount");
				model.setViewName("creditfunds");
				return model;
			}
			// int actualbal1 = (int)session.createQuery(
			// "Select accountbalance from RegUser where
			// username='"+currentUser+"' and
			// accountType='"+accounttype+"'").uniqueResult();

			// get balance of this account type
			// add amount to balance
			// update the database with new balance

			long transactionid=Long.parseLong((Math.abs(new Random().nextLong())+"").substring(0, 10));
			actualbal1 += amount;
			System.out.println(actualbal1);
			String message = reguserserv.creditfunds(currentUser, accounttype, actualbal1);
			int toaccount = reguserserv.getAccountNumber(currentUser, accounttype);
			int fromAccountNo = toaccount;
			if (description.length() > 25)
				description = description.substring(0, 25);
			boolean bool_transaction = reguserserv.createCreditTransaction(transactionid,"credit","completed", toaccount, amount,description);
			// Query q = session.createQuery("Update RegUser SET accountbalance
			// =" + actualbal1
			// + " where username='"+currentUser+"' and
			// accountType='"+accounttype+"'");
			// q.executeUpdate();
			SystemLog systemLog=new SystemLog();
			systemLog.setUsername(currentUser);
			systemLog.setTimestamp((new Date().getTime())+"");
			systemLog.setDescription("Transaction "+transactionid+" Amount "+amount+" To Account No "+toaccount+" From AccountNo "+fromAccountNo);
			systemLogService.addLog(systemLog);
			
			model.addObject("msg", message);
			model.setViewName("transactionSuccess");
			return model;

		}
		model.addObject("error","Invalid Values");
		model.setViewName("creditfunds");
		return model;
	}

	@RequestMapping(value = "/debitfunds", method = RequestMethod.POST)
	@Transactional
	public ModelAndView debitfunds() {

		ModelAndView model = new ModelAndView();
		model.setViewName("debitfunds");
		return model;

	}

	@RequestMapping(value = "/updatedebit", method = RequestMethod.POST)
	@Transactional
	public ModelAndView updatedebit(HttpServletRequest request, Principal principal) {

		Session session = sessionFactory.getCurrentSession();
		ModelAndView model = new ModelAndView();
		final String currentUser = principal.getName();

		TransactionValidator transVal = new TransactionValidator();
		String validAmount = request.getParameter("debitamount").toLowerCase();
		String validFromAccountType = request.getParameter("accounttype").toLowerCase();
		// double amount =
		// Double.parseDouble(request.getParameter("creditamount"));
		// String accounttype = request.getParameter("accounttype");
		boolean bool_Amount, bool_FromAccountType;
		bool_FromAccountType = false;
		bool_Amount = transVal.validateAmount(validAmount);

		if (!(validFromAccountType == null))
			bool_FromAccountType = transVal.validateFromAccountType(validFromAccountType);
		String description = request.getParameter("description").toLowerCase();
		boolean bool_Description = transVal.validateDescription(description);
		
		System.out.println("\n\nBooleans "+bool_Amount+" "+bool_FromAccountType+" "+bool_Description+"\n\n\n");

		if (bool_Amount && bool_FromAccountType&&bool_Description) {
			double amount = Double.parseDouble(validAmount);
			String accounttype = validFromAccountType;
			System.out.println(amount + accounttype);
			double actualbal1 = reguserserv.getBalance(currentUser, accounttype);
			// get balance of this account type
			// add amount to balance
			// update the database with new balance

			if (amount > actualbal1) {
				model.addObject("error", "Cannot debit as amount exceeds account balance");
				model.setViewName("debitfunds");
				return model;

			} else {
				if(amount==0.0){
					model.addObject("error", "Please Enter some amount");
					model.setViewName("debitfunds");
					return model;
				}
				actualbal1 -= amount;

				System.out.println(actualbal1);

				long transactionid=Long.parseLong((Math.abs(new Random().nextLong())+"").substring(0, 10));
				String message = reguserserv.debitfunds(currentUser, accounttype, actualbal1);
				int fromAccountNo = reguserserv.getAccountNumber(currentUser, accounttype);
				int toaccount = fromAccountNo;
				if (description.length() > 25)
					description = description.substring(0, 25);
				boolean bool_transaction = reguserserv.createDebitTransaction(transactionid,"debit","completed", fromAccountNo, amount,description);

				if (bool_transaction) {
					SystemLog systemLog=new SystemLog();
					systemLog.setUsername(currentUser);
					systemLog.setTimestamp((new Date().getTime())+"");
					systemLog.setDescription("Transaction "+transactionid+" Amount "+amount+" To Account No "+toaccount+" From AccountNo "+fromAccountNo);
					systemLogService.addLog(systemLog);
					
					model.addObject("msg", message);
					model.setViewName("transactionSuccess");
					return model;
				} else {
					model.addObject("msg", message);
					model.setViewName("transactionUpdationFailed");
					return model;
				}

			}

		}
		model.addObject("error","Invalid values");
		model.setViewName("debitfunds");
		return model;
	}

	/*
	 * @RequestMapping(value = "/transferfunds", method = RequestMethod.POST)
	 * 
	 * @Transactional public ModelAndView transferfunds(Principal principal) {
	 * 
	 * Session session=sessionFactory.getCurrentSession(); ModelAndView model =
	 * new ModelAndView(); List<Integer> userlist = new ArrayList<Integer>();
	 * final String currentUser = principal.getName();
	 * 
	 * 
	 * try { ResultSet list = (ResultSet) session.createQuery(
	 * "Select reguser from sbs.reguser where username="+currentUser); while
	 * (list.next()) { userlist.add(Integer.parseInt(list.getString(1)));
	 * 
	 * } } catch (SQLException e1) { // TODO Auto-generated catch block
	 * e1.printStackTrace(); } System.out.println(userlist);
	 * model.addObject("regusers", userlist);
	 * model.setViewName("transferfunds"); return model;
	 * 
	 * }
	 */
	@RequestMapping(value = "/transferfunds", method = RequestMethod.POST)
	@Transactional
	public ModelAndView updatetransfer(HttpServletRequest request, Principal principal) {

		Session session = sessionFactory.getCurrentSession();
		TransactionValidator transVal = new TransactionValidator();
		String validAmount = request.getParameter("transferamount").toLowerCase();
		String validFromAccountType = request.getParameter("fromaccounttype").toLowerCase();
		String description = request.getParameter("description").toLowerCase();

		String validToAccount = request.getParameter("toaccountnum");

		boolean bool_Amount, bool_FromAccountType, bool_ToAccount, bool_Description;
		bool_Description = transVal.validateDescription(description);
		bool_Amount = transVal.validateAmount(validAmount);
		bool_FromAccountType = transVal.validateFromAccountType(validFromAccountType);
		bool_ToAccount = false;
		if (!(validToAccount == null))
			bool_ToAccount = transVal.validateToAccount(validToAccount);
		ModelAndView model = new ModelAndView();
		
		if (bool_Amount && bool_FromAccountType && bool_ToAccount&&bool_Description) {
			

			final String currentUser = principal.getName();
			double amount = Double.parseDouble(validAmount);
			String accounttype = validFromAccountType;
			int toaccount = Integer.parseInt(validToAccount);
			System.out.println(amount + accounttype + toaccount);
			if(!transactionService.checkifAccountExists(toaccount)){
				model.addObject("error", "Please select a proper account");
				//redirectAttrs.addFlashAttribute("error", "Please select a proper account");
				//model.setViewName("redirect:transferfunds");
				model.setViewName("forward:transfer");
				return model;
			}
			// get balance of this account type
			// add amount to balance
			// update the database with new balance

			int fromAccountNo = reguserserv.getAccountNumber(currentUser, accounttype);
			//String message = reguserserv.transferfunds(currentUser, accounttype, toaccount, amount);
			if (description.length() > 25)
				description = description.substring(0, 25);
			if(!transactionService.checkBalance(currentUser, accounttype, amount)){
				model.addObject("error", "Insufficient Balance");
				//redirectAttrs.addFlashAttribute("error", "Please select a proper account");
				model.setViewName("forward:transfer");
				//model.setViewName("transferfunds");
				return model;
			}
			long transactionid=Long.parseLong((Math.abs(new Random().nextLong())+"").substring(0, 10));
			String email=passwordService.getEmailId(currentUser);
			request.getSession().setAttribute("transactionid", transactionid);
			/*if(!transactionService.checkifTransactionExists(transactionid))
			{
				model.addObject("error", "Transaction not valid");
				model.setViewName("transferfunds");
				return model;	
			}*/
			if(amount>10000.00){
				//Critical Transactions
				reguserserv.createTransferTransaction(transactionid,"transfer", "pki", fromAccountNo,toaccount, amount, description);
				model.setViewName("usercertification");
				return model;
			}
			
			
			System.out.println("\n\n\nTransactionid "+transactionid+"\n\n\n");
			otpService.generateAndEmailOTP(transactionid, currentUser, email);
			reguserserv.createTransferTransaction(transactionid,"transfer", "otp", fromAccountNo,toaccount, amount, description);
			
			//model.addObject("error", "Insufficient Balance");
			model.setViewName("transactionotp");
			return model;
			//reguserserv.transferfunds(currentUser, accounttype, toaccount, amount);
			
			
			//boolean bool_transaction = reguserserv.createTransferTransaction("transfer", "completed", fromAccountNo,toaccount, amount, description);
			/*if (bool_transaction) {
				model.addObject("msg", message);
				model.setViewName("transactionSuccess");
				return model;
			} else {
				model.addObject("msg", "Transaction Update Failed");
				model.setViewName("transactionUpdationFailed");
				return model;
			}*/

		} else {
			model.addObject("error", "One or more values are incorrect");
			model.setViewName("forward:transfer");
			return model;
		}

	}

	// Merchant Transactions
	@RequestMapping(value = "/merchanttransfer", method = RequestMethod.POST)
	@Transactional
	public ModelAndView merchanttransferfunds(Principal principal) {
		Session session = sessionFactory.getCurrentSession();
		final String currentUser = principal.getName();
		ModelAndView model = new ModelAndView();
		List<Integer> userlist = new ArrayList<Integer>();
		// List<RegUser> reguser = session.createQuery(
		// "select from sbs.RegUser where accountNumber is not null").list();

		List<AccountInfo> reguser = reguserserv.getRegUsers(currentUser);
		for (AccountInfo reg : reguser)
			userlist.add(reg.getAccountNumber());

		System.out.println(userlist);
		model.addObject("regusers", userlist);
		model.setViewName("transferfunds");
		return model;

	}
	
	@RequestMapping(value = "/transactionverify", method = RequestMethod.POST)
	public ModelAndView verifyTransaction(Principal principal,HttpServletRequest request) {
		
		ModelAndView model = new ModelAndView();
		final String currentUser = principal.getName();
		String otp=request.getParameter("otp");
		long transactionid=(long) request.getSession().getAttribute("transactionid");
		PasswordValidator passwordValidator=new PasswordValidator();
		if(!passwordValidator.validateOTP(otp)){
			model.addObject("error", "Invalid OTP");
			model.setViewName("transactionotp");
			return model;
		}
		if(!otpService.verifyTransactionOTP(otp, currentUser, transactionid)){
			model.addObject("error", "OTP expired or does not match");
			model.setViewName("transactionotp");
			return model;
		}
		if(!transactionService.checkifTransactionExists(transactionid))
		{
			model.addObject("error", "Transaction not valid");
			model.setViewName("transactionotp");
			return model;	
		}
		transactionService.modifyTransactionStatus(transactionid, "Pending");
		//model.addObject("regusers", userlist);
		//model.addObject("msg", "Transaction Successful");
		model.setViewName("transactionpending");
		return model;
		
		/*System.out.println("\n\n\n\ntransaction id "+request.getAttribute("transactionid")+"\n\n\n");
		
		System.out.println("\n\n\n\ntransaction id 2 "+request.getSession().getAttribute("transactionid")+"\n\n\n");
		request.getSession().removeAttribute("transactionid");
		System.out.println("\n\n\n\ntransaction id 3 "+request.getSession().getAttribute("transactionid")+"\n\n\n");*/
		//Session session = sessionFactory.getCurrentSession();
		
		//ModelAndView model = new ModelAndView();
		//List<Integer> userlist = new ArrayList<Integer>();
		// List<RegUser> reguser = session.createQuery(
		// "select from sbs.RegUser where accountNumber is not null").list();

		/*List<AccountInfo> reguser = reguserserv.getRegUsers(currentUser);
		for (AccountInfo reg : reguser)
			userlist.add(reg.getAccountNumber());

		System.out.println(userlist);*/
		

	}
	
	@RequestMapping(value="/pendingtransactions", method=RequestMethod.POST)
    public ModelAndView pendingTransactions(HttpServletRequest request){

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails details=(UserDetails)auth.getPrincipal();
		String username=details.getUsername();
		
		ModelAndView model=new ModelAndView();
		List<Transaction> translist=new ArrayList();
		List accounts=new ArrayList();
		accounts.add(transactionService.getAccountNumber(username,"Checking"));
		accounts.add(transactionService.getAccountNumber(username,"Savings"));
		//int checkingAccountNumber=transactionService.getAccountNumber(username,"Checking");
		//int savingsAccountNumber=transactionService.getAccountNumber(username,"Savings");
		translist=transactionService.getPendingTransactions(accounts);
		model.addObject("transactionlist",translist);
		model.setViewName("pendingtransactions");
		return model;
	}
	
	@RequestMapping(value="/approvetransactions", method=RequestMethod.POST)
    public ModelAndView approveTransactions(HttpServletRequest request){

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails details=(UserDetails)auth.getPrincipal();
		String username=details.getUsername();
		long transactionId=Long.parseLong(request.getParameter("transaction"));
		String decision=request.getParameter("decision");
		
		ModelAndView model=new ModelAndView();
		
		if(!transactionService.checkifTransactionExists(transactionId)){
			model.addObject("error","Please select a proper transaction");
			model.setViewName("transactionUpdationFailed");
			return model;
		}
		if(decision.equals("Approve")){
			String status=transactionService.fundTransfer(transactionId);
			if(status.equals("Transaction Approved")){
				
				SystemLog systemLog=new SystemLog();
				systemLog.setUsername(username);
				systemLog.setTimestamp((new Date().getTime())+"");
				systemLog.setDescription("Transaction "+transactionId+" Approved");
				systemLogService.addLog(systemLog);
				
				transactionService.modifyTransactionStatus(transactionId, "Approved");
				model.addObject("msg",status);
				model.setViewName("transactionSuccess");
				return model;
			}
			else{
				
				SystemLog systemLog=new SystemLog();
				systemLog.setUsername(username);
				systemLog.setTimestamp((new Date().getTime())+"");
				systemLog.setDescription("Transaction "+transactionId+" Void Transaction");
				systemLogService.addLog(systemLog);
				
				transactionService.modifyTransactionStatus(transactionId, "Void Transaction");
				model.addObject("error",status);
				model.setViewName("transactionUpdationFailed");
				return model;
			}
			
		}
		
		SystemLog systemLog=new SystemLog();
		systemLog.setUsername(username);
		systemLog.setTimestamp((new Date().getTime())+"");
		systemLog.setDescription("Transaction "+transactionId+" Rejected");
		systemLogService.addLog(systemLog);
		
		transactionService.modifyTransactionStatus(transactionId, "Rejected");
		model.addObject("error","Transaction rejected");
		
		/*List<Transaction> translist=new ArrayList();
		
		int checkingAccountNumber=transactionService.getAccountNumber(username,"Checking");
		int savingsAccountNumber=transactionService.getAccountNumber(username,"Savings");
		translist=transactionService.getPendingTransactions(checkingAccountNumber,savingsAccountNumber);*/
		model.setViewName("transactionUpdationFailed");
		return model;
	}
}
