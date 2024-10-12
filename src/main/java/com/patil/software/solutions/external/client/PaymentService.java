package com.patil.software.solutions.external.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.patil.software.solutions.external.client.exception.CustomException;
import com.patil.software.solutions.external.client.request.PaymentRequest;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@CircuitBreaker(name = "external", fallbackMethod = "myFallBackMethod")
@FeignClient(name = "PAYMENT-SERVICE/payment")
public interface PaymentService {
	@PostMapping
	public ResponseEntity<Long> doPayment(@RequestBody PaymentRequest paymentRequest);
	
	default void myFallBackMethod(Exception e) {
		throw new CustomException("Payment Service is Not Availabe","UNAVAILABLE",500);
	}
}
