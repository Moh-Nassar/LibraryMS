package com.mohammad.library.service;

import com.mohammad.library.model.Book;
import com.mohammad.library.model.CustomException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookService {


	List<Book> getAllBooks();

	List<Book> getAvailableBooks();

	void addBook(Book book);

	Book getBookById(String id) throws CustomException;

	List<Book> getBookByTitle(String title) throws CustomException;

	Book updateBook(Book book) throws CustomException;

	void deleteBook(String id) throws CustomException;

	boolean isAvailable(int quantity);

	void borrowBook(Book book) throws CustomException;

	void returnBook(Book book) throws CustomException;
}
