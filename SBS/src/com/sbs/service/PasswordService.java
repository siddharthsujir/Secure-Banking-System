package com.sbs.service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sbs.model.AccountOTP;
import com.sbs.model.Customer;
import com.sbs.model.Employee;
import com.sbs.model.OTP;
import com.sbs.model.Roles;
import com.sbs.model.Users;

@Service
public class PasswordService {

	
	@Autowired
	SessionFactory sessionFactory;
	
	@Transactional
	public boolean verifyUser(String username){
		Session session=sessionFactory.getCurrentSession();
		List userList=session.createQuery("from Users u where u.username=:username").setParameter("username", username).list();
		if(userList.size()==1)
			return true;
		return false;
	}
	
	@Transactional
	public String getEmailId(String username){
		
		Session session=sessionFactory.getCurrentSession();
		List userList=session.createQuery("from Customer u where u.username=:username").setParameter("username", username).list();
		if(userList.size()==1){
			Customer details=(Customer)userList.get(0);
			return details.getEmailId();
		}
		return null;
	}
	
	@Transactional
	public void updatePassword(Users user,String password){
		
		Session session=sessionFactory.getCurrentSession();
		String shaPassword=DigestUtils.sha256Hex(password);
		user.setPassword(shaPassword);
		user.setEnabled(true);
		session.update(user);
	}
	
	@Transactional
	public Users getUser(String username){
		
		Session session=sessionFactory.getCurrentSession();
		List userList=session.createQuery("from Users u where u.username=:username").setParameter("username", username).list();
		if(userList.size()==1){
			Users user=(Users)userList.get(0);
			return user;
		}
		return null;
	}
	
	@Transactional
	public String getRole(String username){
		
		Session session=sessionFactory.getCurrentSession();
		List userList=session.createQuery("from Roles u where u.username=:username").setParameter("username", username).list();
		if(userList.size()==1){
			Roles roles=(Roles)userList.get(0);
			return roles.getRole();
		}
		return null;
	}
	@Transactional
	public String getInternalUserEmail(String username){
		
		Session session=sessionFactory.getCurrentSession();
		List userList=session.createQuery("from Employee u where u.username=:username").setParameter("username", username).list();
		if(userList.size()==1){
			Employee details=(Employee)userList.get(0);
			return details.getEmail();
		}
		return null;
	}
}
