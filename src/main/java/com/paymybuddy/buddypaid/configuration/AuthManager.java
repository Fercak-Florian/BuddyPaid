package com.paymybuddy.buddypaid.configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component /* ------------- AUTHENTICATION MANAGER ------------- */
public class AuthManager implements AuthenticationManager{
	
	private AuthProvider authenticationProvider;
	
	public AuthManager(AuthProvider authenticationProvider) {
		this.authenticationProvider = authenticationProvider;
	}

	@Override 
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		return authenticationProvider.authenticate(authentication);
	}

}
