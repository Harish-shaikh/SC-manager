package com.SmartContect.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.SmartContect.entities.ContectEntity;

public interface ContactRepos extends JpaRepository<ContectEntity, Integer> {
	//pagination...
	
	
	@Query("from ContectEntity as c where c.userEntity.uId=:userId")
	public Page<ContectEntity> findContactByUser(@Param("userId") int userId,Pageable pepageable);
	

}
