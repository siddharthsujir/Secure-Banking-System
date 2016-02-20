package com.sbs.controller;

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

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
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
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String defaultPage() {
		return "index";
	}

	@RequestMapping(value = "/admin**", method = RequestMethod.GET)
	public ModelAndView adminPage() {
		
		ModelAndView model = new ModelAndView();
		model.addObject("title", "Spring Security Login Form - Database Authentication");
		model.addObject("message", "This page is for ROLE_ADMIN only!");
		model.setViewName("admin");
		return model;
		
	}
	
	@RequestMapping(value = "/user**", method = RequestMethod.GET)
	public ModelAndView userPage() {
		
		ModelAndView model = new ModelAndView();
		model.addObject("title", "Spring Security Login Form - Database Authentication");
		model.addObject("message", "This page is for User only!");
		model.setViewName("user");
		return model;

	}
	
	@RequestMapping(value = "/employee", method = RequestMethod.GET)
	public ModelAndView employeePage() {
		
		ModelAndView model = new ModelAndView();
		model.addObject("title", "Spring Security Login Form - Database Authentication");
		model.addObject("message", "This page is for ROLE_EMPLOYEE only!");
		model.setViewName("employee");
		return model;
		
	}
		
	@RequestMapping(value = "/manager**", method = RequestMethod.GET)
	public ModelAndView managerPage() {
		
		ModelAndView model = new ModelAndView();
		model.addObject("title", "Spring Security Login Form - Database Authentication");
		model.addObject("message", "This page is for ROLE_MANAGER only!");
		model.setViewName("manager");
		return model;
		
	}
	
	@RequestMapping(value = "/merchant**", method = RequestMethod.GET)
	public ModelAndView merchantPage() {
		
		ModelAndView model = new ModelAndView();
		model.addObject("title", "Spring Security Login Form - Database Authentication");
		model.addObject("message", "This page is for ROLE_MERCHANT only!");
		model.setViewName("merchant");
		return model;
		
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(@RequestParam(value = "error", required = false) String error, @RequestParam(value = "logout", required = false) String logout) {
		
		ModelAndView model = new ModelAndView();
		if (error != null) {
			model.addObject("error", "Invalid username and password!");
		}
		if (logout != null) {
			model.addObject("msg", "You've been logged out successfully.");
		}
		model.setViewName("login");
		return model;

	}
	
	//for 403 access denied page
	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public ModelAndView accesssDenied() {
		
		ModelAndView model = new ModelAndView();
		//check if user is login
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserDetails userDetail = (UserDetails) auth.getPrincipal();	
			model.addObject("username", userDetail.getUsername());
		}
		model.setViewName("403");
		return model;

	}
	
	@RequestMapping(value = "/locked", method = RequestMethod.GET)
	public ModelAndView accountLocked() {
		
		ModelAndView model = new ModelAndView();
		//check if user is login
		System.out.println("\n\n\nYALO2\n\n");
		//UserDetails userDetail = (UserDetails) auth.getPrincipal();	
		model.addObject("reason", "Your account has been locked");
		model.setViewName("locked");
		System.out.println("\n\n\nYALO\n\n");
		return model;

	}
}