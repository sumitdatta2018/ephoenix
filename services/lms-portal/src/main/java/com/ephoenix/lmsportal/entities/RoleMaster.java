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


/**
 * 
 * @author mds012
 *
 */
@Getter
@Setter
@Entity
@Table(name = "MST_ROLE")
public class RoleMaster extends BaseEntity {

	private static final long serialVersionUID = 7983081561251461625L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ROLE_ID")
	private Long roleId;

	@NotNull
	@Column(name = "ROLE_NAME")
	private String roleName;
	

	
	/*@Column(name = "ROLE_TYPE_ID")
	private Long roleType;*/

}