package com.paymybuddy.buddypaid.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {
	
		@Autowired
		public MockMvc mockMvc;

		@Disabled
		@Test
		public void testDisplayTransferPage() throws Exception {
			mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk());
		}
		
		/*mockMvc.perform(get("/"))*/
		/*.andDo(print())*/
		/*.andExpect(status().isOk())*/
		/*.andExpect(view().name("transfer"))*/
		/*.andExpect(content().string(containsString("Eric")));*/
}
