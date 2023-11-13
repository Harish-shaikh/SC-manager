package com.SmartContect.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.SmartContect.entities.UserEntity;

public interface UserRepos extends JpaRepository<UserEntity,Integer>{

 @Query("select u from UserEntity u where u.email = :email")
 public UserEntity userdetails(@Param("email") String email);
}
