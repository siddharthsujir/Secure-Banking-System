package com.sbs.controller;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.util.Date;

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
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.sbs.model.SystemLog;
import com.sbs.service.EmailSenderService;
import com.sbs.service.PKICertificateService;
import com.sbs.service.SystemLogService;
import com.sbs.service.TransactionService;
import com.sbs.validator.FileValidator;


/**
 * Handles requests for the application home page.
 */
@Controller
public class PKIController {
	
	@Autowired
	FileValidator fileValidator;
	
	@Autowired
	PKICertificateService pkiCertificateService;
	
	@Autowired
	TransactionService transactionService;
	
	@Autowired
	SystemLogService systemLogService;
	
	/*private static final Logger logger = LoggerFactory.getLogger(HomeController.class);*/
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	
	
	@RequestMapping(value = "/usercertification", method = RequestMethod.POST)
	public ModelAndView certiUploadPage() {
		
		ModelAndView model = new ModelAndView();
		model.setViewName("usercertification");
		return model;
		
	}
	
	@RequestMapping(value = "/uploadpki", method = RequestMethod.POST)
	public ModelAndView certiUpload(@RequestParam(value = "error", required = false) String error, @RequestParam(name="file") MultipartFile file,HttpServletRequest request) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails details=(UserDetails)auth.getPrincipal();
		String username=details.getUsername();
		long transactionid=(long) request.getSession().getAttribute("transactionid");
		
		//System.out.println("\nUsername is "+((UserDetails)auth.getPrincipal()).getUsername()+"\n\n");
		
		//MultipartFile multipartFile=file.getFile();
		System.out.println("Inside Controller");
		ModelAndView model = new ModelAndView();
		if(!fileValidator.validateFile(file,username)){
			model.addObject("error","Please upload a proper file");
			model.setViewName("usercertification");
			return model;
			//model.addObject(", attributeValue)
		}
		if(!pkiCertificateService.encryptData(username, file,transactionid+" "+username)){
			model.addObject("error","User not verified");
			model.setViewName("usercertification");
			return model;
		}
		if(!transactionService.checkifTransactionExists(transactionid))
		{
			model.addObject("error", "Transaction not valid");
			model.setViewName("usercertification");
			return model;	
		}
		transactionService.modifyTransactionStatus(transactionid, "Pending");
		//System.out.println("Certificate Validation "+pkiCertificateService.encryptData("sanchit", file,"data to verify"));
		
		//model.addObject("msg", "Transaction Successful");
		model.setViewName("transactionpending");
		return model;
		
	}	
	
	@RequestMapping(value = "/uploadpkimerchant", method = RequestMethod.POST)
	public ModelAndView certiUploadMerchant(@RequestParam(value = "error", required = false) String error, @RequestParam(name="file") MultipartFile file,HttpServletRequest request) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails details=(UserDetails)auth.getPrincipal();
		String username=details.getUsername();
		long transactionid=(long) request.getSession().getAttribute("transactionid");
		
		//System.out.println("\nUsername is "+((UserDetails)auth.getPrincipal()).getUsername()+"\n\n");
		
		//MultipartFile multipartFile=file.getFile();
		System.out.println("Inside Controller");
		ModelAndView model = new ModelAndView();
		if(!fileValidator.validateFile(file,username)){
			model.addObject("error","Please upload a proper file");
			model.setViewName("merchantcertification");
			return model;
			//model.addObject(", attributeValue)
		}
		if(!pkiCertificateService.encryptData(username, file,transactionid+" "+username)){
			model.addObject("error","User not verified");
			model.setViewName("merchantcertification");
			return model;
		}
		if(!transactionService.checkifTransactionExists(transactionid))
		{
			model.addObject("error", "Transaction not valid");
			model.setViewName("merchantcertification");
			return model;	
		}
		String status=transactionService.fundTransfer(transactionid);
		if(status.equals("Transaction Approved")){
			
			SystemLog systemLog=new SystemLog();
			systemLog.setUsername(username);
			systemLog.setTimestamp((new Date().getTime())+"");
			systemLog.setDescription("Transaction "+transactionid+" Approved");
			systemLogService.addLog(systemLog);
			
			
			transactionService.modifyTransactionStatus(transactionid, "Approved");
			model.addObject("msg",status);
			model.setViewName("transactionSuccess");
			return model;
		}
		else{
			
			SystemLog systemLog=new SystemLog();
			systemLog.setUsername(username);
			systemLog.setTimestamp((new Date().getTime())+"");
			systemLog.setDescription("Transaction "+transactionid+" Approved");
			systemLogService.addLog(systemLog);
			
			
			transactionService.modifyTransactionStatus(transactionid, "Void Transaction");
			model.addObject("error",status);
			model.setViewName("merchantcertification");
			return model;
		}
		
		//transactionService.modifyTransactionStatus(transactionid, "Pending");
		//System.out.println("Certificate Validation "+pkiCertificateService.encryptData("sanchit", file,"data to verify"));
		
		//model.addObject("msg", "Transaction Successful");
		//model.setViewName("transactionpending");
		//return model;
		
	}	
}