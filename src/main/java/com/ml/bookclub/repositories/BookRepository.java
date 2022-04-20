package com.ml.bookclub.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.ml.bookclub.models.Book;




public interface BookRepository extends CrudRepository<Book, Long> {

	
	List<Book>findAll();
	
	Optional<Book> findById(Long id);
	
	void deleteById(Long id);
	
	//Book save(Book book); for create and update
}
