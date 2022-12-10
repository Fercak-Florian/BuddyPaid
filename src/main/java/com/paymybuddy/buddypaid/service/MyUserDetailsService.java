package com.paymybuddy.buddypaid.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.paymybuddy.buddypaid.model.User;
import com.paymybuddy.buddypaid.repository.IUserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {

	private IUserRepository userRepository;

	public MyUserDetailsService(IUserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) {
		Optional<User> optUser = userRepository.findByLogin(username);
		User user = optUser.get();
		if (user == null) {
			throw new UsernameNotFoundException(username);
		}
		boolean modeUserDetails = true;
		if(modeUserDetails) {
			/*BESOIN DE DECLARER LES AUTHORITHIES*/
			UserDetails userDetails = org.springframework.security.core.userdetails.User.withUsername(user.getLogin()).password(user.getPassword()).authorities("USER").build();
			return userDetails;
		} else {
			return new MyUserPrincipal(user);
		}
	}
}