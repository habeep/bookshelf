package com.tansha.library.bookshelf.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
@Entity
@Table(name="tbl_reading_level")
public class ReadingLevel {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "Id")
	private int readingLevelId;
	
	@Column(name="readingLevel")
	private String readingLevel;
	
	@Column(name="description")
	private String description;
	
	@ColumnDefault(value="0")
	@Column(name="isHeader")
	private int isHeader;
	
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

	public ReadingLevel() {
		
	}

	

	/**
	 * @return the readingLevelId
	 */
	public int getReadingLevelId() {
		return readingLevelId;
	}

	/**
	 * @param readingLevelId the readingLevelId to set
	 */
	public void setReadingLevelId(int readingLevelId) {
		this.readingLevelId = readingLevelId;
	}

	/**
	 * @return the readingLevel
	 */
	public String getReadingLevel() {
		return readingLevel;
	}

	/**
	 * @param readingLevel the readingLevel to set
	 */
	public void setReadingLevel(String readingLevel) {
		this.readingLevel = readingLevel;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the isHeader
	 */
	public int getIsHeader() {
		return isHeader;
	}

	/**
	 * @param isHeader the isHeader to set
	 */
	public void setIsHeader(int isHeader) {
		this.isHeader = isHeader;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ReadingLevel [readingLevelId=" + readingLevelId + ", readingLevel=" + readingLevel + ", description=" + description
				+ ", isHeader=" + isHeader + ", isActive=" + isActive + "]";
	}

	

}
