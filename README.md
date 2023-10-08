# LibraryMS
Library Management System for college's staff and students

# Running the application
Run just like a normal application. No need to setup a database beforehand. Database used in this project is MongoDB and it is hosted already in MongoDB Atlas.

# Swagger Documentation
After running the project enter the link below
localhost:8080/swagger-ui/index.html

# Testing endpoints: 
## Create new Book:
Hit the POST request library/book/save from Postman, with body Raw of type json/application. In the JSON, insert the new book as a JSON object as follows:
{
  "title": "Harry Potter",
  "price": 16,
  "quantity" : 5
}

use the endpoint library/book/all to confirm it was saved.

## Borrow a Book:
Hit the POST request library/borrow?bookId=&userId= with request params userId= and bookId= to borrow a book.
example userId = 65227208098953dd13c1d6d5
example bookId = 652290f7c9ae066e77f7dc34

to confirm the book was borrowed, hit the endpoint /user/get/borrowedBooks?userId with requestparam userId= . This endpoint will show all the borrowed books by this user.

## Borrow a Book:
Hit the POST request library/return?bookId=&userId= with request params userId= and bookId= to return a book.
example userId = 65227208098953dd13c1d6d5
example bookId = 652290f7c9ae066e77f7dc34

## NOTE: ids are 24 characters hexa string.
