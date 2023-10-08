package com.mohammad.library.service;

import com.mohammad.library.model.Book;
import com.mohammad.library.model.User;
import com.mohammad.library.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	/**
	 * user data by its id
	 * @param id
	 * @return
	 */
	public User getUserById(String id) {
		ObjectId objectId = getObjectId(id);
		Optional<User> optionalUser = userRepository.findById(objectId);
		return optionalUser.orElse(null);
	}

	/**
	 * Get user data by name
	 * @param name
	 * @return
	 */
	public User getUserByName(String name) {
		User user = userRepository.findByName(name);
		return user;
	}

	/**
	 * add a book to the borrowed books
	 * @param book
	 * @param user
	 */
	public void addBook(Book book, User user){
		user.getBorrowedBooks().add(book);
		this.updateUser(user);
	}

	/**
	 * return book borrowed by the user
	 * @param book
	 * @param user
	 */
	public void returnBook(Book book, User user){
		user.getBorrowedBooks().removeIf(b -> b.getId().equals(book.getId()));
		this.updateUser(user);
	}

	/**
	 * update user data
	 * @param user
	 * @return
	 */
	public User updateUser(User user) {
		if (!userRepository.existsById(user.getId())) {
			return null;
		}
		return userRepository.save(user);
	}

	/**
	 * get borrowed books by user
	 * @param id
	 * @return
	 */
	public List<Book> borrowedBooksByUser(String id){
		return this.getUserById(id).getBorrowedBooks();
	}

	private ObjectId getObjectId(String id){
		if (!ObjectId.isValid(id)) {
			return null;
		}
		return new ObjectId(id);
	}
}
