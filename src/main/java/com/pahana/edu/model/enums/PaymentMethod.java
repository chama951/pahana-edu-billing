package com.pahana.edu.model.enums;

public enum PaymentMethod {
	
	CASH("Cash"),
	CARD("Card"),
	ONLINE("Online");
	
	private final String paymentMethod;

	PaymentMethod(String paymentMethod) {
		this.paymentMethod=paymentMethod;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}	
	
}
