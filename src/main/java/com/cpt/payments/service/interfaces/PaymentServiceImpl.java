package com.cpt.payments.service.interfaces;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.cpt.payments.constant.ErrorCodeEnum;
import com.cpt.payments.exception.ValidationException;
import com.cpt.payments.pojo.CreatePaymentRes;
import com.cpt.payments.pojo.PaymentRequest;

@Service
public class PaymentServiceImpl implements PaymentService {

	@Override
	public CreatePaymentRes createPayment(PaymentRequest paymentRequest) {
		
		if(!paymentRequest.getPayment().getProviderId().equals("PAYPAL")) {
			
			System.out.println("Provider is supported");
			
			throw new ValidationException(
					ErrorCodeEnum.INVALID_PROVIDER.getErrorCode(),
					ErrorCodeEnum.INVALID_PROVIDER.getErrorMessage(),
					HttpStatus.BAD_REQUEST);
		}
		
		CreatePaymentRes createPaymentRes = new CreatePaymentRes();
		createPaymentRes.setTxnRef("TxnRef001");
		createPaymentRes.setProviderRef("ProviderRef001");
		createPaymentRes.setRedirectUrl("htpp://redirecturl.com");
		
		System.out.println("createPayment returning response from service" + createPaymentRes);

		return createPaymentRes;
	}

}
