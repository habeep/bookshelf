package com.tansha.library.bookshelf.service;

import java.util.List;

import com.tansha.library.bookshelf.model.Author;

public interface AuthorService {
	List<Author> getAllAuthors();
	Author getAuthorById(int authorId);
    boolean addAuthor(Author author);
    void updateAuthor(Author author);
    void deleteAuthor(int authorId);
}
