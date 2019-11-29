package com.tansha.library.bookshelf.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
@Entity
@Table(name="tbl_borrow_master")
public class BookBorrow {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "borrowID")
	private int borrowID;
	
	@Column(name="orderId")
	private long orderId;
	
	@Column(name="userId")
	private int userId;
	
	@Column(name="bookID")
	private int bookID;
	
	@Column(name="slotID")
	private int slotID;
	
	@Column(name="requestedON")
	@CreationTimestamp
	private Date requestedON;
	
	@Column(name="returnRequestedON",nullable=true)
	private Date returnRequestedON;
	

	@Column(name="deliveredOn",nullable=true)
	private Date deliveredOn;
	
	@Column(name="estimateddateofReturn",nullable=true)
	private Date estimateddateofReturn;
	
	@Column(name="staffID",nullable=true)
	private int staffID;
	
	
	
	@Column(name="actualDateofReturned",nullable=true)
	private Date actualDateofReturned;
	
	@Column(name="bookReturnStatus")
	private int bookReturnStatus;
	
	@Column(name="confirmBookReturnStatus")
	private int confirmBookReturnStatus;
	
	@Column(name="type_return")
	private int type_return;
	
	@Column(name="createdAt")
	@CreationTimestamp
	private Date createdAt;
	
	@Column(name="modifiedAt")
	@UpdateTimestamp
	private Date modifiedAt;
	
	@Column(name="bookUnDeliveryStatus")
	private int bookUnDeliveryStatus;

	@Column(name="unDeliveryReason")
	private String unDeliveryReason;


	
	

	public BookBorrow() {
		
	}





	/**
	 * @return the borrowID
	 */
	public int getBorrowID() {
		return borrowID;
	}





	/**
	 * @param borrowID the borrowID to set
	 */
	public void setBorrowID(int borrowID) {
		this.borrowID = borrowID;
	}





	/**
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}





	/**
	 * @param userId the userId to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}





	/**
	 * @return the bookID
	 */
	public int getBookID() {
		return bookID;
	}





	/**
	 * @param bookID the bookID to set
	 */
	public void setBookID(int bookID) {
		this.bookID = bookID;
	}





	/**
	 * @return the slotID
	 */
	public int getSlotID() {
		return slotID;
	}





	/**
	 * @param slotID the slotID to set
	 */
	public void setSlotID(int slotID) {
		this.slotID = slotID;
	}





	/**
	 * @return the requestedON
	 */
	public Date getRequestedON() {
		return requestedON;
	}





	/**
	 * @param requestedON the requestedON to set
	 */
	public void setRequestedON(Date requestedON) {
		this.requestedON = requestedON;
	}





	/**
	 * @return the deliveredOn
	 */
	public Date getDeliveredOn() {
		return deliveredOn;
	}





	/**
	 * @param deliveredOn the deliveredOn to set
	 */
	public void setDeliveredOn(Date deliveredOn) {
		this.deliveredOn = deliveredOn;
	}





	/**
	 * @return the estimateddateofReturn
	 */
	public Date getEstimateddateofReturn() {
		return estimateddateofReturn;
	}





	/**
	 * @param estimateddateofReturn the estimateddateofReturn to set
	 */
	public void setEstimateddateofReturn(Date estimateddateofReturn) {
		this.estimateddateofReturn = estimateddateofReturn;
	}





	/**
	 * @return the staffID
	 */
	public int getStaffID() {
		return staffID;
	}





	/**
	 * @param staffID the staffID to set
	 */
	public void setStaffID(int staffID) {
		this.staffID = staffID;
	}





	/**
	 * @return the actualDateofReturned
	 */
	public Date getActualDateofReturned() {
		return actualDateofReturned;
	}





	/**
	 * @param actualDateofReturned the actualDateofReturned to set
	 */
	public void setActualDateofReturned(Date actualDateofReturned) {
		this.actualDateofReturned = actualDateofReturned;
	}





	/**
	 * @return the bookReturnStatus
	 */
	public int getBookReturnStatus() {
		return bookReturnStatus;
	}





	/**
	 * @param bookReturnStatus the bookReturnStatus to set
	 */
	public void setBookReturnStatus(int bookReturnStatus) {
		this.bookReturnStatus = bookReturnStatus;
	}





	/**
	 * @return the confirmBookReturnStatus
	 */
	public int getConfirmBookReturnStatus() {
		return confirmBookReturnStatus;
	}





	/**
	 * @param confirmBookReturnStatus the confirmBookReturnStatus to set
	 */
	public void setConfirmBookReturnStatus(int confirmBookReturnStatus) {
		this.confirmBookReturnStatus = confirmBookReturnStatus;
	}





	/**
	 * @return the createdAt
	 */
	public Date getCreatedAt() {
		return createdAt;
	}





	/**
	 * @param createdAt the createdAt to set
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}





	/**
	 * @return the modifiedAt
	 */
	public Date getModifiedAt() {
		return modifiedAt;
	}





	/**
	 * @param modifiedAt the modifiedAt to set
	 */
	public void setModifiedAt(Date modifiedAt) {
		this.modifiedAt = modifiedAt;
	}





	/**
	 * @return the orderId
	 */
	public long getOrderId() {
		return orderId;
	}





	/**
	 * @param orderId the orderId to set
	 */
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}





	/**
	 * @return the type_return
	 */
	public int getType_return() {
		return type_return;
	}





	/**
	 * @param type_return the type_return to set
	 */
	public void setType_return(int type_return) {
		this.type_return = type_return;
	}




	/**
	 * @return the returnRequestedON
	 */
	public Date getReturnRequestedON() {
		return returnRequestedON;
	}





	/**
	 * @param returnRequestedON the returnRequestedON to set
	 */
	public void setReturnRequestedON(Date returnRequestedON) {
		this.returnRequestedON = returnRequestedON;
	}





	/**
	 * @return the bookUnDeliveryStatus
	 */
	public int getBookUnDeliveryStatus() {
		return bookUnDeliveryStatus;
	}





	/**
	 * @param bookUnDeliveryStatus the bookUnDeliveryStatus to set
	 */
	public void setBookUnDeliveryStatus(int bookUnDeliveryStatus) {
		this.bookUnDeliveryStatus = bookUnDeliveryStatus;
	}





	/**
	 * @return the unDeliveryReason
	 */
	public String getUnDeliveryReason() {
		return unDeliveryReason;
	}





	/**
	 * @param unDeliveryReason the unDeliveryReason to set
	 */
	public void setUnDeliveryReason(String unDeliveryReason) {
		this.unDeliveryReason = unDeliveryReason;
	}





	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BookBorrow [borrowID=" + borrowID + ", orderId=" + orderId + ", userId=" + userId + ", bookID=" + bookID
				+ ", slotID=" + slotID + ", requestedON=" + requestedON + ", returnRequestedON=" + returnRequestedON
				+ ", deliveredOn=" + deliveredOn + ", estimateddateofReturn=" + estimateddateofReturn + ", staffID="
				+ staffID + ", actualDateofReturned=" + actualDateofReturned + ", bookReturnStatus=" + bookReturnStatus
				+ ", confirmBookReturnStatus=" + confirmBookReturnStatus + ", type_return=" + type_return
				+ ", createdAt=" + createdAt + ", modifiedAt=" + modifiedAt + ", bookUnDeliveryStatus="
				+ bookUnDeliveryStatus + ", unDeliveryReason=" + unDeliveryReason + "]";
	}






}
