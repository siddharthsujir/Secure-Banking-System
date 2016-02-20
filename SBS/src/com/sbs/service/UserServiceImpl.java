package com.sbs.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sbs.model.Users;

@Service
public class UserServiceImpl {
	
	@Autowired
	SessionFactory sessionFactory;
	
	@Transactional
	public void addUser(Users user)
	{
		Session session=sessionFactory.getCurrentSession();
		session.save(user);
		System.out.println("User "+ user.getUsername()+ "has been added successfully ");
	}
	@Transactional
	public void deleteUser(Users user,String currentUser)
	{
		Session session=sessionFactory.getCurrentSession();
		session.delete(user);
	}
	
	@Transactional
	public boolean checkUserdb(String user){
		
		List<Users> usrList= new ArrayList();
		usrList=listUser();
		for(Users u : usrList){
			if(u.getUsername().equals(user))
				return true;			
		}
		return false;
		}
	@Transactional
	public List<Users> listUser()
	{

		Session session=sessionFactory.getCurrentSession();
		List<Users> userList=session.createQuery("from Users").list();
		 
	 
	    return userList;
	}

}
