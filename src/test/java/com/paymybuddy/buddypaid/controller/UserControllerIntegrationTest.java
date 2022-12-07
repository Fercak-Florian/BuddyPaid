package com.paymybuddy.buddypaid.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.aspectj.lang.annotation.Before;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.WebApplicationContext;

import com.paymybuddy.buddypaid.workclasses.ModifiedUser;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UserControllerIntegrationTest {
	
		@Autowired
		private MockMvc mockMvc;
		
		@Test
		public void testDisplayRootPage() throws Exception {
			mockMvc.perform(get("/")).andDo(print())
			.andExpect(redirectedUrl("/login"))
			.andExpect(status().isFound());
		}
		
		@Test
		@WithMockUser("jboyd@email.com")
		public void testDisplayTransferPage() throws Exception {
			mockMvc.perform(get("/transfer")).andDo(print())
			.andExpect(status().isOk())
			.andExpect(view().name("transfer"))
			.andExpect(content().string(containsString("Eric")));
		}
		
		@Test
		@WithMockUser("jboyd@email.com")
		public void testDisplayAddConnectionPage() throws Exception {
			mockMvc.perform(get("/add_connection")).andDo(print())
			.andExpect(status().isOk())
			.andExpect(view().name("add_connection"))
			.andExpect(content().string(containsString("My Buddies")));
		}
		
		@Test
		@WithMockUser("jboyd@email.com")
		public void testDisplayHomePage() throws Exception {
			mockMvc.perform(get("/home")).andDo(print())
			.andExpect(status().isOk())
			.andExpect(view().name("home"))
			.andExpect(content().string(containsString("Home")));
		}
		
		
		@Test
		@WithMockUser("jboyd@email.com")
		public void testDisplayProfilePage() throws Exception {
			mockMvc.perform(get("/profile"))
			.andDo(print()).andExpect(status().isOk())
			.andExpect(view().name("profile"))
			.andExpect(content().string(containsString("Modify my informations")));
		}
		
		@Test
		@WithMockUser("jboyd@email.com")
		public void testModifyProfile() throws Exception {
			mockMvc.perform(post("/modifyProfile")
			.contentType(MediaType.parseMediaType("application/x-www-form-urlencoded"))
			.param("firstName", "Matt")
			.param("lastName", "Ford")
			.with(csrf()))
			.andDo(MockMvcResultHandlers.print())
			.andDo(print())
			.andExpect(redirectedUrl("/profile"))
			.andExpect(status().isFound());
		}
		
		@Test
		public void testDisplayContactPage() throws Exception {
			mockMvc.perform(get("/contact")).andDo(print())
			.andExpect(status().isOk())
			.andExpect(view().name("contact"))
			.andExpect(content().string(containsString("Enter your demand")));
		}
		
		@Test
		@WithMockUser("jboyd@email.com")
		public void testSubmitDemand()throws Exception {
			mockMvc.perform(post("/submitDemand")
			.contentType(MediaType.parseMediaType("application/x-www-form-urlencoded"))
			.param("message", "Ma demande pour PayMyBuddy")
			.with(csrf()))
			.andDo(MockMvcResultHandlers.print())
			.andDo(print())
			.andExpect(redirectedUrl("/contact"))
			.andExpect(status().isFound());
		}
		
		@Test
		@WithMockUser("jboyd@email.com")
		public void testSubmitDemandWithAnEmptyMessage()throws Exception {
			mockMvc.perform(post("/submitDemand")
			.contentType(MediaType.parseMediaType("application/x-www-form-urlencoded"))
			.param("message", "")
			.with(csrf()))
			.andDo(MockMvcResultHandlers.print())
			.andDo(print())
			.andExpect(redirectedUrl("/contact"))
			.andExpect(status().isFound());
		}
		
		@Test
		public void testDisplayLogOff() throws Exception {
			mockMvc.perform(get("/logoff")).andDo(print())
			.andExpect(status().isOk())
			.andExpect(view().name("logoff"))
			.andExpect(content().string(containsString("Do you really want to log out ?")));
		}
		
		@Test
		@WithMockUser("jboyd@email.com")
		public void testAddBuddyWithTwoEmptyFields() throws Exception {
			mockMvc.perform(post("/addBuddy")
			.contentType(MediaType.parseMediaType("application/x-www-form-urlencoded"))
			.param("buddyEmail", "")
			.param("login", "")
			.with(csrf()))
			.andDo(MockMvcResultHandlers.print())
			.andDo(print())
			.andExpect(status().isFound())
			.andExpect(redirectedUrl("/add_connection"));
		}
		
		@Test
		@WithMockUser("jboyd@email.com")
		public void testAddBuddyWithAnEmptyLogin() throws Exception {
			mockMvc.perform(post("/addBuddy")
			.contentType(MediaType.parseMediaType("application/x-www-form-urlencoded"))
			.param("buddyEmail", "cferguson@email.com")
			.param("login", "")
			.with(csrf()))
			.andDo(MockMvcResultHandlers.print())
			.andDo(print())
			.andExpect(status().isFound())
			.andExpect(redirectedUrl("/add_connection"));
		}
		
		@Test
		@WithMockUser("jboyd@email.com")
		public void testAddBuddyWithAnEmptyBuddyEmail() throws Exception {
			mockMvc.perform(post("/addBuddy")
			.contentType(MediaType.parseMediaType("application/x-www-form-urlencoded"))
			.param("buddyEmail", "")
			.param("login", "cferguson@email.com")
			.with(csrf()))
			.andDo(MockMvcResultHandlers.print())
			.andDo(print())
			.andExpect(status().isFound())
			.andExpect(redirectedUrl("/add_connection"));
		}
		
		@Test
		@WithMockUser("jboyd@email.com")
		public void testAddBuddyWhenBuddyAlreadyBelongsToTheList() throws Exception {
			mockMvc.perform(post("/addBuddy")
			.contentType(MediaType.parseMediaType("application/x-www-form-urlencoded"))
			.param("buddyEmail", "ecadigan@email.com")
			.param("login", "")
			.with(csrf()))
			.andDo(MockMvcResultHandlers.print())
			.andDo(print())
			.andExpect(status().isFound())
			.andExpect(redirectedUrl("/add_connection"));
		}
}
