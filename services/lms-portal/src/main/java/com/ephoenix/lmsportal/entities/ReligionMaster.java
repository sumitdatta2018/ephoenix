package com.ephoenix.lmsportal.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "MST_RELIGION")
public class ReligionMaster extends BaseEntity {

	private static final long serialVersionUID = -2108971121222802003L;

	@Id
	@Column(name = "RELIGION_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long religionId;

	@NotNull
	@Column(name = "RELIGION_NAME")
	private String religionName;

}
