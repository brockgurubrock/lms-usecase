package com.leavemanagement.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.leavemanagement.entity.User;

public class HistoryResponseDTO {
	private Long historyId;
	@JsonIgnore
	private User user;
	private String appliedCategory;
	private LocalDate toDate;
	private LocalDate fromDate;
	private int noOfDayApplied;
	private String reason;

	public Long getHistoryId() {
		return historyId;
	}

	public void setHistoryId(Long historyId) {
		this.historyId = historyId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getAppliedCategory() {
		return appliedCategory;
	}

	public void setAppliedCategory(String appliedCategory) {
		this.appliedCategory = appliedCategory;
	}

	public LocalDate getToDate() {
		return toDate;
	}

	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
	}

	public LocalDate getFromDate() {
		return fromDate;
	}

	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
	}

	public int getNoOfDayApplied() {
		return noOfDayApplied;
	}

	public void setNoOfDayApplied(int noOfDayApplied) {
		this.noOfDayApplied = noOfDayApplied;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	

}
