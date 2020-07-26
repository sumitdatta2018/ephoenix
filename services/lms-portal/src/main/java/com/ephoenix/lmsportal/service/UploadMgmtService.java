package com.ephoenix.lmsportal.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.camel.Exchange;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.ephoenix.lmsportal.dto.UploadTO;
import com.ephoenix.lmsportal.entities.UploadEntity;
import com.ephoenix.lmsportal.entities.UploadTypeMaster;
import com.ephoenix.lmsportal.entities.UserStudyMap;
import com.ephoenix.lmsportal.generic.code.ActiveConstants;
import com.ephoenix.lmsportal.repository.ClassMasterRepository;
import com.ephoenix.lmsportal.repository.RoleMasterRepository;
import com.ephoenix.lmsportal.repository.RoleUserMapRepository;
import com.ephoenix.lmsportal.repository.StudyPlanMapRepository;
import com.ephoenix.lmsportal.repository.TypeMasterRepository;
import com.ephoenix.lmsportal.repository.UploadMasterRepository;
import com.ephoenix.lmsportal.repository.UploadTypeMasterRepository;
import com.ephoenix.lmsportal.repository.UserClassMapRepository;
import com.ephoenix.lmsportal.repository.UserMasterRepository;
import com.ephoenix.lmsportal.util.LMSUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UploadMgmtService {

	@Autowired
	private UploadTypeMasterRepository uploadTypeMasterRepository;

	@Autowired
	private UploadMasterRepository uploadMasterRepository;

	@Autowired
	private StudyPlanMapRepository studyPlanMapRepository;

	@Value("${upload.base.dir}")
	private String uploadBaseDir;

	@Value("${ephoenix.server.dns}")
	private String serverDns;

	@Autowired
	private ModelMapper modelMapper;

	public UploadTO handleUploadContent(Exchange exchange) {
		String uploadDir = exchange.getProperty("upload-dir").toString();
		String fileName = exchange.getProperty("fileName").toString();
		String uploadType = exchange.getIn().getHeader("uploadType").toString();
		String studyPlanId = null;
		if (exchange.getIn().getHeader("studyPlanId") != null) {
			studyPlanId = exchange.getIn().getHeader("studyPlanId").toString();
		}
		UploadTypeMaster uploadTypeMaster = uploadTypeMasterRepository.findByUploadTypeAndIsActive(uploadType,
				ActiveConstants.ACTIVE.getIsActive());

		UploadEntity uploadEntity = null;
		String filePath = LMSUtil.checkTrailingSlash(uploadDir.replace(uploadBaseDir, ""));
		if (filePath != null && uploadTypeMaster.getUploadTypeId() != null) {
			if (!StringUtils.isEmpty(studyPlanId))
				uploadEntity = uploadMasterRepository.findByFileNameAndFilePathAndStudyPlanIdAndIsActive(fileName,
						filePath, Long.valueOf(studyPlanId), ActiveConstants.ACTIVE.getIsActive());
			else {
				uploadEntity = uploadMasterRepository.findByFileNameAndFilePathAndIsActive(fileName, filePath,
						ActiveConstants.ACTIVE.getIsActive());

			}
		}
		if (uploadEntity == null) {
			uploadEntity = new UploadEntity();

			uploadEntity.setFileName(fileName);

			uploadEntity.setFilePath(filePath);
			uploadEntity.setUploadTypeId(uploadTypeMaster.getUploadTypeId());
			uploadEntity.setStudyPlanId(studyPlanId != null ? Long.valueOf(studyPlanId) : null);
		}
		UploadEntity savedUploadEntity = uploadMasterRepository.save(uploadEntity);
		UploadTO uploadTO = modelMapper.map(savedUploadEntity, UploadTO.class);
		uploadTO.setCompleteUploadPath(new StringBuffer(serverDns).append(filePath).append(fileName).toString());
		return uploadTO;

	}

	public List<UploadTO> fetchtAllUploadDocs(List<Long> uploadTypeIds, Long userId) {
		List<UploadEntity> uploadEntities = new ArrayList<>();
		List<UploadTO> uploadTOs = new ArrayList<>();
		if (!CollectionUtils.isEmpty(uploadTypeIds)) {
			uploadEntities = uploadMasterRepository.findByUploadTypeIdInAndIsActive(uploadTypeIds,
					ActiveConstants.ACTIVE.getIsActive());
		} else if (userId != null) {
			List<UserStudyMap> userStudyMaps = studyPlanMapRepository.findByUserIdAndIsActive(userId,
					ActiveConstants.ACTIVE.getIsActive());
			List<Long> studyPlanIds = userStudyMaps.stream().map(UserStudyMap::getStudyPlanId).distinct()
					.collect(Collectors.toList());
			uploadEntities = uploadMasterRepository.findByStudyPlanIdInAndIsActive(studyPlanIds,
					ActiveConstants.ACTIVE.getIsActive());
		} else {
			uploadEntities = uploadMasterRepository.findByIsActive(ActiveConstants.ACTIVE.getIsActive());
		}
		uploadEntities.stream().forEach(uploadEntity -> {
			UploadTO uploadTO = modelMapper.map(uploadEntity, UploadTO.class);
			uploadTO.setCompleteUploadPath(new StringBuffer(serverDns).append(uploadTO.getFilePath())
					.append(uploadTO.getFileName()).toString());
			uploadTOs.add(uploadTO);
		});
		return uploadTOs;

	}

}
