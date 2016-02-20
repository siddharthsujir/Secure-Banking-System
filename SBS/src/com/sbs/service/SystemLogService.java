package com.sbs.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sbs.model.SystemLog;

@Service
public class SystemLogService {


	@Autowired
	SessionFactory sessionFactory;
	
	//SessionFactory sessionFactory=new Configuration().configure().buildSessionFactory();
	//Session session=sessionFactory.openSession();
	
	@Transactional
	public void addLog(SystemLog syslog)
	{
		Session session=sessionFactory.getCurrentSession();
		//Transaction transaction=session.beginTransaction();
		session.save(syslog);
		//transaction.commit();
	}
}
