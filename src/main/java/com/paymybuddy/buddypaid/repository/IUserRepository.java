package com.paymybuddy.buddypaid.repository;

import java.util.Optional;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.paymybuddy.buddypaid.model.Operation;
import com.paymybuddy.buddypaid.model.User;
import com.paymybuddy.buddypaid.model.UserBuddy;

@Repository
public interface IUserRepository extends CrudRepository<User, Integer>{

	Optional<User> findByFirstNameAndLastName(String firstName, String lastName);
	UserBuddy save(UserBuddy userBuddy);
	Optional<User> findByLogin(String login);
}
