package com.patil.software.solutions.model;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {
	private long orderId;
	private String orderStatus;
	private Instant orderDate;
	private double amount;
	private ProductDetails productDetails;
	private PaymentDetails paymentDetails;

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	@Builder
	public static class ProductDetails {
		private long productId;
		private String productName;
		private long quantity;
		private double price;
	}

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	@Builder
	public static class PaymentDetails {
		private long paymentId;
		private String status;
		private PaymentMode paymentMode;
		private Instant paymentDate;
		private double amount;
		private long orderId;
	}
}
