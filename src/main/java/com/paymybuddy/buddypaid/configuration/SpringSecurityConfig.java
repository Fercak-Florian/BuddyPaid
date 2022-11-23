//package com.paymybuddy.buddypaid.configuration;
//
//import javax.activation.DataSource;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//@Configuration
//@EnableWebSecurity
//public class SpringSecurityConfig extends /*WebSecurityConfigurerAdapter{
//	
//	private DataSourceConfig dataSourceConfig;
//	
//	public SpringSecurityConfig(DataSourceConfig dataSourceConfig) {
//		this.dataSourceConfig = dataSourceConfig;
//	}
//	
//	@Override
//	public void configure(HttpSecurity http) throws Exception {
//		http
//		.authorizeRequests()
//		.antMatchers("/user").hasRole("USER")
//		.anyRequest().authenticated()
//		.and()
//		.formLogin();
//	}
//	
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//	     /* auth.jdbcAuthentication()
//	      /*.dataSource(dataSourceConfig.getDataSource())*/
//	      /*.usersByUsernameQuery("SELECT login, password, true FROM user WHERE login = ?")*/
//	      /*.authoritiesByUsernameQuery("SELECT email,authority FROM authorities WHERE email = ?");*/
//	      /*System.out.println(dataSourceConfig.getDataSource().getClass());*/
//	      /*THE FOLLOWING AUTHENTICATION IS WORKING*/
//	      /*.withUser("flo").password(passwordEncoder().encode("123")).roles("USER");*/
//	      
//	}
//	
//	@Bean
//	public PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
//}
