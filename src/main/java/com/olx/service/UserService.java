package com.olx.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.olx.dto.AuthenticationRequest;
import com.olx.dto.RegisterUser;

public interface UserService extends UserDetailsService {

	public AuthenticationRequest createNewUserRegister(AuthenticationRequest registerUser);

	public List<AuthenticationRequest> getAllRegisteredUsers();

	public boolean deleteAllUsers();

	public boolean deleteUserById(int userId);
}
