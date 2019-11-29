package com.tansha.library.bookshelf.service;

public interface SecurityService {
	String findLoggedInUsername();

	void autoLogin(String username, String password);
}
