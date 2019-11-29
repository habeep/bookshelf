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
@Table(name="tbl_subscription_rules_master")
public class SubscriptionRule {
	@Id
	@GeneratedValue
	@Column(name = "ruleId")
	private int ruleId;
	
	
	@Column(name="ruleName", nullable=false, length=45)
	private String ruleName;
	
	@Column(name="noofMonths", nullable=false)
	private int noofMonths;
	
	@Column(name="maxNumberofBooks", nullable=false)
	private int maxNumberofBooks;
	
	@Column(name="maxNumberofDelivery", nullable=false)
	private int maxNumberofDelivery;
	
	@Column(name="rule", nullable=false)
	private String rule;
	
	
	@Column(name="createdAt")
	@CreationTimestamp()
	private Date createdAt;
	
	@Column(name="modifiedAt")
	@UpdateTimestamp
	private Date modifiedAt;
	
	@ColumnDefault(value="1")
	@Column(name="isActive")
	private int isActive;
	
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

	
	public SubscriptionRule() {
		
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
	 * @return the ruleName
	 */
	public String getRuleName() {
		return ruleName;
	}

	/**
	 * @param ruleName the ruleName to set
	 */
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
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

	/**
	 * @return the rule
	 */
	public String getRule() {
		return rule;
	}

	/**
	 * @param rule the rule to set
	 */
	public void setRule(String rule) {
		this.rule = rule;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SubscriptionRule [ruleId=" + ruleId + ", ruleName=" + ruleName + ", noofMonths=" + noofMonths
				+ ", maxNumberofBooks=" + maxNumberofBooks + ", maxNumberofDelivery=" + maxNumberofDelivery + ", rule="
				+ rule + ", createdAt=" + createdAt + ", modifiedAt=" + modifiedAt + ", isActive=" + isActive + "]";
	}

	

	
	
	

}
