package com.tansha.library.bookshelf.service.impl;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tansha.library.bookshelf.model.Language;
import com.tansha.library.bookshelf.repository.LanguageRepository;

import com.tansha.library.bookshelf.service.LanguageService;

@Service
public class LanguageServiceImpl implements LanguageService {
	@Autowired
	private LanguageRepository languageRepository;
	@Override
	public Language getLanguageById(int languageId) {
		Language obj = languageRepository.findByLanguageID(languageId).get(0);
		return obj;
	}	
	@Override
	public List<Language> getAllLanguages(){
		
		List<Language> list = new ArrayList<>();
		languageRepository.findAll().forEach(e -> list.add(e));
		return list;
	}
	@Override
	public synchronized boolean addLanguage(Language language){

	        List<Language> list = languageRepository.findByLanguage(language.getLanguage()); 	
                if (list.size() > 0) {
    	           return false;
                } else {
    	        languageRepository.save(language);
    	        return true;
       }
	}
	@Override
	public void updateLanguage(Language language) {
		languageRepository.save(language);
	}
	@Override
	public void deleteLanguage(int languageId) {
		languageRepository.delete(getLanguageById(languageId));
	}
}
