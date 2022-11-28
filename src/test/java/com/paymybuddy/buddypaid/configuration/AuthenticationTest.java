package com.paymybuddy.buddypaid.configuration;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext context;
	
	@BeforeEach
	public void init() {
	   mockMvc = MockMvcBuilders
	   .webAppContextSetup(context)
	   .apply(springSecurity())
	   .build();
	}
	
	/*VERIFIER QUE LA PAGE LOGIN EST BIEN ACCESSIBLE*/
	@Test
	public void shouldReturnDefaultMessage() throws Exception{
		mockMvc.perform(get("/login")).andDo(print()).andExpect(status().isOk());
	}
	
	/*VERIFIER QUE L'AUTHENTIFICATION SE FAIT BIEN AVEC DES IDENTIFIANTS CORRECTS*/
	@Test
	public void userLoginTest() throws Exception{
		mockMvc.perform(formLogin("/login").user("jboyd@email.com").password("jboyd")).andExpect(authenticated());
	}
	
	/*VERIFIER QUE L'AUTHENTIFICATION ECHOUE AVEC DES IDENTIFIANTS ERRONES*/
	@Test
	public void userLoginFail() throws Exception{
		mockMvc.perform(formLogin("/login").user("a").password("b")).andExpect(unauthenticated());
	}
}
