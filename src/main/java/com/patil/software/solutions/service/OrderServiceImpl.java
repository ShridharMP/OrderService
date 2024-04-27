package com.patil.software.solutions.service;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.patil.software.solutions.entity.Order;
import com.patil.software.solutions.model.OrderRequest;
import com.patil.software.solutions.repository.OrderRepository;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Override
	public long placeOrder(OrderRequest orderRequest) {
		log.info("Placing Oredr: {}", orderRequest);

		Order order = Order.builder().amount(orderRequest.getTotalAmount()).orderStatus("CREATED")
				.productId(orderRequest.getProductId()).orderDate(Instant.now()).quantity(orderRequest.getQuantity())
				.build();
		order = orderRepository.save(order);
		log.info("Order Placed Successfully with Order ID: {}", order.getId());
		return order.getId();
	}

}
