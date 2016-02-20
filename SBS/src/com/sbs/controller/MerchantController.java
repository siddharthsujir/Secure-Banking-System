package com.sbs.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sbs.model.AccountInfo;
import com.sbs.model.ExtPayRequest;
import com.sbs.model.SystemLog;
import com.sbs.service.OTPService;
import com.sbs.service.PKICertificateService;
import com.sbs.service.PasswordService;
import com.sbs.service.RegUserService;
import com.sbs.service.SystemLogService;
import com.sbs.service.TransactionService;
import com.sbs.validator.PasswordValidator;
import com.sbs.validator.TransactionValidator;

@Controller
public class MerchantController {

	@Autowired
	SessionFactory sessionFactory;

	@Autowired
	RegUserService reguserserv;
	
	@Autowired
	TransactionService transactionService;
	
	@Autowired
	PasswordService passwordService;
	
	@Autowired
	OTPService otpService;
	
	@Autowired
	PKICertificateService pkiCertificateService;
	
	@Autowired
	SystemLogService systemLogService;
	
	
	// Merchant Transactions

	@RequestMapping(value = "/merchanttransferfund", method = RequestMethod.POST)
	@Transactional
	public ModelAndView merchanttransferfunds(Principal principal) {
		/*Session session = sessionFactory.getCurrentSession();
		final String currentUser = principal.getName();
		ModelAndView model = new ModelAndView();
		List<Integer> userlist = new ArrayList<Integer>();
		// List<RegUser> reguser = session.createQuery(
		// "select from sbs.RegUser where accountNumber is not null").list();
		//List<ExtPayRequest> reguser = reguserserv.getRegUsersForPayment(currentUser);
		List<ExtPayRequest> reguser=reguserserv.getRegUsersForPayment(currentUser);
		List<String> approvedUserList=new ArrayList();
		
		for(ExtPayRequest extPayRequest: reguser){
			approvedUserList.add(extPayRequest.getUsername());
		}
		List<AccountInfo> approvedUsers=reguserserv.getUsersForPayment(approvedUserList);
		
		for (AccountInfo reg : approvedUsers)
			userlist.add(reg.getAccountNumber());

		System.out.println(userlist);
		model.addObject("regusers", userlist);
		model.setViewName("merchanttransferfunds");
		return model;*/
		
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
		model.setViewName("merchanttransferfunds");
		return model;


	}

	@RequestMapping(value = "/merchanttransferpayment", method = RequestMethod.POST)
	@Transactional
	public ModelAndView merchantUpdatetransfer(HttpServletRequest request, Principal principal) {

		Session session = sessionFactory.getCurrentSession();
		TransactionValidator transVal = new TransactionValidator();
		String validAmount = request.getParameter("transferamount").toLowerCase();
		String validFromAccountType = request.getParameter("fromaccounttype").toLowerCase();
		String description = request.getParameter("description").toLowerCase();

		String validFromAccount = request.getParameter("fromaccountnum");

		boolean bool_Amount, bool_FromAccountType, bool_FromAccount, bool_Description;
		bool_Description = transVal.validateDescription(description);
		bool_Amount = transVal.validateAmount(validAmount);
		bool_FromAccountType = transVal.validateFromAccountType(validFromAccountType);
		bool_FromAccount = false;
		if (!(validFromAccount == null))
			bool_FromAccount = transVal.validateToAccount(validFromAccount);
		ModelAndView model = new ModelAndView();

		if (bool_Amount && bool_FromAccountType && bool_FromAccount&&bool_Description) {

			
			final String currentUser = principal.getName();
			double amount = Double.parseDouble(validAmount);
			String accounttype = validFromAccountType;
			int fromaccount = Integer.parseInt(validFromAccount);
			System.out.println(amount + accounttype + fromaccount);
			if(amount==0.0){
				model.addObject("error", "Please enter an amount");
				model.setViewName("forward:merchanttransferfund");
				return model;
			}
			// get balance of this account type
			// add amount to balance
			// update the database with new balance

			int toAccountNo = reguserserv.getAccountNumber(currentUser, accounttype);
			String message = reguserserv.merchantTransferfunds(fromaccount, accounttype, toAccountNo, amount);
			if (description.length() > 25)
				description = description.substring(0, 25);


			
			if(!transactionService.checkPaymentBalance(fromaccount, amount)){
				model.addObject("error", "Payer does not have balance");
				model.setViewName("forward:merchanttransferfund");
				return model;
			}
			long transactionid=Long.parseLong((Math.abs(new Random().nextLong())+"").substring(0, 10));
			String email=passwordService.getEmailId(currentUser);
			request.getSession().setAttribute("transactionid", transactionid);
//			pkiCertificateService.createPKICertForUsers(currentUser, "123456", email);
			if(amount>10000.00){
				//Critical Transactions
				reguserserv.createTransferTransaction(transactionid,"payment", "pki", fromaccount,toAccountNo, amount, description);
				model.setViewName("merchantcertification");
				return model;
			}
			
			
			System.out.println("\n\n\nTransactionid "+transactionid+"\n\n\n");
			otpService.generateAndEmailOTP(transactionid, currentUser, email);
			reguserserv.createTransferTransaction(transactionid,"payment", "otp", fromaccount,toAccountNo, amount, description);
			
			//model.addObject("error", "Insufficient Balance");
			model.setViewName("paymentotp");
			return model;
			/*boolean bool_transaction = reguserserv.createTransferTransaction("merchant_transfer", "completed",
					fromaccount, toAccountNo, amount, description);

			if (bool_transaction) {
				model.addObject("msg", message);
				model.setViewName("transactionSuccess");
				return model;
			} else {
				model.addObject("msg", "Transaction Update Failed");
				model.setViewName("transactionUpdationFailed");
				return model;
			}*/

		} else {
			model.addObject("error", "Please Enter proper values");
			model.setViewName("forward:merchanttransferfund");
			return model;
		}

	}
	
	@RequestMapping(value = "/paymentverify", method = RequestMethod.POST)
	public ModelAndView verifyPayment(Principal principal,HttpServletRequest request) {
		
		ModelAndView model = new ModelAndView();
		final String currentUser = principal.getName();
		String otp=request.getParameter("otp");
		long transactionid=(long) request.getSession().getAttribute("transactionid");
		PasswordValidator passwordValidator=new PasswordValidator();
		if(!passwordValidator.validateOTP(otp)){
			model.addObject("error", "Invalid OTP");
			model.setViewName("paymentotp");
			return model;
		}
		if(!otpService.verifyTransactionOTP(otp, currentUser, transactionid)){
			model.addObject("error", "OTP expired or does not match");
			model.setViewName("paymentotp");
			return model;
		}
		System.out.println("\n\n\nOTP "+otp+" transaction id "+transactionid+"\n\n\n");
		
		if(!transactionService.checkifTransactionExists(transactionid))
		{
			model.addObject("error", "Transaction not valid");
			model.setViewName("paymentotp");
			return model;	
		}
		
		String status=transactionService.fundTransfer(transactionid);
		System.out.println("\n\n\nStatus "+status+"\n\n\n");
		if(status.equals("Transaction Approved")){
			System.out.println("\n\n\nInside if\n\n\n");
			
			SystemLog systemLog=new SystemLog();
			systemLog.setUsername(currentUser);
			systemLog.setTimestamp((new Date().getTime())+"");
			systemLog.setDescription("Transaction "+transactionid+" Approved");
			systemLogService.addLog(systemLog);
			
			transactionService.modifyTransactionStatus(transactionid, "Approved");
			model.addObject("msg",status);
			model.setViewName("transactionSuccess");
			return model;
		}
		else{
			System.out.println("\n\n\nInside else\n\n\n\n");
			
			SystemLog systemLog=new SystemLog();
			systemLog.setUsername(currentUser);
			systemLog.setTimestamp((new Date().getTime())+"");
			systemLog.setDescription("Transaction "+transactionid+" Void Transaction");
			systemLogService.addLog(systemLog);
			
			transactionService.modifyTransactionStatus(transactionid, "Void Transaction");
			model.addObject("error",status);
			model.setViewName("paymentotp");
			return model;
		}
		
		
		//transactionService.modifyTransactionStatus(transactionid, "Pending");
		//model.addObject("regusers", userlist);
		//model.addObject("msg", "Transaction Successful");
		/*model.setViewName("transactionpending");
		return model;*/
	}
	
	
	@RequestMapping(value="/viewsystemlog", method=RequestMethod.GET)
	@Transactional
    public ModelAndView displaysystemlog(HttpServletRequest request){
		ModelAndView model=new ModelAndView();    
		Session session=sessionFactory.getCurrentSession();
		List<SystemLog> systemlog=session.createQuery("from SystemLog s").list();
		model.addObject("systemlog",systemlog);
		model.setViewName("displaysystemlog");
		return model;
	}
}