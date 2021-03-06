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
@Table(name="MAP_ROLE_MENU")
public class RoleMenuMap extends BaseEntity {
	
	private static final long serialVersionUID = -864435872019746156L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ROLE_MENU_MAP_ID")
	private Long roleMenuMapId;
	
	@NotNull
	@Column(name="ROLE_ID")
	private Long roleId;
	
	@NotNull
	@Column(name="MENU_ID")
	private Long menuId;
	
}