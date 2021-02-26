package com.cs.sigm.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cs.sigm.adapter.domain.PostDTO;
import com.cs.sigm.domain.Post;

@Service
public class PostMapper {

	public Post map(PostDTO request) {
		// @formatter:off
		return Post.builder()
			.id(request.getId())
			.idPicture(request.getIdPicture())
			.content(request.getContent())
			.title(request.getTitle())
			.build();
		// @formatter:on
	}

	public PostDTO map(Post response) {
		// @formatter:off
		return PostDTO.builder()
			.id(response.getId())
			.idPicture(response.getIdPicture())
			.content(response.getContent())
			.title(response.getTitle())
			.build();
		// @formatter:on
	}

	public List<Post> mapRequest(List<PostDTO> lst) {
		List<Post> response = new ArrayList<>();
		for (PostDTO cur : lst) {
			response.add(map(cur));
		}
		return response;
	}

	public List<PostDTO> mapResponse(List<Post> lst) {
		List<PostDTO> response = new ArrayList<>();
		for (Post cur : lst) {
			response.add(map(cur));
		}
		return response;
	}

}
