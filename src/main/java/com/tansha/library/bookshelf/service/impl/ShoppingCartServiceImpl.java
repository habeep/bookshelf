package com.tansha.library.bookshelf.service.impl;

import com.tansha.library.bookshelf.exceptions.NotEnoughProductsInStockException;
import com.tansha.library.bookshelf.model.Book;
import com.tansha.library.bookshelf.repository.BookRepository;
import com.tansha.library.bookshelf.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Shopping Cart is implemented with a Map, and as a session bean
 *
 * @author Dusan
 */
@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final BookRepository bookRepository;

    private Map<Book, Integer> books = new HashMap<>();

    @Autowired
    public ShoppingCartServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
     * If product is in the map just increment quantity by 1.
     * If product is not in the map with, add it with quantity 1
     *
     * @param book
     */
    @Override
    public void addProduct(Book book) {
        if (books.containsKey(book)) {
            books.replace(book, books.get(book) + 1);
        } else {
            books.put(book, 1);
        }
    }

    /**
     * If product is in the map with quantity > 1, just decrement quantity by 1.
     * If product is in the map with quantity 1, remove it from map
     *
     * @param book
     */
    @Override
    public void removeProduct(Book book) {
        if (books.containsKey(book)) {
            if (books.get(book) > 1)
                books.replace(book, books.get(book) - 1);
            else if (books.get(book) == 1) {
                books.remove(book);
            }
        }
    }

    /**
     * @return unmodifiable copy of the map
     */
    @Override
    public Map<Book, Integer> getProductsInCart() {
        return Collections.unmodifiableMap(books);
    }

    /**
     * Checkout will rollback if there is not enough of some product in stock
     *
     * @throws NotEnoughProductsInStockException
     */
    @Override
    public void checkout() throws NotEnoughProductsInStockException {
    	Book book;
        for (Map.Entry<Book, Integer> entry : books.entrySet()) {
            // Refresh quantity for every product before checking 
            book = bookRepository.findOne(entry.getKey().getBookId());
            if (book.getIsBookBorrowed() == 1)
                throw new NotEnoughProductsInStockException(book);
            entry.getKey().setIsBookBorrowed(0);
        }
        bookRepository.saveAll(books.keySet());
        //bookRepository.();
        books.clear();
    }

	@Override
	public BigDecimal getTotal() {
		// TODO Auto-generated method stub
		return null;
	}

    /*@Override
    public BigDecimal getTotal() {
        return books.entrySet().stream()
                .map(entry -> entry.getKey().getPrice().multiply(BigDecimal.valueOf(entry.getValue())))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }*/
}
