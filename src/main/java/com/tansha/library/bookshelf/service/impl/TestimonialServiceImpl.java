package com.tansha.library.bookshelf.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tansha.library.bookshelf.model.Testimonial;
import com.tansha.library.bookshelf.repository.TestimonialRepository;
import com.tansha.library.bookshelf.service.TestimonialService;

@Service
public class TestimonialServiceImpl implements TestimonialService {

	@Autowired
	private TestimonialRepository testimonialRepository;

	@Override
	public List<Testimonial> getAllTestimonal() {
		List<Testimonial> list = new ArrayList<>();
		testimonialRepository.findAll().forEach(e -> list.add(e));
		return list;
	}

	@Override
	public boolean addTestimonial(Testimonial Testimonial) {
		testimonialRepository.save(Testimonial);
		return true;
	}

	@Override
	public void updateTestimonial(Testimonial Testimonial) {
		testimonialRepository.save(Testimonial);

	}

	@Override
	public void deleteTestimonial(int TestimonialId) {
		testimonialRepository.delete(findById(TestimonialId));

	}

	public Testimonial findById(int testimonialId) {

		return null;
	}

}
