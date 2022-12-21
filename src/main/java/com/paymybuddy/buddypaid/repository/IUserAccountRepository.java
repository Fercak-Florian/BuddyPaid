package com.paymybuddy.buddypaid.repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.paymybuddy.buddypaid.model.UserAccount;

@Repository
public interface IUserAccountRepository extends CrudRepository<UserAccount, Integer>{
	
	public Iterable<UserAccount> findByUserId(int id);
	
	@Query(value = "SELECT SUM(amount) FROM user_account WHERE user_id = :userId", nativeQuery = true)
	public double sumOfAmount(@Param("userId") Integer userId);
}
