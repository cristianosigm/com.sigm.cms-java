package com.cs.sigm.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cs.sigm.adapter.domain.UserDTO;
import com.cs.sigm.domain.User;

@Service
public class UserMapper {

	public User map(UserDTO request) {
		// @formatter:off
		return User.builder()
			.id(request.getId())
			.approved(request.getApproved())
			.blocked(request.getBlocked())
			/*
			.dateApproval(request.getDateApproval())
			.dateBlocked(request.getDateBlocked())
			.dateLastLogin(request.getDateLastLogin())
			.dateValidation(request.getDateValidation())
			*/
			.displayName(request.getDisplayName())
			.email(request.getEmail())
			.failedAttempts(request.getFailedAttempts())
			.idRole(request.getIdRole())
			.name(request.getName())
			.password(request.getPassword())
			.username(request.getUsername())
			.validated(request.getValidated())
			.build();
		// @formatter:on
	}

	public UserDTO map(User response) {
		// @formatter:off
		return UserDTO.builder()
			.id(response.getId())
			.approved(response.getApproved())
			.blocked(response.getBlocked())
			/*
			.dateApproval(response.getDateApproval())
			.dateBlocked(response.getDateBlocked())
			.dateLastLogin(response.getDateLastLogin())
			.dateValidation(response.getDateValidation())
			*/
			.displayName(response.getDisplayName())
			.email(response.getEmail())
			.failedAttempts(response.getFailedAttempts())
			.idRole(response.getIdRole())
			.name(response.getName())
			.password(response.getPassword())
			.username(response.getUsername())
			.validated(response.getValidated())
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
