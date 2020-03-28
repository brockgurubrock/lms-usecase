package com.leavemanagement.restcontroller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.leavemanagement.dto.ApplyLeaveRequestDTO;
import com.leavemanagement.dto.AvailableLeaveResponseDTO;
import com.leavemanagement.dto.HistoryResponseDTO;
import com.leavemanagement.dto.SucessResponseDTO;
import com.leavemanagement.exception.CategoryNotFoundException;
import com.leavemanagement.exception.LeaveNotSufficientException;
import com.leavemanagement.exception.UserNotFoundException;
import com.leavemanagement.service.LeaveService;


@RestController
@RequestMapping("/users")
public class LeaveRestController {
	@Autowired
	private LeaveService leaveService;
	Logger log=LoggerFactory.getLogger(LeaveRestController.class);

	@GetMapping(value = "/{userId}/leaves")
	public ResponseEntity<AvailableLeaveResponseDTO> getLeave(@RequestParam(name = "userId") Long userId)
			throws UserNotFoundException {
		AvailableLeaveResponseDTO response = leaveService.getLeave(userId);
		log.info("Displaying the available leaves for the particular user");
		return new ResponseEntity<>(response, HttpStatus.OK);
		

	}

	@PostMapping(value = "/leaves")
	public ResponseEntity<SucessResponseDTO> applyLeave(@RequestBody ApplyLeaveRequestDTO applyLeaveRequestDTO)
			throws UserNotFoundException, CategoryNotFoundException, LeaveNotSufficientException {

		SucessResponseDTO response = leaveService.applyLeave(applyLeaveRequestDTO);
		log.info("applying the leave for the given user and with given criteria");
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@GetMapping(value = "/{userId}/history")
	public ResponseEntity<List<HistoryResponseDTO>> leaveHistory(@RequestParam(name = "userId") Long userId)
			throws UserNotFoundException {
		List<HistoryResponseDTO> response = leaveService.leaveHistory(userId);
		log.info("display the leave history for the particular user");
		return new ResponseEntity<>(response, HttpStatus.OK);

	}
}
