package com.mohammad.library.service;

import com.mohammad.library.model.Book;
import com.mohammad.library.repository.BookRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

	@Autowired
	BookRepository bookRepository;

	public List<Book> getAllBooks() {
		return bookRepository.findAll();
	}

	public List<Book> getAvailableBooks() {
		return bookRepository.availableBooks();
	}


	public void addBook(Book book) {
		bookRepository.save(book);
	}

	public Book getBookById(String id) {
		if (!ObjectId.isValid(id)) {
			return null;
		}
		ObjectId objectId = new ObjectId(id);
		Optional<Book> optionalBook = bookRepository.findById(objectId);
		return optionalBook.orElse(null);
	}

	public Book updateBook(Book book) {
		if (!bookRepository.existsById(book.getId())) {
			return null;
		}
		return bookRepository.save(book);
	}

	public void deleteBook(String id) {
		if (!ObjectId.isValid(id)) {
			return;
		}
		ObjectId objectId = new ObjectId(id);
		bookRepository.deleteById(objectId);
	}

	public boolean isAvailable(int quantity) {
		return quantity > 0;
	}

	public void borrowBook(Book book) {
		book.setQuantity(book.getQuantity() - 1);
		this.updateBook(book);
	}

	public void returnBook(Book book) {
		book.setQuantity(book.getQuantity() + 1);
		this.updateBook(book);
	}

}
