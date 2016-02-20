package com.sbs.controller;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.sbs.service.EmailSenderService;
import com.sbs.service.OTPService;
import com.sbs.service.PasswordService;
import com.sbs.validator.PasswordValidator;

/**
 * Handles requests for the application home page.
 */
@Controller
public class PasswordController {
	
	@Autowired
	OTPService otpService;
	
	@Autowired
	PasswordService passwordService;
	
	@Autowired
	PasswordValidator passwordValidator;
	
	/*private static final Logger logger = LoggerFactory.getLogger(HomeController.class);*/
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
		
	/*@RequestMapping(value = {"/","/welcome"}, method = RequestMethod.GET)
	public ModelAndView defaultPage() {
	  ModelAndView model = new ModelAndView();
	  model.addObject("title", "Spring Security Login Form - Database Authentication");
	  model.addObject("message", "This is default page!");
	  model.setViewName("admin");
	  return model;
	}*/
	
	
	@RequestMapping(value = "/forgotPassword", method = RequestMethod.GET)
	public ModelAndView forgotPassword() {
		
		/*String password=UUID.randomUUID().toString().substring(0, 8);
		ApplicationContext context = new ClassPathXmlApplicationContext("Spring-Mail.xml");
		EmailSenderService emailSenderService = (EmailSenderService) context.getBean("emailSenderService");*/
//		emailSenderService.sendSimpleEmail(username, subject, body);
		
		ModelAndView model = new ModelAndView();
		model.setViewName("passwordresetotp");
		return model;

	}
	
	@RequestMapping(value = "/forgotpasswordotp", method = RequestMethod.POST)
	public ModelAndView forgotPasswordOtp(HttpServletRequest request) {
		System.out.println("Inside Controller");
		
		ModelAndView model = new ModelAndView();
		String username=request.getParameter("username");
		
		//PasswordValidator passwordValidator=new PasswordValidator();
		if(!passwordValidator.validateUsername(username)){
			model.addObject("error","Please Enter a valid username");
			model.setViewName("passwordresetotp");
			return model;
		}
		
		String password=UUID.randomUUID().toString().substring(0, 8);
		/*ApplicationContext context = new ClassPathXmlApplicationContext("Spring-Mail.xml");
		EmailSenderService emailSenderService = (EmailSenderService) context.getBean("emailSenderService");
		emailSenderService.sendSimpleEmail(username, subject, body);*/
		//String email=passwordService.getEmailId(username);
		
		String role=passwordService.getRole(username);
		if(role.equals(null)||role.equals("")){
			model.addObject("error","Invalid Username");
			model.setViewName("passwordreset");
			return model;
		}
		
		String email="";
		if(role.equals("ROLE_USER")||role.equals("ROLE_MERCHANT"))
			email=passwordService.getEmailId(username);
		else
			email=passwordService.getInternalUserEmail(username);
		System.out.println("\n\n\nusername and email "+username+" "+email+"\n\n\n");
		otpService.generateAndEmailAccountOTP(username, email);
		
		model.setViewName("passwordreset");
		return model;

	}
	
	@RequestMapping(value = "/resetpassword", method = RequestMethod.POST)
	public ModelAndView resetPassword(HttpServletRequest request) {
		
		
		ModelAndView model = new ModelAndView();
		String username=request.getParameter("username");
		String otp=request.getParameter("otp");
		String password=request.getParameter("password");
		String confirmPassword=request.getParameter("confirmpassword");
		
		//PasswordValidator passwordValidator=new PasswordValidator();
		
		if(!passwordValidator.validateUsername(username)){
			model.addObject("error","Please Enter a valid username");
			model.setViewName("passwordreset");
			return model;
		}
		
		if(!passwordValidator.validateOTP(otp)){
			model.addObject("error","Please Enter a valid OTP");
			model.setViewName("passwordreset");
			return model;
		}
		
		if(!passwordValidator.validatePassword(password,confirmPassword)){
			model.addObject("error","Please Enter a valid Password");
			model.setViewName("passwordreset");
			return model;
		}
		
		
		String role=passwordService.getRole(username);
		if(role.equals(null)||role.equals("")){
			model.addObject("error","Invalid Username");
			model.setViewName("passwordreset");
			return model;
		}
		
		String email="";
		if(role.equals("ROLE_USER")||role.equals("ROLE_MERCHANT"))
			email=passwordService.getEmailId(username);
		else
			email=passwordService.getInternalUserEmail(username);
		
		if(email.equals(null)||email.equals("")){
			model.addObject("error","Invalid Username");
			model.setViewName("passwordreset");
			return model;
		}
		
		if(!otpService.verifyAccountOTP(otp, username, email)){
			model.addObject("error","OTP does not match or is expired");
			model.setViewName("passwordreset");
			return model;
		}
		
		passwordService.updatePassword(passwordService.getUser(username),password);
		
		model.setViewName("login");
		return model;

	}
}