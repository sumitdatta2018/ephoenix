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

	public UserTO updateUserById(Long id, UserTO userTO) {
		// TODO Auto-generated method stub
		UserMaster userMaster = userMasterRepository.findByIdAndIsActive(id, ActiveConstants.ACTIVE.getIsActive());
		if (userMaster == null) {
			throw new LMSPortalException(ErrorCode.UMS_ERROR_CODE002.name());
		}
		userMaster.setAadharNum(userTO.getAadharNum()!=null?userTO.getAadharNum():userMaster.getAadharNum());
		userMaster.setAddress(userTO.getAddress()!=null?userTO.getAddress():userMaster.getAddress());
		userMaster.setCity(userTO.getCity()!=null?userTO.getCity():userMaster.getCity());
		userMaster.setDistrict(userTO.getDistrict()!=null?userTO.getDistrict():userMaster.getDistrict());
		userMaster.setCasteId(userTO.getCasteId()!=null?userTO.getCasteId():userMaster.getCasteId());
		userMaster.setContact(userTO.getContact()!=null?userTO.getContact():userMaster.getContact());
		userMaster.setDistrictId(userTO.getDistrictId()!=null? userTO.getDistrictId():userMaster.getDistrictId());
		userMaster.setDob(userTO.getDob()!=null?userTO.getDob():userMaster.getDob());
		userMaster.setFatherName(userTO.getFatherName()!=null?userTO.getFatherName():userMaster.getFatherName());
		userMaster.setMothername(userTO.getMothername()!=null?userTO.getMothername():userMaster.getMothername());
		userMaster.setInstituteName(userTO.getInstituteName()!=null?userTO.getInstituteName():userMaster.getInstituteName());
		userMaster.setIsActive(userTO.getIsActive()!=null?userTO.getIsActive():userMaster.getIsActive());
		userMaster.setLastQualification(userTO.getLastQualification()!=null?userTO.getLastQualification():userMaster.getLastQualification());
		userMaster.setPin(userTO.getPin()!=null?userTO.getPin():userMaster.getPin());
		userMaster.setGender(userTO.getGender()!=null?userTO.getGender():userMaster.getGender());
		userMaster.setReligionId(userTO.getReligionId()!=null?userTO.getReligionId():userMaster.getReligionId());
		userMaster.setStateId(userTO.getStateId()!=null?userTO.getStateId():userMaster.getStateId());
		UserMaster savedUserMaster = userMasterRepository.save(userMaster);
		UserTO savedUserTo = modelMapper.map(savedUserMaster, UserTO.class);

		return savedUserTo;
	}
	
	/*public UserTO fetchUserAllUser(Long id) {
		
		Optional<UserMaster> userMasterOpt = userMasterRepository.find(id);
		UserTO user = modelMapper.map(userMasterOpt.get(), UserTO.class);
		// TODO Auto-generated method stub

		return user;
	}*/
	

}
