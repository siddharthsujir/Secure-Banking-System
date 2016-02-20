package com.sbs.controller;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.sbs.model.Customer;
import com.sbs.model.EmpReqUser;
import com.sbs.model.Employee;
import com.sbs.model.EmployeeReqUser;
import com.sbs.model.Roles;
import com.sbs.model.Transaction;
import com.sbs.model.Users;
import com.sbs.service.EmployeeService;
import com.sbs.service.EmployeeServiceImpl;
import com.sbs.service.TransactionService;
import com.sbs.service.UserList;
import com.sbs.validator.Validate;
import com.sbs.validator.transacValidator;

@Controller
public class IntUserController {

	@Autowired
	SessionFactory sessionFactory;
	@Autowired 
	EmployeeServiceImpl employeeserv;
	@Autowired 
	TransactionService transactionService;
	@Autowired
	UserList userlist;
	
	
	@RequestMapping(value="/CreateRegUser", method=RequestMethod.POST)
    public ModelAndView createRegPage(){
		ModelAndView model= new ModelAndView();
	    model.addObject("title","Please fill the form");
	    model.setViewName("NewRegUser");
		return model;
	
	}
	
	
	@RequestMapping(value="/NewRegUser", method=RequestMethod.POST)
    public ModelAndView NewRegPage(@ModelAttribute("emp") Employee emp){
		System.out.println(emp.getFname()+emp.getLname());
		ModelAndView model=new ModelAndView();
		Validate val=new Validate();
		
		boolean bolu,bolf,boll,bolp,bole,bola,bolr;
		bole=val.validateEmail(emp.getEmail());
		bolu=val.validateUsername(emp.getUsername());
		bolf=val.validateFirstname(emp.getFname());
		boll=val.validateLastname(emp.getLname());
		bolp=val.validatePhno(emp.getPhno());
		bola=val.validateAddress(emp.getAddress());
		bolr=val.validateCorrectRole(emp.getDesname());
		System.out.println(bole+ " "+bolu+" "+bolf+" "+boll+" "+bolp);
		if(bole && bolu && bolf && boll && bolp&&bolr){
	    
		//EmployeeServiceImpl employeeserv=new EmployeeServiceImpl() ;
		employeeserv.addEmployee(emp);
		model.addObject("title","Employee Stored successfully");
		}
		else{
			model.addObject("title","Employee not stored!!!");
		}
		model.setViewName("NewRegUserConfm");
		return model;
		
	}
	/*@RequestMapping(value="/UpdateRegUser", method=RequestMethod.POST)
    public ModelAndView UpdateRegPage(){
		ModelAndView model= new ModelAndView();
	    model.addObject("title","Please enter email address");
	    model.setViewName("NewUpdateRegUser");
		return model;
	
	}
	
	@RequestMapping(value="/NewEmailUpdateRegUser", method=RequestMethod.POST)
    public ModelAndView NewUpdateRegPage(@ModelAttribute("emp") Employee emp){
		//System.out.println(emp.getFname()+emp.getLname());
		ModelAndView model=new ModelAndView();
	    //EmployeeServiceImpl employeeserv=new EmployeeServiceImpl() ;
		employeeserv.updateEmployee(emp);
		model.setViewName("NewUpdateRegUserConfm");
		return model;
		
	}*/
	@Transactional
	@RequestMapping(value="/DeleteRegUser", method=RequestMethod.POST)
    public ModelAndView DeleteRegPage(){
		ModelAndView model= new ModelAndView();
		Session session=sessionFactory.getCurrentSession();
		List<Roles> usersList=session.createQuery("from Roles r where r.role=:role or r.role=:role1").setParameter("role","ROLE_EMPLOYEE").setParameter("role1", "ROLE_MANAGER").list();
		model.addObject("person", usersList);
		//model.addObject("title","Please enter Username");
	    model.setViewName("NewDeleteRegUser");
		return model;
	
	}
	@Transactional
	@RequestMapping(value="/NewDelRegUser", method=RequestMethod.POST)
    public ModelAndView NewDelRegPage(HttpServletRequest request){
		//System.out.println(emp.getFname()+emp.getLname());
		ModelAndView model=new ModelAndView();
	    //EmployeeServiceImpl employeeserv=new EmployeeServiceImpl() ;
		//employeeserv.deleteEmployee(emp);
		Session session=sessionFactory.getCurrentSession();
		Employee emp2= null;
		Roles role2=null;
		Users usr2=null;
		String inter= request.getParameter("internaluser");
		List<Employee> UserList=session.createQuery("from Employee e where e.username=:user").setParameter("user",inter).list();
		for(Employee emp : UserList){
			emp2=emp;
		    
		}
		session.delete(emp2);
		List<Roles> UserList1=session.createQuery("from Roles e where e.username=:user").setParameter("user",inter).list();
		for(Roles rol : UserList1){
			role2=rol;
		}
		session.delete(role2);
		List<Users> UserList2=session.createQuery("from Users e where e.username=:user").setParameter("user",inter).list();
		for(Users usr : UserList2){
			usr2=usr;
		}
		session.delete(usr2);
		
		//model.addObject("person", UserList);
		model.setViewName("NewDeleteRegUserConfm");
		return model;
		
	}
	@Transactional
	@RequestMapping(value="/RequestForAuth", method=RequestMethod.POST)
	public ModelAndView RequestForAuth(){
		System.out.println("\n\nRequestForAuth\n\n");
		Session session=sessionFactory.getCurrentSession();
		ModelAndView model=new ModelAndView();
		String person;
		List<Roles> usersList=session.createQuery("from Roles r where r.role=:role").setParameter("role","ROLE_USER").list();
		model.addObject("person", usersList);
		model.setViewName("NewRequestForAuthConfm");
		return model;
		
	}
	@RequestMapping(value = "/empreqcust", method = RequestMethod.POST)
	public ModelAndView emprequestcust(HttpServletRequest request) {
		ModelAndView model = new ModelAndView();
		EmpReqUser emp = new EmpReqUser();
		String extuser = request.getParameter("extuser");
	    String intuser=request.getParameter("intuser");
	    emp.setExtuser(extuser);
	    emp.setIntuser(intuser);
        emp.setRequest(1);
	    //System.out.println(extuser+""+intuser);
        //model.addObject("cust",extuser);
        //EmployeeServiceImpl employeeserv=new EmployeeServiceImpl();
        employeeserv.requestCustAuth(emp);
        model.addObject("title","Sent Successfully");
        model.setViewName("NewEmpReqCust");
		return model;
	}
	@Transactional
	@RequestMapping(value="/RequestFromEmp", method=RequestMethod.GET)
	public ModelAndView RequestFromEmp(HttpServletRequest request){
		
		//SessionFactory sessionFactory=new Configuration().configure().buildSessionFactory();
		Session session=sessionFactory.getCurrentSession();
		ModelAndView model = new ModelAndView();
		String cust=request.getParameter("auth");
		List<EmpReqUser> usersList=session.createQuery("from EmpReqUser e where e.extuser=:ext and e.request=:req").setParameter("ext",cust).setParameter("req",1).list();
		if(usersList.size()>=1){
		model.addObject("person", usersList);
		model.setViewName("NewRequestFromEmp");
		}
		else{
			model.setViewName("NoRequestYet");
		}
		return model;
	}
	
	@RequestMapping(value="/customerAccept", method=RequestMethod.GET)
	public ModelAndView CustomerAcceptOption(HttpServletRequest request){
		ModelAndView model = new ModelAndView();
		//EmployeeServiceImpl employeeserv=new EmployeeServiceImpl();
		String inter=request.getParameter("auth");
		//System.out.println(inter);
		employeeserv.updateEmpReqUser(inter);
		model.addObject("title","Option selected Successfully");
		model.setViewName("NewCustomerOption");
		return model;
	}	
	
	@RequestMapping(value="/customerReject", method=RequestMethod.GET)
	public ModelAndView CustomerRejectOption(){
		ModelAndView model = new ModelAndView();
		model.addObject("title","Option selected Successfully");
		model.setViewName("NewCustomerOption");
		return model;
	}	
	@Transactional	
	@RequestMapping(value="/RequestAcceptedByUser", method=RequestMethod.GET)
	public ModelAndView RequestAcceptbyUser(HttpServletRequest request){
	ModelAndView model = new ModelAndView();
	String inter=request.getParameter("auth");
	Session session=sessionFactory.getCurrentSession();
	   List<EmpReqUser> usersList=session.createQuery("select E.extuser from EmpReqUser E where E.request=:req and E.intuser=:int").setParameter("req", 0).setParameter("int", inter).list();
	  // System.out.println(usersList.size());
	   //System.out.println(usersList.get(0));
	   model.addObject("person", usersList);
	   model.setViewName("NewRequestAcceptedByUser");
	return model;
	}
	
	@RequestMapping(value="/initialTransac", method=RequestMethod.POST)
	public ModelAndView InitialTransacOption(HttpServletRequest request){
		ModelAndView model = new ModelAndView();
		String extuser = request.getParameter("extuser");
		//System.out.println(extuser);
		request.getSession().setAttribute("tran", extuser);
		model.addObject("person",extuser);
		model.setViewName("initialTransacOfUser");
		return model;
	}
	
	@RequestMapping(value="/UserAccount", method=RequestMethod.GET)
	public ModelAndView UserAccountOption(HttpServletRequest request){
		ModelAndView model = new ModelAndView();
		String extuser = request.getParameter("extuser");
		System.out.println(extuser);
		model.addObject("person",extuser);
		model.setViewName("UserAccountPage");
		return model;
	}
	
	@RequestMapping(value="/TimeOutEmployee", method=RequestMethod.GET)
    public ModelAndView TimeOut(){
		ModelAndView model= new ModelAndView();
		try{
		    Thread.sleep(3000);
		    model.addObject("title","Please enter Username");
		    model.setViewName("NewTimeOut");
		}catch(InterruptedException e){
		    e.printStackTrace();
		}
		return model;
	}
	@Transactional
	@RequestMapping(value="/RequestForUserAuth", method=RequestMethod.GET)
	public ModelAndView RequestForUserAuth(){
		//System.out.println("\n\nRequestForAuth\n\n");
		Session session=sessionFactory.getCurrentSession();
		ModelAndView model=new ModelAndView();
		String person;
		List<Roles> usersList=session.createQuery("from Roles r where r.role=:role").setParameter("role","ROLE_USER").list();
		model.addObject("person", usersList);
		model.setViewName("NewRequestForUserAuthConfm");
		return model;
		
	}
	@Transactional
	@RequestMapping(value="/viewTransactionsofUser",method=RequestMethod.POST)
    public ModelAndView RequestTransacview(HttpServletRequest request){
		/*ModelAndView model= new ModelAndView();
		Session session=sessionFactory.getCurrentSession();
		List<Transaction> usersList=session.createQuery("from Transaction t where  ").list();
		model.addObject("title",usersList);*/
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails details=(UserDetails)auth.getPrincipal();
		//String username= request.getParameter("tran");
		String username=(String) request.getSession().getAttribute("tran");
      //System.out.println(username);
		//Put your username here

		ModelAndView model=new ModelAndView();
		List<Transaction> translist=new ArrayList();
		List accounts=new ArrayList();
		accounts.add(transactionService.getAccountNumber(username,"Checking"));
		accounts.add(transactionService.getAccountNumber(username,"Savings"));
		//int checkingAccountNumber=transactionService.getAccountNumber(username,"Checking");
		//int savingsAccountNumber=transactionService.getAccountNumber(username,"Savings");
		translist=transactionService.getPendingTransactions(accounts);
		model.addObject("transactionlist",translist);
		model.setViewName("NewviewTransactionodUser");
		//request.getSession().removeAttribute("tran");
	  return model;
	}
	
	
	
	@Transactional
	@RequestMapping(value="/ModifyTransactionsofUser",method=RequestMethod.POST)
    public ModelAndView RequestTransacModify(HttpServletRequest request){
		ModelAndView model=new ModelAndView();
		String username=(String) request.getSession().getAttribute("tran");
		//System.out.println(username);
		List<Transaction> translist=new ArrayList();
		List accounts=new ArrayList();
		accounts.add(transactionService.getAccountNumber(username,"Checking"));
		accounts.add(transactionService.getAccountNumber(username,"Savings"));
		translist=transactionService.getPendingTransactions(accounts);
		model.addObject("transactionlist",translist);
		model.setViewName("NewModifyTransactionofUser");
		return model;
	}
	
	@Transactional
	@RequestMapping(value="/modiTransactions",method=RequestMethod.POST)
	 public ModelAndView RequestPerTracModify(HttpServletRequest request){
		ModelAndView model=new ModelAndView();
		long transac=Long.parseLong(request.getParameter("transaclist"));
	//    transac =longrequest.getParameter("transaclist");
		String username=(String) request.getSession().getAttribute("tran");
		//System.out.println(transac+ "  username "+ username);
		//EmployeeServiceImpl employeeserv= new EmployeeServiceImpl();
		List accounts=new ArrayList();
		accounts.add(transactionService.getAccountNumber(username,"Checking"));
		accounts.add(transactionService.getAccountNumber(username,"Savings"));
		Transaction t= new Transaction();
		List<Transaction> lus=new ArrayList<Transaction>(); 
         		lus=employeeserv.getAllTransactions(accounts);
         System.out.println(lus.size());		
         for(Transaction a: lus){
        		if(a.getTransaction_id()==transac)
        			t=a;
        	}
        //System.out.println(t.getTransaction_id()+" "+t.getFromAccount());
       model.addObject("title",t); 
		model.setViewName("NewModifyTransacOfUser");
		return model;
	}
	
	@RequestMapping(value="/TransacAmountModify",method=RequestMethod.POST)
	 public ModelAndView RequestTransacAmountModify(HttpServletRequest request){
		System.out.println("\n\nIn here\n\n");
		ModelAndView model=new ModelAndView();
		transacValidator val= new transacValidator();
		Transaction t=new Transaction();
		boolean bolt,bolts,bolfa,bolta,bola,bold;
		bolt=val.validateTransactionId(Long.parseLong(request.getParameter("transaction_id")));
		bolts=val.validateTransactionStatus((request.getParameter("transactionStatus")));
		bolfa=val.validateFromAccount(Integer.parseInt(request.getParameter("fromAccount")));
		bolta=val.validateToAccount(Integer.parseInt(request.getParameter("toAccount")));
		bola=val.validateAmount(Double.parseDouble(request.getParameter("amount")));
		bold=val.validateDiscription(request.getParameter("description"));
		System.out.println( bolt+" "+bolts+" "+bolfa+" "+bolta+" "+bola+" "+bold);
		System.out.println(Long.parseLong(request.getParameter("transaction_id")));
		System.out.println((request.getParameter("transactionStatus")));
		System.out.println(Integer.parseInt(request.getParameter("fromAccount")));
		System.out.println(Integer.parseInt(request.getParameter("toAccount")));
		System.out.println(Double.parseDouble(request.getParameter("amount")));
		System.out.println(request.getParameter("description"));
		System.out.println(bolt+" "+bolts+" "+bolfa+" "+bolta+" "+bola+" "+bold);
		if(bolt && bolts && bolfa && bolta && bola && bold){
			t.setTransaction_id(Long.parseLong(request.getParameter("transaction_id")));
			t.setTransactiontime(new Date());
			t.setTransactionStatus((request.getParameter("transactionStatus")));
			t.setFromAccount(Integer.parseInt(request.getParameter("fromAccount")));
			t.setToAccount(Integer.parseInt(request.getParameter("toAccount")));
			t.setAmount(Double.parseDouble(request.getParameter("amount")));
			t.setDescription(request.getParameter("description"));
			t.setTransactionType(request.getParameter("transactionType"));
			employeeserv.upTran(t);
			model.addObject("title", "transaction updated succesfully");
		}
		else{
			model.addObject("title", "Please check the values you have updated");
		}
		//String transac = request.getParameter("amount");
		//System.out.println(t.getTransaction_id());
		//System.out.println(t.getAmount());
		//System.out.println("hello");
		model.setViewName("NewTransacAmountModify");
		return model;
	}
	
	@RequestMapping(value="/DeleteTransactionsofUser",method=RequestMethod.POST)
	 public ModelAndView RequestTransacDel(HttpServletRequest request){
		ModelAndView model=new ModelAndView();
		String username=(String) request.getSession().getAttribute("tran");
		List<Transaction> translist=new ArrayList();
		List accounts=new ArrayList();
		accounts.add(transactionService.getAccountNumber(username,"Checking"));
		accounts.add(transactionService.getAccountNumber(username,"Savings"));
		translist=transactionService.getPendingTransactions(accounts);
		model.addObject("transactionlist",translist);
		model.setViewName("NewDeleteTransactionsofUser");
		return model;
	}
	
	
	@RequestMapping(value="/deleteTransaction",method=RequestMethod.POST)
	 public ModelAndView TransacDel(HttpServletRequest request){
		ModelAndView model=new ModelAndView();
		long tid= Long.parseLong(request.getParameter("transaclist"));
		//System.out.println(tid);
		String username=(String) request.getSession().getAttribute("tran");
		//System.out.println(transac+ "  username "+ username);
		//EmployeeServiceImpl employeeserv= new EmployeeServiceImpl();
		List accounts=new ArrayList();
		accounts.add(transactionService.getAccountNumber(username,"Checking"));
		accounts.add(transactionService.getAccountNumber(username,"Savings"));
		Transaction t= new Transaction();
		List<Transaction> lus=new ArrayList<Transaction>(); 
         		lus=employeeserv.getAllTransactions(accounts);
         System.out.println(lus.size());		
         for(Transaction a: lus){
        		if(a.getTransaction_id()==tid)
        			t=a;
        	}
         employeeserv.delTran(t);
		model.addObject("title", "Trasaction Deleted Succesfully");
		model.setViewName("NewDeleteTransaction");
	   return model;
	}
	@RequestMapping(value="/EmpReqUserAccount", method=RequestMethod.POST)
	public ModelAndView RequestEmpReqUserAccount(HttpServletRequest request){
		ModelAndView model=new ModelAndView();
		EmployeeReqUser emp= new EmployeeReqUser();
		String extuser = request.getParameter("extuser");
	    String intuser=request.getParameter("intuser");
	    emp.setCustomer(extuser);
	    emp.setEmployee(intuser);
        emp.setRequest(1);
	    System.out.println(intuser+""+extuser);
	    //EmployeeServiceImpl employeeserv=new EmployeeServiceImpl();
        employeeserv.requestCustomerAuth(emp);
		model.setViewName("NewFinalEmpReqUser");
		return model;
	}
	@Transactional
	@RequestMapping(value="/ReqFromEmp", method=RequestMethod.GET)
	public ModelAndView ReqFromEmp(HttpServletRequest request){
	    Session session=sessionFactory.getCurrentSession();
		ModelAndView model = new ModelAndView();
		String cust=request.getParameter("auth");
		System.out.println(cust);
		List<EmployeeReqUser> usersList=session.createQuery("from EmployeeReqUser e where e.Customer=:ext and e.request=:req").setParameter("ext",cust).setParameter("req",1).list();
		//System.out.println(usersList.get(0));
		
		if(usersList.size()>=1){
		model.addObject("person", usersList);
		model.setViewName("NewReqFromEmp");
		}
		else{
			model.setViewName("NoRequestYet");
		}
		//model.setViewName("NewReqFromEmp");
		return model;
	}
	@RequestMapping(value="/customerAcc", method=RequestMethod.GET)
	public ModelAndView CustomerAccOption(HttpServletRequest request){
		ModelAndView model = new ModelAndView();
		//EmployeeServiceImpl employeeserv=new EmployeeServiceImpl();
		String inter=request.getParameter("auth");
		System.out.println(inter);
		employeeserv.updateEmployeeReqUser(inter);
		model.addObject("title","Option selected Successfully");
		model.setViewName("NewCustomerOption");
		return model;
	}	
	@RequestMapping(value="/customerRej", method=RequestMethod.GET)
	public ModelAndView CustomerRejOption(){
		ModelAndView model = new ModelAndView();
		model.addObject("title","Option selected Successfully");
		model.setViewName("NewCustomerOption");
		return model;
	}
	@Transactional	
	@RequestMapping(value="/RequestAccByUser", method=RequestMethod.GET)
	public ModelAndView RequestAccbyUser(HttpServletRequest request){
		ModelAndView model = new ModelAndView();
		String inter=request.getParameter("auth");
		Session session=sessionFactory.getCurrentSession();
	    List<EmpReqUser> usersList=session.createQuery("select E.Customer from EmployeeReqUser E where E.request=:req and E.Employee=:int").setParameter("req", 0).setParameter("int", inter).list();
	    System.out.println(usersList.size());
	   // System.out.println(usersList.get(0));
		model.addObject("person", usersList);
	    model.setViewName("NewRequestAccbyUser");
		return model;
	}
	
	
	@Transactional
	@RequestMapping(value="/NewTimeOutEmployee", method=RequestMethod.POST)
	public ModelAndView RequestForPII(){
		//System.out.println("\n\nRequestForAuth\n\n");
		Session session=sessionFactory.getCurrentSession();
		ModelAndView model=new ModelAndView();
		String person;
		List<Roles> usersList=session.createQuery("from Roles r where r.role=:role").setParameter("role","ROLE_USER").list();
		model.addObject("person", usersList);
		model.setViewName("TimeOutEmployee");
		return model;
		
	}

	@RequestMapping(value="/PIIGovernment", method=RequestMethod.POST)
    public ModelAndView TimeOutPIIGovern(HttpServletRequest request){
		ModelAndView model= new ModelAndView();
		String extuser = request.getParameter("extuser");
		System.out.println(extuser);
		try{
		    Thread.sleep(3000);
		    model.addObject("title",extuser);
		    model.setViewName("newtimeouts");
		}catch(InterruptedException e){
		    e.printStackTrace();
		}
		return model;
	}

@Transactional
	@RequestMapping(value="/PIIdetails", method=RequestMethod.POST)
	public ModelAndView UserPIIDetails(HttpServletRequest request){
		ModelAndView model=new ModelAndView();    
		String username=request.getParameter("username");
		System.out.println("username"+username);
		List<Customer> customerlist=new ArrayList();
		customerlist=userlist.getCList(username);
		System.out.println("List"+customerlist);
		model.addObject("customerlist",customerlist);
		model.setViewName("customerdetails");
		return model;
	}


@Transactional
@RequestMapping(value="/UpdateRegUser", method=RequestMethod.POST)
public ModelAndView UpdateRegPage(){
	Session session=sessionFactory.getCurrentSession();
	ModelAndView model=new ModelAndView();
	String person;
	List<Roles> usersList=session.createQuery("from Roles r where r.role=:role or r.role=:role1 or r.role=:role2").setParameter("role","ROLE_EMPLOYEE").setParameter("role1","ROLE_MANAGER").setParameter("role2","ROLE_ADMIN").list();
	model.addObject("person", usersList);
    //model.addObject("title","Please enter email address");
    model.setViewName("NewOneUpdateRegUser");
	return model;

}


@Transactional
@RequestMapping(value="/OneUpdateInter", method=RequestMethod.POST)
public ModelAndView UpdateInterPage(HttpServletRequest request){
	ModelAndView model=new ModelAndView();
 String inter = request.getParameter("intuser");
 System.out.println(inter);
 
 Employee e= new Employee();
	List<Employee> lus=new ArrayList<Employee>(); 
		lus=employeeserv.getEmpDetails();
System.out.println(lus.size());		
for(Employee a: lus){
		if(a.getUsername().equals(inter))
			e=a;
   //System.out.println(a.getUsername());
	}
System.out.println(e.getUsername());
model.addObject("title",e);
model.setViewName("NewUpdateRegUser");
	return model;
}
@RequestMapping(value="/NewEmailUpdateRegUser", method=RequestMethod.POST)
public ModelAndView NewUpdateRegPage(@ModelAttribute("emp") Employee emp){
	//System.out.println(emp.getFname()+emp.getLname());
	ModelAndView model=new ModelAndView();
    //EmployeeServiceImpl employeeserv=new EmployeeServiceImpl() ;
	employeeserv.updatEmployee(emp);
	model.setViewName("NewUpdateRegUserConfm");
	return model;
	
}
	
  
}
