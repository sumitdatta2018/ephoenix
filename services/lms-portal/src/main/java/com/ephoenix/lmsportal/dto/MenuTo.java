package com.ephoenix.lmsportal.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class MenuTo extends BaseTO  {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String name;

	private String label;

	private String parentMenu;

	private String action;

	private Integer isLeftMenu;

	private Integer browser;

	private Integer mobile;

	private Integer mobileBrowser;

	private Integer isDashboard;

	private Integer isVisible;

	private Integer isDefault;

	private String iconName;

	private Integer sequence;

	private String homeIcon;
}