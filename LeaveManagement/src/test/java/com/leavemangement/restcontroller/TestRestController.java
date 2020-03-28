package com.leavemangement.restcontroller;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import com.leavemanagement.dto.ApplyLeaveRequestDTO;
import com.leavemanagement.dto.AvailableLeaveResponseDTO;
import com.leavemanagement.dto.HistoryResponseDTO;
import com.leavemanagement.dto.SucessResponseDTO;
import com.leavemanagement.entity.Category;
import com.leavemanagement.entity.User;
import com.leavemanagement.exception.CategoryNotFoundException;
import com.leavemanagement.exception.LeaveNotSufficientException;
import com.leavemanagement.exception.UserNotFoundException;
import com.leavemanagement.restcontroller.LeaveRestController;
import com.leavemanagement.service.LeaveService;

@RunWith(MockitoJUnitRunner.class)
public class TestRestController {
	@Mock
	private LeaveService leaveService;
	@InjectMocks
	private LeaveRestController leaveRestController;
	private User user = null;
	private AvailableLeaveResponseDTO availableLeaveResponseDTO = null;
	private HistoryResponseDTO historyResponseDTO = null;
	private SucessResponseDTO sucessResponseDTO = null;
	private ApplyLeaveRequestDTO applyLeaveRequestDTO = null;
	private Category category = null;
	private List<HistoryResponseDTO> listresponse = new ArrayList<HistoryResponseDTO>();

	@Before
	public void setup() {
		user = new User();
		user.setUserId(1L);
		user.setEmail("guru@gmail.com");
		availableLeaveResponseDTO = new AvailableLeaveResponseDTO();
		availableLeaveResponseDTO.setAnnualAvailable(20);
		availableLeaveResponseDTO.setMyLeaveAvailable(20);
		availableLeaveResponseDTO.setRestrictedAvailable(20);
		sucessResponseDTO = new SucessResponseDTO();
		sucessResponseDTO.setMessage("The leave is Applied Successfully");
		sucessResponseDTO.setStatusCode(200);
		category = new Category();
		category.setCategoryId(1L);
		category.setCategoryName("guru");
		applyLeaveRequestDTO = new ApplyLeaveRequestDTO();
		applyLeaveRequestDTO.setCategoryId(1L);
		applyLeaveRequestDTO.setUserId(1L);
		applyLeaveRequestDTO.setLeaveReason("fuction");
		applyLeaveRequestDTO.setFromDate(LocalDate.of(2020, 03, 21));
		applyLeaveRequestDTO.setToDate(LocalDate.of(2020, 03, 23));
		historyResponseDTO = new HistoryResponseDTO();
		historyResponseDTO.setAppliedCategory(category.getCategoryName());
		historyResponseDTO.setFromDate(LocalDate.of(2020, 03, 21));
		historyResponseDTO.setToDate(LocalDate.of(2020, 03, 23));
		historyResponseDTO.setUser(user);
		listresponse.add(historyResponseDTO);

	}

	@Test
	public void testGetLeave() throws UserNotFoundException {
		Mockito.when(leaveService.getLeave(1L)).thenReturn(availableLeaveResponseDTO);
		ResponseEntity<AvailableLeaveResponseDTO> response = leaveRestController.getLeave(1L);
		assertEquals(response.getBody().getMyLeaveAvailable(), availableLeaveResponseDTO.getMyLeaveAvailable());
	}

	@Test
	public void testApplyLeave() throws UserNotFoundException, CategoryNotFoundException, LeaveNotSufficientException {
		Mockito.when(leaveService.applyLeave(applyLeaveRequestDTO)).thenReturn(sucessResponseDTO);
		ResponseEntity<SucessResponseDTO> response = leaveRestController.applyLeave(applyLeaveRequestDTO);
		assertEquals(response.getBody().getMessage(), sucessResponseDTO.getMessage());
	}

	@Test
	public void testLeaveHistory() throws UserNotFoundException {
		Mockito.when(leaveService.leaveHistory(1L)).thenReturn(listresponse);
		ResponseEntity<List<HistoryResponseDTO>> response = leaveRestController.leaveHistory(1L);
		assertFalse(response.getBody().isEmpty());
	}

	
	

}
