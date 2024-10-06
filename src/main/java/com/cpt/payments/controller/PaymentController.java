package com.cpt.payments.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cpt.payments.pojo.CreatePaymentRes;
import com.cpt.payments.pojo.PaymentRequest;
import com.cpt.payments.service.interfaces.HmacSha256Service;
import com.cpt.payments.service.interfaces.PaymentService;
import com.google.gson.Gson;

@RestController
@RequestMapping("/v1/payment")
public class PaymentController {

	private HmacSha256Service hmacSha256Service;
	
	private Gson gson;
	
	private PaymentService paymentService ;
	
	public PaymentController (HmacSha256Service hmacSha256Service,Gson gson, 
			PaymentService paymentService) {
	this.hmacSha256Service = hmacSha256Service;
	this.gson =gson;
	this.paymentService = paymentService;
	}
	
	@PostMapping
	public ResponseEntity<CreatePaymentRes> createPayment(
         @RequestBody PaymentRequest paymentRequest) throws Exception {
		
		System.out.println("createpayment request received | PaymentRequest " + paymentRequest);
		
		CreatePaymentRes createPaymentRes = paymentService.createPayment(paymentRequest);
				
		System.out.println("createPayment recieved from controller response: "+ createPaymentRes);
		
		ResponseEntity<CreatePaymentRes> responseEntity =new ResponseEntity<CreatePaymentRes>(
				createPaymentRes, HttpStatus.CREATED);
		
		System.out.println("controller returning responseEntity:" + responseEntity);
		
		
		return responseEntity;
	}
	
	@PostMapping("/{txnRef}/capture")
	public String capturePayment(@PathVariable String txnRef ) {
		System.out.println("capturePayment request recieve | txnRef"+ txnRef);
		
		return "Payment capture: "+ txnRef;
	}
	@GetMapping("/{txnRef}")
	public String getPayment(@PathVariable String txnRef ) {
		System.out.println("getPayment request recieve | txnRef"+ txnRef);
		
		return "getPayment: "+ txnRef;

	}
}
