package com.patil.software.solutions.service;

import com.patil.software.solutions.model.OrderRequest;
import com.patil.software.solutions.model.OrderResponse;

public interface OrderService {
	long placeOrder(OrderRequest orderRequest);

	OrderResponse getOrderDetails(long orderId);
}
