package com.tansha.library.bookshelf.model;

import java.sql.Date;

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
@Table(name="tbl_books_details")
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "bookId")
	private int bookId;
	
	@Column(name="bookTitle", nullable=false, length=155)
	private String bookTitle;
	
	
	@Column(name="ISBNCODE", nullable=false, length=20)
	private String isbncode;
	
	@Column(name="languageId", nullable=false)
	private int languageId;
	
	@Column(name="authorID", nullable=false)
	private int authorID;
	
	@Column(name="publisherID", nullable=false)
	private int publisherID;
	
	@Column(name="categoryID", nullable=false)
	private int categoryID;
	
	@Column(name="publishedYear", nullable=false)
	private int publishedYear;
	
	@Column(name="createdAt")
	@CreationTimestamp()
	private Date createdAt;
	
	@Column(name="modifiedAt")
	@UpdateTimestamp
	private Date modifiedAt;
	
	@ColumnDefault(value="1")
	@Column(name="isActive")
	private int isActive;
	
	@ColumnDefault(value="1")
	@Column(name="isBookBorrowed")
	private int isBookBorrowed;
	
	@ColumnDefault(value="1")
	@Column(name="isLost")
	private int isLost;
	
	@ColumnDefault(value="1")
	@Column(name="isFeaturedBook")
	private int isFeaturedBook;
	
	@Column(name="title_long")
	private String longTitle;

	@Column(name="description")
	private String description;
	
	@Column(name="amount", nullable=false)
	private int amount;
	
	@Column(name="noofPages", nullable=false)
	private int noofPages;

	@Column(name="readingLevelId", nullable=false)
	private int readingLevelId;

	@Column(name="binding", nullable=false)
	private String binding;

	
	
	
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

	
	public Book() {
		
	}

	/**
	 * @return the bookId
	 */
	public int getBookId() {
		
		return bookId;
	}

	/**
	 * @param bookId the bookId to set
	 */
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	/**
	 * @return the bookTitle
	 */
	public String getBookTitle() {
		return bookTitle;
	}

	/**
	 * @param bookTitle the bookTitle to set
	 */
	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}

	

	

	/**
	 * @return the isbncode
	 */
	public String getIsbncode() {
		return isbncode;
	}

	/**
	 * @param isbncode the isbncode to set
	 */
	public void setIsbncode(String isbncode) {
		this.isbncode = isbncode;
	}

	/**
	 * @return the languageId
	 */
	public int getLanguageId() {
		return languageId;
	}

	/**
	 * @param languageId the languageId to set
	 */
	public void setLanguageId(int languageId) {
		this.languageId = languageId;
	}

	/**
	 * @return the authorID
	 */
	public int getAuthorID() {
		return authorID;
	}

	/**
	 * @param authorID the authorID to set
	 */
	public void setAuthorID(int authorID) {
		this.authorID = authorID;
	}

	/**
	 * @return the publisherID
	 */
	public int getPublisherID() {
		return publisherID;
	}

	/**
	 * @param publisherID the publisherID to set
	 */
	public void setPublisherID(int publisherID) {
		this.publisherID = publisherID;
	}

	/**
	 * @return the categoryID
	 */
	public int getCategoryID() {
		return categoryID;
	}

	/**
	 * @param categoryID the categoryID to set
	 */
	public void setCategoryID(int categoryID) {
		this.categoryID = categoryID;
	}

	/**
	 * @return the publishedYear
	 */
	public int getPublishedYear() {
		return publishedYear;
	}

	/**
	 * @param publishedYear the publishedYear to set
	 */
	public void setPublishedYear(int publishedYear) {
		this.publishedYear = publishedYear;
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
	 * @return the isBookBorrowed
	 */
	public int getIsBookBorrowed() {
		return isBookBorrowed;
	}

	/**
	 * @param isBookBorrowed the isBookBorrowed to set
	 */
	public void setIsBookBorrowed(int isBookBorrowed) {
		this.isBookBorrowed = isBookBorrowed;
	}

	/**
	 * @return the isFeaturedBook
	 */
	public int getIsFeaturedBook() {
		return isFeaturedBook;
	}

	/**
	 * @param isFeaturedBook the isFeaturedBook to set
	 */
	public void setIsFeaturedBook(int isFeaturedBook) {
		this.isFeaturedBook = isFeaturedBook;
	}

	/**
	 * @return the isLost
	 */
	public int getIsLost() {
		return isLost;
	}

	/**
	 * @param isLost the isLost to set
	 */
	public void setIsLost(int isLost) {
		this.isLost = isLost;
	}

	/**
	 * @return the longTitle
	 */
	public String getLongTitle() {
		return longTitle;
	}

	/**
	 * @param longTitle the longTitle to set
	 */
	public void setLongTitle(String longTitle) {
		this.longTitle = longTitle;
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
	 * @return the noofPages
	 */
	public int getNoofPages() {
		return noofPages;
	}

	/**
	 * @param noofPages the noofPages to set
	 */
	public void setNoofPages(int noofPages) {
		this.noofPages = noofPages;
	}

	/**
	 * @return the binding
	 */
	public String getBinding() {
		return binding;
	}

	/**
	 * @param binding the binding to set
	 */
	public void setBinding(String binding) {
		this.binding = binding;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Book [bookId=" + bookId + ", bookTitle=" + bookTitle + ", isbncode=" + isbncode + ", languageId="
				+ languageId + ", authorID=" + authorID + ", publisherID=" + publisherID + ", categoryID=" + categoryID
				+ ", publishedYear=" + publishedYear + ", createdAt=" + createdAt + ", modifiedAt=" + modifiedAt
				+ ", isActive=" + isActive + ", isBookBorrowed=" + isBookBorrowed + ", isLost=" + isLost
				+ ", isFeaturedBook=" + isFeaturedBook + ", longTitle=" + longTitle + ", description=" + description
				+ ", amount=" + amount + ", noofPages=" + noofPages + ", readingLevelId=" + readingLevelId
				+ ", binding=" + binding + "]";
	}

	

	

	
	

}
