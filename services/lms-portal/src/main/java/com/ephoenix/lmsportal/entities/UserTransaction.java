package com.ephoenix.lmsportal.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

/**
 * @author mds012
 *
 */
@Getter
@Setter
@Entity
@Table(name = "TRANSACTION")
public class UserTransaction extends BaseEntity {

	private static final long serialVersionUID = -2108971121222802003L;
	
	@Id
	@Column(name = "T_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long tId;
	
	@NotEmpty
	@Column(name = "T_NUMBER")
	private String tNumber;
	
	@NotEmpty
	@Column(name = "USER_ID")
	private Long userId;
	
	@Column(name = "AMOUNT")
	private Double amount;
	
	
	@NotEmpty
	@Column(name = "STATUS")
	private String status;
	
	}
