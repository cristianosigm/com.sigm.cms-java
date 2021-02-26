package com.cs.sigm.adapter.domain;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDTO implements Serializable {

	private static final long serialVersionUID = 7531807705318187551L;

	private Long id;

	private Long idPicture;

	@NotEmpty(message = "{validation.post.title}")
	private String title;

	@NotEmpty(message = "{validation.post.content}")
	private String content;

}
