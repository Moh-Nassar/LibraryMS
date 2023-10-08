package com.mohammad.library.service;

import com.mohammad.library.model.Book;
import com.mohammad.library.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LibraryServiceImpl implements LibraryService {

	@Autowired
	BookService bookService;

	@Autowired
	UserService userService;

	/**
	 * User borrows a Book
	 * @param userId id of the user
	 * @param bookId id of the book
	 */
	public void borrowBook(String userId, String bookId){
		//get what user borrowing what book
		Book book = bookService.getBookById(bookId);
		User user = userService.getUserById(userId);

		//check if they exist
		if (book == null || user == null) {
			return;
		}

		//check availability
		int bookQuantity = book.getQuantity();
		if (!bookService.isAvailable(bookQuantity)) {
			return;
		}

		//update the user and book data
		bookService.borrowBook(book);
		userService.addBook(book, user);
	}

	/**
	 * User returns a Book
	 * @param userId id of the user
	 * @param bookId id of the book
	 */
	public void returnBook(String userId, String bookId){
		//get what user borrowing what book
		Book book = bookService.getBookById(bookId);
		User user = userService.getUserById(userId);

		//check if they exist
		if (book == null || user == null) {
			return;
		}

		//check availability
		if (!user.getBorrowedBooks().contains(book)) {
			return;
		}

		//update the user and book data
		bookService.returnBook(book);
		userService.returnBook(book, user);
	}

}
