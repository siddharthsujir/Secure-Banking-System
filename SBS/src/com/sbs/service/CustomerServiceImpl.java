package com.sbs.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sbs.model.AccountInfo;
import com.sbs.model.Customer;
import com.sbs.model.SystemLog;

@Service
public class CustomerServiceImpl {

	@Autowired
	SessionFactory sessionFactory;
	
	@Autowired 
	SystemLogService syslogserv;
    java.util.Date date= new java.util.Date();
    
    
	SystemLog syslog=new SystemLog();
	
	
	@Transactional
	public void addCustomer(Customer customer,String password,String currentUser) {
		// TODO Auto-generated method stub
		try
		{
			
		Session session=sessionFactory.getCurrentSession();
		Timestamp timest=new Timestamp(date.getTime());
		System.out.println("Isnide cust serv impl");
		String timestamp=timest.toString();
		System.out.println("Inside Service "+customer.getFirstName());
		session.save(customer);
//		ApplicationContext context = new ClassPathXmlApplicationContext("Spring-Mail.xml");
//		EmailSenderService emailSenderService = (EmailSenderService) context.getBean("emailSenderService");
//		emailSenderService.sendSimpleEmail(customer.getEmailId(), "Your Credentials!!!!","Username "+customer.getUserName()+"  Password "+password);
		syslog.setUsername(currentUser);
		syslog.setDescription("New Customer added with the name "+customer.getFirstName()+" "+customer.getLastName());
		syslog.setTimestamp(timestamp);
		syslogserv.addLog(syslog);
		
		
		
		}
		catch(Exception e)
		{
			Timestamp timest=new Timestamp(date.getTime());
			String timestamp=timest.toString();
			syslog.setUsername(currentUser);
			syslog.setDescription("Attempt to add a new customer failed");
			syslog.setTimestamp(timestamp);
			syslogserv.addLog(syslog);
		}
		System.out.println("Hi this is Customer");
	}
	
	@Transactional
	public void removeCustomer(Customer customer,String currentUser)
	{
		Session session=sessionFactory.getCurrentSession();
		try
		{
		session.delete(customer);
		System.out.println("Customer Removed");
		Timestamp timest=new Timestamp(date.getTime());
		String timestamp=timest.toString();
		syslog.setUsername(currentUser);
		syslog.setDescription("Customer "+customer.getFirstName()+" "+customer.getLastName()+" deleted successfully");
		syslog.setTimestamp(timestamp);
		syslogserv.addLog(syslog);
		}
		catch(Exception e)
		{
			Timestamp timest=new Timestamp(date.getTime());
			String timestamp=timest.toString();
			syslog.setUsername(currentUser);
			syslog.setDescription("Attempt to delete customer "+customer.getFirstName()+" "+customer.getLastName()+" failed");
			syslog.setTimestamp(timestamp);
			syslogserv.addLog(syslog);
		}
	}
   
	@Transactional
	public void addCustomerAccount(AccountInfo account,String currentUser)
	{
		Session session=sessionFactory.getCurrentSession();
		try
		{
			Timestamp timest=new Timestamp(date.getTime());
			String timestamp=timest.toString();
			System.out.println(" Account Type which is gonna be added now:"+account.getAccountType()+" with the account number "+account.getAccountNumber());
			session.save(account);
			syslog.setUsername(currentUser);
			syslog.setDescription("New Customer account added for the customer "+account.getUsername());
			syslog.setTimestamp(timestamp);
			syslogserv.addLog(syslog);
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
			Timestamp timest=new Timestamp(date.getTime());
			String timestamp=timest.toString();
			syslog.setUsername(currentUser);
			syslog.setDescription("Attempt to add a new customer account failed"+e);
			syslog.setTimestamp(timestamp);
			syslogserv.addLog(syslog);
		}
		
	}
	
	@Transactional
	public void updateCustomer(Customer customer,String currentUser) {
		// TODO Auto-generated method stub
		Session session=sessionFactory.getCurrentSession();
		Query query=null;
		String username=customer.getUsername();
		try
		{
		session.update(customer);
		Timestamp timest=new Timestamp(date.getTime());
		String timestamp=timest.toString();
		syslog.setUsername(currentUser);
		syslog.setDescription("Customer updated Successfully");
		syslog.setTimestamp(timestamp);
		syslogserv.addLog(syslog);
		}
		catch(Exception e)
		{
			Timestamp timest=new Timestamp(date.getTime());
			String timestamp=timest.toString();
			syslog.setUsername(currentUser);
			syslog.setDescription("Customer updated Successfully");
			syslog.setTimestamp(timestamp);
			syslogserv.addLog(syslog);
		}
		
		
	}		
	@Transactional
	public void deleteCustomerAccounts(String user,String currentUser) {
		// TODO Auto-generated method stub
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from AccountInfo where username=? ");
		query.setParameter(0, user);
		List<AccountInfo> list=new ArrayList();
		list=query.list();
		AccountInfo ainf=new AccountInfo();
		for(AccountInfo accinf: list)
		{
			ainf=accinf;
		
		
		try
		{
		session.delete(ainf);
		Timestamp timest=new Timestamp(date.getTime());
		String timestamp=timest.toString();
		syslog.setUsername(currentUser);
		syslog.setDescription("Customer updated Successfully");
		syslog.setTimestamp(timestamp);
		syslogserv.addLog(syslog);
		}
		catch(Exception e)
		{
			Timestamp timest=new Timestamp(date.getTime());
			String timestamp=timest.toString();
			syslog.setUsername(currentUser);
			syslog.setDescription("Customer updated Successfully");
			syslog.setTimestamp(timestamp);
			syslogserv.addLog(syslog);
		}}
}
	
	@Transactional
	public List<Customer> listCustomer()
	{

		Session session=sessionFactory.getCurrentSession();
		List<Customer> requestList = session.createQuery("from Customer").list();
				
		return requestList;
	}
}