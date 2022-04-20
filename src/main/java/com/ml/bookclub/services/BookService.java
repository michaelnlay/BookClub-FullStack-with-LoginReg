package com.ml.bookclub.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ml.bookclub.models.Book;
import com.ml.bookclub.repositories.BookRepository;

@Service
public class BookService {
	
	//using dependency injection rather than @Autowire
	private final BookRepository bookRepo;
	//construct
	public BookService(BookRepository bookRepo) {
		this.bookRepo=bookRepo;
	}


	//Create Book
	public Book createBook(Book book) {
		return bookRepo.save(book);
	}
	
	//Update Book
		public Book updateBook(Book book) {
			return bookRepo.save(book);
		}

	//Show all Book
	public List<Book> getAllBooks(){
		return bookRepo.findAll();
	}
	
	//Show or find one BOok ---this is the way to do it!
	public Book findOneBook(Long id) {
		Optional<Book>optionalBook = bookRepo.findById(id);
		if(optionalBook.isPresent()) {
			return optionalBook.get();
		}else {
			return null;
		}
	}

	//Delete One
	public void deleteBook(Long id) {
		bookRepo.deleteById(id);
	}
}