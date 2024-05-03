package com.patil.software.solutions.external.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.patil.software.solutions.external.client.exception.CustomException;

@FeignClient(name = "PRODUCT-SERVICE/product")
public interface ProductService {
	@PutMapping("/reduceQuantity/{id}")
	ResponseEntity<Void> reduceProductQuantity(@PathVariable("id") long productId, @RequestParam long quantity);

	default void myFallBackMethod(Exception e) {
		throw new CustomException("Product service is not availabe", "UNAVAILABLE", 500);
	}
}
