package com.tansha.library.bookshelf.model;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
@Entity
@Table(name="tbl_user_subscriptions")
public class UserSubscription {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name="userId", nullable=false)
	private int userId;
	
	
	@Column(name="validFrom", nullable=false, length=20)
	private Date validFrom;
	
	@Column(name="validTo", nullable=false)
	private Date validTo;
	
	@Column(name="subscriptionId", nullable=false)
	private int subscriptionId;
	
	
	@Column(name="createdAt")
	@CreationTimestamp
	private Date createdAt;
	
	@Column(name="updatedAt")
	@UpdateTimestamp
	private Date updatedAt;
	
	@Column(name="paymentStatus")
	private String paymentStatus;
	
	@Column(name="paymentTransactionId")
	private String paymentTransactionId;
	
	@Column(name="paymentUrl")
	private String paymentUrl;
	 
	public UserSubscription() {
		
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
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
	 * @return the validFrom
	 */
	public Date getValidFrom() {
		return validFrom;
	}

	/**
	 * @param validFrom the validFrom to set
	 */
	public void setValidFrom(Date validFrom) {
		this.validFrom = validFrom;
	}

	/**
	 * @return the validTo
	 */
	public Date getValidTo() {
		return validTo;
	}

	/**
	 * @param validTo the validTo to set
	 */
	public void setValidTo(Date validTo) {
		this.validTo = validTo;
	}

	

	/**
	 * @return the subscriptionId
	 */
	public int getSubscriptionId() {
		return subscriptionId;
	}

	/**
	 * @param subscriptionId the subscriptionId to set
	 */
	public void setSubscriptionId(int subscriptionId) {
		this.subscriptionId = subscriptionId;
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
	 * @return the updatedAt
	 */
	public Date getUpdatedAt() {
		return updatedAt;
	}

	/**
	 * @param updatedAt the updatedAt to set
	 */
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	/**
	 * @return the paymentStatus
	 */
	public String getPaymentStatus() {
		return paymentStatus;
	}

	/**
	 * @param paymentStatus the paymentStatus to set
	 */
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	/**
	 * @return the paymentTransactionId
	 */
	public String getPaymentTransactionId() {
		return paymentTransactionId;
	}

	/**
	 * @param paymentTransactionId the paymentTransactionId to set
	 */
	public void setPaymentTransactionId(String paymentTransactionId) {
		this.paymentTransactionId = paymentTransactionId;
	}
	
	

	/**
	 * @return the paymentUrl
	 */
	public String getPaymentUrl() {
		return paymentUrl;
	}

	/**
	 * @param paymentUrl the paymentUrl to set
	 */
	public void setPaymentUrl(String paymentUrl) {
		this.paymentUrl = paymentUrl;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "UserSubscription [id=" + id + ", userId=" + userId + ", validFrom=" + validFrom + ", validTo=" + validTo
				+ ", subscriptionId=" + subscriptionId + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt
				+ ", paymentStatus=" + paymentStatus + ", paymentTransactionId=" + paymentTransactionId
				+ ", paymentUrl=" + paymentUrl + "]";
	}

	

	
	
		
	
	

}
