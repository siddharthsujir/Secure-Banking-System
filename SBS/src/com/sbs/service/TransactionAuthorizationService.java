package com.sbs.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sbs.model.SystemLog;
import com.sbs.model.Transaction;

@Service
public class TransactionAuthorizationService {
	
	@Autowired
	SessionFactory sessionFactory;

	@Autowired
	SystemLogService syslogserv;
    java.util.Date date= new java.util.Date();
    
	SystemLog syslog=new SystemLog();
	
	@Transactional
	public List getCriticalTransaction()
	{
		Session session=sessionFactory.getCurrentSession();
		List<Transaction> transactList=new ArrayList();
		Query query= session.createQuery("from Transaction where transactionStatus like ? and amount>10000 ");
		query.setParameter(0, "Pending");
		transactList=query.list();
		return transactList;
	}
	
	@Transactional
	public void updateTransaction(String transactionid,String approved,String currentUser)
	{
		Session session=sessionFactory.getCurrentSession();
		Timestamp timest=new Timestamp(date.getTime());
		String timestamp=timest.toString();
		Query query=null;
		long transid=Long.parseLong(transactionid);
		org.hibernate.Transaction transaction=session.beginTransaction();
		try
		{
	     if(approved.equals("yes"))
	     {
			query = session.createQuery("update Transaction set transactionStatus=:status where transaction_id= :transid");
			query.setParameter("status", "Approved");
			query.setParameter("transid", transid);
			query.executeUpdate();
			syslog.setUsername("System");
			syslog.setDescription("Transaction with id "+ transactionid+" Approved by "+currentUser);
			syslog.setTimestamp(timestamp);
			syslogserv.addLog(syslog);
	     }
	     else if(approved.equals("no"))
	     {
	 	    query = session.createQuery("update Transaction set transactionStatus=:status where transaction_id= :transid");
			query.setParameter("transid", transid);
			query.setParameter("status", "rejected"); 
			query.executeUpdate();
			syslog.setUsername("System");
			syslog.setDescription("Transaction with id "+ transactionid+" rejected by "+currentUser);
			syslog.setTimestamp(timestamp);
			syslogserv.addLog(syslog);
	     }

		}
		catch(Exception e)
		{
			syslog.setUsername(currentUser);
			syslog.setDescription("Attempt to approve transaction failed"+e);
			syslog.setTimestamp(timestamp);
			syslogserv.addLog(syslog);
		}
	}

}
