package com.cs.sigm.adapter.domain;

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
public class OperationLogDTO implements Serializable {

	private static final long serialVersionUID = -3387349111681718644L;

	private Long id;

	private String logEntry;

	private Date dateOperation;

}
