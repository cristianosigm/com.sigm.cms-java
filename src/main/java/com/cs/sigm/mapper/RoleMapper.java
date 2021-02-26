package com.cs.sigm.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cs.sigm.adapter.domain.RoleDTO;
import com.cs.sigm.domain.Role;

@Service
public class RoleMapper {

	public Role map(RoleDTO request) {
		// @formatter:off
		return Role.builder()
			.id(request.getId())
			.description(request.getDescription())
			.title(request.getTitle())
			.build();
		// @formatter:on
	}

	public RoleDTO map(Role response) {
		// @formatter:off
		return RoleDTO.builder()
			.id(response.getId())
			.description(response.getDescription())
			.title(response.getTitle())
			.build();
		// @formatter:on
	}

	public List<Role> mapRequest(List<RoleDTO> lst) {
		List<Role> response = new ArrayList<>();
		for (RoleDTO cur : lst) {
			response.add(map(cur));
		}
		return response;
	}

	public List<RoleDTO> mapResponse(List<Role> lst) {
		List<RoleDTO> response = new ArrayList<>();
		for (Role cur : lst) {
			response.add(map(cur));
		}
		return response;
	}

}
