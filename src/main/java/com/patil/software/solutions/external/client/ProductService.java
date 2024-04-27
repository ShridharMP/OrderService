package com.patil.software.solutions.external.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "PRODUCT-SERVICE/product")
public interface ProductService {
	@PutMapping("/reduceQuantity/{id}")
	ResponseEntity<Void> reduceProductQuantity(@PathVariable("id") long productId, @RequestParam long quantity);
}
