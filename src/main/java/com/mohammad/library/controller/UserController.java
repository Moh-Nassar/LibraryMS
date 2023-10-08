package com.mohammad.library.controller;

import com.mohammad.library.model.Book;
import com.mohammad.library.model.CustomException;
import com.mohammad.library.model.User;
import com.mohammad.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("user")
public class UserController {

	@Autowired
	UserService userService;

	/**
	 * Get user byId
	 * @param userId
	 * @return
	 */
	@GetMapping("/get/byId")
	public ResponseEntity<Map<String, User>> goGetUser(
			@RequestParam String userId
	) throws CustomException {
		Map<String, User> response = new HashMap<>();

		User user = userService.getUserById(userId);
		if (user == null) {
			return new ResponseEntity<>(new HashMap<>(), HttpStatus.NOT_FOUND);
		}
		response.put("User", user);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * Get user by name
	 * @param name
	 * @return
	 */
	@GetMapping("/get/byName")
	public ResponseEntity<Map<String, User>> doGetUserByName(
			@RequestParam String name
	) throws CustomException {
		Map<String, User> response = new HashMap<>();

		User user = userService.getUserByName(name);
		if (user == null) {
			return new ResponseEntity<>(new HashMap<>(), HttpStatus.NOT_FOUND);
		}
		response.put("User", user);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/get/borrowedBooks")
	public ResponseEntity<Map<String, List<Book>>> doGetBorrowedBooks(
			@RequestParam(name = "userId") String id
	){
		Map<String, List<Book>> response = new HashMap<>();

		List<Book> books = userService.borrowedBooksByUser(id);
		response.put("Books", books);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
