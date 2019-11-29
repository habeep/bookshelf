package com.tansha.library.bookshelf.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.tansha.library.bookshelf.model.DeliverySlot;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
//import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
//import org.springframework.data;
//import org.springframework.boot.autoconfigur
@Transactional
public interface DeliverySlotRepository extends CrudRepository<DeliverySlot,String> {

	//List<DeliverySlot> findBySlotID();
	
	DeliverySlot findBySlotID(int slotId);
	
	List<DeliverySlot> findByAreaID(int areaId);
	
	@Query("SELECT ds from DeliverySlot ds where ds.dateOfdelivery >= CURDATE() ")
	List<DeliverySlot> getAllDeliveries();

	@Query("SELECT count(ds) from DeliverySlot ds where ds.dateOfdelivery = ?1 AND ds.staffID= ?2 AND ds.areaID = ?3 AND ds.deliveryTimeFrom = ?4 AND ds.deliveryTimeTill = ?5")
	int checkDeliverySlot(Date dateOfDelivery, int staffId,int areaID,int deliveryTimeFrom,int deliveryTimeTill);
	
	@Query("SELECT ds from DeliverySlot ds where ds.dateOfdelivery >= CURDATE() AND ds.staffID= ?1  ")
	List<DeliverySlot> getStaffAllocatedDeliveries(int staffId);
	
	@Query(value="SELECT ds.* from tbl_delivery_slot_master ds INNER JOIN tbl_area_master area ON area.areaID=ds.areaID INNER JOIN tbl_users user ON user.pincode = area.pincode where user.id = ?1 AND ds.dateOfdelivery > date_add(CURDATE(),interval 1 day) ORDER by ds.dateOfdelivery ASC" , nativeQuery = true)
	List<DeliverySlot> getAllDeliveriesForUserArea(int userId);
	
}
