

package com.sbs.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.sbs.model.AccountInfo;
import com.sbs.model.AuthorizationRequest;
import com.sbs.model.Customer;
import com.sbs.model.Employee;
import com.sbs.model.ModifyRequest;
import com.sbs.model.Roles;
import com.sbs.model.Transaction;
import com.sbs.model.Users;
import com.sbs.service.AuthorizationrequestService;
import com.sbs.service.CustomerService;
import com.sbs.service.CustomerServiceImpl;
import com.sbs.service.EmailSenderService;
import com.sbs.service.EmployeeService;
import com.sbs.service.EmployeeServiceImpl;
import com.sbs.service.PKICertificateService;
import com.sbs.service.RequestUserAuthorizationService;
import com.sbs.service.RoleServiceImpl;
import com.sbs.service.TransactionAuthorizationService;
import com.sbs.service.UserList;
import com.sbs.service.UserServiceImpl;
import com.sbs.validator.Validate;

/**
 * Handles requests for the application home page.
 */
@Controller
public class EmployeeController {
	
	// @Autowired the service object
	//private static final Logger logger = Logger.getLogger(HomeController.class);
	
	@Autowired
	SessionFactory sessionFactory;
	@Autowired 
	CustomerServiceImpl customerserv;
	@Autowired 
	RequestUserAuthorizationService reqauthserv;
	@Autowired 
    RoleServiceImpl roleServ;
	@Autowired 
    UserServiceImpl userServ;
	@Autowired 
	TransactionAuthorizationService transauthserv;
	@Autowired
	AuthorizationrequestService authreqserv;
	@Autowired
	UserList userlist;
	@Autowired
	PKICertificateService pkiserv;
	@Autowired
	EmailSenderService emailSenderService;
	
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */

	@RequestMapping(value="/createindividualuser", method=RequestMethod.GET)
    public ModelAndView createIndUserPage(){
		ModelAndView model= new ModelAndView();
	    model.addObject("title","Please fill the form");
	    model.setViewName("newindividualuser");
		return model;
	
	}
	
	
	@RequestMapping(value="/newindividualuser", method=RequestMethod.POST)
	@Transactional
    public ModelAndView newIndividualUserPage(@ModelAttribute("customer") Customer customer,Principal principal){
		String userName;
		String password;
		Session session=sessionFactory.getCurrentSession();
		String currentUser=principal.getName();
		ModelAndView model=new ModelAndView();
		Users user=new Users();
		Roles role=new Roles();
		
		Validate val=new Validate();
		
		boolean bolu,bolf,boll,bolp,bole, bol,bolr;
		bol=val.validateAddress(customer.getAddress());
		bole=val.validateEmail(customer.getEmailId());
		
		bolf=val.validateFirstname(customer.getFirstName());
		boll=val.validateLastname(customer.getLastName());
		bolp=val.validatePhno(customer.getPhoneNumber());
		bolr=val.validateRole(customer.getRole());
		
		System.out.println(bole +" "+bolf +" "+ boll +" "+ bolp+" "+bol+" "+bolr);
		
		if(bole && bolf && boll && bolp&&bol&&bolr){
			userName=generateUserName(customer.getFirstName(),customer.getLastName());
			if(!val.validateUsername(userName)){
				model.setViewName("addingcustomerfailed");
				return model;
			}
			password=generatePassword();
			customer.setUsername(userName);
			String shaPassword=DigestUtils.sha256Hex(password);
			user.setUsername(userName);
			user.setPassword(shaPassword);
			role.setUsername(userName);
			role.setRole(customer.getRole());
			customer.setUsername(userName);
			 try
			    {
			    userServ.addUser(user);
			    }
			    catch(Exception e)
			    {
			    	System.out.println("User couldnt be added "+e);
			    	System.exit(0);
			    }
			 
			 try
			    {
			    roleServ.addRole(role,currentUser);
			    }
			    catch(Exception e)
			    {
			    	System.out.println("Role couldnt be added "+e);
			    	System.exit(0);
			    }
	    try
	    {
	    customerserv.addCustomer(customer,password,currentUser);
	    }
	    catch(Exception e)
	    {
	    	System.out.println("Customer couldnt be added"+e);
	    	System.exit(0);
	    }
	    
	   
	    
	    
	    //Adding the account Info
	    try
	    {
	    	
	    	int i=1;
	    	while(i<3)
	    	{
	    		if(i==1)
	    		{
	    			System.out.println("Creating Checking account");
	    			AccountInfo checkingaccount=new AccountInfo();
	    			checkingaccount.setUsername(userName);
	    			checkingaccount.setAccountType("Checking");
	    			int accountNumber=generateAccountNumber();
					checkingaccount.setAccountNumber(accountNumber);
					customerserv.addCustomerAccount(checkingaccount,currentUser);
					
	    		}
	    		else if(i==2)
	    		{
	    			System.out.println("Creating Savings account");
	    			AccountInfo savingsaccount=new AccountInfo();
	    			savingsaccount.setUsername(userName);
	    			savingsaccount.setAccountType("Saving");
	    			int accountNumber=generateAccountNumber();
					savingsaccount.setAccountNumber(accountNumber);
					customerserv.addCustomerAccount(savingsaccount,currentUser);
	    		}
	    		i++;
	    	}
	    }
	    catch(Exception e)
	    {
	    	System.out.println("Account couldnt be added "+e);
	    	e.printStackTrace();
	    	System.exit(0);
	    }
	    ApplicationContext context = new ClassPathXmlApplicationContext("Spring-Mail.xml");
	    emailSenderService = (EmailSenderService) context.getBean("emailSenderService");

	    	    pkiserv.createPKICertForUsers(currentUser, password, customer.getEmailId());

	    model.setViewName("newcustomeradded");
		}
		else
			model.setViewName("addingcustomerfailed");
	return model;
		
	}
	
	
	@RequestMapping(value="/deleteexternaluser", method=RequestMethod.GET)
    public ModelAndView deleteExternalUserPage(){
		ModelAndView model= new ModelAndView();
		List<Customer> userList=new ArrayList();
		userList=userlist.getFullCustomerList();
	    model.addObject("userList",userList);
	    model.setViewName("deleteexternaluser");
		return model;
	
	}
	
	@RequestMapping(value="/removeexternaluser", method=RequestMethod.POST)
	@Transactional
    public ModelAndView removeExternalUserPage(HttpServletRequest request, Principal principal){
		Session session=sessionFactory.getCurrentSession();
		String currentUser=principal.getName();
		String user=request.getParameter("customer");
		ModelAndView model=new ModelAndView();
		Customer customer=null;
		Users user1=null;
		Roles role=null;
		Validate validate=new Validate();
		Boolean boolu=validate.validateUsername(user);
		if(boolu)
		{
		List<Customer> custlist=userlist.getCList(user);
		for(Customer cust: custlist)
			customer=cust;
		List<Roles> rolelist=userlist.getRoleList(user);
		for(Roles rle: rolelist)
			role=rle;
		List<Users> userlst=userlist.getUsrList(user);
		for(Users usr: userlst)
			user1=usr;
		for(Roles rle: rolelist)
			role=rle;
//		String userName;
//		String password;


		//user.setUsername(userName);
	    try
	    {
	    customerserv.removeCustomer(customer,currentUser);
	    }
	    catch(Exception e)
	    {
	    	System.out.println("Customer couldnt be removed");
	    	System.exit(0);
	    }
	    try
	    {
	    	roleServ.deleteRole(role, currentUser);
	    }
	    catch(Exception e)
	    {
	    	System.out.println("Role couldnt be removed");
	    	System.exit(0);
	    }
	    try
	    {
	    	userServ.deleteUser(user1, currentUser);
	    }
	    catch(Exception e)
	    {
	    	System.out.println("User couldnt be removed");
	    	System.exit(0);
	    }
	    try
	    {
	     customerserv.deleteCustomerAccounts(user, currentUser);
	    }
	    catch(Exception e)
	    {
	     System.out.println("Account couldnt be removed");
	    	System.exit(0);
	    }
		model.setViewName("customerremoved");
		}
		else
		{
			model.setViewName("addingcustomerfailed");
		}
		return model;
		
	}
	
	@RequestMapping(value="/updatecustomer", method=RequestMethod.GET)
    public ModelAndView updateExternalUserPage(){
		ModelAndView model= new ModelAndView();
		List<Roles> userlists=new ArrayList();
		userlists=userlist.getUserList();
		System.out.println("update User list"+userlists);
	    model.addObject("title","Please fill the form");
        model.addObject("userlist",userlists);
        model.setViewName("customerupdate");
		return model;
	}
	
	@RequestMapping(value = "/modifycustomer", method = RequestMethod.POST)
	public ModelAndView modify(HttpServletRequest request){
		ModelAndView model = new ModelAndView();
		ModifyRequest mreq = new ModifyRequest();
		String customername=request.getParameter("customer");
		Customer customer= new Customer();
		List<Customer> lus=new ArrayList<Customer>(); 
		System.out.println("Customer name"+customername);
		Validate validate=new Validate();
		Boolean boolu=validate.validateUsername(customername);
		if(boolu)
		{
	    lus=userlist.getCustomerList(customername);
		for(Customer cus: lus){
			if(cus.getUsername().equals(customername))
				customer=cus;
		}		
		
		
			System.out.println("Customer "+lus);
		model.addObject("customer",customer);
		model.setViewName("ModifyDet");
		}
		else
		{
			model.setViewName("addingcustomerfailed");
		}
		return model;
	}
	

	@RequestMapping(value = "/ModifyDetailsUser2", method = RequestMethod.POST)
	public ModelAndView modifyPage(@ModelAttribute("customer") Customer customer,Principal principal) {
		String currentuser=principal.getName();
		String message1="Update not Allowed";
		int i = 0;
		System.out.println("in modify detail");
				
		Validate val=new Validate();
		
		boolean bolu,bola,boll,bolp,bole;
		bole=val.validateEmail(customer.getEmailId());
		bolu=val.validateUsername(customer.getUsername());
		bolp=val.validatePhno(customer.getPhoneNumber());
		bola=val.validateAddress(customer.getAddress());
		if(bole && bolu && bolp && bola){
		
			try{
			customerserv.updateCustomer(customer, currentuser);
			}
		catch(Exception ie)
		{
			ie.printStackTrace();
		}
	
	}
		ModelAndView model = new ModelAndView();
		model.addObject("message",message1);
		model.setViewName("modifiedcustomer");
		return model;
}

	
	@RequestMapping(value="/requestauthorization", method=RequestMethod.GET)
    public ModelAndView listOfUsersPage(){
		ModelAndView model=new ModelAndView();
		List<Roles> userlists=new ArrayList();
		userlists=userlist.getUserList();
	    model.addObject("userlist",userlists);	    
		model.setViewName("requestauthorization");
		return model;
		
	}
	
	@RequestMapping(value="/submitauthrequest", method=RequestMethod.GET)
	@Transactional
    public ModelAndView submitAuthRequestPage(HttpServletRequest request,Principal principal){
		String employeeName;
		Session session=sessionFactory.getCurrentSession();
		String customerName;
		AuthorizationRequest requestauth=new AuthorizationRequest();
		ModelAndView model=new ModelAndView();
		UserList userlist=new UserList();
		employeeName=principal.getName();
		customerName=request.getParameter("customer");
		Validate validate=new Validate();
		boolean boolu=validate.validateUsername(customerName);
		if(boolu)
		{
		requestauth.setCustomername(customerName);
		requestauth.setEmployeename(employeeName);
		requestauth.setRequeststatus(0);
		reqauthserv.requestAuthorization(requestauth);
		model.setViewName("authreqsubmitted");
		}
		else
		{
			model.setViewName("addingcustomerfailed");
		}
		return model;	
	}
	
	@RequestMapping(value="/viewauthorizedtransactions", method=RequestMethod.GET)
    public ModelAndView authorizedlistofUsersPage(HttpServletRequest request,Principal principal){
		ModelAndView model=new ModelAndView();
	    //  String name =  //get logged in username
		String currentUser=principal.getName();
		System.out.println("Currently Used by :"+currentUser);
		List<AuthorizationRequest> userlists=new ArrayList();
		userlists=userlist.getAuthorizedUserList(currentUser);
	    model.addObject("userlist",userlists);	    
		model.setViewName("viewauthorizedusers");
		return model;
		
	}
	
	@RequestMapping(value="/viewcustomertransact", method=RequestMethod.POST)
    public ModelAndView viewCustomerTransactPage(HttpServletRequest request){
		ModelAndView model=new ModelAndView();    
		request.getAttribute("customer");
		String customername=request.getParameter("customer");
		List transList=new ArrayList();
		List debitList=new ArrayList();
		List creditList=new ArrayList();
		Validate validate=new Validate();
		boolean boolu=validate.validateUsername(customername);
		int checking=0;
		int savings=0;
		if(boolu)
		{
		List<AccountInfo> checkingList=userlist.getAccountNumber(customername, "Checking");
		for(AccountInfo acct: checkingList){
			checking=acct.getAccountNumber();
		}	
		List<AccountInfo> savingsList=userlist.getAccountNumber(customername, "Savings");
		for(AccountInfo acct: savingsList){
			savings=acct.getAccountNumber();
		}
		System.out.println("\n\n\nAccount "+checking+" "+savings+"\n\n");
		transList=userlist.getAuthorizedCustomerTransactionList(customername,checking,savings);
		creditList=userlist.getAuthorizedCustomerCreditList(customername,checking,savings);
		debitList=userlist.getAuthorizedCustomerDebitList(customername,checking,savings);
		model.addObject("transList",transList);
		model.addObject("creditList",creditList);
		model.addObject("debitList",debitList);
		model.setViewName("customertransaction");
		}
		else
		{
			model.setViewName("addingcustomerfailed");
		}
		return model;
	}
	
	@RequestMapping(value="/authorizecriticaltransactions", method=RequestMethod.GET)
	@Transactional
    public ModelAndView authorizecriticalTransPage(HttpServletRequest request){
		ModelAndView model=new ModelAndView();    
		List<Transaction> translist=new ArrayList();
		translist=transauthserv.getCriticalTransaction();
		model.addObject("transactionlist",translist);
		model.setViewName("criticaltransactions");
		return model;
	}
	
	@RequestMapping(value="/submittransactionapproval", method=RequestMethod.POST)
	@Transactional
    public ModelAndView transactionapproval(HttpServletRequest request,Principal principal){
		ModelAndView model=new ModelAndView();  
		
		String decision;
		String transactionid;
		Validate validate=new Validate();
		boolean boolt;
	    transactionid=request.getParameter("transaction");
	    boolt=validate.validateTransactionId(transactionid);
		String currentUser=principal.getName();
		if(boolt)
		{
		decision=request.getParameter("decision");
		if(decision.equals("Yes"))
		{

			transauthserv.updateTransaction(transactionid,"yes",currentUser);
		}
		else
		{
			transauthserv.updateTransaction(transactionid,"no",currentUser);
			
		}
		
		model.setViewName("criticaltransactionsapproved");
		}
		else
		{
			model.setViewName("addingcustomerfailed");
		}
		return model;
	}
	
	
	// Should be moved to customers controller. Should be used only for development purpose:
	
	@RequestMapping(value="/authorizerequestview", method=RequestMethod.GET)
    public ModelAndView authorizerequestPage(HttpServletRequest request,Principal principal){
		ModelAndView model=new ModelAndView();  
		String currentUser=principal.getName();
		List<AuthorizationRequest> list=authreqserv.getEmployeeList(currentUser);
		model.addObject("empList", list);
		model.setViewName("authorizerequest");
		return model;
		
	}
	
	@RequestMapping(value="/authorizerequest", method=RequestMethod.GET)
	@Transactional
    public ModelAndView authorizeRequest(HttpServletRequest request,Principal principal){
		ModelAndView model=new ModelAndView();
		String decision;
		String employee;
		String currentUser=principal.getName();	
		decision=request.getParameter("decision");
		Validate validate=new Validate();
		boolean boolu;
		if(decision.equals("Yes"))
		{
			employee=request.getParameter("employee");
			boolu=validate.validateUsername(employee);
			if(boolu)
			{
			System.out.println("Customer "+ currentUser +" has approved :"+employee);
			authreqserv.updateAuthorizationRequest(currentUser, employee);
			model.setViewName("approvalprovided");
			}
			else
			{
				model.setViewName("addingcustomerfailed");
			}
			
			
		}
		else
		{
			employee=request.getParameter("employee");
			boolu=validate.validateUsername(employee);
			if(boolu)
			{
			System.out.println("Customer "+ currentUser +" has declined :"+employee);
			authreqserv.deleteRequest(currentUser, employee);
			model.setViewName("approvalprovided");
			}
			else
			{
				model.setViewName("addingcustomerfailed");
			}
			
		}
		model.setViewName("approvalprovided");
		return model;
		
	}
	
	@RequestMapping(value="/authorizemodifyrequest", method=RequestMethod.GET)
    public ModelAndView authorizemodifyrequestPage(HttpServletRequest request,Principal principal){
		ModelAndView model=new ModelAndView();  
		List requestlist=new ArrayList();
		requestlist=userlist.getRequestList();
		System.out.println("Modify Request "+requestlist);
		model.addObject("requestlist",requestlist);
		model.setViewName("authorizemodifyrequest");
		return model;
	}	
	
	@RequestMapping(value="/authorizemodification", method=RequestMethod.POST)
	@Transactional
    public ModelAndView authorizeModification(HttpServletRequest request,Principal principal){
		ModelAndView model=new ModelAndView();
		String decision;
		String customer;
		Validate validate=new Validate();
		boolean boolu;
		String currentUser=principal.getName();	
		decision=request.getParameter("decision");
		if(decision.equals("Yes"))
		{
			customer=request.getParameter("customer");
			boolu=validate.validateUsername(customer);
			if(boolu)
			{
			System.out.println("Customer "+ currentUser +" has approved :"+customer);
			authreqserv.updateModifyRequest(currentUser, customer);	
			model.setViewName("approvalprovided");
			}
			else
			{
				model.setViewName("addingcustomerfailed");
			}
		}
		else
		{
			customer=request.getParameter("customer");
			boolu=validate.validateUsername(customer);
			if(boolu)
			{
			System.out.println("Customer "+ currentUser +" has declined :"+customer);
			authreqserv.deleteModifyRequest(currentUser, customer);
			model.setViewName("approvalprovided");
			}
			else
			{
				model.setViewName("addingcustomerfailed");
			}
			
		}
		return model;
		
	}
	
	public String generateUserName(String firstName,String lastName)
	{
		int random=(int) Math.ceil(Math.random()*100);
		String userName="";
		userName=firstName.charAt(0)+lastName.substring(0, lastName.length())+random;
		return userName;
	}
	
	public String generatePassword()
	{
		String password=UUID.randomUUID().toString().substring(0, 8);
		return password;
	}
	
	public int generateAccountNumber()
	{
		int accountnumber;
		
		Random rn = new Random();
		int answer = rn.nextInt(100000) + 1;
		accountnumber=answer;
		
		return accountnumber;
	}


}
