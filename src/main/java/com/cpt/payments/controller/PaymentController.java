package com.cpt.payments.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cpt.payments.pojo.PaymentRequest;
import com.cpt.payments.service.interfaces.HmacSha256Service;
import com.google.gson.Gson;

@RestController
@RequestMapping("/v1/payment")
public class PaymentController {

	private HmacSha256Service hmacSha256Service;
	
	private Gson gson;
	
	public PaymentController (HmacSha256Service hmacSha256Service,Gson gson) {
	this.hmacSha256Service = hmacSha256Service;
	this.gson =gson;	
	}
	
	@PostMapping
	public String createPayment(@RequestHeader("HmacSignature") String hmacSignature,
         @RequestBody PaymentRequest paymentRequest) throws Exception {
		
		System.out.println("createpayment request received | PaymentRequest " + paymentRequest + "|hmacSignature:"+ hmacSignature);
		
		String paymentReqAsJson = gson.toJson(paymentRequest);
		
		System.out.println("paymentReqAsJson :" + paymentReqAsJson);
		
		
		boolean isValide = hmacSha256Service.varifyHMAC(paymentReqAsJson, hmacSignature);

		
		
		return "Payment created| isValid:" + isValide;
	}
	
}