package com.ml.bookclub.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ml.bookclub.models.User;

//.. imports .. //

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
 
	List<User>findAll();
	
	Optional<User> findByEmail(String email); //only have this optional findByEmail is part of our login and register process b/c we are verifying whether or not email exisit in our DB
	
	Optional<User> findById(Long id); //use to find one User based on id
	//the findById is look into the DB, if found, pass forward to Services and Controller 
}

