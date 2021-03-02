package com.cs.sigm.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cs.sigm.adapter.domain.ContentDTO;
import com.cs.sigm.domain.Content;

@Service
public class ContentMapper {

	public Content map(ContentDTO request) {
		// @formatter:off
		return Content.builder()
			.id(request.getId())
			.idPicture(request.getIdPicture())
			.content(request.getContent())
			.title(request.getTitle())
			.build();
		// @formatter:on
	}

	public ContentDTO map(Content response) {
		// @formatter:off
		return ContentDTO.builder()
			.id(response.getId())
			.idPicture(response.getIdPicture())
			.content(response.getContent())
			.title(response.getTitle())
			.build();
		// @formatter:on
	}

	public List<Content> mapRequest(List<ContentDTO> lst) {
		List<Content> response = new ArrayList<>();
		for (ContentDTO cur : lst) {
			response.add(map(cur));
		}
		return response;
	}

	public List<ContentDTO> mapResponse(List<Content> lst) {
		List<ContentDTO> response = new ArrayList<>();
		for (Content cur : lst) {
			response.add(map(cur));
		}
		return response;
	}

}
