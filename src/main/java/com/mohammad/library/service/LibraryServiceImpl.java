package com.mohammad.library.service;

import com.mohammad.library.model.Book;
import com.mohammad.library.model.CustomException;
import com.mohammad.library.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
	public void borrowBook(String userId, String bookId) throws CustomException {
		//get what user borrowing what book
		Book book = bookService.getBookById(bookId);
		User user = userService.getUserById(userId);

		//check if they exist
		if (book == null || user == null) {
			throw new CustomException("Book or User do not exists!", HttpStatus.BAD_REQUEST);
		}

		//check availability
		int bookQuantity = book.getQuantity();
		if (!bookService.isAvailable(bookQuantity)) {
			throw new CustomException("Book is not available to borrow.", HttpStatus.CONFLICT);
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
	public void returnBook(String userId, String bookId) throws CustomException{
		//get what user borrowing what book
		Book book = bookService.getBookById(bookId);
		User user = userService.getUserById(userId);

		//check if they exist
		if (book == null || user == null) {
			throw new CustomException("Book or User do not exists!", HttpStatus.BAD_REQUEST);
		}

		//check availability
		if (!user.getBorrowedBooks().stream().anyMatch(b -> b.getId().equals(book.getId()))) {
			throw new CustomException("User did not borrow the book already.", HttpStatus.CONFLICT);
		}

		//update the user and book data
		bookService.returnBook(book);
		userService.returnBook(book, user);
	}

}
