package com.tansha.library.bookshelf.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
@Entity
@Table(name="tbl_users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name="name")
	private String name;
	


	@Column(name="houseNumber", length=20)
	private String houseNumber;

	
	@Column(name="street", length=20)
	private String street;

	@Column(name="locality", length=20)
	private String locality;

	@Column(name="city", length=20)
	private String city;

	@Column(name="pincode", length=20)
	private int pincode;

	@Column(name="district", length=20)
	private String district;

	@Column(name="landmark", length=20)
	private String landmark;

	@Column(name="emailId", length=20)
	private String emailId;
	
	@Transient
	private String confirmpassword;
	
	@Column(name="password", length=20)
	private String password;

	@Column(name="phoneNumber", length=20)
	private long phoneNumber;

	//@Column(name="gender", length=20)
	//private String gender;

	@Column(name="dateOfBirth",nullable=true)
	private Date dateOfBirth;

	@Column(name="dateofJoin")
	@CreationTimestamp()
	private Date dateofJoin;

	

	@Column(name="createdAt")
	@CreationTimestamp()
	private Date createdAt;
	
	@Column(name="modifiedAt")
	@UpdateTimestamp
	private Date modifiedAt;
	
	@Column(name = "isActive")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int isActive;
	
	
	@Column(name = "reset_token")
	private String resetToken;
	
	@Column(name = "numberofChildrens",nullable=true)
	private int numberofChildrens;
	
	@Column(name = "readingLevelIds",nullable=true)
	private String readingLevelIds;
	
	@Column(name = "flag")
	private int flag;
	
	/**
	 * @return the flag
	 */
	public int getFlag() {
		return flag;
	}

	/**
	 * @param flag the flag to set
	 */
	public void setFlag(int flag) {
		this.flag = flag;
	}

	public User() {
		
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the pincode
	 */
	public int getPincode() {
		return pincode;
	}

	/**
	 * @param pincode the pincode to set
	 */
	public void setPincode(int pincode) {
		this.pincode = pincode;
	}

	/**
	 * @return the district
	 */
	public String getDistrict() {
		return district;
	}

	/**
	 * @param district the district to set
	 */
	public void setDistrict(String district) {
		this.district = district;
	}

	

	/**
	 * @return the emailId
	 */
	public String getEmailId() {
		return emailId;
	}

	/**
	 * @param emailId the emailId to set
	 */
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}


	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the phoneNumber
	 */
	public long getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	

	
	/**
	 * @return the dateOfBirth
	 */
	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	/**
	 * @param dateOfBirth the dateOfBirth to set
	 */
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	/**
	 * @return the dateofJoin
	 */
	public Date getDateofJoin() {
		return dateofJoin;
	}

	/**
	 * @param dateofJoin the dateofJoin to set
	 */
	public void setDateofJoin(Date dateofJoin) {
		this.dateofJoin = dateofJoin;
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

	
	/**
	 * @return the resetToken
	 */
	public String getResetToken() {
		return resetToken;
	}

	/**
	 * @param resetToken the resetToken to set
	 */
	public void setResetToken(String resetToken) {
		this.resetToken = resetToken;
	}
	
	

	/**
	 * @return the confirmpassword
	 */
	public String getConfirmpassword() {
		return confirmpassword;
	}

	/**
	 * @param confirmpassword the confirmpassword to set
	 */
	public void setConfirmpassword(String confirmpassword) {
		this.confirmpassword = confirmpassword;
	}

	/**
	 * @return the houseNumber
	 */
	public String getHouseNumber() {
		return houseNumber;
	}

	/**
	 * @param houseNumber the houseNumber to set
	 */
	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}

	/**
	 * @return the street
	 */
	public String getStreet() {
		return street;
	}

	/**
	 * @param street the street to set
	 */
	public void setStreet(String street) {
		this.street = street;
	}

	/**
	 * @return the locality
	 */
	public String getLocality() {
		return locality;
	}

	/**
	 * @param locality the locality to set
	 */
	public void setLocality(String locality) {
		this.locality = locality;
	}

	/**
	 * @return the landmark
	 */
	public String getLandmark() {
		return landmark;
	}

	/**
	 * @param landmark the landmark to set
	 */
	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}

	
	/**
	 * @return the numberofChildrens
	 */
	public int getNumberofChildrens() {
		return numberofChildrens;
	}

	/**
	 * @param numberofChildrens the numberofChildrens to set
	 */
	public void setNumberofChildrens(int numberofChildrens) {
		this.numberofChildrens = numberofChildrens;
	}

	/**
	 * @return the readingLevelIds
	 */
	public String getReadingLevelIds() {
		return readingLevelIds;
	}

	/**
	 * @param readingLevelIds the readingLevelIds to set
	 */
	public void setReadingLevelIds(String readingLevelIds) {
		this.readingLevelIds = readingLevelIds;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", houseNumber=" + houseNumber + ", street=" + street
				+ ", locality=" + locality + ", city=" + city + ", pincode=" + pincode + ", district=" + district
				+ ", landmark=" + landmark + ", emailId=" + emailId + ", confirmpassword=" + confirmpassword
				+ ", password=" + password + ", phoneNumber=" + phoneNumber + ", dateOfBirth=" + dateOfBirth
				+ ", dateofJoin=" + dateofJoin + ", createdAt=" + createdAt + ", modifiedAt=" + modifiedAt
				+ ", isActive=" + isActive + ", resetToken=" + resetToken + ", numberofChildrens=" + numberofChildrens
				+ ", readingLevelIds=" + readingLevelIds + "]";
	}

	

	
	
}
