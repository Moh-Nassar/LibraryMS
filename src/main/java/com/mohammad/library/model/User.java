package com.mohammad.library.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("users")
public class User {
	@Id
	ObjectId id;
	String name;
	String faculty;
	Date dateOfBirth;
	@DocumentReference
	List<Book> borrowedBooks;
	String type;
}
