package com.mohammad.library.repository;

import com.mohammad.library.model.Book;
import com.mohammad.library.model.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User, ObjectId> {

	@Query("{name: ?0}")
	User findByName(String name);

	@Query(value = "{_id: ?0}", fields = "borrowedBooks")
	List<Book> getBorrowedBooks(ObjectId objectId);
}
