package com.ephoenix.lmsportal.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ephoenix.lmsportal.client.KeycloakInvoker;
import com.ephoenix.lmsportal.dto.UserTO;
import com.ephoenix.lmsportal.entities.RoleMaster;
import com.ephoenix.lmsportal.entities.RoleUserMap;
import com.ephoenix.lmsportal.entities.TypeMaster;
import com.ephoenix.lmsportal.entities.UserMaster;
import com.ephoenix.lmsportal.excp.LMSPortalException;
import com.ephoenix.lmsportal.generic.code.ActiveConstants;
import com.ephoenix.lmsportal.generic.code.ErrorCode;
import com.ephoenix.lmsportal.generic.code.ICommonConstants;
import com.ephoenix.lmsportal.repository.RoleMasterRepository;
import com.ephoenix.lmsportal.repository.RoleUserMapRepository;
import com.ephoenix.lmsportal.repository.TypeMasterRepository;
import com.ephoenix.lmsportal.repository.UserMasterRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserMgmtService {

	@Autowired
	private UserMasterRepository userMasterRepository;
	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private TypeMasterRepository typeMasterRepository;


	@Autowired
	private RoleUserMapRepository roleUserMapRepository;

	@Autowired
	private RoleMasterRepository roleMasterRepository;


	@Value("${cms.file.upload.path}")
	private String cmsFilePath;

	@Value("${cms.file.retrieve.path}")
	private String cmsRetrievePath;

	@Autowired
	private KeycloakInvoker keycloakInvoker;

	@Transactional
	public UserTO createUser(UserTO userTO) {
		UserMaster userMaster = modelMapper.map(userTO, UserMaster.class);
		UserMaster exsistingUserWithID = userMasterRepository.findByUserLoginIdIgnoreCaseOrEmailOrMobileAndIsActive(
				userTO.getUserLoginId(), userTO.getEmail(), userTO.getMobile(), ActiveConstants.ACTIVE.getIsActive());
		if (exsistingUserWithID != null) {
			throw new LMSPortalException(ErrorCode.UMS_ERROR_CODE010.name());
		}
		/*
		 * UserMaster exsistingUserWithMobile =
		 * userMasterRepository.findByMobileIgnoreCaseAndIsActive(userTO.getMobile(),
		 * ActiveConstants.ACTIVE.getIsActive()); if (exsistingUserWithMobile != null) {
		 * throw new LMSPortalException(ErrorCode.UMS_ERROR_CODE011.name()); }
		 */

		String userType = userTO.getUserType() == null ? "STUDENT" : userTO.getUserType();
		TypeMaster typeMasterEnt = typeMasterRepository.findByTypeNameIgnoreCaseAndIsActive(userType,
				ActiveConstants.ACTIVE.getIsActive());
		userMaster.setUserTypeId(typeMasterEnt.getTypeId());

		UserMaster savedUserMaster = userMasterRepository.save(userMaster);
		RoleMaster roleMaster = roleMasterRepository.findByRoleNameIgnoreCaseAndIsActive(userType,
				ActiveConstants.ACTIVE.getIsActive());
		RoleUserMap roleUserMapEntity = new RoleUserMap();
		roleUserMapEntity.setRoleId(roleMaster.getRoleId());
		roleUserMapEntity.setUserId(savedUserMaster.getId());
		roleUserMapEntity.setCreatedBy(ICommonConstants.DEFAULT_USER);
		roleUserMapRepository.save(roleUserMapEntity);
		/*
		 * UserClassMap userClassMap = new UserClassMap();
		 * userClassMap.setClassId(userTO.getClassID());
		 * userClassMap.setUserId(savedUserMaster.getId());
		 * userClassMapRepository.save(userClassMap);
		 */
		userTO = modelMapper.map(savedUserMaster, UserTO.class);

		keycloakInvoker.createKeycloakUser(userTO);
		return userTO;
	}

	public UserTO fetchUserById(Long id) {
		Optional<UserMaster> userMasterOpt = userMasterRepository.findById(id);
		UserTO user = modelMapper.map(userMasterOpt.get(), UserTO.class);
		// TODO Auto-generated method stub

		return user;
	}

}
