package com.sbs.service;
import java.io.IOException;
import java.util.List;

import org.hibernate.HibernateError;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sbs.model.Users;
import com.sbs.model.ViewTransaction;
import com.sbs.model.Customer;
import com.sbs.model.ModifyRequest;
import org.hibernate.Transaction;

@Service
public class ModifyRequestImpl {
	@Autowired
	SessionFactory sessionFactory;	
	
	
	@Transactional	
public void addModifyRequest(ModifyRequest mreq) throws DataIntegrityViolationException{
		
		Session session=sessionFactory.getCurrentSession() ;
		//Transaction transaction=session.beginTransaction();
	     
		session.save(mreq);
		//transaction.commit();
		System.out.println("request created");
	 
	    
	}
	
	
	@Transactional	
public int updateCustomer(Customer u) throws IOException {
		
		Session session=sessionFactory.getCurrentSession();
		System.out.println(u.getUsername());
		int updateValue=0;
		//System.out.println(requestList.get(0).getRequestStatus());
		
		/*if(checkPermission(u)==1)
		{*/
		
			
			/*Query q1 =session.createQuery("update Customer s set s.firstName=:fname,s.lastName=:lname,s.address=:address, s.phoneNumber=:phone,s.role=:role, s.emailId=:email where s.username=:username");
			q1.setParameter("username" ,u.getUsername());
			q1.setParameter("fname" ,fname);
			q1.setParameter("lname" ,lname);
			q1.setParameter("address" ,u.getAddress());
			q1.setParameter("phone" ,u.getPhoneNumber());
			q1.setParameter("email" ,u.getEmailId());
			q1.setParameter("role" ,role);
			updateValue = q1.executeUpdate();*/
			//session.cancelQuery();
			//session=sessionFactory.getCurrentSession();
			System.out.println("\n\n\nhello "+u.getCustomerId()+" "+u.getAddress()+" "+u.getEmailId()+" "+u.getFirstName()+" "+u.getLastName()+" "+u.getPhoneNumber()+" "+u.getRole()+" "+u.getUsername()+"\n\n\n");
			session.update(u);
			//transaction.commit();
			System.out.println("Customer updated");
			
			 
		//}
		//transaction.commit();
		return updateValue;
	 
	    
	}
	@Transactional
public int checkPermission(Customer u){
	Session session=sessionFactory.getCurrentSession();
	List<ModifyRequest> requestList = session.createQuery("from ModifyRequest a where a.username=:username").setParameter("username", u.getUsername()).list();
	List<Customer> c =session.createQuery("from Customer c where c.username=:username").setParameter("username" ,u.getUsername()).list();
	System.out.println("status is    "+requestList.get(0).isRequestStatus());
	if(requestList.get(0).isRequestStatus() && c!=null)
	{
		if(c.get(0).getCustomerId()==u.getCustomerId())
		return 1;
	}
	return 0;
}

}
