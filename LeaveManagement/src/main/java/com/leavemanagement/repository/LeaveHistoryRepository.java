package com.leavemanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.leavemanagement.entity.LeaveHistory;

@Repository
public interface LeaveHistoryRepository extends JpaRepository<LeaveHistory,Long>{
	public List<LeaveHistory> findAllByUserUserId(Long userId);

}
