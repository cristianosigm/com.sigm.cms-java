package com.cs.sigm.mapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cs.sigm.adapter.domain.RoleDTO;
import com.cs.sigm.domain.fixed.Role;

@Service
public class RoleMapper {

	public Role mapById(Long id) {
		return map(RoleDTO.builder().id(id).build());
	}


	public Role map(RoleDTO request) {
		return Arrays.asList(Role.values()).stream().filter(r -> r.getId() == request.getId()).findFirst().orElse(null);
	}

	public RoleDTO map(Role response) {
		return RoleDTO.builder().id(response.getId()).title(response.getKey()).build();
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
