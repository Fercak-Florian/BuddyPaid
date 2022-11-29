package com.paymybuddy.buddypaid.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UserControllerIntegrationTest {
	
		@Autowired
		private MockMvc mockMvc;
		
		@Autowired
		private WebApplicationContext context;
		
		@Mock
		private Authentication authentication;
		
		/*----------------------------------------------------------------------------------------*/
		
		/*@Before
		private void mockAuthentication() {
			
		    when(authentication.getPrincipal()).thenReturn("jboyd@email.com");

		    SecurityContext securityContext = mock(SecurityContext.class);
		    when(securityContext.getAuthentication()).thenReturn(authentication);
		    SecurityContextHolder.setContext(securityContext);
		}*/
		
		/*----------------------------------------------------------------------------------------*/
		
		@Before(value = "authentication")
		public void setUp() {
			User user = new User("jboyd@email.com", "jboyd", null);
			Authentication authentication = new UsernamePasswordAuthenticationToken(user,null);
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		
		
		@BeforeEach
		public void init() {
		   mockMvc = MockMvcBuilders
		   .webAppContextSetup(context)
		   .apply(springSecurity())
		   .build();
		}

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
		
		/*@Test
		public void testDisplayProfilePage() throws Exception {
			mockMvc.perform(get("/profile.html")).andDo(print())
			.andExpect(status().isOk())
			.andExpect(view().name("profile"))
			.andExpect(content().string(containsString("John")));
		}*/
		
		@Test
		public void testDisplayProfilePage() throws Exception {
			mockMvc.perform(get("/transfer.html"))
			.andDo(print()).andExpect(status().isOk())
			.andExpect(view().name("transfer"))
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
