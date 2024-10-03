package com.cpt.payments.service.interfaces;

public interface HmacSha256Service {
	
	public String calculateHMAC (String data) throws Exception;
	
	public boolean varifyHMAC(String data , String receivedHmac);

}
