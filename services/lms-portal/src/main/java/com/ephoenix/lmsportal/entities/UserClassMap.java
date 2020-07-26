
package com.ephoenix.lmsportal.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
@Entity
@Table(name = "MAP_USER_CLASS")
public class UserClassMap extends BaseEntity {

	private static final long serialVersionUID = 2250479798317195753L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "MAP_USER_CLASS_ID")
	private Long userClassId;

	@NotNull
	@Column(name = "CLASS_ID")
	private Long classId;

	@Column(name = "USER_ID")
	private Long userId;

}
