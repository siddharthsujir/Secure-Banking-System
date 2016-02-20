package com.sbs.service;
import com.sbs.model.UserPKIDetails;
import com.sbs.model.AccountInfo;
import com.sbs.model.EmpReqUser;
import com.sbs.model.Transaction;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionService {
	@Autowired
	SessionFactory sessionFactory;
	
	
	
	@Transactional
	public boolean createTransaction(String transactionType,String transactionStatus,int fromAccountNo,int toAccountNo,double amount,String description)
	{
		if(fromAccountNo != 0 && toAccountNo != 0){
		Session session=sessionFactory.getCurrentSession();
		java.util.Date dt = new java.util.Date();

		Transaction newTransaction = new Transaction();
		newTransaction.setTransactiontime(dt);
		newTransaction.setTransactionType(transactionType);
		newTransaction.setTransactionStatus(transactionStatus);
		newTransaction.setFromAccount(fromAccountNo);
		newTransaction.setToAccount(toAccountNo);
		newTransaction.setAmount(amount);
		newTransaction.setDescription(description);
		
		
		
		
		session.save(newTransaction);
		return true;
		}
		
		return false;
		
	}
	
	@Transactional
	public void modifyTransactionStatus(long transactionId,String status){
		
		Session session=sessionFactory.getCurrentSession();
		List transactionList=session.createQuery("from Transaction t where t.transaction_id=:transactionId").setParameter("transactionId", transactionId).list();
		if(transactionList.size()==1){
			Transaction transaction=(Transaction)transactionList.get(0);
			transaction.setTransactionStatus(status);
			session.update(transaction);
		}
	}
	
	@Transactional
	public boolean checkBalance(String username,String accountType,double amount){
		
		Session session=sessionFactory.getCurrentSession();
		List accountList=session.createQuery("from AccountInfo a where a.username=:username and a.accountType=:accountType").setParameter("username", username).setParameter("accountType", accountType).list();
		if(accountList.size()==1){
			AccountInfo accountInfo=(AccountInfo)accountList.get(0);
			if((accountInfo.getAccountbalance()-amount)>0)
				return true;
		}
		return false;
	}
	
	@Transactional
	public int getAccountNumber(String username,String accountType){
		
		Session session=sessionFactory.getCurrentSession();
		List accountList=session.createQuery("from AccountInfo a where a.username=:username and a.accountType=:accountType").setParameter("username", username).setParameter("accountType", accountType).list();
		if(accountList.size()==1){
			AccountInfo accountInfo=(AccountInfo)accountList.get(0);
			return accountInfo.getAccountNumber();
		}
		return 0;
	}
	
	@Transactional
	public List getPendingTransactions(List accounts){
		
		Session session=sessionFactory.getCurrentSession();
		List transactionList=session.createQuery("from Transaction t where t.toAccount in (:accounts) and t.transactionStatus=:transactionStatus").setParameterList("accounts", accounts).setParameter("transactionStatus", "Pending").list();
		return transactionList;
	}
	
	@Transactional
	public String fundTransfer(long transactionId){
		
		Session session=sessionFactory.getCurrentSession();
		List transactionList=session.createQuery("from Transaction t where t.transaction_id=:transactionId").setParameter("transactionId", transactionId).list();
		if(transactionList.size()==1){
			Transaction transaction=(Transaction)transactionList.get(0);
			int fromAccount=transaction.getFromAccount();
			int toAccount=transaction.getToAccount();
			double amount=transaction.getAmount();
			
			List accountList=session.createQuery("from AccountInfo a where a.accountNumber=:accountNumber").setParameter("accountNumber", fromAccount).list();
			if(accountList.size()==1){
				AccountInfo accountInfo=(AccountInfo)accountList.get(0);
				if((accountInfo.getAccountbalance()-amount)>0){
					
					accountInfo.setAccountbalance(accountInfo.getAccountbalance()-amount);
					session.update(accountInfo);
					
					List accountList2=session.createQuery("from AccountInfo a where a.accountNumber=:accountNumber").setParameter("accountNumber", toAccount).list();
					AccountInfo accountInfo2=(AccountInfo)accountList2.get(0);
					accountInfo2.setAccountbalance(accountInfo2.getAccountbalance()+amount);
					session.update(accountInfo2);
					
					/*transaction.setTransactionStatus("Approved");
					session.update(transaction);*/
					
					return "Transaction Approved";
				}
			}
			return "Insufficient Balance";
		}
		return "Invalid Transaction Selected";
	}
	
	@Transactional
	public boolean checkPaymentBalance(int accountNumber,double amount){
		
		Session session=sessionFactory.getCurrentSession();
		List accountList=session.createQuery("from AccountInfo a where a.accountNumber=:accountNumber").setParameter("accountNumber", accountNumber).list();
		if(accountList.size()==1){
			AccountInfo accountInfo=(AccountInfo)accountList.get(0);
			if((accountInfo.getAccountbalance()-amount)>0)
				return true;
		}
		return false;
	}
	
	@Transactional
	public boolean checkifAccountExists(int accountNo){
		
		Session session=sessionFactory.getCurrentSession();
		List accountList=session.createQuery("from AccountInfo a where a.accountNumber=:accountNumber").setParameter("accountNumber", accountNo).list();
		if(accountList.size()==1)
			return true;
		return false;
	}

	@Transactional
	public boolean checkifTransactionExists(long transactionId){
		
		Session session=sessionFactory.getCurrentSession();
		List accountList=session.createQuery("from Transaction a where a.transaction_id=:transaction_id").setParameter("transaction_id", transactionId).list();
		if(accountList.size()==1)
			return true;
		return false;
	}
	
	@Transactional
	public void getTran(String status){
		Session session=sessionFactory.getCurrentSession();
		System.out.println("Inside Tran");
		List u=session.createQuery("from EmpReqUser t where t.request=:request and t.intuser=:intuser").setParameter("request", 0).setParameter("intuser",status).list();
		for (int i = 0; i < u.size(); i++) {
			System.out.println("\n\n\nhello "+((EmpReqUser)u.get(i)).getExtuser());
		}
	}
	
	@Transactional
	public double getBalance(String username,String accountType){
		
		Session session=sessionFactory.getCurrentSession();
		List accountList=session.createQuery("from AccountInfo a where a.username=:username and a.accountType=:accountType").setParameter("username", username).setParameter("accountType", accountType).list();
		if(accountList.size()==1){
			AccountInfo accountInfo=(AccountInfo)accountList.get(0);
			
				return accountInfo.getAccountbalance();
		}
	return 0.0;
	}
	
	@Transactional
	public List getAllTransactions(int accNo){
		
		Session session=sessionFactory.getCurrentSession();
		List transactionList=session.createQuery("from Transaction t where t.fromAccount = :accNo OR  t.toAccount=:accNo").setParameter("accNo", accNo).list();
		return transactionList;
	}
	@Transactional
	public List getAllDebits(int accNo){
		
		Session session=sessionFactory.getCurrentSession();
		List transactionList=session.createQuery("from TransactionDebits t where t.toAccount = :accNo").setParameter("accNo", accNo).list();
		return transactionList;
	}
	@Transactional
		public List getAllCredits(int accNo){
		
		Session session=sessionFactory.getCurrentSession();
		List transactionList=session.createQuery("from TransactionCredits t where t.fromAccount = :accNo").setParameter("accNo", accNo).list();
		return transactionList;
	}
}
