package com.sbs.controller;

import java.io.IOException;
import java.security.spec.ECField;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.transaction.annotation.Transactional;

import com.sbs.model.Attempts;
import com.sbs.model.Users;

public class VerifyFailureHandler implements AuthenticationFailureHandler{

	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	@Transactional
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
		

		Session session=sessionFactory.getCurrentSession();
		String target="login?error";
		
		//System.out.println("\n\n1\n\n");
		List usersList=session.createQuery("from Users u where u.username=:username").setParameter("username", request.getParameter("username")).list();
		if(usersList.size()==1){
		
			//System.out.println("\n\n2\n\n");
			Users u=(Users)usersList.get(0);
			
			if(u.isEnabled()){
				//System.out.println("\n\n3\n\n");
				List attemptList=session.createQuery("from Attempts a where a.username=:username").setParameter("username", request.getParameter("username")).list();
				if(attemptList.size()==0){
					//System.out.println("\n\n4\n\n");
					Attempts a =new Attempts();
					a.setUsername(request.getParameter("username"));
					a.setAttempt(1);
					a.setDatetime(new Date().getTime());
//					Transaction transaction=session.beginTransaction();
					session.save(a);
//					transaction.commit();
					/*System.out.println("\n\n"+a.getUsername()+"\n\n");
					System.out.println("\n\n"+a.getAttempt()+"\n\n");
					System.out.println("\n\n"+a.getDatetime()+"\n\n");*/
				}
				else{
					//System.out.println("\n\n5\n\n");
					Attempts a=(Attempts)attemptList.get(0);
					int totalAttempts=a.getAttempt();
					long currentTime=new Date().getTime();
					long date=a.getDatetime();
					
					if(totalAttempts>=2){
						//System.out.println("\n\n6\n\n");
						u.setEnabled(false);
//						Transaction transaction=session.beginTransaction();
						session.update(u);
						session.delete(a);
//						transaction.commit();
						target="locked";
					}
					else if(totalAttempts<2 && (currentTime-date)<=900000){
						//System.out.println("\n\n7\n\n");
						a.setAttempt(a.getAttempt()+1);
						a.setDatetime(currentTime);
//						Transaction transaction=session.beginTransaction();
						session.update(a);
//						transaction.commit();
					}
					else{
						//System.out.println("\n\n8\n\n");
						a.setDatetime(new Date().getTime());
//						Transaction transaction=session.beginTransaction();
						session.update(a);
//						transaction.commit();
					}
				}
			}
		}
		response.sendRedirect(target);
		
	}

}
