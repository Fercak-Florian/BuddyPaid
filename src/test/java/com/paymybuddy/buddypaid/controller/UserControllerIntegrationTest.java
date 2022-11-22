package com.paymybuddy.buddypaid.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UserControllerIntegrationTest {
	
		@Autowired
		public MockMvc mockMvc;

		@Test
		public void testDisplayTransferPage() throws Exception {
			mockMvc.perform(get("/")).andDo(print())
			.andExpect(status().isOk())
			.andExpect(view().name("transfer"))
			.andExpect(content().string(containsString("Eric")));
		}
		
		@Test
		public void testDisplayAddConnectionPage() throws Exception {
			mockMvc.perform(get("/add_connection.html")).andDo(print())
			.andExpect(status().isOk())
			.andExpect(view().name("add_connection"))
			.andExpect(content().string(containsString("Tessa")));
		}
		
		@Test
		public void testDisplayHomePage() throws Exception {
			mockMvc.perform(get("/home.html")).andDo(print())
			.andExpect(status().isOk())
			.andExpect(view().name("home"));
		}
		
		@Disabled
		@Test
		public void testDisplayTranferPage() throws Exception {
			mockMvc.perform(get("/transfer.html")).andDo(print())
			.andExpect(status().isOk())
			.andExpect(view().name("transfer"));
		}
		
		@Test
		public void testDisplayProfilePage() throws Exception {
			mockMvc.perform(get("/profile.html")).andDo(print())
			.andExpect(status().isOk())
			.andExpect(view().name("profile"))
			.andExpect(content().string(containsString("John")));
		}
		
		@Disabled
		@Test
		public void testmodifyProfile() throws Exception {
			mockMvc.perform(post("/modifyProfile")).andDo(print())
			.andExpect(status().isOk())
			.andExpect(view().name("profile.html"));
		}
		
		@Test
		public void testDisplayContactPage() throws Exception {
			mockMvc.perform(get("/contact.html")).andDo(print())
			.andExpect(status().isOk())
			.andExpect(view().name("contact"))
			.andExpect(content().string(containsString("Enter your demand")));
		}
		
		@Disabled
		@Test
		public void testSubmitDemand() throws Exception {
			mockMvc.perform(post("/submitDemand")).andDo(print())
			.andExpect(status().isOk())
			.andExpect(view().name("contact.html"));
		}
		
		@Disabled
		@Test
		public void testAddBuddy() throws Exception {
			mockMvc.perform(post("/addBuddy")).andDo(print())
			.andExpect(status().isOk())
			.andExpect(view().name("/add_connection.html"));
		}
}
