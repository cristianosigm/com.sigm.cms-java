package com.cs.sigm.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cs.sigm.adapter.domain.RoleDTO;
import com.cs.sigm.domain.fixed.Role;

@RestController
@RequestMapping(value = "/roles")
public class RoleController {

	@GetMapping
	public List<RoleDTO> findAll() {
		return Arrays.asList(Role.values()).stream().map(c -> RoleDTO.builder().id(c.getId()).title(c.getKey()).build())
				.collect(Collectors.toList());
	}

	@GetMapping("/{id}")
	public RoleDTO findSingle(@PathVariable Long id) {
		final Role result = Arrays.asList(Role.values()).stream().filter(r -> r.getId() == id).findFirst().orElse(null);
		return (RoleDTO.builder().id(result.getId()).title(result.getKey()).build());
	}

}
