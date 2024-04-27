package com.patil.software.solutions.service;

import com.patil.software.solutions.model.OrderRequest;

public interface OrderService {
	long placeOrder(OrderRequest orderRequest);
}
