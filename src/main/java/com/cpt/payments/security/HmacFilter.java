package com.cpt.payments.security;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.cpt.payments.service.interfaces.HmacSha256Service;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class HmacFilter extends OncePerRequestFilter {
	
	private HmacSha256Service hmacSha256Service;

	public HmacFilter (HmacSha256Service hmacSha256Service) {
		this.hmacSha256Service = hmacSha256Service;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		
		System.out.println("======= HmacFilter: doFilterInternal");
		
		
		WrappedRequest wrappedRequest = new  WrappedRequest(request);
		
            String data = request.getRequestURI();
		
		if(wrappedRequest.getBody() !=null 
				&& !wrappedRequest.getBody().isEmpty()) {
			data = data + "|"+ getNormalizedJson(wrappedRequest.getBody());
		}
		
		
		String receivedHmacSignature = request.getHeader("HmacSignature");
		
		System.out.println("----------HmacFilter: receivedHmacSignature:"+ receivedHmacSignature);
		System.out.println("----------HmacFilter: sending request data for sign processing"+"| data:"+data);
		
		boolean isValid = hmacSha256Service.varifyHMAC(data, receivedHmacSignature);
		
		System.out.println("HmacFilter: isValid"+ isValid);
		
		
		if(isValid) {
			
			System.out.println("HmacFilter: isValid. Calling next filter in line");
			
			SecurityContext context = SecurityContextHolder.createEmptyContext(); 
			
			Authentication authentication =
			    new HmacAuthenticationToken("ECOM", ""); 
			
			context.setAuthentication(authentication);

			SecurityContextHolder.setContext(context); 
			System.out.println("HmacFilter: SecurityContextHolder set with Authentication Token");

			
			filterChain.doFilter(wrappedRequest, response);
		}else {
			
			System.out.println("HmacFilter: NOT valid. Sending 401");
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		}
		
	}
	
	public String getNormalizedJson(String rawJson) {
	    Gson gson = new Gson();
	    // Parse the raw JSON string
	    JsonElement jsonElement = JsonParser.parseString(rawJson);
	    // Convert it back to a JSON string
	    return gson.toJson(jsonElement);
	}


}
