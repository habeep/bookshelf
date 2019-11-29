package com.tansha.library.bookshelf.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;
import com.tansha.library.bookshelf.model.Testimonial;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
@Transactional
public interface TestimonialRepository extends CrudRepository<Testimonial,String> {

	List<Testimonial> findById(int id);
	Optional<Testimonial> findByUserid(int userid);
	
	@Query("SELECT testi.testimonial,user.name,user.emailId from Testimonial testi, User user where testi.userid=user.id AND testi.isApproved = ?1 AND testi.isActive = 1 ")
	List<Object[]> getTestimonials(int approvedFlag);
	
	
	 
}