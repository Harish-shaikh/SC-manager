package com.SmartContect.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.SmartContect.dao.UserRepos;
import com.SmartContect.entities.UserEntity;

@Component
public class CustumUserDetailService implements UserDetailsService {
	@Autowired
	private UserRepos userRepos;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity userEntity= userRepos.userdetails(username);
		if(userEntity==null) {
			
		}
	 CustumUserDetail  custumUserDetail= new CustumUserDetail(userEntity);
		return custumUserDetail ;
	    }
	}


