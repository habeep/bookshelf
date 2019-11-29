package com.tansha.library.bookshelf.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.tansha.library.bookshelf.model.Children;

import org.springframework.data.repository.CrudRepository;
//import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
//import org.springframework.data;
//import org.springframework.boot.autoconfigur
@Transactional
public interface ChildrenRepository extends CrudRepository<Children,String> {

	List<Children> findByUserId(int userId);
	List<Children> findById(int id);
	List<Children> findByDateOfBirthAndFirstName(Date dateOfBirth,String firstName);
	Children findByEmailId(String email);
	 
}
