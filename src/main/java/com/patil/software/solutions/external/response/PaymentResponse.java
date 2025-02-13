package com.patil.software.solutions.external.response;

import java.time.Instant;

import com.patil.software.solutions.model.PaymentMode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentResponse {
	private long paymentId;
	private String status;
	private PaymentMode paymentMode;
	private Instant paymentDate;
	private double amount;
	private long orderId;

}
