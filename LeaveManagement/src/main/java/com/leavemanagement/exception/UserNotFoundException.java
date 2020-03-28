package com.leavemanagement.exception;

public class UserNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	
	public UserNotFoundException(Long userId) {
		super("User with userid"+":"+userId+"is not found");
		
	}
	private static final Integer statuscode=602;


	public static Integer getStatuscode() {
		return statuscode;
	}
	

}
