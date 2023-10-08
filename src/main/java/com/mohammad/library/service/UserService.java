package com.mohammad.library.service;

import com.mohammad.library.model.Book;
import com.mohammad.library.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
	/**
	 * update user data
	 * @param user
	 * @return
	 */
	User updateUser(User user);

	/**
	 * return book borrowed by the user
	 * @param book
	 * @param user
	 */
	void returnBook(Book book, User user);

	/**
	 * add a book to the borrowed books
	 * @param book
	 * @param user
	 */
	void addBook(Book book, User user);

	/**
	 * user data by its id
	 * @param id
	 * @return
	 */
	User getUserById(String id);
}
