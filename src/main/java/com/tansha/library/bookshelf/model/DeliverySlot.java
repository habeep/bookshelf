package com.tansha.library.bookshelf.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
@Entity
@Table(name="tbl_delivery_slot_master")
public class DeliverySlot {
	@Id
	@GeneratedValue
	@Column(name = "slotID")
	private int slotID;
	
	@Column(name="staffID")
	private int staffID;
	
	@Column(name="dateOfdelivery")
	private Date dateOfdelivery;
	
	@Column(name="deliveryTimeFrom")
	private int deliveryTimeFrom;
	
	@Column(name="deliveryTimeTill")
	private int deliveryTimeTill;
	
	@Column(name="areaID")
	private int areaID;

	public DeliverySlot() {
		
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
	 * @return the dateOfdelivery
	 */
	public Date getDateOfdelivery() {
		return dateOfdelivery;
	}

	/**
	 * @param dateOfdelivery the dateOfdelivery to set
	 */
	public void setDateOfdelivery(Date dateOfdelivery) {
		this.dateOfdelivery = dateOfdelivery;
	}

	/**
	 * @return the deliveryTimeFrom
	 */
	public int getDeliveryTimeFrom() {
		return deliveryTimeFrom;
	}

	/**
	 * @param deliveryTimeFrom the deliveryTimeFrom to set
	 */
	public void setDeliveryTimeFrom(int deliveryTimeFrom) {
		this.deliveryTimeFrom = deliveryTimeFrom;
	}

	/**
	 * @return the deliveryTimeTill
	 */
	public int getDeliveryTimeTill() {
		return deliveryTimeTill;
	}

	/**
	 * @param deliveryTimeTill the deliveryTimeTill to set
	 */
	public void setDeliveryTimeTill(int deliveryTimeTill) {
		this.deliveryTimeTill = deliveryTimeTill;
	}

	/**
	 * @return the areaID
	 */
	public int getAreaID() {
		return areaID;
	}

	/**
	 * @param areaID the areaID to set
	 */
	public void setAreaID(int areaID) {
		this.areaID = areaID;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DeliverySlot [slotID=" + slotID + ", staffID=" + staffID + ", dateOfdelivery=" + dateOfdelivery
				+ ", deliveryTimeFrom=" + deliveryTimeFrom + ", deliveryTimeTill=" + deliveryTimeTill + ", areaID="
				+ areaID + "]";
	}

	

}
