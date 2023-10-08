package com.mohammad.library.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.mohammad.library.model.Book;
import com.mohammad.library.model.CustomException;
import com.mohammad.library.model.User;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {LibraryServiceImpl.class})
@ExtendWith(SpringExtension.class)
class LibraryServiceImplTest {
	@MockBean
	private BookService bookService;

	@Autowired
	private LibraryServiceImpl libraryServiceImpl;

	@MockBean
	private UserService userService;

	@Test
	public void testBorrowBook_shouldCallUserAndBookService() throws CustomException {
		Book book = new Book();
		book.setId(new ObjectId());
		book.setAuthor("Test Author");
		book.setTitle("Test Title");
		book.setQuantity(5);

		User user = new User();
		user.setId(new ObjectId());
		user.setName("Test NAme");
		user.setType("Student");

		when(bookService.isAvailable(any(Integer.class))).thenReturn(true);
		when(bookService.getBookById(book.getId().toString())).thenReturn(book);
		when(userService.getUserById(user.getId().toString())).thenReturn(user);

		libraryServiceImpl.borrowBook(user.getId().toString(), book.getId().toString());

		verify(bookService).borrowBook(book);
		verify(userService).addBook(book,user);
	}

	@Test
	public void testBorrowBook_userDoesNotExist_shouldThrowException() throws CustomException {
		Book book = new Book();
		book.setId(new ObjectId());
		book.setAuthor("Test Author");
		book.setTitle("Test Title");
		book.setQuantity(5);

		User user = new User();
		user.setId(new ObjectId());
		user.setName("Test NAme");
		user.setType("Student");

		when(bookService.isAvailable(any(Integer.class))).thenReturn(true);
		when(bookService.getBookById(book.getId().toString())).thenReturn(book);
		when(userService.getUserById(user.getId().toString())).thenReturn(null);

		assertThrows(
				CustomException.class,
				() -> libraryServiceImpl.borrowBook(book.getId().toString(), user.getId().toString())
		);
	}

	@Test
	public void testBorrowBook_bookUnavailable_shouldThrowException() throws CustomException {
		Book book = new Book();
		book.setId(new ObjectId());
		book.setAuthor("Test Author");
		book.setTitle("Test Title");
		book.setQuantity(5);

		User user = new User();
		user.setId(new ObjectId());
		user.setName("Test NAme");
		user.setType("Student");

		when(bookService.isAvailable(any(Integer.class))).thenReturn(false);
		when(bookService.getBookById(book.getId().toString())).thenReturn(book);
		when(userService.getUserById(user.getId().toString())).thenReturn(user);

		assertThrows(
				CustomException.class,
				() -> libraryServiceImpl.borrowBook(book.getId().toString(), user.getId().toString())
		);
	}
}

