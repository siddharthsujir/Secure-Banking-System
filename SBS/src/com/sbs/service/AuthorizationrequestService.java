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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sbs.model.Roles;
import com.sbs.model.SystemLog;

@Service
public class AuthorizationrequestService {
	@Autowired
	SessionFactory sessionFactory;
	@Autowired
	SystemLogService syslogserv;
    java.util.Date date= new java.util.Date();
	SystemLog syslog=new SystemLog();
	
	@Transactional
	public List getEmployeeList(String currentUser)
	{
		Session session=sessionFactory.getCurrentSession();
		Query query= session.createQuery("from AuthorizationRequest where customername like ? and requeststatus=0");
		query.setParameter(0,currentUser);
		List<Roles> empList=new ArrayList();
		empList=query.list();
		return empList;
	}
	
	@Transactional
	public void updateAuthorizationRequest(String currentUser,String employee)
	{
		try
		{
		Timestamp timest=new Timestamp(date.getTime());
		String timestamp=timest.toString();
		Session session=sessionFactory.getCurrentSession();
		Query query = session.createQuery("update AuthorizationRequest set requeststatus=1 where employeename= :employee and customername= :customer ");
		query.setParameter("employee", employee);
		query.setParameter("customer", currentUser);
		query.executeUpdate();
		syslog.setUsername("System");
		syslog.setDescription("Authorization request updated after approval provided by "+currentUser+" to "+employee);
		syslog.setTimestamp(timestamp);
		syslogserv.addLog(syslog);
		}
		catch(Exception e)
		{
			System.out.println("Update of authorizationrequest failed: "+e);
			Timestamp timest=new Timestamp(date.getTime());
			String timestamp=timest.toString();
			syslog.setUsername(currentUser);
			syslog.setDescription("Attempt to update authorization request failed");
			syslog.setTimestamp(timestamp);
			syslogserv.addLog(syslog);
		}
	}
	
	@Transactional
	public void updateModifyRequest(String currentUser,String customer)
	{
		try
		{
		Timestamp timest=new Timestamp(date.getTime());
		String timestamp=timest.toString();
		Session session=sessionFactory.getCurrentSession();
		Query query = session.createQuery("update ModifyRequest set request_status=1 where username= :customer ");
		query.setParameter("customer", customer);
		query.executeUpdate();
		syslog.setUsername("System");
		syslog.setDescription("Modify request updated after approval provided by "+currentUser+" to "+customer);
		syslog.setTimestamp(timestamp);
		syslogserv.addLog(syslog);
		}
		catch(Exception e)
		{
			System.out.println("Update of authorizationrequest failed: "+e);
			Timestamp timest=new Timestamp(date.getTime());
			String timestamp=timest.toString();
			syslog.setUsername(currentUser);
			syslog.setDescription("Attempt to update Modify request failed");
			syslog.setTimestamp(timestamp);
			syslogserv.addLog(syslog);
		}
	}
	@Transactional
	public void deleteRequest(String currentUser,String employee)
	{
	
		try
		{
		Session session=sessionFactory.getCurrentSession();
		Query query = session.createQuery("delete AuthorizationRequest where employeename= :employee and customername= :customer ");
		query.setParameter("employee", employee);
		query.setParameter("customer", currentUser);
		query.executeUpdate();
		Timestamp timest=new Timestamp(date.getTime());
		String timestamp=timest.toString();
		syslog.setUsername(currentUser);
		syslog.setDescription("Deleted the authorization request as it was declined by "+currentUser);
		syslog.setTimestamp(timestamp);
		syslogserv.addLog(syslog);
		}
		catch(Exception e)
		{
			System.out.println("Update of authorizationrequest failed: "+e);
			Timestamp timest=new Timestamp(date.getTime());
			String timestamp=timest.toString();
			syslog.setUsername(currentUser);
			syslog.setDescription("Attempt to delete authorization request failed");
			syslog.setTimestamp(timestamp);
			syslogserv.addLog(syslog);
		}
	}
	
	@Transactional
	public void deleteModifyRequest(String currentUser,String customer)
	{
		try
		{
		Session session=sessionFactory.getCurrentSession();
		Query query = session.createQuery("delete ModifyRequest where username= :customer");
		query.setParameter("customer", customer);
		query.executeUpdate();
		Timestamp timest=new Timestamp(date.getTime());
		String timestamp=timest.toString();
		syslog.setUsername(currentUser);
		syslog.setDescription("Deleted the modify request as it was declined by "+currentUser);
		syslog.setTimestamp(timestamp);
		syslogserv.addLog(syslog);
		}
		catch(Exception e)
		{
			Timestamp timest=new Timestamp(date.getTime());
			String timestamp=timest.toString();
			syslog.setUsername(currentUser);
			syslog.setDescription("Attempt to modify authorization request failed");
			syslog.setTimestamp(timestamp);
			syslogserv.addLog(syslog);
		}
	}
}
