package com.SmartContect.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity

public class Configs {
	    @Bean
	    public UserDetailsService getuserDetailsService() {
	        return new CustumUserDetailService();
	    }

	    @Bean
	    public BCryptPasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }

	    @Bean
	    public DaoAuthenticationProvider authenticationProvider() {
	        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
	        daoAuthenticationProvider.setUserDetailsService(this.getuserDetailsService());
	        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
	        return daoAuthenticationProvider;
	    } 
	    @Bean
	    public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
	      http.csrf().disable().authorizeHttpRequests()
	      .requestMatchers("/user**")
	      .authenticated()
	      .anyRequest()
	      .permitAll()
	      .and()
	      .formLogin().loginPage("/login").defaultSuccessUrl("/user/index");
	      http.authenticationProvider(authenticationProvider());
	        return http.build();
	    }
	}


