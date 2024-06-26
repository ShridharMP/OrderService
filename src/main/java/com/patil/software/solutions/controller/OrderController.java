package com.patil.software.solutions.controller;

import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.patil.software.solutions.model.OrderRequest;
import com.patil.software.solutions.model.OrderResponse;
import com.patil.software.solutions.service.OrderService;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/order")
@Log4j2
public class OrderController {
	@Autowired
	private OrderService orderService;

	@PostMapping("/placeOrder")
	public ResponseEntity<Long> placeOrder(@RequestBody OrderRequest orderRequest) {
		long orderId = orderService.placeOrder(orderRequest);
		log.info("Order Id: {}", orderId);
		return new ResponseEntity<>(orderId, HttpStatus.OK);
	}

	@GetMapping("/{orderId}")
	public ResponseEntity<OrderResponse> getOrderDetails(@PathVariable(name = "orderId") long orderId) {
		OrderResponse orderResponse = orderService.getOrderDetails(orderId);
		log.info("OrderResponse Details-> Amount:" + orderResponse.getAmount() + "OrderId:" + orderResponse.getOrderId()
				+ "OrderStatus:" + orderResponse.getOrderStatus() + "OrderDate:" + orderResponse.getOrderDate()
				+ "PaymentDetails:" + orderResponse.getPaymentDetails() + "ProductDetails:"
				+ orderResponse.getProductDetails());
		return new ResponseEntity<>(orderResponse, HttpStatus.OK);

	}
}
