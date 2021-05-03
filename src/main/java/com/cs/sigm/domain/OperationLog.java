package com.cs.sigm.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tbl_operation_log", indexes = { 
		@Index(name = "idx_operation_log_id_entity", columnList = "id_entity"),
		@Index(name = "idx_operation_log_code_entity", columnList = "code_entity"),
		@Index(name = "idx_operation_log_code_operation", columnList = "code_operation") })
public class OperationLog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "id_entity")
	private Long idEntity;

	@NotNull
	@Column(name = "id_operator")
	private Long idOperator;

	@NotNull
	@Column(name = "code_entity")
	private Integer codeEntity;

	@NotNull
	@Column(name = "code_operation")
	private Integer codeOperation;

	@NotNull
	@Column(name = "date_operation")
	@Builder.Default
	private Date dateOperation = new Date();

	private String notes;

}
