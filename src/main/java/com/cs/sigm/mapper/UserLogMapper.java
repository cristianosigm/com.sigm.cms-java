package com.cs.sigm.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cs.sigm.adapter.domain.UserLogDTO;
import com.cs.sigm.domain.UserLog;

@Service
public class UserLogMapper {

	public UserLog map(UserLogDTO request) {
		// @formatter:off
		return UserLog.builder()
			.id(request.getId())
			.idOperation(request.getIdOperation())
			.idOperator(request.getIdOperator())
			.idUser(request.getIdUser())
			.dateOperation(request.getDateOperation())
			.notes(request.getNotes())
			.build();
		// @formatter:on
	}

	public UserLogDTO map(UserLog response) {
		// @formatter:off
		return UserLogDTO.builder()
			.id(response.getId())
			.idOperation(response.getIdOperation())
			.idOperator(response.getIdOperator())
			.idUser(response.getIdUser())
			.dateOperation(response.getDateOperation())
			.notes(response.getNotes())
			.build();
		// @formatter:on
	}

	public List<UserLog> mapRequest(List<UserLogDTO> lst) {
		List<UserLog> response = new ArrayList<>();
		for (UserLogDTO cur : lst) {
			response.add(map(cur));
		}
		return response;
	}

	public List<UserLogDTO> mapResponse(List<UserLog> lst) {
		List<UserLogDTO> response = new ArrayList<>();
		for (UserLog cur : lst) {
			response.add(map(cur));
		}
		return response;
	}

}
