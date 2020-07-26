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

/**
 * @author mds012
 *
 */
@Getter
@Setter
@Entity
@Table(name = "MST_CLASS")
public class ClassMaster extends BaseEntity {

	private static final long serialVersionUID = -2108971121222802003L;

	@Id
	@Column(name = "CLASS_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long classId;

	@NotNull
	@Column(name = "CLASS_NAME")
	private String className;

}
