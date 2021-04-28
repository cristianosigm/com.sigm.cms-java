package com.cs.sigm.mapper;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.cs.sigm.adapter.domain.ContentLogDTO;
import com.cs.sigm.domain.ContentLog;
import com.cs.sigm.domain.fixed.Operation;
import com.cs.sigm.service.UserService;

@Service
public class ContentLogMapper {
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private UserService userService;
	
	public ContentLogDTO mapSingle(ContentLog response) {
		// @formatter:off
		return ContentLogDTO.builder()
			.id(response.getId())
			.dateOperation(response.getDateOperation())
			.notes(response.getNotes())
			// TODO: read selected or default locale
			.operation(messageSource.getMessage("enum.operation.".concat(Operation.getKeyById(response.getId())), null, Locale.getDefault()))
			.operator(userService.getNameById(response.getIdOperator()))
			.idContent(response.getIdContent())
			.build();
		// @formatter:on
	}
	
	public List<ContentLogDTO> mapResponse(List<ContentLog> lst) {
		if (lst == null) {
			return null;
		}
		return lst.stream().map(x -> mapSingle(x)).collect(Collectors.toList());
	}
	
}
