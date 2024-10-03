package com.cpt.payments.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class Payment {

	
	 private String currency;
     private String amount;
     private String brandName;
     private String locale;
     private String returnUrl;
     private String cancelUrl;
     private String country;
     private String merchantTxnRef;
     private String paymentMethod;
     private String providerId;
     private String paymentType;
}
