package com.sbs.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sbs.model.AccountInfo;
import com.sbs.model.AuthorizationRequest;
import com.sbs.model.Customer;
import com.sbs.model.Roles;
import com.sbs.model.Users;

@Service
public class UserList {
	@Autowired
	SessionFactory sessionFactory;
	
	@Transactional
	public List getUserList()
	{
		Session session=sessionFactory.getCurrentSession();
		Query query= session.createQuery("from Roles where role like ? or role like ? ");
		query.setParameter(0, "ROLE_USER");
		query.setParameter(1, "ROLE_MERCHANT");
		List<Roles> userList=new ArrayList();
		userList=query.list();
		return userList;
	}
	
	@Transactional
	public List getRoleList(String username)
	{
		Session session=sessionFactory.getCurrentSession();
		Query query= session.createQuery("from Roles r where r.username=?");
		query.setParameter(0,username);
		List<Roles> userList=query.list();
		return userList;
	}
	
	@Transactional
	public List getUsrList(String username)
	{
		Session session=sessionFactory.getCurrentSession();
		Query query= session.createQuery("from Users u where u.username=?");
		query.setParameter(0,username);
		List<Users> userList=query.list();
		return userList;
	}
	
	@Transactional
	public List getFullCustomerList()
	{
		Session session=sessionFactory.getCurrentSession();
		Query query= session.createQuery("from Customer c");
		List<Customer> userList=query.list();
		return userList;
	}
	
	@Transactional
	public List getCList(String username)
	{
		Session session=sessionFactory.getCurrentSession();
		Query query= session.createQuery("from Customer c where c.username=?");
		query.setParameter(0,username);
		List<Customer> userList=query.list();
		return userList;
	}
	
	@Transactional
	public List getCustomerList(String username)
	{
		Session session=sessionFactory.getCurrentSession();
		Query query= session.createQuery("from Customer c where c.username=?");
	query.setParameter(0, username);
		List<Customer> userList=query.list();
		System.out.println("Isndied "+userList.size());
		return userList;
	}
	
	@Transactional
	public List getRequestList()
	{
		Session session=sessionFactory.getCurrentSession();
		Query query= session.createQuery("from ModifyRequest where requestStatus=0");
		List<Roles> requestlist=new ArrayList();
		requestlist=query.list();
		return requestlist;
	}
	
	@Transactional
	public List getAuthorizedUserList(String currentUser)
	{
		Session session=sessionFactory.getCurrentSession();
		List<AuthorizationRequest> authuserList=new ArrayList();
		Query query= session.createQuery("from AuthorizationRequest where requeststatus=1 and employeename=? ");
		query.setParameter(0, currentUser);
		authuserList=query.list();
		return authuserList;
	}
	@Transactional
	public List getAuthorizedCustomerTransactionList(String customername,int checking,int savings)
	{
		Session session=sessionFactory.getCurrentSession();
		List transList=new ArrayList();
		
		Query query1= session.createQuery("from Transaction t where t.fromAccount=? or t.fromAccount=? or t.toAccount=? or t.toAccount=?");
		query1.setParameter(0, checking);
		query1.setParameter(1, savings);
		query1.setParameter(2, savings);
		query1.setParameter(3, checking);
		transList=query1.list();
		System.out.println(transList);
		return transList;
	}
	
	
	@Transactional
	public List getAuthorizedCustomerDebitList(String customername,int checking,int savings)
	{
		Session session=sessionFactory.getCurrentSession();
		List debitList=new ArrayList();
		Query query2=session.createQuery("from TransactionDebits t where t.toAccount=? or t.toAccount=? ");
		query2.setParameter(0, checking);
		query2.setParameter(1, savings);
		debitList=query2.list();
		return debitList;
	}
	
	@Transactional
	public List getAuthorizedCustomerCreditList(String customername,int checking,int savings)
	{
		Session session=sessionFactory.getCurrentSession();
		List creditList=new ArrayList();
		Query query3=session.createQuery("from TransactionCredits t where t.fromAccount=? or t.fromAccount=? ");
		query3.setParameter(0, checking);
		query3.setParameter(1, savings);
		creditList=query3.list();
		return creditList;
	}
	
	
	@Transactional
	public List getAccountNumber(String customername,String accounttype)
	{
		Session session=sessionFactory.getCurrentSession();
		List<AccountInfo> accttype=new ArrayList();
		Query query2=session.createQuery("from AccountInfo where username=? and accounttype=?");
		query2.setParameter(0, customername);
		query2.setParameter(1, accounttype);
		accttype=query2.list();
		return accttype;
	}
	
}
