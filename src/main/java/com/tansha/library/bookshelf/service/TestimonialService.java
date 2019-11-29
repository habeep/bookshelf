package com.tansha.library.bookshelf.service;

import java.util.List;
import com.tansha.library.bookshelf.model.Testimonial;

public interface TestimonialService {

	List<Testimonial> getAllTestimonal();

	boolean addTestimonial(Testimonial Testimonial);

	void updateTestimonial(Testimonial Testimonial);

	void deleteTestimonial(int TestimonialId);

}
