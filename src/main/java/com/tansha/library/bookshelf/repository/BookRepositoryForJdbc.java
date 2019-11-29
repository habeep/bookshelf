package com.tansha.library.bookshelf.repository;

import java.util.List;

import com.tansha.library.bookshelf.model.SearchResult;

public interface BookRepositoryForJdbc {

	List<SearchResult> selectSearchBooks(int publisherId,int bookCategoryId,int languageId,int readingLevelList);
}
