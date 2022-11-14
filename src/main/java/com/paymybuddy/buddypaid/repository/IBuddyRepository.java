package com.paymybuddy.buddypaid.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.paymybuddy.buddypaid.model.Buddy;

@Repository
public interface IBuddyRepository extends CrudRepository<Buddy, Integer> {
	
	/*DERIVED QUERY*/
	public Iterable<Buddy> findByUserId(int user_id);
} 
