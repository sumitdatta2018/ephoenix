package com.ephoenix.lmsportal.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ephoenix.lmsportal.dto.MenuTo;
import com.ephoenix.lmsportal.dto.RoleTo;
import com.ephoenix.lmsportal.dto.UserLoginTO;
import com.ephoenix.lmsportal.dto.UserTO;
import com.ephoenix.lmsportal.entities.MenuMaster;
import com.ephoenix.lmsportal.entities.RoleMaster;
import com.ephoenix.lmsportal.entities.RoleMenuMap;
import com.ephoenix.lmsportal.entities.RoleUserMap;
import com.ephoenix.lmsportal.entities.UserMaster;
import com.ephoenix.lmsportal.excp.LMSPortalException;
import com.ephoenix.lmsportal.generic.code.ActiveConstants;
import com.ephoenix.lmsportal.generic.code.ErrorCode;
import com.ephoenix.lmsportal.repository.MenuMasterRepository;
import com.ephoenix.lmsportal.repository.RoleMasterRepository;
import com.ephoenix.lmsportal.repository.RoleMenuMapRepository;
import com.ephoenix.lmsportal.repository.RoleUserMapRepository;
import com.ephoenix.lmsportal.repository.UserMasterRepository;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class MenuService {
	

	@Autowired
	private MenuMasterRepository menuMasterRepository;
	@Autowired
	private ModelMapper modelMapper;

	

	private static final Logger LOGGER = LoggerFactory.getLogger(MenuService.class);

	

	public List<MenuTo> fetchAllMenu() {
		List<MenuMaster> menuEnts = menuMasterRepository.findByIsDefaultAndIsActiveOrderBySequenceAsc(1, ActiveConstants.ACTIVE.getIsActive());
		List<MenuTo> menuTos = new ArrayList<>();
		menuEnts.stream().forEach(menuEnt ->{
			MenuTo menuTo = modelMapper.map(menuEnt, MenuTo.class);
			menuTos.add(menuTo);
		});
		return menuTos;
	}

}
