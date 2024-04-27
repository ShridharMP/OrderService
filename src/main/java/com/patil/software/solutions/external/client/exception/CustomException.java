package com.patil.software.solutions.external.client.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper=false)
public class CustomException extends RuntimeException{
	private static final long serialVersionUID = 3451813444897912575L;
	private String errorCode;
	private int status;
	
	public CustomException(String message,String errorCode,int status) {
		super(message);
		this.errorCode=errorCode;
		this.status=status;
	}
}
