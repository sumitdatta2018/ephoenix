package com.ephoenix.lmsportal.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
@Table(name = "MST_MENU")
public class MenuMaster extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "MENU_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "MENU_NAME")
	private String name;

	@Column(name = "MENU_LABEL")
	private String label;

	@Column(name = "PARENT_MENU")
	private String parentMenu;

	@Column(name = "ACTION")
	private String action;

	@Column(name = "IS_LEFTMENU")
	private Integer isLeftMenu;

	@Column(name = "BROWSER")
	private Integer browser;

	@Column(name = "MOBILE")
	private Integer mobile;

	@Column(name = "MOBILE_BROWSER")
	private Integer mobileBrowser;

	@Column(name = "IS_DASHBOARD")
	private Integer isDashboard;

	@Column(name = "IS_VISIBLE")
	private Integer isVisible;

	@Column(name = "IS_DEFAULT")
	private Integer isDefault;

	@Column(name = "ICON_NAME")
	private String iconName;

	@Column(name = "ORDER_BY")
	private Integer sequence;

	@Column(name = "HOME_ICON")
	private String homeIcon;
}