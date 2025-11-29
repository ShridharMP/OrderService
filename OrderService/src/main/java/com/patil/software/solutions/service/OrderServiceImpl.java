package com.patil.software.solutions.service;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.patil.software.solutions.ProductService.model.ProductResponse;
import com.patil.software.solutions.entity.Order;
import com.patil.software.solutions.external.client.PaymentService;
import com.patil.software.solutions.external.client.ProductService;
import com.patil.software.solutions.external.client.exception.CustomException;
import com.patil.software.solutions.external.client.request.PaymentRequest;
import com.patil.software.solutions.external.response.PaymentResponse;
import com.patil.software.solutions.model.OrderRequest;
import com.patil.software.solutions.model.OrderResponse;
import com.patil.software.solutions.repository.OrderRepository;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private ProductService productSrvice;

	@Autowired
	private PaymentService paymentService;

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public long placeOrder(OrderRequest orderRequest) {
		log.info("Placing Oredr: {}", orderRequest);

		productSrvice.reduceProductQuantity(orderRequest.getProductId(), orderRequest.getQuantity());

		log.info("Creating Oredr with status CREATED");

		Order order = Order.builder().amount(orderRequest.getTotalAmount()).orderStatus("CREATED")
				.productId(orderRequest.getProductId()).orderDate(Instant.now()).quantity(orderRequest.getQuantity())
				.build();

		order = orderRepository.save(order);

		log.info("Order Placed Successfully with Order ID: {}", order.getId());

		log.info("Calling Payment Service to complete the payment");

		PaymentRequest paymentRequest = PaymentRequest.builder().amount(orderRequest.getTotalAmount())
				.paymentMode(orderRequest.getPaymentMode()).orderId(order.getId()).build();

		String orderStatus = null;
		try {
			paymentService.doPayment(paymentRequest);
			log.info("Payment Successfully Complted");
			orderStatus = "Success";
		} catch (Exception e) {
			log.info("Payment Failed");
			orderStatus = "PAYMENT_FAILED";
		}
		order.setOrderStatus(orderStatus);
		orderRepository.save(order);
		return order.getId();
	}

	
	@Override
	public OrderResponse getOrderDetails(long orderId) {
		Order order = orderRepository.findById(orderId).orElseThrow(
				() -> new CustomException("Order not found for the given id:" + orderId, "NOT_FOUND", 404));

		log.info("Product Details getting called from OrderService using RestTemplate");

		ProductResponse productResponse = restTemplate
				.getForObject("http://PRODUCT-SERVICE/product/" + order.getProductId(), ProductResponse.class);

		OrderResponse.ProductDetails productDetails = OrderResponse.ProductDetails.builder()
				.productId(productResponse.getProductId()).productName(productResponse.getProductName())
				.price(productResponse.getPrice()).quantity(productResponse.getQuantity()).build();

		PaymentResponse paymentResponse = restTemplate
				.getForObject("http://PAYMENT-SERVICE/payment/order/" + order.getId(), PaymentResponse.class);

		OrderResponse.PaymentDetails paymentDetails = OrderResponse.PaymentDetails.builder()
				.paymentDate(paymentResponse.getPaymentDate()).amount(paymentResponse.getAmount())
				.paymentId(paymentResponse.getPaymentId()).paymentMode(paymentResponse.getPaymentMode())
				.status(paymentResponse.getStatus()).orderId(paymentResponse.getOrderId()).build();

		OrderResponse orderResponse = OrderResponse.builder().orderId(order.getId()).amount(order.getAmount())
				.orderStatus(order.getOrderStatus()).orderDate(order.getOrderDate()).productDetails(productDetails)
				.paymentDetails(paymentDetails).build();
		return orderResponse;
	}

}
