package com.leavemangement.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import com.leavemanagement.dto.ApplyLeaveRequestDTO;
import com.leavemanagement.dto.AvailableLeaveResponseDTO;
import com.leavemanagement.dto.HistoryResponseDTO;
import com.leavemanagement.dto.SucessResponseDTO;
import com.leavemanagement.entity.Category;
import com.leavemanagement.entity.Leave;
import com.leavemanagement.entity.LeaveHistory;
import com.leavemanagement.entity.User;
import com.leavemanagement.exception.CategoryNotFoundException;
import com.leavemanagement.exception.LeaveNotSufficientException;
import com.leavemanagement.exception.UserNotFoundException;
import com.leavemanagement.repository.CategoryRepository;
import com.leavemanagement.repository.LeaveHistoryRepository;
import com.leavemanagement.repository.LeaveRepository;
import com.leavemanagement.repository.UserRepository;
import com.leavemanagement.service.LeaveServiceImpl;
import com.leavemangement.restcontroller.TestRestController;

@RunWith(MockitoJUnitRunner.Silent.class)
public class TestLeaveService {
	@Mock
	private LeaveRepository leaveRepository;
	@Mock
	private LeaveHistoryRepository leaveHistoryRepository;
	@Mock
	private UserRepository userRepository;
	@Mock
	private CategoryRepository categoryRepository;
	@InjectMocks
	private TestRestController testRestController;

	@InjectMocks
	private LeaveServiceImpl leaveServiceImpl;
	private User user = null;
	private AvailableLeaveResponseDTO availableLeaveResponseDTO = null;
	private HistoryResponseDTO historyResponseDTO = null;
	private SucessResponseDTO sucessResponseDTO = null;
	private ApplyLeaveRequestDTO applyLeaveRequestDTO = null;
	private ApplyLeaveRequestDTO applyLeaveRequestDTO1 = null;
	private Category retricted = null;
	private Category annual = null;
	private Category myleave = null;
	private List<HistoryResponseDTO> listresponse = new ArrayList<HistoryResponseDTO>();
	private Leave restrictedleave = null;
	private Leave annualleave = null;
	private Leave myleaveLeave = null;
	private LeaveHistory leaveHistory = null;
	private List<LeaveHistory> listLeave = null;

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
		retricted = new Category();
		retricted.setCategoryId(1L);
		retricted.setCategoryName("retricted");
		annual = new Category();
		annual.setCategoryId(2L);
		annual.setCategoryName("annual");
		myleave = new Category();
		myleave.setCategoryId(3L);
		myleave.setCategoryName("myleave");
		applyLeaveRequestDTO = new ApplyLeaveRequestDTO();
		applyLeaveRequestDTO.setCategoryId(1L);
		applyLeaveRequestDTO.setUserId(1L);
		applyLeaveRequestDTO.setLeaveReason("fuction");
		applyLeaveRequestDTO.setFromDate(LocalDate.of(2020, 03, 21));
		applyLeaveRequestDTO.setToDate(LocalDate.of(2020, 03, 23));
		applyLeaveRequestDTO1 = new ApplyLeaveRequestDTO();
		applyLeaveRequestDTO1.setCategoryId(1L);
		applyLeaveRequestDTO1.setUserId(1L);
		applyLeaveRequestDTO1.setLeaveReason("fuction");
		applyLeaveRequestDTO1.setFromDate(LocalDate.of(2020, 03, 15));
		applyLeaveRequestDTO1.setToDate(LocalDate.of(2020, 03, 28));
		historyResponseDTO = new HistoryResponseDTO();
		historyResponseDTO.setAppliedCategory(retricted.getCategoryName());
		historyResponseDTO.setFromDate(LocalDate.of(2020, 03, 21));
		historyResponseDTO.setToDate(LocalDate.of(2020, 03, 23));
		historyResponseDTO.setUser(user);
		listresponse.add(historyResponseDTO);
		restrictedleave = new Leave();
		restrictedleave.setCategory(retricted);
		restrictedleave.setAvailableLeave(10);
		annualleave = new Leave();
		annualleave.setCategory(annual);
		myleaveLeave = new Leave();
		myleaveLeave.setCategory(myleave);
		leaveHistory = new LeaveHistory();
		leaveHistory.setAppliedCategory(retricted.getCategoryName());
		leaveHistory.setFromDate(LocalDate.of(2020, 03, 21));
		leaveHistory.setToDate(LocalDate.of(2020, 03, 23));
		leaveHistory.setReason("fuction");
		leaveHistory.setNoOfDayApplied(3);
		leaveHistory.setUser(user);
		leaveHistory.setCategory(retricted);
		leaveHistory.setHistoryId(1L);
		listLeave = new ArrayList<LeaveHistory>();
		listLeave.add(leaveHistory);
	}

	@Test
	public void testGetLeave() throws UserNotFoundException {
		Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
		Mockito.when(leaveRepository.findByCategoryCategoryIdAndUserUserId(1L, 1L)).thenReturn(restrictedleave);
		Mockito.when(leaveRepository.findByCategoryCategoryIdAndUserUserId(2L, 1L)).thenReturn(annualleave);
		Mockito.when(leaveRepository.findByCategoryCategoryIdAndUserUserId(3L, 1L)).thenReturn(myleaveLeave);
		AvailableLeaveResponseDTO responseDTO = leaveServiceImpl.getLeave(1L);
		assertEquals(responseDTO.getAnnualAvailable(), annualleave.getAvailableLeave());
	}

	@Test
	public void testApplyLeave() throws UserNotFoundException, CategoryNotFoundException, LeaveNotSufficientException {
		Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
		Mockito.when(leaveRepository.findByCategoryCategoryIdAndUserUserId(1L, 1L)).thenReturn(restrictedleave);
		Mockito.when(categoryRepository.findById(1L)).thenReturn(Optional.of(retricted));
		Mockito.when(leaveRepository.save(restrictedleave)).thenReturn(restrictedleave);
		// Mockito.when(leaveHistoryRepository.save(leaveHistory)).thenReturn(leaveHistory);
		SucessResponseDTO responseDTO = leaveServiceImpl.applyLeave(applyLeaveRequestDTO);
		assertEquals(responseDTO.getMessage(), "The leave is Applied Successfully");
	}

	@Test
	public void testLeaveHistory() {
		Mockito.when(leaveHistoryRepository.findAllByUserUserId(1L)).thenReturn(listLeave);
		List<HistoryResponseDTO> responseDTO = leaveServiceImpl.leaveHistory(1L);
		assertFalse(responseDTO.isEmpty());
	}

	@Test(expected = UserNotFoundException.class)
	public void testUserNotFoud() throws UserNotFoundException {
		Mockito.when(userRepository.findById(2L)).thenReturn(Optional.of(user));
		leaveServiceImpl.getLeave(1L);

	}

	@Test(expected = CategoryNotFoundException.class)
	public void testCategoryNotFoundException()
			throws UserNotFoundException, CategoryNotFoundException, LeaveNotSufficientException {
		Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
		Mockito.when(leaveRepository.findByCategoryCategoryIdAndUserUserId(1L, 1L)).thenReturn(restrictedleave);
		Mockito.when(categoryRepository.findById(4L)).thenReturn(Optional.of(retricted));
		Mockito.when(leaveRepository.save(restrictedleave)).thenReturn(restrictedleave);

		leaveServiceImpl.applyLeave(applyLeaveRequestDTO);

	}

	@Test(expected = LeaveNotSufficientException.class)
	public void testLeaveNotSufficientException()
			throws UserNotFoundException, CategoryNotFoundException, LeaveNotSufficientException {
		Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
		Mockito.when(leaveRepository.findByCategoryCategoryIdAndUserUserId(1L, 1L)).thenReturn(restrictedleave);
		Mockito.when(categoryRepository.findById(1L)).thenReturn(Optional.of(retricted));
		Mockito.when(leaveRepository.save(restrictedleave)).thenReturn(restrictedleave);

		leaveServiceImpl.applyLeave(applyLeaveRequestDTO1);

	}

}
