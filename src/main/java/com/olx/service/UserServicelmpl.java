package com.olx.service;

import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.olx.dto.AuthenticationRequest;
import com.olx.dto.RegisterUser;
import com.olx.entity.UserEntity;
import com.olx.repo.UserRepo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;

@Service
public class UserServicelmpl implements UserService {
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		List<UserEntity> userEntityList = userRepo.findByUserName(username);
		if (userEntityList.size() == 0) {
			throw new UsernameNotFoundException(username);
		}

		UserEntity userEntity = userEntityList.get(0);
		List<GrantedAuthority> autorities = new ArrayList<GrantedAuthority>();
		autorities.add(new SimpleGrantedAuthority(userEntity.getRoles()));

		User user = new User(userEntity.getUserName(), passwordEncoder.encode(userEntity.getPassword()), autorities);

		return user;
	}

	private UserEntity getRegisterUserEntityFromDTO(AuthenticationRequest registerUser) {
		UserEntity registerUserEntity = this.modelMapper.map(registerUser, UserEntity.class);
		return registerUserEntity;
	}

	private AuthenticationRequest getRegisterUserDTOFromEntity(UserEntity registerUserEntity) {

		AuthenticationRequest RegisterUserDTO = this.modelMapper.map(registerUserEntity, AuthenticationRequest.class);
		return RegisterUserDTO;
	}

	private List<AuthenticationRequest> getRegisterUserDTOListFromEntityList(List<UserEntity> registerUserEntityList) {
		List<AuthenticationRequest> registerUserDTOList = new ArrayList<AuthenticationRequest>();
		for (UserEntity registerUserEntity : registerUserEntityList) {
			registerUserDTOList.add(getRegisterUserDTOFromEntity(registerUserEntity));
		}
		return registerUserDTOList;
	}

	@Override
	public AuthenticationRequest createNewUserRegister(AuthenticationRequest registerUser) {
		UserEntity registerUserEntity = getRegisterUserEntityFromDTO(registerUser);
		registerUserEntity = userRepo.save(registerUserEntity);
		return getRegisterUserDTOFromEntity(registerUserEntity);
	}

	@Override
	public List<AuthenticationRequest> getAllRegisteredUsers() {
		List<UserEntity> registerEntityList = userRepo.findAll();
		return getRegisterUserDTOListFromEntityList(registerEntityList);
	}

	@Override
	public boolean deleteAllUsers() {
		userRepo.deleteAll();
		return true;
	}

	@Override
	public boolean deleteUserById(int userId) {
		userRepo.deleteById(userId);
		return true;
	}

}
