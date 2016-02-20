package com.sbs.service;

import java.io.IOException;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sbs.model.Customer;
import com.sbs.model.ExtPayRequest;
import com.sbs.model.ModifyRequest;
import com.sbs.model.Users;
import com.sbs.model.ViewTransaction;
@Service
public class ExtPayService {
	@Autowired
	SessionFactory sessionFactory;	
	/*SessionFactory sessionFactory=new Configuration().configure().buildSessionFactory();
	Session session=sessionFactory.openSession();*/
	@Transactional
	public void addExtRequest(ExtPayRequest req) throws DataIntegrityViolationException{
		// TODO Auto-generated method stub
		Session session=sessionFactory.getCurrentSession();
		//Transaction transaction=session.beginTransaction();
		session.save(req);
		//transaction.commit();
		System.out.println("request created");
	}
	@Transactional
	public void removeExtRequest(ExtPayRequest req)
	{
		Session session=sessionFactory.getCurrentSession();
		//Transaction transaction=session.beginTransaction();
		Query q1 =session.createQuery("delete from ExtPayRequest e where e.username=:user and e.merchant=:merchant");
		q1.setParameter("user" ,req.getUsername());
		q1.setParameter("merchant" ,req.getMerchant());
		q1.executeUpdate();
		//transaction.commit();
		System.out.println("Customer Removed");
	}
   
	//@SuppressWarnings("unchecked")
	@Transactional
	public List<ExtPayRequest> showExtRequest(){
		Session session=sessionFactory.getCurrentSession();
		List<ExtPayRequest> requestList = session.createQuery("from ExtPayRequest").list();
				
		return requestList;
		
	}
	@Transactional
	public void updateExtReq(ExtPayRequest req)
	{	int updateValue=0;
		System.out.println("user" +req.getUsername());
		System.out.println("merchant" +req.getMerchant());
		System.out.println("status"+req.getRequestStatus());
	
		Session session=sessionFactory.getCurrentSession();
		//Transaction transaction=session.beginTransaction();
		Query q1 =session.createQuery("update ExtPayRequest e set e.requestStatus=:status where e.username=:user and e.merchant=:merchant");
		q1.setParameter("user" ,req.getUsername());
		q1.setParameter("merchant" ,req.getMerchant());
		q1.setParameter("status" ,req.getRequestStatus());
		updateValue = q1.executeUpdate();
		//transaction.commit();
		System.out.println(updateValue);
		
	}


}

	
	

