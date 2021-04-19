package com.cs.sigm.exception.response;

import java.io.Serializable;
import java.util.Date;

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
public class CmsResponseBody implements Serializable {
	
	private static final long serialVersionUID = -943187653538381886L;
	
	@Builder.Default
	private Date timestamp = new Date();
	
	private int status;
	
	private String error;
	
	private String details;
	
}
