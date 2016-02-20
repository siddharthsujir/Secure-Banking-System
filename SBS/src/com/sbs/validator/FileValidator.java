package com.sbs.validator;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileValidator {

	public boolean validateFile(MultipartFile file,String username){
		
		boolean check=false;
		System.out.println("Inside validator");
		if(file.getContentType().equals("application/x-pkcs12")&&file.getSize()>0&&file.getOriginalFilename().equals(username+".pfx")&&!file.isEmpty()){
			System.out.println("Inside if");
			check=true;
		}
		else
			System.out.println("Outside if");
		//System.out.println("\nContent type: "+file.getContentType());
		//System.out.println("\nFile size: "+file.getSize());
		//System.out.println("\nOriginal File Name: "+file.getOriginalFilename());
		//System.out.println("\nEmpty File: "+file.isEmpty());
		
		
		return check;
	}
}