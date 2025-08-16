package com.pahana.edu.model.enums;

public enum BillStatus {

	DRAFT("Draft"),
	PENDING("Pending"),
	PAID("Paid"),
	CANCELLED("Cancelled"),
	REFUNDED("Refunded");

	private final String displayName;

	BillStatus(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return displayName;
	}

}
