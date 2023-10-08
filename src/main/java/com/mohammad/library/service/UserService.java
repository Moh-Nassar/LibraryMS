package com.mohammad.library.service;

import com.mohammad.library.model.Book;
import com.mohammad.library.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

	User updateUser(User user);


	void returnBook(Book book, User user);


	void addBook(Book book, User user);


	User getUserById(String id);

	User getUserByName(String name);

	List<Book> borrowedBooksByUser(String id);
}
