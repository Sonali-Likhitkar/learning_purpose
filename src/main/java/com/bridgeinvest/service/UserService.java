package com.bridgeinvest.service;

import com.bridgeinvest.dto.LoginRequest;
import com.bridgeinvest.entity.User;

import java.io.ByteArrayInputStream;
import java.util.List;

public interface UserService {

	    boolean login(LoginRequest loginRequest);

	public void convertToCsv(List<User> users, String fileName);

	void convertToPdf(List<User> users, String fileName);

	 public void convertToWord(List<User> users, String fileName);

}


