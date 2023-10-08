package com.mohammad.library.service;

import com.mohammad.library.model.CustomException;
import org.springframework.stereotype.Service;

@Service
public interface LibraryService {

	void returnBook(String userId, String bookId) throws CustomException;

	void borrowBook(String userId, String bookId) throws CustomException;
}
