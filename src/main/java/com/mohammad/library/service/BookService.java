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
	public List<Book> getAllBooks();

	/**
	 * Get all Books available to be borrowed. Having more than 0 quantity.
	 * @return
	 */
	public List<Book> getAvailableBooks();

	/**
	 * Add new Book
	 * @param book
	 */
	public void addBook(Book book);

	/**
	 * Get a book providing id.
	 * @param id
	 * @return
	 */
	public Book getBookById(String id);

	/**
	 * Update existing book
	 * @param book
	 * @return
	 */
	public Book updateBook(Book book);

	/**
	 * Delete existing Book.
	 * @param id
	 */
	public void deleteBook(String id);

	/**
	 * Check if a book is available to borrow.
	 * @param quantity
	 * @return
	 */
	public boolean isAvailable(int quantity);

	/**
	 * remove copy of the book from db. decrease its quantity by 1.
	 * @param book
	 */
	public void borrowBook(Book book);

	/**
	 * add copy of the book to the db. increase its quantity by 1.
	 * @param book
	 */
	public void returnBook(Book book);
}
