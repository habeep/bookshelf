package com.tansha.library.bookshelf.service;

import java.util.List;

import com.tansha.library.bookshelf.model.Language;

public interface LanguageService {
	List<Language> getAllLanguages();
	Language getLanguageById(int languageId);
    boolean addLanguage(Language language);
    void updateLanguage(Language language);
    void deleteLanguage(int languageId);
}
