package com.mohammad.library.service;

import org.springframework.stereotype.Service;

@Service
public interface LibraryService {

	public void returnBook(String userId, String bookId);

	public void borrowBook(String userId, String bookId);
}
