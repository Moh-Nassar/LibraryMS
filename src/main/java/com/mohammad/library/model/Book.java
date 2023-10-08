package com.mohammad.library.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document("books")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {

	@Id
	ObjectId id;
	String title;
	String Author;
	Date publishedDate;
	Integer quantity;
	String genre;
	String type;
	float price;
}
