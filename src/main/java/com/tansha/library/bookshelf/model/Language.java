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
@Table(name="tbl_language_master")
public class Language {
	@Id
	@GeneratedValue
	@Column(name = "languageID")
	private int languageID;
	
	@Column(name="language",nullable=false,length=155,unique=true)
	private String language;
	
	@Column(name="createdAt")
	@CreationTimestamp()
	private Date createdAt;
	
	@Column(name="updatedAt")
	@UpdateTimestamp
	private Date updatedAt;
	
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

	
	public Language() {
		
	}


	/**
	 * @return the languageID
	 */
	public int getLanguageID() {
		return languageID;
	}


	/**
	 * @param languageID the languageID to set
	 */
	public void setLanguageID(int languageID) {
		this.languageID = languageID;
	}


	/**
	 * @return the language
	 */
	public String getLanguage() {
		return language;
	}


	/**
	 * @param language the language to set
	 */
	public void setLanguage(String language) {
		this.language = language;
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
		return updatedAt;
	}


	/**
	 * @param modifiedAt the modifiedAt to set
	 */
	public void setModifiedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "language [languageID=" + languageID + ", language=" + language + ", createdAt=" + createdAt
				+ ", modifiedAt=" + updatedAt + "]";
	}
	
	
	

}
