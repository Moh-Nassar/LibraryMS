package com.mohammad.library.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.mohammad.library.model.Book;
import com.mohammad.library.model.CustomException;
import com.mohammad.library.repository.BookRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {BookServiceImpl.class})
@ExtendWith(SpringExtension.class)
class BookServiceImplUnitTest {
	@MockBean
	private BookRepository bookRepository;

	@Autowired
	private BookServiceImpl bookServiceImpl;

	/**
	 * Test get all books
	 */
	@Test
	void testGetAllBooks() {
		ArrayList<Book> bookList = new ArrayList<>();
		when(bookRepository.findAll()).thenReturn(bookList);
		List<Book> actualAllBooks = bookServiceImpl.getAllBooks();
		assertSame(bookList, actualAllBooks);
		assertTrue(actualAllBooks.isEmpty());
		verify(bookRepository).findAll();
	}

	/**
	 * testGetAvailableBooks
	 */
	@Test
	void testGetAvailableBooks() {
		ArrayList<Book> bookList = new ArrayList<>();
		when(bookRepository.availableBooks()).thenReturn(bookList);
		List<Book> actualAvailableBooks = bookServiceImpl.getAvailableBooks();
		assertSame(bookList, actualAvailableBooks);
		assertTrue(actualAvailableBooks.isEmpty());
		verify(bookRepository).availableBooks();
	}

	/**
	 * Test Add Book
	 */
	@Test
	void testAddBook() {
		Book book = new Book();
		book.setAuthor("Author Test");
		book.setGenre("Adventure");
		book.setId(ObjectId.get());
		book.setQuantity(5);
		book.setTitle("Amazing Book");
		book.setType("journal");
		when(bookRepository.save(Mockito.<Book>any())).thenReturn(book);

		Book book2 = new Book();
		book2.setAuthor("Author Test");
		book2.setGenre("Adventure");
		ObjectId id = ObjectId.get();
		book2.setId(id);
		book2.setQuantity(5);
		book2.setTitle("Amazing Book");
		book2.setType("Journal");
		bookServiceImpl.addBook(book2);
		verify(bookRepository).save(Mockito.<Book>any());
		assertEquals("Author Test", book2.getAuthor());
		assertEquals("Journal", book2.getType());
		assertEquals("Amazing Book", book2.getTitle());
		assertEquals(5, book2.getQuantity().intValue());
		assertSame(id, book2.getId());
		assertEquals("Adventure", book2.getGenre());
		assertTrue(bookServiceImpl.getAllBooks().isEmpty());
	}

	/**
	 * Test get by Id when Id is not valid ObjectId
	 */
	@Test
	void testGetBookById_IdNotValid() {
		assertThrows(CustomException.class, () -> bookServiceImpl.getBookById("123123"));
	}

	/**
	 * Test get by Id when Id is valid but does not exist. Should return null.
	 */
	@Test
	void testGetBookById_IdDoesNotExist() throws CustomException {
		when(bookRepository.findById(any(ObjectId.class))).thenReturn(Optional.empty());
		assertEquals(null, bookServiceImpl.getBookById(new ObjectId().toString()));
	}

	/**
	 * Test get by Id when Id is valid and exists. Should return Book data.
	 */
	@Test
	void testGetBookById_returnBook() throws CustomException {
		Book book = new Book();
		book.setTitle("Test");
		when(bookRepository.findById(any(ObjectId.class))).thenReturn(Optional.of(book));
		assertEquals(book, bookServiceImpl.getBookById(new ObjectId().toString()));
	}

	/**
	 * Test updating book that does not exist
	 * @throws CustomException
	 */
	@Test
	void testUpdateBook_IdDoesNotExist_ShouldThrowException() {
		when(bookRepository.existsById(any(ObjectId.class))).thenReturn(false);
		assertThrows(CustomException.class, () -> bookServiceImpl.updateBook(new Book()));
	}

	/**
	 * Test updating book that exists
	 * @throws CustomException
	 */
	@Test
	void testUpdateBook_IdExists_ShouldTryToSave() throws CustomException {
		Book book = new Book();
		when(bookRepository.existsById(any())).thenReturn(true);
		bookServiceImpl.updateBook(book);
		verify(bookRepository).save(book);
	}

	/**
	 * Test deleting book with invalid id
	 */
	@Test
	void testDeleteBook_IdInvalid_ShouldThrowException() {
		assertThrows(CustomException.class, () -> bookServiceImpl.deleteBook("123"));
	}

	/**
	 * Test deleting book with valid id
	 */
	@Test
	void testDeleteBook_IdValid_ShouldTryToDelete() throws CustomException {
		ObjectId objectId = new ObjectId();
		bookServiceImpl.deleteBook(objectId.toString());
		verify(bookRepository).deleteById(objectId);
	}

	/**
	 * Test availability of a book
	 */
	@Test
	void testIsAvailable() {
		assertTrue(bookServiceImpl.isAvailable(1));
		assertFalse(bookServiceImpl.isAvailable(0));
	}

	/**
	 * Test Borrow Book
	 */
	@Test
	void testBorrowBook() throws CustomException {
		Book book = new Book();
		book.setAuthor("Test Author");
		book.setId(ObjectId.get());
		book.setQuantity(2);
		book.setTitle("Test Title");
		when(bookRepository.save(Mockito.<Book>any())).thenReturn(book);
		when(bookRepository.existsById(Mockito.<ObjectId>any())).thenReturn(true);

		Book book2 = new Book();
		book2.setAuthor("Test Author");
		book2.setId(ObjectId.get());
		book2.setQuantity(1);
		book2.setTitle("Dr");
		bookServiceImpl.borrowBook(book2);
		verify(bookRepository).existsById(Mockito.<ObjectId>any());
		verify(bookRepository).save(Mockito.<Book>any());
		assertEquals(0, book2.getQuantity().intValue());
	}

	/**
	 * Test return Book
	 */
	@Test
	void testReturnBook() throws CustomException {
		Book book = new Book();
		book.setAuthor("Test Author");
		book.setId(ObjectId.get());
		book.setQuantity(1);
		book.setTitle("Test Titl");
		when(bookRepository.save(Mockito.<Book>any())).thenReturn(book);
		when(bookRepository.existsById(Mockito.<ObjectId>any())).thenReturn(true);

		Book book2 = new Book();
		book2.setAuthor("Test Author");
		book2.setGenre("Genre");
		book2.setId(ObjectId.get());
		book2.setQuantity(1);
		book.setTitle("Test Title");
		bookServiceImpl.returnBook(book2);
		verify(bookRepository).existsById(Mockito.<ObjectId>any());
		verify(bookRepository).save(Mockito.<Book>any());
		assertEquals(2, book2.getQuantity().intValue());
	}
}

