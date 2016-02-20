package com.sbs.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sbs.model.EmpReqUser;
import com.sbs.model.Employee;
import com.sbs.model.EmployeeReqUser;
import com.sbs.model.Roles;
import com.sbs.model.Users;
@Service
public class EmployeeServiceImpl {

	@Autowired
	SessionFactory sessionFactory;
	
	//@Autowired
	//EmailSenderService emailserv;
//	SessionFactory sessionFactory=new Configuration().configure().buildSessionFactory();
	//Session session=sessionFactory.openSession();
	@Transactional
	public void upTran(com.sbs.model.Transaction t){
		Session session=sessionFactory.getCurrentSession();
		session.update(t);
	}
	@Transactional
	public void delTran(com.sbs.model.Transaction t){
		Session session=sessionFactory.getCurrentSession();
		session.delete(t);
	}
	
	@Transactional    
   	public List getAllTransactions(List accounts){
		
		System.out.println("Employee Service Impl");
		Session session=sessionFactory.getCurrentSession();
		
		List transactionList=session.createQuery("from Transaction t where t.toAccount in (:accounts) and t.transactionStatus=:transactionStatus").setParameterList("accounts", accounts).setParameter("transactionStatus", "Pending").list();
		
		return transactionList;
	}

	@Transactional
	public void addEmployee(Employee emp) {
		// TODO Auto-generated method stub
		updateEmployeeUser(emp);
		Session session=sessionFactory.getCurrentSession();
		//Transaction transaction=session.beginTransaction();
		session.save(emp);
		//transaction.commit();
		System.out.println("Hi this is Employee");
		
		
	}
   
	@Transactional
	public void updateEmployeeUser(Employee emp){
		Users usr = new Users();
		usr.setUsername(emp.getUsername());
		String password=UUID.randomUUID().toString().substring(0, 8);
		String shaPassword=DigestUtils.sha256Hex(password);
		Session session=sessionFactory.getCurrentSession();
		//emp.getEmail();
		/////////////////SHA256 NEEDED;
		//PKI
		usr.setPassword(shaPassword);
		usr.setEnabled(true);
		//Transaction transaction=session.beginTransaction();
		session.save(usr);
		//transaction.commit();
		updateEmployeeUserRole(emp);
	    //emailserv.sendSimpleEmail(emp.getEmail(), "Your Credentials", "Your Password is "+usr.getPassword());
		//certificateService.createPKICertForUsers(emp.getUsername(), password, emp.getEmail());
		ApplicationContext context = new ClassPathXmlApplicationContext("Spring-Mail.xml");
		EmailSenderService emailSenderService = (EmailSenderService) context.getBean("emailSenderService");
		emailSenderService.sendSimpleEmail(emp.getEmail(), "Your Credentials "+password+"!","Username: "+emp.getUsername()+"\nPassword: "+usr.getPassword());
	}
	
	@Transactional
	public void updateEmployeeUserRole(Employee emp){
		Session session=sessionFactory.getCurrentSession();
		Roles role=new Roles();
		String rol;
		int role_id;
		if(emp.getDesname().equals("Reg")){
			rol="ROLE_EMPLOYEE";
		}
		else if(emp.getDesname().equals("sysmgr")){
			rol="ROLE_MANAGER";
		}
		else{
			rol="ROLE_ADMIN";
		}
		role.setUsername(emp.getUsername());
		role.setRole(rol);
		//Transaction transaction=session.beginTransaction();
		session.save(role);
		//transaction.commit();
	}
	
	@Transactional
	public void updateEmployee(Employee emp) {
	//	Transaction transaction=session.beginTransaction();
		Session session=sessionFactory.getCurrentSession();
		//session.getTransaction().begin();
		/*Query query = session.createSQLQuery("update Employee set Fname = :Fname,Lname= :Lname,Desname=:Desname,Address=:Address,Phno=:Phno,Email = :Email" + " where Username=:username");
		query.setParameter("Fname", emp.getFname());
		query.setParameter("Lname", emp.getLname());
		query.setParameter("Desname", emp.getDesname());
		query.setParameter("Address", emp.getAddress());
		query.setParameter("Phno", emp.getPhno());
		query.setParameter("Email", emp.getEmail());
		query.setParameter("username", emp.getUsername());
		query.executeUpdate();*/
		  
		if(!emp.getFname().equals("")){
			Query query = session.createSQLQuery("update Employee set Fname = :Fname"+" where Username=:username");
			query.setParameter("Fname", emp.getFname());
			query.setParameter("username",emp.getUsername());
			query.executeUpdate();
		}
		 if(!emp.getLname().equals("")){
			Query query = session.createSQLQuery("update Employee set Lname = :Lname"+" where Username=:username");
			query.setParameter("Lname", emp.getLname());
			query.setParameter("username",emp.getUsername());
			query.executeUpdate();
		}
		 if(!emp.getAddress().equals("")){
			Query query = session.createSQLQuery("update Employee set Address = :Address"+" where Username=:username");
			query.setParameter("Address", emp.getAddress());
			query.setParameter("username",emp.getUsername());
			query.executeUpdate();
		}
		if(!emp.getPhno().equals("")){ 
			Query query = session.createSQLQuery("update Employee set Phno = :Phno"+" where Username=:username");
			query.setParameter("Phno", emp.getPhno());
			query.setParameter("username",emp.getUsername());
			query.executeUpdate();
		}
		if(!emp.getEmail().equals("")){ 
			Query query = session.createSQLQuery("update Employee set Email = :Email"+" where Username=:username");
			query.setParameter("Email", emp.getEmail());
			query.setParameter("username",emp.getUsername());
			query.executeUpdate();
		}
		//else{
		//	System.out.println("No changes Made");
	//	}
		//session.getTransaction().commit();
		//updateEmployeeUser(emp);
	    //System.out.println(emp.getFname()+emp.getLname()+emp.getEmail());

	    
	}
	
	@Transactional
	public void updateEmployeeReqUser(String Customer){
		Session session=sessionFactory.getCurrentSession();
		//session.getTransaction().begin();
		Query query = session.createSQLQuery("update EmployeeReqUser set request = :req where Customer=:ext");
		query.setParameter("req", 0);
		query.setParameter("ext",Customer);
		query.executeUpdate();
		//session.getTransaction().commit();
	}
	
	@Transactional
	public void updateEmpReqUser(String customer){
		//session.getTransaction().begin();
		Session session=sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery("update EmpReqUser set request = :req where extuser=:ext");
		query.setParameter("req", 0);
		query.setParameter("ext",customer);
		query.executeUpdate();
		//session.getTransaction().commit();
	}
	
	@Transactional
	public void Print(Employee emp){
		System.out.println("Employee"+emp.getFname());
	}
	
	@Transactional
	public void deleteEmployee(Employee emp){
		//session.getTransaction().begin();
		Session session=sessionFactory.getCurrentSession();
		/*Query query = session.createSQLQuery("Delete From Employee " + " where Username	 = :Username");
		query.setParameter("Username", emp.getUsername());
	    query.executeUpdate();
	    Query query1 = session.createSQLQuery("Delete From Roles " + " where Username	 = :Username");
		query1.setParameter("Username", emp.getUsername());
	    query1.executeUpdate();
	    Query query2 = session.createSQLQuery("Delete From Users " + " where Username	 = :Username");
		query2.setParameter("Username", emp.getUsername());
	    query2.executeUpdate();*/
		session.delete(emp);
		//session.getTransaction().commit();
	}
	@Transactional
    public void requestCustAuth(EmpReqUser empuser){
    	//Transaction transaction=session.beginTransaction();
    	Session session=sessionFactory.getCurrentSession();
    	session.save(empuser);
    	/*List<EmpReqUser> usersList=session.createQuery("from EmpReqUser e where e.extuser=:ext and e.intuser=:int").setParameter("ext",empuser.getExtuser()).setParameter("int",empuser.getIntuser()).list();
		System.out.println(empuser.getExtuser()+ "  "+ empuser.getIntuser() +" "+ empuser.getRequest() );
    	if(usersList.size()==1){
    		//session.update(empuser);
    	//session.save(empuser);
    		System.out.println(usersList.size());
		}
		else{
			System.out.println("Outside");
			//
		}*/
		//transaction.commit();
    }
    @Transactional
    public void requestCustomerAuth(EmployeeReqUser emp){
    	Session session=sessionFactory.getCurrentSession();
    	//Transaction transaction=session.beginTransaction();
		session.save(emp);
		//transaction.commit();
    }
    @Transactional
    public List getEmpList(String intuser){
    	Session session=sessionFactory.getCurrentSession();
    	List usersList=session.createQuery("from EmpReqUser E where E.request=:req and E.intuser=:int").setParameter("req", 0).setParameter("int", intuser).list();
    	for (int i = 0; i < usersList.size(); i++) {
			System.out.println("hello "+((EmpReqUser)usersList.get(i)).getExtuser());
		}
    	return usersList;
    }
    
   /* public List getAuthUsers(List userList,String username){
    	Session session=sessionFactory.getCurrentSession();
    	List<EmpReqUser> usersList=session.createQuery("from EmpReqUser E where E.intuser=:req and E.extuser in (:int)").setParameter("req", username).setParameterList("int", userList).list();
    	List l=new ArrayList();
    	for (EmpReqUser e:usersList){
    		if(userList.contains(e.getExtuser()))
    	}
    }*/
    
    
    @Transactional
    public List getEmpDetails(){
    Session session=sessionFactory.getCurrentSession();
    List transactionList=session.createQuery("from Employee").list();
    return transactionList;

    //return null;
    }
    
    @Transactional
    public void updatEmployee(Employee emp){
    Session session=sessionFactory.getCurrentSession();
    session.update(emp);
    }
}
