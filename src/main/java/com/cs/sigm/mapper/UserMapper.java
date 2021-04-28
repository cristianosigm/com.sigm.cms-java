package com.cs.sigm.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cs.sigm.adapter.domain.UserDTO;
import com.cs.sigm.domain.User;
import com.cs.sigm.domain.fixed.Role;

@Service
public class UserMapper {
	
	public User map(UserDTO request) {
		// @formatter:off
		return User.builder()
			.id(request.getId())
			.idRole(request.getIdRole())
			.displayName(request.getDisplayName())
			.email(request.getEmail())
			.name(request.getName())
			.username((request.getUsername() == null || request.getUsername().isBlank()) ? request.getEmail() : request.getUsername())
			.password(request.getPassword())
			.changePassword(request.getChangePassword() == null ? Boolean.FALSE : request.getChangePassword())
			.currentPassword(request.getCurrentPassword())
			.passwordConfirm(request.getPasswordConfirm())
			.build();
		// @formatter:on
	}
	
	public UserDTO map(User response) {
		// @formatter:off
		return UserDTO.builder()
			.id(response.getId())
			.idRole(response.getIdRole())
			.roleName(Role.getKeyById(response.getIdRole()))
//			.approved(response.getApproved())
			.blocked(response.getBlocked())
			.displayName(response.getDisplayName())
			.email(response.getEmail())
			.name(response.getName())
			.validated(response.getValidated())
			.username(response.getUsername())
			.build();
		// @formatter:on
	}
	
	public List<User> mapRequest(List<UserDTO> lst) {
		List<User> response = new ArrayList<>();
		for (UserDTO cur : lst) {
			response.add(map(cur));
		}
		return response;
	}
	
	public List<UserDTO> mapResponse(List<User> lst) {
		List<UserDTO> response = new ArrayList<>();
		for (User cur : lst) {
			response.add(map(cur));
		}
		return response;
	}
	
}
