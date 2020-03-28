package com.leavemanagement.exception;

public class CategoryNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;
	private static final Integer statusCode = 612;

	public CategoryNotFoundException(Long categoryId) {
		super("category is not found for the categoryId"+":" + categoryId);

	}

	public static Integer getStatuscode() {
		return statusCode;
	}

}
