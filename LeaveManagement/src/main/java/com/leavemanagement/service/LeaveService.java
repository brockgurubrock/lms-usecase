package com.leavemanagement.service;

import java.util.List;

import com.leavemanagement.dto.ApplyLeaveRequestDTO;
import com.leavemanagement.dto.AvailableLeaveResponseDTO;
import com.leavemanagement.dto.HistoryResponseDTO;
import com.leavemanagement.dto.SucessResponseDTO;
import com.leavemanagement.exception.CategoryNotFoundException;
import com.leavemanagement.exception.LeaveNotSufficientException;
import com.leavemanagement.exception.UserNotFoundException;

public interface LeaveService {
	public AvailableLeaveResponseDTO getLeave(Long userId)throws UserNotFoundException;
	public SucessResponseDTO applyLeave(ApplyLeaveRequestDTO applyLeaveRequestDTO) throws UserNotFoundException, CategoryNotFoundException, LeaveNotSufficientException;
	public List<HistoryResponseDTO>leaveHistory(Long UserId);
}
