package com.sbs.controller;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.sbs.model.Users;
import com.sbs.model.ViewTransaction;
import com.sbs.service.ViewStatementDAO;
import com.sbs.validator.Validate;
import com.sbs.model.ModifyRequest;
import com.sbs.model.Transaction;
import com.sbs.model.Customer;
import com.sbs.model.ExtPayRequest;
import com.sbs.service.CustomerServiceImpl;
import com.sbs.service.ExtPayService;
//import com.sbs.service.ModifyRequestDAO;
import com.sbs.service.ModifyRequestImpl;
import com.sbs.service.TransactionService;
import com.sbs.service.UserServiceImpl;

/**
 * Handles requests for the application home page.
 */
@Controller
public class SampleController {
	
	
	@Autowired
	SessionFactory sessionFactory;
	@Autowired
	ViewStatementDAO vs;
	@Autowired
	ModifyRequestImpl m;
	@Autowired
	ExtPayService eps;
	@Autowired
	CustomerServiceImpl us;
	@Autowired
	TransactionService transactionService;
	@Autowired
	UserServiceImpl usr;
	

	@RequestMapping(value = "/ModifyDetails", method = RequestMethod.POST)
	public ModelAndView modify(@RequestParam(value = "username", required = true) String s){
		ModelAndView model = new ModelAndView();
		ModifyRequest mreq = new ModifyRequest();
		Validate v=new Validate();
		Customer c= new Customer();
		List<Customer> lus=new ArrayList<Customer>(); 
		if(v.validateUsername(s)){
		mreq.setUsername(s);
		mreq.setRequestStatus(false);
		lus=us.listCustomer();
		for(Customer a: lus){
			if(a.getUsername().equals(s))
				c=a;
		}
		System.out.println(mreq.isRequestStatus()+mreq.getUsername());
		try
		{
			m.addModifyRequest(mreq);
			
		}
		catch(DataIntegrityViolationException e)
		{
			model.addObject("message","Request already sent");
			model.addObject("customer",c);
			model.setViewName("ModifyDetails");
			return model;
		}
		
		}
		
		model.addObject("customer",c);
		model.setViewName("ModifyDetails");
		return model;
	}
	
	
	@RequestMapping(value = "/requestPayment", method = RequestMethod.POST)
	public ModelAndView request(@RequestParam(value = "merchantname", required = true) String s){
		ModelAndView model = new ModelAndView();
		List<Customer> userList = new ArrayList<Customer>();
		List<Customer> toRemove = new ArrayList<Customer>();
		Validate v= new Validate();
		//CustomerServiceImpl us = new CustomerServiceImpl();
		
			userList=us.listCustomer();
		
		if(us!=null && v.validateUsername(s)){
			
		
		for (Customer a : userList)
		{
			System.out.println(a.getRole());
			if(a.getUsername().equals(s))
				toRemove.add(a);
		}
		userList.removeAll(toRemove);
		}
		System.out.println(userList.size());
		model.addObject("merchant",s);
		model.addObject("userList",userList);
		model.setViewName("requestPayment");
		return model;
	}
	
	
	
	@RequestMapping(value = "/extReqSent", method = RequestMethod.POST)
	public ModelAndView modify11(@RequestParam(value = "merchantname", required = true) String mname, HttpServletRequest request){
		ModelAndView model = new ModelAndView();
		ExtPayRequest ereq = new ExtPayRequest();
		Validate v= new Validate();
		int check1=0;
		String message="Request sent successfully";
		//ExtPayService eps= new ExtPayService();
		String username= request.getParameter("userList");
		System.out.println(username);
		List<Users> usrList= new ArrayList();
		usrList=usr.listUser();
		for(Users u : usrList){
			if(u.getUsername().equals(mname))
				check1=1;				
		}
		if(username!=null && check1==1 && v.validateUsername(mname) && v.validateUsername(username)){
		ereq.setMerchant(mname);
		ereq.setUsername(username);
		ereq.setRequestStatus(0);
		try
		{
			eps.addExtRequest(ereq);
		}
		catch(DataIntegrityViolationException e)
		{
			message="Request Already sent";
		}
		}
		System.out.println("hi");
		model.addObject("message",message);
		model.setViewName("merchant");
		return model;
	}
	
	@RequestMapping(value = "/ViewRequests", method = RequestMethod.POST)
	public ModelAndView CustomerAcceptOption(@RequestParam(value = "username", required = true) String user){
		ModelAndView model = new ModelAndView();
		Validate v= new Validate();
		List<ExtPayRequest> toRemove= new ArrayList<ExtPayRequest>();
		System.out.println(user+" is user");
		List<ExtPayRequest> reqList=eps.showExtRequest();
		if(usr.checkUserdb(user) && v.validateUsername(user)){
		for (ExtPayRequest a : reqList)
		{
			
			if(!a.getUsername().equals(user) || a.getRequestStatus()==1)
				toRemove.add(a);
		}
		reqList.removeAll(toRemove);
		}
		System.out.println(reqList.size()+" is size");
		//EmployeeServiceImpl employeeserv=new EmployeeServiceImpl();
		
		
		model.addObject("reqList",reqList);
		model.addObject("user",user);
		model.setViewName("ViewRequests");
		return model;
	}
	@RequestMapping(value = "/extReqReject", method = RequestMethod.POST)
	public ModelAndView reject(@RequestParam(value = "user", required = true) String s,HttpServletRequest request){
		ModelAndView model = new ModelAndView();
		ExtPayRequest ereq = new ExtPayRequest();
		Validate v= new Validate();
		//ExtPayService eps= new ExtPayService();
		String mname= request.getParameter("merchantList");
		if(usr.checkUserdb(mname) && v.validateUsername(mname) && usr.checkUserdb(s) && v.validateUsername(s)){
		ereq.setMerchant(mname);
		ereq.setUsername(s);
		//ereq.setRequestStatus(1);
		eps.removeExtRequest(ereq);
		}
		model.addObject("message","Request Rejeted");
		model.setViewName("user");
		return model;
	}
	
	
	@RequestMapping(value = "/extReqConfirm", method = RequestMethod.POST)
	public ModelAndView CustomerAcceptOption(@RequestParam(value = "user", required = true) String s,HttpServletRequest request){
		ModelAndView model = new ModelAndView();
		ExtPayRequest ereq = new ExtPayRequest();
		Validate v=new Validate();
		//ExtPayService eps= new ExtPayService();
		String mname= request.getParameter("merchantList");
		System.out.println(mname);
		if(usr.checkUserdb(mname) && v.validateUsername(mname) && usr.checkUserdb(s) && v.validateUsername(s)){
			
		ereq.setMerchant(mname);
		ereq.setUsername(s);
		ereq.setRequestStatus(1);
		eps.updateExtReq(ereq);
		}
		System.out.println("hi");
		model.addObject("message","Request Accepted successfully");
		model.setViewName("user");
		return model;
	}
	
	
	
	
	@RequestMapping(value = "/ModifyDetailsUser", method = RequestMethod.POST)
	public ModelAndView modifyPage11(@ModelAttribute("customer") Customer customer, HttpServletRequest request) {
				
		String message1="Update not Allowed";
		int i = 0;
		System.out.println(request.getUserPrincipal());
				
		Validate val=new Validate();
		if(customer.getUsername().equals(request.getUserPrincipal()));
		{
		boolean bolu,bolf,boll,bolp,bole,bola;
		bolf=val.validateFirstname(customer.getFirstName());
		boll=val.validateLastname(customer.getLastName());
		bolu=val.validateUsername(customer.getUsername());
		
		bola=val.validateAddress(customer.getAddress());
		
		bole=val.validateEmail(customer.getEmailId());
		
		bolp=val.validatePhno((customer.getPhoneNumber())+"");
		System.out.println(bole+ " "+bolu+" "+bola+ " "+" "+bolp);
		if(bole && bolu && bola && bolp&&boll&&bolf){
		
		try{
			if(m.checkPermission(customer)==1)
			{i = m.updateCustomer(customer);
			message1="Update Successfull";
			}
		}
		catch(IOException ie)
		{
			ie.printStackTrace();
		}
		
		
			
		
		
		}
		}
		ModelAndView model = new ModelAndView();
		model.addObject("message",message1);
		model.setViewName("ModifyDetailsUser");
		
		return model;
}
	@RequestMapping(value="/viewStatement", method=RequestMethod.POST)
    public ModelAndView pendingTransactions( HttpServletRequest request){

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails details=(UserDetails)auth.getPrincipal();
		Validate v=new Validate();
		ModelAndView model=new ModelAndView();
		String username=details.getUsername();
		if(usr.checkUserdb(username) && v.validateUsername(username)){
		double chAmount=transactionService.getBalance(username,"Checking");
		double sAmount=transactionService.getBalance(username,"Savings");
		//Put your username here

		
		model.addObject("chAmount",chAmount);
		model.addObject("sAmount",sAmount);
		}
		model.setViewName("viewStatement");
		return model;
	}

@RequestMapping(value="/statement", method=RequestMethod.POST)
public ModelAndView savingState( @RequestParam(value = "accType", required = true) int accType,HttpServletRequest request){

	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	UserDetails details=(UserDetails)auth.getPrincipal();
	String username=details.getUsername();
	int AccNo=0;
	String acc="";
	//Put your username here
	Validate v=new Validate();
	ModelAndView model=new ModelAndView();
	List<Transaction> translist=new ArrayList();
	List debitlist=new ArrayList();
	List creditlist=new ArrayList();
	double chAmount=0.0;
	if(usr.checkUserdb(username) && v.validateUsername(username)){
		
	if(accType==1){
	AccNo=transactionService.getAccountNumber(username,"Checking");
	chAmount=transactionService.getBalance(username,"Checking");
	
	acc="Checking";
	}
	else if(accType==2){
		AccNo=transactionService.getAccountNumber(username,"Savings");
		acc="Savings";
		chAmount=transactionService.getBalance(username,"Savings");
		
	}
	}
	translist=transactionService.getAllTransactions(AccNo);
	debitlist=transactionService.getAllDebits(AccNo);
	creditlist=transactionService.getAllCredits(AccNo);
	
	model.addObject("acc",acc);
	model.addObject("accNo",AccNo);
	model.addObject("Balance",chAmount);
	model.addObject("transactionlist",translist);
	model.addObject("debitlist",debitlist);
	model.addObject("creditlist",creditlist);
	model.setViewName("statement");
	return model;
}

}

