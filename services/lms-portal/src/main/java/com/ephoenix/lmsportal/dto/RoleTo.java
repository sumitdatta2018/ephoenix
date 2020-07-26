package com.ephoenix.lmsportal.dto;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
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

public class RoleTo extends BaseTO {

	private static final long serialVersionUID = 7983081561251461625L;
	private Long roleId;
	private String roleName;
	private String roleCode;

}