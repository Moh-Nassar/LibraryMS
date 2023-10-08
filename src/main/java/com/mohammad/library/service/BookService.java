package com.mohammad.library.service;

import com.mohammad.library.model.Book;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookService {

	/**
	 * Get All books exists in the db
	 * @return
	 */
	List<Book> getAllBooks();

	/**
	 * Get all Books available to be borrowed. Having more than 0 quantity.
	 * @return
	 */
	List<Book> getAvailableBooks();

	/**
	 * Add new Book
	 * @param book
	 */
	void addBook(Book book);

	/**
	 * Get a book providing id.
	 * @param id
	 * @return
	 */
	Book getBookById(String id);

	/**
	 * Update existing book
	 * @param book
	 * @return
	 */
	Book updateBook(Book book);

	/**
	 * Delete existing Book.
	 * @param id
	 */
	void deleteBook(String id);

	/**
	 * Check if a book is available to borrow.
	 * @param quantity
	 * @return
	 */
	boolean isAvailable(int quantity);

	/**
	 * remove copy of the book from db. decrease its quantity by 1.
	 * @param book
	 */
	void borrowBook(Book book);

	/**
	 * add copy of the book to the db. increase its quantity by 1.
	 * @param book
	 */
	void returnBook(Book book);
}
