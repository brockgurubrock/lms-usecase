package com.leavemanagement.entity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
/*
 *@author guruchandru v 
 * */
@Entity
@Table(name = "Leavetable")
public class Leave {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "leave_Id")
	private Long leaveId;
	@Column(name = "availableLeave")
	private int availableLeave;
	@OneToOne
	@JoinColumn(name = "category_id")
	private Category category;
	@OneToOne
	@JoinColumn(name = "user_Id")
	private User user;
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Long getLeaveId() {
		return leaveId;
	}
	public void setLeaveId(Long leaveId) {
		this.leaveId = leaveId;
	}
	public int getAvailableLeave() {
		return availableLeave;
	}
	public void setAvailableLeave(int availableLeave) {
		this.availableLeave = availableLeave;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	

	

	

}
