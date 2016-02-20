package com.sbs.service;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sbs.model.AccountInfo;
import com.sbs.model.AccountOTP;
import com.sbs.model.ExtPayRequest;
import com.sbs.model.OTP;

import com.sbs.model.Transaction;
import com.sbs.model.TransactionCredits;
import com.sbs.model.TransactionDebits;


@Service
public class RegUserService {
	@Autowired
	SessionFactory sessionFactory;
	
	
	@Transactional
	public List<ExtPayRequest> getRegUsersForPayment(String username){
		Session session=sessionFactory.getCurrentSession();
		
		List<ExtPayRequest> reguser = session.createQuery("from ExtPayRequest r where r.merchant=:merchant and r.requestStatus=:requestStatus").setParameter("merchant",username).setParameter("requestStatus", 1).list();
		
		return reguser;
	}
	
	@Transactional
	public List<AccountInfo> getUsersForPayment(List<String> userList){
		Session session=sessionFactory.getCurrentSession();
		List<AccountInfo> reguser = session.createQuery("from AccountInfo a where a.username in :username and 1=1").setParameterList("username", userList).list();
		return reguser;
	}
	
	@Transactional
	public List<AccountInfo> getRegUsers(String username){
		Session session=sessionFactory.getCurrentSession();
		
		List<AccountInfo> reguser = session.createQuery("from AccountInfo r where r.username!=:username").setParameter("username",username).list();
		
		return reguser;
	}
	
	@Transactional
	public double getBalance(String currentUser ,String accounttype){
		Session session=sessionFactory.getCurrentSession();
		double balance =0;
		List<AccountInfo> reguser = session.createQuery("from AccountInfo r where r.username=:username and r.accountType=:accounttype").setParameter("username", currentUser).setParameter("accounttype", accounttype).list();
		if(reguser.size()==1){
			System.out.println("Inside if");
			AccountInfo reg=reguser.get(0);
			balance = reg.getAccountbalance();
		}
			
			
		return balance;
		
		
	
	}
	
	@Transactional
	public int getAccountNumber(String currentUser ,String accounttype){
		Session session=sessionFactory.getCurrentSession();
		int accountNumber = 0;
		List<AccountInfo> reguser = session.createQuery("from AccountInfo r where r.username=:username and r.accountType=:accounttype").setParameter("username", currentUser).setParameter("accounttype", accounttype).list();
		if(reguser.size()==1){
			System.out.println("Inside if");
			AccountInfo reg=reguser.get(0);
			accountNumber = reg.getAccountNumber();
		}
			
			
		return accountNumber;
		
		
	
	}
	
	@Transactional
	public String transferfunds(String currentUser,String accounttype,int toaccount,double amount ){
		
		double actualbal1 = 0,balance2=0;
		int fromAccountNo = 0,toAccountNo=0;
		String message = "";
		Session session=sessionFactory.getCurrentSession();
		List<AccountInfo> reguser = session.createQuery("from AccountInfo r where r.username=:username and r.accountType=:accounttype").setParameter("username", currentUser).setParameter("accounttype", accounttype).list();
		if(reguser.size()==1){
			
			AccountInfo reg=reguser.get(0);
			actualbal1 =	reg.getAccountbalance();
			
			if (actualbal1 !=0 && actualbal1 < amount)
				message = "Transfer amount exceeds Actual Balance";
			else {
				actualbal1 -= amount;
				reg.setAccountbalance(actualbal1);
				fromAccountNo = reg.getAccountNumber();// For transaction log
				session.update(reg);
				
			
				}
		List<AccountInfo> reguser1 = session.createQuery("from AccountInfo r where r.accountNumber=:accountNumber and r.accountType=:accounttype").setParameter("accountNumber",toaccount).setParameter("accounttype", accounttype).list();
		if(reguser1.size()==1){
			
			AccountInfo reg1=reguser1.get(0);
			balance2 =	reg1.getAccountbalance();
			toAccountNo = reg1.getAccountNumber();
			if(balance2 >=0)
			{
			balance2 += amount;
			reg1.setAccountbalance(balance2);
			session.update(reg1);
			message = "Success";
			}
			else{
				message = "Transfer Failed";
			}
		}
		/*double actualbal1 =  (double)session.createQuery(
				"Select accountbalance from RegUser where username='"+currentUser+"' and accountType='"+accounttype+"'").uniqueResult();
		double balance2 =  (double)session.createQuery(
				"Select accountbalance from RegUser where accountNumber="+toaccount+" and accountType='"+accounttype+"'").uniqueResult();
		System.out.println(actualbal1);
		if (actualbal1 !=0 && actualbal1 < amount)
			return "Transfer amount exceeds Actual Balance";
		else {
			actualbal1 -= amount;
			balance2 += amount;
			System.out.println(actualbal1);
			
			RegUser creditor = new RegUser();
			RegUser	debitor = new RegUser();
			creditor = (RegUser)session.createQuery("from RegUser where username='"+currentUser+"' and accountType='"+accounttype+"'").uniqueResult();
			creditor.setAccountbalance(actualbal1);
			debitor = (RegUser)session.createQuery("from RegUser where accountNumber='"+toaccount+"' and accountType='"+accounttype+"'").uniqueResult();
			debitor.setAccountbalance(balance2);
			
			session.save(debitor);
			session.save(creditor);*/
			
			
		}
		/*if(message.equals("Success")){
			
			newTransaction.createTransaction("transfer","in-process",fromAccountNo,toAccountNo,amount,"time-pass");
			
		}
		*/
		return message;
	}
	
	@Transactional
	public String creditfunds(String currentUser, String accounttype, double balance ){
		Session session=sessionFactory.getCurrentSession();
		
		/*RegUser creditor = new RegUser();
		creditor = (RegUser)session.createQuery("from RegUser where username='"+currentUser+"' and accountType='"+accounttype+"'").uniqueResult();
		creditor.setAccountbalance(balance);
		session.save(creditor);
		*/
		
		List<AccountInfo> reguser = session.createQuery("from AccountInfo r where r.username=:username and r.accountType=:accounttype").setParameter("username", currentUser).setParameter("accounttype", accounttype).list();
		if(reguser.size()==1){
			
			AccountInfo reg=reguser.get(0);
			reg.setAccountbalance(balance);
			session.update(reg);
			return "Credit Successful";
		}
		return "Credit Unsuccessful";
		
	}
	
	@Transactional
	public String debitfunds(String currentUser, String accounttype, double balance ){
		Session session=sessionFactory.getCurrentSession();
		/*RegUser creditor = new RegUser();
		creditor = (RegUser)session.createQuery("from RegUser where username='"+currentUser+"' and accountType='"+accounttype+"'").uniqueResult();
		creditor.setAccountbalance(balance);
		session.save(creditor);
		*/
		List<AccountInfo> reguser = session.createQuery("from AccountInfo r where r.username=:username and r.accountType=:accounttype").setParameter("username", currentUser).setParameter("accounttype", accounttype).list();
		if(reguser.size()==1){
			
			AccountInfo reg=reguser.get(0);
			reg.setAccountbalance(balance);
			session.update(reg);
			return "Debit Successful";
		}
		return "Debit Unsuccessful";
		
		
	}
	
	//Methods for Updating the Transaction in the Transaction Table
	
	@Transactional
	public boolean createTransferTransaction(long transactionId,String transactionType,String transactionStatus,int fromAccountNo,int toAccountNo,double amount,String description)
	{
		if(fromAccountNo != 0 && toAccountNo != 0){
		Session session=sessionFactory.getCurrentSession();
		
		java.sql.Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
		Transaction newTransaction = new Transaction();
		newTransaction.setTransactiontime(date);
		newTransaction.setTransactionType(transactionType);
		newTransaction.setTransactionStatus(transactionStatus);
		newTransaction.setFromAccount(fromAccountNo);
		newTransaction.setToAccount(toAccountNo);
		newTransaction.setAmount(amount);
		newTransaction.setDescription(description);
		newTransaction.setTransaction_id(transactionId);
	
		
		
		
		session.save(newTransaction);
		return true;
		}
		return false;
		
	}
	
	@Transactional
	public boolean createDebitTransaction(long transactionId,String transactionType,String transactionStatus,int fromAccountNo,double amount,String description)
	{
		if(fromAccountNo != 0){
			Session session=sessionFactory.getCurrentSession();
			
			java.sql.Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
			TransactionDebits newTransaction = new TransactionDebits();
			newTransaction.setTransactiontime(date);
			newTransaction.setTransactionType(transactionType);
			newTransaction.setTransactionStatus(transactionStatus);
			newTransaction.setToAccount(fromAccountNo);
			newTransaction.setTransaction_id(transactionId);
			newTransaction.setAmount(amount);
			newTransaction.setDescription(description);
		
			
			
			
			session.save(newTransaction);
			return true;
			}
			return false;
		
	}
	

	@Transactional
	public boolean createCreditTransaction(long transactionId,String transactionType,String transactionStatus,int toAccountNumber,double amount,String description)
	{
		if(toAccountNumber != 0){
			Session session=sessionFactory.getCurrentSession();
			
			java.sql.Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
			TransactionCredits newTransaction = new TransactionCredits();
			newTransaction.setTransactiontime(date);
			newTransaction.setTransactionType(transactionType);
			newTransaction.setTransactionStatus(transactionStatus);
			newTransaction.setFromAccount(toAccountNumber);
			newTransaction.setTransaction_id(transactionId);
			
			newTransaction.setAmount(amount);
			newTransaction.setDescription(description);
		
			
			
			
			session.save(newTransaction);
			return true;
			}
			return false;
		
	}
//Merchant's Transfer

@Transactional
public String merchantTransferfunds(int fromAccountNo,String accounttype,int toaccount,double amount ){
	
	double fromBalance = 0,toBalance=0;
	int fromAccountNumber = 0,toAccountNo=0;
	String message = "";
	System.out.println("Test"+fromAccountNo+toaccount);
	Session session=sessionFactory.getCurrentSession();
	
		/*	actualbal1 += amount;
			reg.setAccountbalance(actualbal1);
			fromAccountNo = reg.getAccountNumber();// For transaction log
			session.update(reg);*/
	List<AccountInfo> reguser1 = session.createQuery("from AccountInfo r where r.accountNumber=:accountNumber and r.accountType=:accounttype").setParameter("accountNumber",fromAccountNo).setParameter("accounttype", accounttype).list();
	if(reguser1.size()==1){
		
		AccountInfo reg1=reguser1.get(0);
		fromBalance =	reg1.getAccountbalance();
		if(fromBalance> amount && fromBalance !=0 ){
			fromBalance -=amount;
			System.out.println("fromBalance:"+fromBalance);
			reg1.setAccountbalance(fromBalance);
			
			List<AccountInfo> reguser2 = session.createQuery("from AccountInfo r where r.accountNumber=:accountNumber and r.accountType=:accounttype").setParameter("accountNumber",toaccount).setParameter("accounttype", accounttype).list();	
			if(reguser2.size()==1){
				
				AccountInfo reg2=reguser2.get(0);
				toBalance =	reg2.getAccountbalance();
				System.out.println("toBalance:"+toBalance);
				toBalance += amount;
				reg2.setAccountbalance(toBalance);
				session.update(reg1);
				session.update(reg2);
			}
			else
				message = "Transaction Failed";
		
		}
		else 
			message = "Account Balance is less";
	
return message;
	}	
	return message;
}

}
	
	
	
	
	
	
	