package com.mohammad.library.service;

import com.mohammad.library.model.Book;
import com.mohammad.library.model.CustomException;
import com.mohammad.library.repository.BookRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

	@Autowired
	BookRepository bookRepository;

	/**
	 * Get All books exists in the db
	 * @return
	 */
	public List<Book> getAllBooks() {
		return bookRepository.findAll();
	}

	/**
	 * Get all Books available to be borrowed. Having more than 0 quantity.
	 * @return
	 */
	public List<Book> getAvailableBooks() {
		return bookRepository.availableBooks();
	}

	/**
	 * Add new Book
	 * @param book
	 */
	public void addBook(Book book) {
		bookRepository.save(book);
	}

	/**
	 * Get a book providing id.
	 * @param id
	 * @return
	 */
	public Book getBookById(String id) throws CustomException {
		if (!ObjectId.isValid(id)) {
			throw new CustomException("Invalid ObjectId format!", HttpStatus.BAD_REQUEST);
		}
		ObjectId objectId = new ObjectId(id);
		Optional<Book> optionalBook = bookRepository.findById(objectId);
		return optionalBook.orElse(null);
	}

	/**
	 * Update existing book
	 * @param book
	 * @return
	 */
	public Book updateBook(Book book) throws CustomException {
		if (!bookRepository.existsById(book.getId())) {
			throw new CustomException("Book does not exist!", HttpStatus.NOT_FOUND);
		}
		return bookRepository.save(book);
	}

	/**
	 * Delete existing Book.
	 * @param id
	 */
	public void deleteBook(String id) throws CustomException {
		if (!ObjectId.isValid(id)) {
			throw new CustomException("Book Id is not valid!", HttpStatus.BAD_REQUEST);
		}
		ObjectId objectId = new ObjectId(id);
		bookRepository.deleteById(objectId);
	}

	/**
	 * Check if a book is available to borrow.
	 * @param quantity
	 * @return
	 */
	public boolean isAvailable(int quantity) {
		return quantity > 0;
	}

	/**
	 * remove copy of the book from db. decrease its quantity by 1.
	 * @param book
	 */
	public void borrowBook(Book book) throws CustomException{
		book.setQuantity(book.getQuantity() - 1);
		this.updateBook(book);
	}

	/**
	 * add copy of the book to the db. increase its quantity by 1.
	 * @param book
	 */
	public void returnBook(Book book) throws CustomException{
		book.setQuantity(book.getQuantity() + 1);
		this.updateBook(book);
	}

}
