package com.paymybuddy.buddypaid.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.paymybuddy.buddypaid.model.Operation;

@Repository
public interface IOperationRepository extends CrudRepository<Operation, Integer>{
	
	/*REQUETES SQL NATIVES*/

	@Query(value = "SELECT SUM(amount) FROM operation WHERE user_id = :userId", nativeQuery = true)
	public int sumOfDebit(@Param("userId") Integer userId);
	
	
	@Query(value = "SELECT SUM(amount) FROM operation WHERE buddy_id = :userId", nativeQuery = true)
	public int sumOfCredit(@Param("userId") Integer userId);
}
