package com.tansha.library.bookshelf.service.impl;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tansha.library.bookshelf.model.Author;
import com.tansha.library.bookshelf.repository.AuthorRepository;
import com.tansha.library.bookshelf.service.AuthorService;

@Service
public class AuthorServiceImpl implements AuthorService {
	@Autowired
	private AuthorRepository authorRepository;
	@Override
	public Author getAuthorById(int authorId) {
		Author obj = authorRepository.findByAuthorID(authorId).get(0);
		return obj;
	}	
	@Override
	public List<Author> getAllAuthors(){
		List<Author> list = new ArrayList<>();
		authorRepository.findAll().forEach(e -> list.add(e));
		return list;
	}
	@Override
	public synchronized boolean addAuthor(Author author){
	        List<Author> list = authorRepository.findByAuthorName(author.getAuthorName()); 	
                if (list.size() > 0) {
    	           return false;
                } else {
    	        authorRepository.save(author);
    	        return true;
       }
	}
	@Override
	public void updateAuthor(Author author) {
		authorRepository.save(author);
	}
	@Override
	public void deleteAuthor(int authorId) {
		authorRepository.delete(getAuthorById(authorId));
	}
}
