package com.mohammad.library.repository;

import com.mohammad.library.model.Book;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends MongoRepository<Book, ObjectId> {
	@Query("{quantity: {$gt: 0}}")
	List<Book> availableBooks();
}
