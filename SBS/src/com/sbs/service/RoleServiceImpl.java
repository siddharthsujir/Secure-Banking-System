package com.sbs.service;

import java.security.Principal;
import java.sql.Timestamp;

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
public class RoleServiceImpl{

	@Autowired
	SessionFactory sessionFactory;
	@Autowired
	SystemLogService syslogserv;
    java.util.Date date= new java.util.Date();
    
	
	SystemLog syslog=new SystemLog();
	
	@Transactional
	public void addRole(Roles role,String currentUser)
	{
		
		Session session=sessionFactory.getCurrentSession();
		try
		{
		session.save(role);
		System.out.println("Role has been added for the user "+role.getUsername());
		Timestamp timest=new Timestamp(date.getTime());
		String timestamp=timest.toString();
		syslog.setUsername(currentUser);
		syslog.setDescription("The role for the user "+role.getUsername()+"has been added");
		syslog.setTimestamp(timestamp);
		syslogserv.addLog(syslog);
		}
		catch(Exception e)
		{
			Timestamp timest=new Timestamp(date.getTime());
			String timestamp=timest.toString();
			syslog.setUsername(currentUser);
			syslog.setDescription("Attempt to add a new customer in the roles table failed");
			syslog.setTimestamp(timestamp);
			syslogserv.addLog(syslog);
		}
	}
	
	@Transactional
	public void deleteRole(Roles role,String currentUser)
	{
		
		Session session=sessionFactory.getCurrentSession();
		try
		{
		session.delete(role);
		System.out.println("Role has been added for the user "+role.getUsername());
		Timestamp timest=new Timestamp(date.getTime());
		String timestamp=timest.toString();
		syslog.setUsername(currentUser);
		syslog.setDescription("The role for the user "+role.getUsername()+"has been delete");
		syslog.setTimestamp(timestamp);
		syslogserv.addLog(syslog);
		}
		catch(Exception e)
		{
			Timestamp timest=new Timestamp(date.getTime());
			String timestamp=timest.toString();
			syslog.setUsername(currentUser);
			syslog.setDescription("Attempt to delete a new customer in the roles table failed");
			syslog.setTimestamp(timestamp);
			syslogserv.addLog(syslog);
		}
	}
}
