package com.sbs.service;

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

import com.sbs.model.AuthorizationRequest;
import com.sbs.model.SystemLog;

@Service
public class RequestUserAuthorizationService {
	
	@Autowired
	SessionFactory sessionFactory;
	
	@Autowired
	SystemLogService syslogserv;
    java.util.Date date= new java.util.Date();
    
	SystemLog syslog=new SystemLog();
	
	@Transactional
	public void requestAuthorization(AuthorizationRequest authrequest)
	{
		Session session=sessionFactory.getCurrentSession();
		try
		{
		session.save(authrequest);
		Timestamp timest=new Timestamp(date.getTime());
		String timestamp=timest.toString();
		syslog.setUsername(authrequest.getEmployeename());
		syslog.setDescription("Request to view customer transaction has been submitted by the user "+authrequest.getEmployeename());
		syslog.setTimestamp(timestamp);
		syslogserv.addLog(syslog);
		}
		catch(Exception e)
		{
			session.save(authrequest);
			Timestamp timest=new Timestamp(date.getTime());
			String timestamp=timest.toString();
			syslog.setUsername(authrequest.getEmployeename());
			syslog.setDescription("Request to view customer transaction failed");
			syslog.setTimestamp(timestamp);
			syslogserv.addLog(syslog);
			
		}
	}

}
