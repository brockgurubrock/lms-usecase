package com.leavemanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.leavemanagement.dto.SucessResponseDTO;

@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(CategoryNotFoundException.class)
	ResponseEntity<SucessResponseDTO> categoryNotFoundException(CategoryNotFoundException exception) {
		SucessResponseDTO sucessResponseDTO = new SucessResponseDTO();
		sucessResponseDTO.setMessage(exception.getMessage());
		sucessResponseDTO.setStatusCode(CategoryNotFoundException.getStatuscode());
		return new ResponseEntity<>(sucessResponseDTO, HttpStatus.OK);

	}

	@ExceptionHandler(UserNotFoundException.class)
	ResponseEntity<SucessResponseDTO> userNotFoundException(UserNotFoundException exception) {
		SucessResponseDTO sucessResponseDTO = new SucessResponseDTO();
		sucessResponseDTO.setMessage(exception.getMessage());
		sucessResponseDTO.setStatusCode(CategoryNotFoundException.getStatuscode());
		return new ResponseEntity<>(sucessResponseDTO, HttpStatus.OK);

	}

	@ExceptionHandler(LeaveNotSufficientException.class)
	ResponseEntity<SucessResponseDTO> leaveNotSufficientException(LeaveNotSufficientException exception) {
		SucessResponseDTO sucessResponseDTO = new SucessResponseDTO();
		sucessResponseDTO.setMessage(exception.getMessage());
		sucessResponseDTO.setStatusCode(CategoryNotFoundException.getStatuscode());
		return new ResponseEntity<>(sucessResponseDTO, HttpStatus.OK);

	}

}
