package com.paymybuddy.buddypaid.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import javax.transaction.Transactional;

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
		.with(csrf()))
		.andDo(MockMvcResultHandlers.print())
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(view().name("description"))
		.andExpect(content().string(containsString("Transaction summary")));
	}
	
	@Test
	@WithMockUser("jboyd@email.com")
	public void testConfirmOperationSuccessed() throws Exception{
		/*ARRANGE*/
		testAddOperation();
		/*ACT AND ASSERT*/
		mockMvc.perform(post("/confirmOperation")
		.contentType(MediaType.parseMediaType("application/x-www-form-urlencoded"))
		.param("description", "Bowling")
		.with(csrf()))
		.andDo(print())
		.andExpect(redirectedUrl("/transfer_result"))
		.andExpect(status().isFound());
	}
	
	@Test
	@WithMockUser("jboyd@email.com")
	public void testConfirmOperationFailed() throws Exception{
		/*ARRANGE*/
		mockMvc.perform(post("/addOperation")
				.contentType(MediaType.parseMediaType("application/x-www-form-urlencoded"))
				.param("buddyId", "3")
				.param("amount", "20")
				.with(csrf()));
		/*ACT AND ASSERT*/
		mockMvc.perform(post("/confirmOperation")
		.contentType(MediaType.parseMediaType("application/x-www-form-urlencoded"))
		.param("description", "Bowling")
		.with(csrf()))
		.andDo(print())
		.andExpect(redirectedUrl("/transfer_result"))
		.andExpect(status().isFound());
	}
	
	@Test
	@WithMockUser("jboyd@email.com")
	public void testDisplayTransferResult() throws Exception {
		mockMvc.perform(get("/transfer_result"))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(view().name("transfer_result"))
		.andExpect(content().string(containsString("Transfer state")));;
	}
}