package com.wo.domain.attendance;

import java.io.Serializable;

public class ErrorCell implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String cellAddress;
	private String message;
	private String actualValue;

	public ErrorCell(String cellAddress, String message, String actualValue) {
		this.actualValue = actualValue;
		this.cellAddress = cellAddress;
		this.message = message;
	}

	public String getCellAddress() {
		return cellAddress;
	}

	public void setCellAddress(String cellAddress) {
		this.cellAddress = cellAddress;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getActualValue() {
		return actualValue;
	}

	public void setActualValue(String actualValue) {
		this.actualValue = actualValue;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ErrorCell [cellAddress=");
		builder.append(cellAddress);
		builder.append(", message=");
		builder.append(message);
		builder.append(", actualValue=");
		builder.append(actualValue);
		builder.append("]");
		return builder.toString();
	}

}
