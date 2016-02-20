package com.sbs.service;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sbs.model.AccountOTP;
import com.sbs.model.OTP;

@Service
public class OTPService {

	
	@Autowired
	SessionFactory sessionFactory;
	
	@Transactional
	public void generateAndEmailOTP(long transactionid,String username, String email){
		
		String password=UUID.randomUUID().toString().substring(0, 8);
		
		OTP otp=new OTP();
		otp.setOtpid(UUID.randomUUID().toString());
		otp.setEmail(email);
		otp.setPassword(password);
		otp.setTransactionid(transactionid);
		otp.setUsername(username);
		otp.setExpirytime(new Date().getTime());
		
		Session session=sessionFactory.getCurrentSession();
		session.save(otp);
		
		ApplicationContext context = new ClassPathXmlApplicationContext("Spring-Mail.xml");
		EmailSenderService emailSenderService = (EmailSenderService) context.getBean("emailSenderService");
		emailSenderService.sendSimpleEmail(email, "OTP "+password+" for transaction", "Hello,\n\nHere is your OTP.\n\n"+password+"\n\nThe OTP will expire in 15 minutes.\n\nThank you.");
		
		
	}
	
	@Transactional
	public boolean verifyTransactionOTP(String password,String username,long transactionid){
		
		Session session=sessionFactory.getCurrentSession();
		List<OTP> otpList=session.createQuery("from OTP o where o.username=:username and o.transactionid=:transactionid").setParameter("username", username).setParameter("transactionid", transactionid).list();
		if(otpList.size()==1){
			System.out.println("Inside if");
			OTP otp=otpList.get(0);
			String userOtp=otp.getPassword();
			long expiry=otp.getExpirytime();
			System.out.println("\n\nOTP\n"+otp.getPassword()+" "+password+"\n\n");
			if(userOtp.equals(password)){
				System.out.println("Inside if 2");
				if((new Date().getTime()-expiry)<=900000){
					System.out.println("Inside if 3");
					session.delete(otp);
					return true;
				}
			}
		}
		
		return false;
	}
	
	@Transactional
	public void generateAndEmailAccountOTP(String username,String email){
		
		String password=UUID.randomUUID().toString().substring(0, 8);
		Session session=sessionFactory.getCurrentSession();
		
		AccountOTP accountOTP=new AccountOTP();
		accountOTP.setOtpid(UUID.randomUUID().toString());
		accountOTP.setPassword(password);
		accountOTP.setUsername(username);
		accountOTP.setEmail(email);
		accountOTP.setExpirytime(new Date().getTime());
		
		session.save(accountOTP);
		
		ApplicationContext context = new ClassPathXmlApplicationContext("Spring-Mail.xml");
		EmailSenderService emailSenderService = (EmailSenderService) context.getBean("emailSenderService");
		emailSenderService.sendSimpleEmail(email, "OTP for Locked Account", "Hello,\n\nHere is your OTP.\n\n"+password+"\n\nThe OTP will expire in 15 minutes.\n\nThank you.");
	}
	
	@Transactional
	public boolean verifyAccountOTP(String password,String username,String email){
		
		Session session=sessionFactory.getCurrentSession();
		List<AccountOTP> otpList=session.createQuery("from AccountOTP o where o.username=:username and o.email=:email order by expirytime desc").setParameter("username", username).setParameter("email", email).list();
		if(otpList.size()>0){
			System.out.println("Inside if");
			AccountOTP  accountOtp=otpList.get(0);
			String userOtp=accountOtp.getPassword();
			long expiry=accountOtp.getExpirytime();
			System.out.println("\n\nOTP\n"+accountOtp.getPassword()+" "+password+"\n\n");
			if(userOtp.equals(password)){
				System.out.println("Inside if 2");
				if((new Date().getTime()-expiry)<=900000){
					System.out.println("Inside if 3");
					deleteAccountOTP(username);
					return true;
				}
			}
		}
		
		return false;
	}
	
	@Transactional
	public void deleteAccountOTP(String username){
		Session session=sessionFactory.getCurrentSession();
		List<AccountOTP> otpList=session.createQuery("from AccountOTP o where o.username=:username").setParameter("username", username).list();
		for (int i = 0; i < otpList.size(); i++) {
			AccountOTP  accountOtp=otpList.get(i);
			session.delete(accountOtp);
		}
	}
}
