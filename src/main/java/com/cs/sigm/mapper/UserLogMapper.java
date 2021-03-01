package com.cs.sigm.mapper;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.cs.sigm.adapter.domain.UserLogDTO;
import com.cs.sigm.domain.UserLog;
import com.cs.sigm.domain.fixed.Operation;
import com.cs.sigm.service.UserService;

@Service
public class UserLogMapper {

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private UserService userService;

	public UserLogDTO mapSingle(UserLog response) {
		// @formatter:off
		return UserLogDTO.builder()
			.id(response.getId())
			.dateOperation(response.getDateOperation())
			.notes(response.getNotes())
			// TODO: read selected or default locale
			.operation(messageSource.getMessage("enum.operation.".concat(Operation.getKeyById(response.getId())), null, Locale.US))
			.operator(userService.getNameById(response.getIdOperator()))
			.user(userService.getNameById(response.getIdUser()))
			.build();
		// @formatter:on
	}

	public List<UserLogDTO> mapResponse(List<UserLog> lst) {
		if (lst == null) {
			return null;
		}
		return lst.stream().map(x -> mapSingle(x)).collect(Collectors.toList());
	}

}
