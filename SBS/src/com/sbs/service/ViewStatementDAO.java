package com.sbs.service;
import java.io.IOException;
import java.util.List;

import org.hibernate.HibernateError;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sbs.model.ViewTransaction;
import org.hibernate.Transaction;

@Service
public class ViewStatementDAO	{
	@Autowired
	SessionFactory sessionFactory;	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<ViewTransaction> stateList(String username) throws IOException {
		
		
		Session session=sessionFactory.getCurrentSession();
		List<ViewTransaction> listTrans=session.createQuery("from ViewTransaction a where a.username=:username").setParameter("username", username).list();
		System.out.println(username);
		System.out.println(listTrans.size());
	    
	 
	    return listTrans;
	}

}

