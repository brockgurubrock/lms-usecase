package com.leavemanagement.entity;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "leaveHistory")
public class LeaveHistory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "history_Id")
	private Long historyId;
	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;

	@Column(name = "applied_Category")
	private String appliedCategory;
	@Column(name = "to_Date")
	private LocalDate toDate;
	@Column(name = "from_Date")
	private LocalDate fromDate;
	@Column(name = "no_of_Day_Applied")
	private int noOfDayApplied;
	@OneToOne
	private Category category;
	@Column(name = "leave_reason")
	private String reason;

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

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	public Long getHistoryId() {
		return historyId;
	}
	public void setHistoryId(Long historyId) {
		this.historyId = historyId;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
}
