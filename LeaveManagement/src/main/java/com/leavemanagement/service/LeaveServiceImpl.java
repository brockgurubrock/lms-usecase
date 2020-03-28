package com.leavemanagement.service;

import java.time.Period;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leavemanagement.constant.Constant;
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

@Service
@Transactional
public class LeaveServiceImpl implements LeaveService {
	@Autowired
	private LeaveRepository leaveRepository;
	@Autowired
	private UserRepository UserRepository;
	@Autowired
	private LeaveHistoryRepository leaveHistoryRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	Logger log=LoggerFactory.getLogger(LeaveServiceImpl.class);

	@Override
	public AvailableLeaveResponseDTO getLeave(Long userId) throws UserNotFoundException {
		Optional<User> user = UserRepository.findById(userId);
		if (!user.isPresent()) {
			throw new UserNotFoundException(userId);
		}
		Leave restricedLeave = leaveRepository.findByCategoryCategoryIdAndUserUserId(Constant.Restricted, userId);
		Leave annualLeave = leaveRepository.findByCategoryCategoryIdAndUserUserId(Constant.Annual, userId);
		Leave myLeave = leaveRepository.findByCategoryCategoryIdAndUserUserId(Constant.MyLeave, userId);

		AvailableLeaveResponseDTO availableLeaveResponseDTO = new AvailableLeaveResponseDTO();
		availableLeaveResponseDTO.setAnnualAvailable(annualLeave.getAvailableLeave());
		availableLeaveResponseDTO.setRestrictedAvailable(restricedLeave.getAvailableLeave());
		availableLeaveResponseDTO.setMyLeaveAvailable(myLeave.getAvailableLeave());
		log.info("Displaying the Avaialble leaves for the User Successfully");
		return availableLeaveResponseDTO;
	}

	@Override
	public SucessResponseDTO applyLeave(ApplyLeaveRequestDTO applyLeaveRequestDTO)
			throws UserNotFoundException, CategoryNotFoundException, LeaveNotSufficientException {

		Optional<User> user = UserRepository.findById(applyLeaveRequestDTO.getUserId());
		if (!user.isPresent()) {
			throw new UserNotFoundException(applyLeaveRequestDTO.getUserId());
		}
		Leave leave = leaveRepository.findByCategoryCategoryIdAndUserUserId(applyLeaveRequestDTO.getCategoryId(),
				applyLeaveRequestDTO.getUserId());
		Optional<Category> category = categoryRepository.findById(applyLeaveRequestDTO.getCategoryId());
		if (!category.isPresent()) {
			throw new CategoryNotFoundException(applyLeaveRequestDTO.getCategoryId());
		}
		Period period = Period.between(applyLeaveRequestDTO.getFromDate(), applyLeaveRequestDTO.getToDate());
		int noOfLeaveApplying = period.getDays() + 1;
		if (leave.getAvailableLeave() < noOfLeaveApplying) {

			throw new LeaveNotSufficientException(applyLeaveRequestDTO.getUserId());
		}
		leave.setAvailableLeave(leave.getAvailableLeave() - noOfLeaveApplying);
		leaveRepository.save(leave);
		LeaveHistory history = new LeaveHistory();
		history.setUser(user.get());
		history.setCategory(category.get());
		history.setAppliedCategory(category.get().getCategoryName());
		history.setFromDate(applyLeaveRequestDTO.getFromDate());
		history.setToDate(applyLeaveRequestDTO.getToDate());
		history.setNoOfDayApplied(noOfLeaveApplying);
		history.setReason(applyLeaveRequestDTO.getLeaveReason());
		leaveHistoryRepository.save(history);
		SucessResponseDTO responseDTO = new SucessResponseDTO();
		responseDTO.setMessage(Constant.SuccessApply);
		responseDTO.setStatusCode(Constant.SuccessStatusCode);
		log.info("The leave is Applied Successfully");

		return responseDTO;
	}

	@Override
	public List<HistoryResponseDTO> leaveHistory(Long UserId) {
		List<LeaveHistory> listLeaveHistory = leaveHistoryRepository.findAllByUserUserId(UserId);
		List<HistoryResponseDTO> listresponseDto = listLeaveHistory.stream().map(history -> {
			HistoryResponseDTO responseDTO = new HistoryResponseDTO();
			BeanUtils.copyProperties(history, responseDTO);
			return responseDTO;

		}).collect(Collectors.toList());
		log.info("leave histroy retrived Successfully");
		return listresponseDto;
	}

}
