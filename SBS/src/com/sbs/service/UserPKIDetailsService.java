package com.sbs.service;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sbs.model.UserPKIDetails;

@Service
public class UserPKIDetailsService {

	@Autowired
	SessionFactory sessionFactory;
	
	@Transactional
	public void saveUserPKIDetails(String userId, String username, String certPassword, byte[] pubKey){
		
		Session session=sessionFactory.getCurrentSession();
		UserPKIDetails details=new UserPKIDetails();
		details.setUserid(userId);
		details.setUsername(username);
		details.setCertpassword(certPassword);
		details.setPubkey(pubKey);
		session.save(details);
		
	}
	@Transactional
	public byte[] getPublicKey(String username){
		Session session=sessionFactory.getCurrentSession();
		List list=session.createQuery("from UserPKIDetails u where u.username=:username").setParameter("username", username).list();
		UserPKIDetails details=(UserPKIDetails)list.get(0);
		byte[] pubkey=details.getPubkey();
		return pubkey;
	}
	
	@Transactional
	public String getCertPassword(String username){
		Session session=sessionFactory.getCurrentSession();
		List list=session.createQuery("from UserPKIDetails u where u.username=:username").setParameter("username", username).list();
		UserPKIDetails details=(UserPKIDetails)list.get(0);
		String certPassword=details.getCertpassword();
		return certPassword;
	}
	
}
