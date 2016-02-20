package com.sbs.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PKICertificateService {
	
	@Autowired
	UserPKIDetailsService pkiDetailsService;
	
	@Transactional
	public void createPKICertForUsers(String username, String password, String emailId){
		KeyPairGenerator keyGen;
		try {
			
			/*Generate keys*/
			keyGen = KeyPairGenerator.getInstance("RSA");
			SecureRandom secureRandom= SecureRandom.getInstance("SHA1PRNG");
			keyGen.initialize(2048, secureRandom);
			KeyPair keyPair= keyGen.generateKeyPair();
			PrivateKey priv = keyPair.getPrivate();
			PublicKey pub = keyPair.getPublic();
			
			/*Generate Signarue*/
			/*Signature signature = Signature.getInstance("SHA256withRSA");
			signature.initSign(priv);
			byte privKey[]=priv.getEncoded();*/
			//System.out.println("Enc \n"+Arrays.toString(privKey));
			
			/*Generate user certi*/
		    CertificateFactory certificateFactory =   CertificateFactory.getInstance("X509");
		    X509Certificate xc=(X509Certificate)certificateFactory.generateCertificate(PKICertificateService.class.getClassLoader().getResourceAsStream("authority.crt"));
		    X509Certificate xc2=(X509Certificate)certificateFactory.generateCertificate(PKICertificateService.class.getClassLoader().getResourceAsStream("authoritychild.crt"));
		    
		    
		    KeyStore keyStore = KeyStore.getInstance("PKCS12");
		    String certPassword=UUID.randomUUID().toString().substring(0, 8);
			char[] certPasswordArray = certPassword.toCharArray();
			keyStore.load(null,null);
			
			/*Store user certi*/
			keyStore.setKeyEntry(username+"_authority", priv, certPasswordArray, new Certificate []{xc2,xc});
			FileOutputStream fos = new FileOutputStream(username+".pfx");
			keyStore.store(fos, certPasswordArray);
			fos.close();
			//fileInputStream.close();
			//fileInputStream2.close();
			
			
			FileSystemResource attachment = new FileSystemResource(username+".pfx");
	
			/*Email certi to user*/
			ApplicationContext context = new ClassPathXmlApplicationContext("Spring-Mail.xml");
			EmailSenderService emailSenderService = (EmailSenderService) context.getBean("emailSenderService");
			String body="Hello,\n\nThis is your certificate. Keep it in a safe place. Do not share this certificate with anyone.\n\nYou will need this certificate to encrypt your transactions.\n\nThe password to view your certificate details is '"+certPassword+"' without quotes.\n\nThe password to login to your account is '"+password+"' without quotes.\n\nThanks,\nGroup 13.";
			emailSenderService.sendEmailWithAttachment("group13ss2015@gmail.com", emailId, "User Certificate for "+username, body, attachment);
			
			/*Delete file*/
			Files.deleteIfExists(Paths.get(attachment.getPath()));
	
			/*save details*/
			pkiDetailsService.saveUserPKIDetails(UUID.randomUUID().toString(), username, certPassword, pub.getEncoded());
			
			//encryptData(username, certPassword,priv,pub);
		
		} catch (NoSuchAlgorithmException | CertificateException | KeyStoreException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void verifyData(){
		
	}
	
	public boolean encryptData(String username, MultipartFile file,String data){
		
		KeyStore ks;
		try {
			String password=pkiDetailsService.getCertPassword(username);
			System.out.println("Inside pki");
			ks = KeyStore.getInstance("PKCS12");
			ks.load(file.getInputStream(),password.toCharArray());
			PrivateKey priv=(PrivateKey) ks.getKey(username+"_authority", password.toCharArray());
			
			Signature signature = Signature.getInstance("SHA256withRSA");
			signature.initSign(priv);
			
			//String s="Sanchit";
			
			signature.update(data.getBytes());
			
			byte sigBytes[]=signature.sign();
			//System.out.println("Enc \n"+Arrays.toString(sigBytes));
			
			Signature sig = Signature.getInstance("SHA256withRSA");
			
			byte []pubEnc=pkiDetailsService.getPublicKey(username);
			//byte pubEnc=pkiDetailsService.getPublicKey(username);
			
			X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(pubEnc);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PublicKey pub=keyFactory.generatePublic(pubKeySpec);
			
			//System.out.println("\n\n\nPUB \n"+pub.equals(pk));
			sig.initVerify(pub);
			sig.update(data.getBytes());
			
			//System.out.println("\n\n\n\nDec \n"+sig.verify(sigBytes));
			return sig.verify(sigBytes);
		}
		catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException | InvalidKeySpecException | KeyStoreException | CertificateException | IOException | UnrecoverableKeyException e) {
			e.printStackTrace();
		}
		return false;
	}
	
}
