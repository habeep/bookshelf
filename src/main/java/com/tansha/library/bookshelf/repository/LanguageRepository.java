package com.tansha.library.bookshelf.repository;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.tansha.library.bookshelf.model.Language;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
//import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
//import org.springframework.data;
//import org.springframework.boot.autoconfigur
@Transactional
public interface LanguageRepository extends CrudRepository<Language,String> {

	List<Language> findByLanguage(String language);
	List<Language> findByLanguageID(int languageID);
	
	@Query("SELECT language.languageID from Language language where language.language LIKE  %?1%")
	List<Integer> findByLanguageLike(String language);
	
	@Query("SELECT language from Language language where language.isActive = 1")
	List<Language> findAllActiveLanguage(int isActiveFlag);
	 
}
