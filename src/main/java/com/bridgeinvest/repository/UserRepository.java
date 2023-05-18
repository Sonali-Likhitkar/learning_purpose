package com.bridgeinvest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bridgeinvest.entity.User;

public interface UserRepository extends JpaRepository<User, Long>  {
	
	    User findByUsername(String username);
	}


