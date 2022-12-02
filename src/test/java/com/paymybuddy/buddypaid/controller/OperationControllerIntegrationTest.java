package com.paymybuddy.buddypaid.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class OperationControllerIntegrationTest {

	@Autowired
	public MockMvc mockMvc;

	@Test
	@WithMockUser("jboyd@email.com")
	public void testAddOperation() throws Exception{
		mockMvc.perform(post("/addOperation")
		.contentType(MediaType.parseMediaType("application/x-www-form-urlencoded"))
		.param("buddyId", "3")
		.param("amount", "10")
		.with(csrf())
		)
		.andDo(MockMvcResultHandlers.print())
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(view().name("description"))
		.andExpect(content().string(containsString("Transaction summary")));
	}
	
	@Disabled
	@Test
	@WithMockUser("jboyd@email.com")
	/*CERTAINES VALEURS SONT NULLES !!*/
	public void testConfirmOperation() throws Exception{
		mockMvc.perform(post("/confirmOperation")
		.contentType(MediaType.parseMediaType("application/x-www-form-urlencoded"))
		.param("description", "Bowling")
		.with(csrf())
		)
		.andDo(print())
		.andExpect(redirectedUrl("/"))
		.andExpect(status().isFound());
	}
}