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
@Table(name = "tbl_user_log", indexes = {
	@Index(name = "idx_user_log_id_user", columnList = "id_user")
})
public class ContentLog {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Column(name = "id_operation")
	private Long idOperation;
	
	@NotNull
	@Column(name = "id_operator")
	private Long idOperator;
	
	@NotNull
	@Column(name = "id_user")
	private Long idContent;
	
	@NotNull
	@Column(name = "date_operation")
	@Builder.Default
	private Date dateOperation = new Date();
	
	private String notes;
	
}
