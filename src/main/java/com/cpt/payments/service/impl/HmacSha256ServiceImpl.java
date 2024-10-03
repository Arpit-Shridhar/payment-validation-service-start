package com.cpt.payments.service.impl;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Service;

import com.cpt.payments.service.interfaces.HmacSha256Service;

@Service
public class HmacSha256ServiceImpl implements HmacSha256Service {
	
	private static final String  HMAC_SHA256 =  "HmacSHA256";
	
	@Override
	public String calculateHMAC (String jsonInput) {
		
		String secretKey = "THIS_IS_MY_SECRATE";
		
		
		try {
			 SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), HMAC_SHA256);
		       
			 Mac mac = Mac.getInstance(HMAC_SHA256);
			 mac.init(keySpec);
			 
			 byte[] signatureByte = mac.doFinal(jsonInput.getBytes(StandardCharsets.UTF_8));
		       
			 String signature = Base64.getEncoder().encodeToString(signatureByte);
					 
					 System.out.println("HMAC-SHA256-signature"+ signature);
			 
			 return signature;
		} catch (NoSuchAlgorithmException | InvalidKeyException e ) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }
		    
		 System.out.println("HMAC-SHA256 failed  to generate signature null");
		
		return null;
	}
	
	@Override
	public boolean varifyHMAC(String data , String receivedHmac) {
		
		String generateSignature = calculateHMAC(data);
		
		 System.out.println("receivedHmac | "+ receivedHmac);
		 System.out.println("generateSignature | "+ generateSignature);
		 
		 if(generateSignature!=null && generateSignature.equals(receivedHmac)) {
			 System.out.println("HMAC-SHA256-signature is valide :");
			 return true;
		 }
		
		 System.out.println("HMAC-SHA256-signature is inValide");

		return false;
	}

}

