package com.tansha.library.bookshelf.repository;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.tansha.library.bookshelf.model.Publisher;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
@Transactional
public interface PublisherRepository extends CrudRepository<Publisher,String> {

	List<Publisher> findByPublisherName(String publisherName);
	List<Publisher> findByPublisherId(int publisherId);
	
	@Query("SELECT publisher.publisherId from Publisher publisher where publisher.publisherName LIKE  %?1%")
	List<Integer> findByPublisherNameLike(String publisherName);
	 
	@Query("SELECT publisher from Publisher publisher where publisher.isActive = ?1")
	List<Publisher> findAllActivePublisher(int isActiveFlag);
}
