package com.tansha.library.bookshelf.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
@Entity
@Table(name="tbl_subscriptions_master")
public class Subscription {
	@Id
	@GeneratedValue
	@Column(name = "subc_ID")
	private int subcId;
	
	
	@Column(name="subscriptionName", nullable=false, length=45)
	private String subscriptionName;
	
	@Column(name="subscriptionDescription", nullable=false)
	private String subscriptionDescription;
	
	@Column(name="subscriptionSummary", nullable=false)
	private String subscriptionSummary;
	
	@Column(name="ruleId", nullable=false)
	private int ruleId;
	
	@Column(name="amount", nullable=false)
	private int amount;
	
	
	@Column(name="createdAt")
	@CreationTimestamp()
	private Date createdAt;
	
	@Column(name="updatedAt")
	@UpdateTimestamp
	private Date updatedAt;
	
	@ColumnDefault(value="1")
	@Column(name="isActive")
	private int isActive;
	
	@Column(name="noofMonths", nullable=true)
	private int noofMonths;
	
	@Column(name="maxNumberofBooks", nullable=false)
	private int maxNumberofBooks;
	
	@Column(name="maxNumberofDelivery", nullable=false)
	private int maxNumberofDelivery;
	
	
	
	/**
	 * @return the isActive
	 */
	public int getIsActive() {
		return isActive;
	}

	/**
	 * @param isActive the isActive to set
	 */
	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	public Subscription() {
		
	}

	

	/**
	 * @return the subcId
	 */
	public int getSubcId() {
		return subcId;
	}



	/**
	 * @param subcId the subcId to set
	 */
	public void setSubcId(int subcId) {
		this.subcId = subcId;
	}



	/**
	 * @return the subscriptionName
	 */
	public String getSubscriptionName() {
		return subscriptionName;
	}

	/**
	 * @param subscriptionName the subscriptionName to set
	 */
	public void setSubscriptionName(String subscriptionName) {
		this.subscriptionName = subscriptionName;
	}

	/**
	 * @return the subscriptionDescription
	 */
	public String getSubscriptionDescription() {
		return subscriptionDescription;
	}

	/**
	 * @param subscriptionDescription the subscriptionDescription to set
	 */
	public void setSubscriptionDescription(String subscriptionDescription) {
		this.subscriptionDescription = subscriptionDescription;
	}

	/**
	 * @return the subscriptionSummary
	 */
	public String getSubscriptionSummary() {
		return subscriptionSummary;
	}

	/**
	 * @param subscriptionSummary the subscriptionSummary to set
	 */
	public void setSubscriptionSummary(String subscriptionSummary) {
		this.subscriptionSummary = subscriptionSummary;
	}

	/**
	 * @return the ruleId
	 */
	public int getRuleId() {
		return ruleId;
	}

	/**
	 * @param ruleId the ruleId to set
	 */
	public void setRuleId(int ruleId) {
		this.ruleId = ruleId;
	}

	/**
	 * @return the amount
	 */
	public int getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(int amount) {
		this.amount = amount;
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
	 * @return the noofMonths
	 */
	public int getNoofMonths() {
		return noofMonths;
	}

	/**
	 * @param noofMonths the noofMonths to set
	 */
	public void setNoofMonths(int noofMonths) {
		this.noofMonths = noofMonths;
	}

	/**
	 * @return the maxNumberofBooks
	 */
	public int getMaxNumberofBooks() {
		return maxNumberofBooks;
	}

	/**
	 * @param maxNumberofBooks the maxNumberofBooks to set
	 */
	public void setMaxNumberofBooks(int maxNumberofBooks) {
		this.maxNumberofBooks = maxNumberofBooks;
	}

	/**
	 * @return the maxNumberofDelivery
	 */
	public int getMaxNumberofDelivery() {
		return maxNumberofDelivery;
	}

	/**
	 * @param maxNumberofDelivery the maxNumberofDelivery to set
	 */
	public void setMaxNumberofDelivery(int maxNumberofDelivery) {
		this.maxNumberofDelivery = maxNumberofDelivery;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Subscription [subcId=" + subcId + ", subscriptionName=" + subscriptionName
				+ ", subscriptionDescription=" + subscriptionDescription + ", subscriptionSummary="
				+ subscriptionSummary + ", ruleId=" + ruleId + ", amount=" + amount + ", createdAt=" + createdAt
				+ ", updatedAt=" + updatedAt + ", isActive=" + isActive + ", noofMonths=" + noofMonths
				+ ", maxNumberofBooks=" + maxNumberofBooks + ", maxNumberofDelivery=" + maxNumberofDelivery + "]";
	}

	

	

}
