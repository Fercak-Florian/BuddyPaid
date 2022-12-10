package com.paymybuddy.buddypaid.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.paymybuddy.buddypaid.model.User;
import com.paymybuddy.buddypaid.model.UserBuddy;

@Repository
public interface IUserRepository extends CrudRepository<User, Integer>{
	UserBuddy save(UserBuddy userBuddy);
	Optional<User> findByLogin(String login);
}
