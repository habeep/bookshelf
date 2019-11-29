package com.tansha.library.bookshelf.service.impl;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tansha.library.bookshelf.model.Publisher;
import com.tansha.library.bookshelf.repository.PublisherRepository;
import com.tansha.library.bookshelf.service.PublisherService;

@Service
public class PublisherServiceImpl implements PublisherService {
	@Autowired
	private PublisherRepository publisherRepository;
	@Override
	public Publisher getPublisherById(int publisherId) {
		Publisher obj = publisherRepository.findByPublisherId(publisherId).get(0);
		return obj;
	}	
	@Override
	public List<Publisher> getAllPublishers(){
		
		List<Publisher> list = new ArrayList<>();
		publisherRepository.findAll().forEach(e -> list.add(e));
		return list;
	}
	@Override
	public synchronized boolean addPublisher(Publisher publisher){

	        List<Publisher> list = publisherRepository.findByPublisherName(publisher.getPublisherName()); 	
                if (list.size() > 0) {
    	           return false;
                } else {
    	        publisherRepository.save(publisher);
    	        return true;
       }
	}
	@Override
	public void updatePublisher(Publisher publisher) {
		publisherRepository.save(publisher);
	}
	@Override
	public void deletePublisher(int publisherId) {
		publisherRepository.delete(getPublisherById(publisherId));
	}
}
