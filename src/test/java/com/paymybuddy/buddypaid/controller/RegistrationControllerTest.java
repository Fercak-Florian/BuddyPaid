package com.paymybuddy.buddypaid.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class RegistrationControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void testShowRegistrationForm() throws Exception {
			mockMvc.perform(get("/registration")).andDo(print())
			.andExpect(status().isOk())
			.andExpect(view().name("registration"))
			.andExpect(content().string(containsString("Please register")));
	}
	
	@Test
	public void testRegisterUserAccount() throws Exception {
			mockMvc.perform(post("/registration").contentType(MediaType.parseMediaType("application/x-www-form-urlencoded"))
					.param("login", "mford@email.com")
					.param("password", "mford")
					.param("firstName", "Matt")
					.param("lastName", "Ford")
					.with(csrf())
					)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(view().name("login"));
	}
}
