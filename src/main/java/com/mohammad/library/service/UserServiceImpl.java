package com.mohammad.library.service;

import com.mohammad.library.model.Book;
import com.mohammad.library.model.User;
import com.mohammad.library.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	public User getUserById(String id) {
		if (!ObjectId.isValid(id)) {
			return null;
		}
		ObjectId objectId = new ObjectId(id);
		Optional<User> optionalUser = userRepository.findById(objectId);
		return optionalUser.orElse(null);
	}

	public void addBook(Book book, User user){
		user.getBorrowedBooks().add(book);
		this.updateUser(user);
	}

	public void returnBook(Book book, User user){
		user.getBorrowedBooks().removeIf(b -> b.getId().equals(book.getId()));
		this.updateUser(user);
	}

	public User updateUser(User user) {
		if (!userRepository.existsById(user.getId())) {
			return null;
		}
		return userRepository.save(user);
	}
}
