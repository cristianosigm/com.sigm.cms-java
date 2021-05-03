package com.cs.sigm.mapper;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.cs.sigm.adapter.domain.OperationLogDTO;
import com.cs.sigm.domain.OperationLog;
import com.cs.sigm.domain.fixed.Entity;
import com.cs.sigm.domain.fixed.Operation;
import com.cs.sigm.service.UserService;

@Service
public class OperationLogMapper {

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private UserService userService;

	public OperationLogDTO mapSingle(OperationLog response) {

		final StringBuilder bfr = new StringBuilder(1024);

		// @formatter:off
		bfr
		// entity...
		.append(messageSource.getMessage("enum.entity.".concat(
				Entity.getKeyByCode(response.getCodeEntity())),
				null,
				Locale.getDefault())
		).append(" ")
		// ...action...
		.append(messageSource.getMessage("enum.operation.".concat(Operation.getKeyByCode(response.getCodeOperation())), null, Locale.getDefault()))
		// ...performed by...
		.append(" by ").append(userService.getNameById(response.getIdOperator()));

		return OperationLogDTO.builder()
			.id(response.getId())
			.dateOperation(response.getDateOperation())
			.logEntry(bfr.toString())
			.build();
		// @formatter:on
	}

	public List<OperationLogDTO> mapResponse(List<OperationLog> lst) {
		if (lst == null) {
			return null;
		}
		return lst.stream().map(x -> mapSingle(x)).collect(Collectors.toList());
	}

}
