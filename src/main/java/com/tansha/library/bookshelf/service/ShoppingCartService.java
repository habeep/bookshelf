package com.tansha.library.bookshelf.service;

import com.tansha.library.bookshelf.exceptions.NotEnoughProductsInStockException;
import com.tansha.library.bookshelf.model.Book;

import java.math.BigDecimal;
import java.util.Map;

public interface ShoppingCartService {

    void addProduct(Book book);

    void removeProduct(Book book);

    Map<Book, Integer> getProductsInCart();

    void checkout() throws NotEnoughProductsInStockException;

    BigDecimal getTotal();
}
