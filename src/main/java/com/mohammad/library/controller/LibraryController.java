package com.mohammad.library.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.mohammad.library.model.Book;
import com.mohammad.library.model.CustomException;
import com.mohammad.library.service.BookService;
import com.mohammad.library.service.LibraryService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/library")
public class LibraryController {

	@Autowired
	BookService bookService;

	@Autowired
	LibraryService libraryService;

	@GetMapping("/test")
	public String test(){
		return "Hello Library";
	}

	/**
	 * Get all books exists in the DB
	 * @return
	 */
	@GetMapping("/book/all")
	public List<Book> getAll(){
		return bookService.getAllBooks();
	}

	/**
	 * Get all books available to be borrowed
	 * @return
	 */
	@GetMapping("/book/allAvailable")
	public List<Book> getAllAvailable(){
		return bookService.getAvailableBooks();
	}

	/**
	 * Add new Book
	 * @param bookJSON
	 * @return
	 */
	@PostMapping("/book/save")
	public ResponseEntity<String> doSave(
			@RequestBody String bookJSON
	){
		Book book;
		try {
			book = new ObjectMapper().readValue(bookJSON, Book.class);
		} catch (JsonProcessingException e) {
			return new ResponseEntity<>("invalid JSON format: " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		bookService.addBook(book);
		return new ResponseEntity<>("Book added successfully!", HttpStatus.valueOf(200));
	}

	/**
	 * Get Book by ID
	 * @param id
	 * @return
	 * @throws CustomException
	 */
	@GetMapping("/book/byId")
	public ResponseEntity<Map<String, Book>> doGetById(
			@RequestParam String id
	) throws CustomException {
		Map<String, Book> response = new HashMap<>();

		Book book = bookService.getBookById(id);
		if (book == null) {
			return new ResponseEntity<>(new HashMap<>(), HttpStatus.NOT_FOUND);
		}
		response.put("Book", book);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * Update existing book
	 * @param bookJSON JSON String of the book to be saved
	 * @return
	 * @throws CustomException
	 */
	@PutMapping("/book/update")
	public ResponseEntity<String> doUpdateBook(
			@RequestBody String bookJSON
	) throws CustomException {
		Book book;
		try {
			book = new ObjectMapper().readValue(bookJSON, Book.class);
		} catch (JsonProcessingException e) {
			return new ResponseEntity<>("invalid JSON format: " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		Book updatedBook = bookService.updateBook(book);
		if (updatedBook == null) {
			return new ResponseEntity<>("Book doesn't exist", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>("Book updated successfully!", HttpStatus.OK);
	}

	/**
	 * Delete Book from the DB by its ID
	 * @param id
	 * @return
	 * @throws CustomException
	 */
	@DeleteMapping("/book/delete")
	public ResponseEntity<String> doDeleteBook(
			@RequestParam String id
	) throws CustomException {
		bookService.deleteBook(id);
		return new ResponseEntity<>("Book deleted successfully", HttpStatus.OK);
	}

	/**
	 * Borrow a book
	 * @param userId user Id that borrows
	 * @param bookId book Id that is borrowed
	 * @return
	 * @throws CustomException
	 */
	@PostMapping("/borrow")
	public ResponseEntity<String> doBorrow(
			@RequestParam String userId,
			@RequestParam String bookId
	) throws CustomException {
		libraryService.borrowBook(userId, bookId);
		return new ResponseEntity<>("Book " + bookId + " successfully borrowed by " + userId , HttpStatus.OK);
	}

	/**
	 * Return already borrowed Book
	 * @param userId
	 * @param bookId
	 * @return
	 * @throws CustomException
	 */
	@PostMapping("/return")
	public ResponseEntity<String> doReturn(
			@RequestParam String userId,
			@RequestParam String bookId
	) throws CustomException {
		libraryService.returnBook(userId, bookId);
		return new ResponseEntity<>("Book " + bookId + " successfully borrowed by " + userId , HttpStatus.OK);
	}
}
