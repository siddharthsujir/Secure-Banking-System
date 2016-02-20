package com.sbs.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.transaction.annotation.Transactional;

import com.sbs.model.Attempts;
import com.sbs.model.Users;

public class VerifyLoginHandler implements AuthenticationSuccessHandler {

	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	@Transactional
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		
		authenticateUsers(request,response,authentication);
		
	}
	
	@Transactional
	protected void authenticateUsers(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
		
		Session session=sessionFactory.getCurrentSession();
		
		String target="403";
		//System.out.println("\n\n\n"+request.getParameter("username")+"\n\n\n");
		List usersList=session.createQuery("from Users u where u.username=:username").setParameter("username", request.getParameter("username")).list();
		System.out.println("\n\n userlist size "+usersList.size());
		if(usersList.size()==1){
			//System.out.println("\n\n\nHERE\n\n");
			Users u=(Users) usersList.get(0);
			if(u.isEnabled()){
				removeLock(u);
				//System.out.println("\n\n\nHERE2\n\n");
				Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
				for (GrantedAuthority authority : authorities) {
					//System.out.println("\n\n\nHERE3\n\n");
					
					//System.out.println(authority.getAuthority());
					if (authority.getAuthority().equals("ROLE_USER"))
						target = "user";
					else if(authority.getAuthority().equals("ROLE_MERCHANT"))
						target = "merchant";
					else if(authority.getAuthority().equals("ROLE_EMPLOYEE"))
						target = "employee";
					else if(authority.getAuthority().equals("ROLE_MANAGER"))
						target = "manager";
					else if(authority.getAuthority().equals("ROLE_ADMIN"))
						target = "admin";
				}
			}
		}
		//System.out.println("\n\n\n"+target);
		response.sendRedirect(target);
	}
	
	@Transactional
	public void removeLock(Users u){

		Session session=sessionFactory.getCurrentSession();
		List attemptList=session.createQuery("from Attempts a where a.username=:username").setParameter("username", u.getUsername()).list();
		if(attemptList.size()==1){
			Attempts a=(Attempts)attemptList.get(0);
//			Transaction transaction=session.beginTransaction();
			session.delete(a);
//			transaction.commit();
		}
	}
}
