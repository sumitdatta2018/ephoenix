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
@Table(name = "MST_DISTRICT")
public class DistrictMaster extends BaseEntity {

	private static final long serialVersionUID = -2108971121222802003L;

	@Id
	@Column(name = "DISTRICT_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long districtId;

	@NotNull
	@Column(name = "DISTRICT_NAME")
	private String districtName;

}
