package com.paymybuddy.buddypaid.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void testLoginPageShouldBeDisplayed() throws Exception {
		mockMvc.perform(get("/login")).andDo(print())
		.andExpect(status().isOk())
		.andExpect(view().name("login"))
		.andExpect(content().string(containsString("Remember me")));
	}
	
	@Test
	public void testUserLoginWithSuccess() throws Exception {
	   mockMvc.perform(formLogin("/login")
			   .user("jboyd@email.com")
			   .password("jboyd"))
	   		   .andExpect(authenticated());
	}
	
	@Test
	public void testUserLoginFail() throws Exception {
	   mockMvc.perform(formLogin("/login")
			   .user("unknownuser@email.com")
			   .password("unknown"))
	   		   .andExpect(unauthenticated());
	}
}