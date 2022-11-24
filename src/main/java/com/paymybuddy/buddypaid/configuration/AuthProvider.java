package com.paymybuddy.buddypaid.configuration;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.paymybuddy.buddypaid.service.MyUserDetailsService;

@Component /* ------------- AUTHENTICATION PROVIDER ------------- */
public class AuthProvider implements AuthenticationProvider {
	
	@Autowired
	private MyUserDetailsService userDetailsService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		/*COMPARAISON ENTRE LES INFOS SAISIES PAR LE USER (AUTHENTICATION)*/
		/*ET*/
		/*LES INFORMATIONS TROUVEES DANS LA BASE DE DONNEES*/
		String userName = authentication.getName();
		String password = authentication.getCredentials().toString();
		System.out.println(userName);
		System.out.println(password);
		userDetailsService.loadUserByUsername(userName);
		return new UsernamePasswordAuthenticationToken(userName, password, new ArrayList<>());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return true; /*DEBUT DE FONCTIONNEMENT AVEC TRUE*/
	}

}
