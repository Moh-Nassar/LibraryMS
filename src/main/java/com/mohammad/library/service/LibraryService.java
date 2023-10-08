package com.mohammad.library.service;

import org.springframework.stereotype.Service;

@Service
public interface LibraryService {

	void returnBook(String userId, String bookId);

	void borrowBook(String userId, String bookId);
}
