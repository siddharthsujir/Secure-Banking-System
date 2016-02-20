package com.sbs.validator;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class CaptchaValidator {

	/*public static final String url = "https://www.google.com/recaptcha/api/siteverify";
	public static final String secret = "6Le45g8TAAAAACgF-9UadBnF9bVRbu1qGyOzvmuq";
	private final static String USER_AGENT = "Mozilla/5.0";*/
	
	/*public boolean verifyCaptcha(String response){
		if(response.equals(null)||response.equals(""))
			return false;
		try {
			URL url=new URL("https://www.google.com/recaptcha/api/siteverify");
			HttpsURLConnection connection=(HttpsURLConnection)url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("User-Agent", "Mozilla/5.0");
			connection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			
			String post="secret=6Le45g8TAAAAACgF-9UadBnF9bVRbu1qGyOzvmuq&response="+response;
			connection.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
	        wr.writeBytes(post);
	        wr.flush();
	        wr.close();

	        int code=connection.getResponseCode();
	        BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(connection.getInputStream()));
	        
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
}
