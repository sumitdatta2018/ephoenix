package com.ephoenix.lmsportal.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.camel.Exchange;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.ephoenix.lmsportal.dto.UploadTO;
import com.ephoenix.lmsportal.entities.ClassMaster;
import com.ephoenix.lmsportal.entities.StudyPlanEntity;
import com.ephoenix.lmsportal.entities.SubjectMaster;
import com.ephoenix.lmsportal.entities.UploadEntity;
import com.ephoenix.lmsportal.entities.UploadTypeMaster;
import com.ephoenix.lmsportal.entities.UserStudyMap;
import com.ephoenix.lmsportal.excp.LMSPortalException;
import com.ephoenix.lmsportal.generic.code.ActiveConstants;
import com.ephoenix.lmsportal.generic.code.ErrorCode;
import com.ephoenix.lmsportal.generic.code.MessageCode;
import com.ephoenix.lmsportal.repository.UserStudyPlanMapRepository;
import com.ephoenix.lmsportal.repository.ClassMasterRepository;
import com.ephoenix.lmsportal.repository.StudyPlanRepository;
import com.ephoenix.lmsportal.repository.SubjectMasterRepository;
import com.ephoenix.lmsportal.repository.UploadMasterRepository;
import com.ephoenix.lmsportal.repository.UploadTypeMasterRepository;
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
	private UserStudyPlanMapRepository studyPlanMapRepository;

	@Autowired
	private StudyPlanRepository studyPlanRepository;

	@Autowired
	private ClassMasterRepository classMasterRepository;

	@Autowired
	private SubjectMasterRepository subjectMasterRepository;

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
		if (exchange.getProperty("thumbnail-content") != null) {
			String fileFormat = exchange.getProperty("fileFormat").toString();
			ByteArrayOutputStream thumbnailConIns = (ByteArrayOutputStream) exchange.getProperty("thumbnail-content");

			try {
				File thumbnailFile = new File(new StringBuffer(uploadDir).append("/").append(fileName.split("\\.")[0])
						.append("-").append("thumbnail").append(".").append(fileName.split("\\.")[1]).toString());
				// thumbnailFile.mkdirs() ;
				OutputStream outputStream = new FileOutputStream(thumbnailFile);

				thumbnailConIns.writeTo(outputStream);
				outputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

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

		if (uploadTypeMaster.getUploadTypeId().equals(uploadEntity.getUploadTypeId())) {
			uploadTO.setThumbnailURL(new StringBuffer(serverDns).append(uploadTO.getFilePath())
					.append(uploadTO.getFileName().split("\\.")[0]).append("-").append("thumbnail").append(".")
					.append(uploadTO.getFileName().split("\\.")[1]).toString());
		}
		return uploadTO;

	}

	public List<UploadTO> fetchtAllUploadDocs(List<Long> uploadTypeIds, Long userId) {
		List<UploadEntity> uploadEntities = new ArrayList<>();
		List<UploadTO> uploadTOs = new ArrayList<>();
		Map<Long, StudyPlanEntity> studyPlanMapTemp = null;
		UploadTypeMaster uploadTypeMaster = uploadTypeMasterRepository.findByUploadTypeAndIsActive("GALLERY",
				ActiveConstants.ACTIVE.getIsActive());
		if (!CollectionUtils.isEmpty(uploadTypeIds)) {
			uploadEntities = uploadMasterRepository.findByUploadTypeIdInAndIsActive(uploadTypeIds,
					ActiveConstants.ACTIVE.getIsActive());
		} else if (userId != null) {
			List<UserStudyMap> userStudyMaps = studyPlanMapRepository.findByUserIdAndIsActive(userId,
					ActiveConstants.ACTIVE.getIsActive());
			List<Long> studyPlanIds = userStudyMaps.stream().map(UserStudyMap::getStudyPlanId).distinct()
					.collect(Collectors.toList());
			studyPlanMapTemp = studyPlanRepository
					.findByStudyPlanIdInAndIsActive(studyPlanIds, ActiveConstants.ACTIVE.getIsActive()).stream()
					.collect(Collectors.toMap(StudyPlanEntity::getStudyPlanId, study -> study));
			uploadEntities = uploadMasterRepository.findByStudyPlanIdInAndIsActive(studyPlanIds,
					ActiveConstants.ACTIVE.getIsActive());
		} else {
			uploadEntities = uploadMasterRepository.findByIsActive(ActiveConstants.ACTIVE.getIsActive());
		}
		Map<Long, StudyPlanEntity> studyPlanMap = studyPlanMapTemp;
		List<Long> classIds = new ArrayList<>();
		List<Long> subjectIds = new ArrayList<>();
		if (!CollectionUtils.isEmpty(studyPlanMap)) {

			classIds = studyPlanMap.entrySet().stream().map(studyPlan -> studyPlan.getValue())
					.filter(studyPlan -> studyPlan.getClassId() != null).map(StudyPlanEntity::getClassId)
					.collect(Collectors.toList());
			subjectIds = studyPlanMap.entrySet().stream().map(studyPlan -> studyPlan.getValue())
					.filter(studyPlan -> studyPlan.getSubjectId() != null).map(StudyPlanEntity::getSubjectId)
					.filter(subjectEntity -> subjectEntity != null).collect(Collectors.toList());

		}

		Map<Long, ClassMaster> classMasterMap = classMasterRepository
				.findByClassIdInAndIsActive(classIds, ActiveConstants.ACTIVE.getIsActive()).stream()
				.collect(Collectors.toMap(ClassMaster::getClassId, classMaster -> classMaster));
		Map<Long, SubjectMaster> subjectMasterMap = subjectMasterRepository
				.findBySubjectIdInAndIsActive(subjectIds, ActiveConstants.ACTIVE.getIsActive()).stream()
				.collect(Collectors.toMap(SubjectMaster::getSubjectId, subjectMaster -> subjectMaster));

		uploadEntities.stream().forEach(uploadEntity -> {
			UploadTO uploadTO = modelMapper.map(uploadEntity, UploadTO.class);
			uploadTO.setCompleteUploadPath(new StringBuffer(serverDns).append(uploadTO.getFilePath())
					.append(uploadTO.getFileName()).toString());

			if (uploadTypeMaster.getUploadTypeId().equals(uploadEntity.getUploadTypeId())) {
				uploadTO.setThumbnailURL(new StringBuffer(serverDns).append(uploadTO.getFilePath())
						.append(uploadTO.getFileName().split("\\.")[0]).append("-").append("thumbnail").append(".")
						.append(uploadTO.getFileName().split("\\.")[1]).toString());
			}
			if (!CollectionUtils.isEmpty(studyPlanMap)) {

				StudyPlanEntity studyPlanEntity = studyPlanMap.get(uploadTO.getStudyPlanId());
				uploadTO.setClassName(classMasterMap.get(studyPlanEntity.getClassId()).getClassName());
				uploadTO.setSubjectName(subjectMasterMap.get(studyPlanEntity.getSubjectId()).getSubjectName());
			}
			uploadTOs.add(uploadTO);

		});
		return uploadTOs;

	}

	@Transactional
	public String deleteUploadedDoc(Long uploadDocId) {
		UploadEntity uploadEntity = uploadMasterRepository.findByUploadIdAndIsActive(uploadDocId,
				ActiveConstants.ACTIVE.getIsActive());

		UploadTypeMaster uploadTypeMaster = uploadTypeMasterRepository.findByUploadTypeAndIsActive("GALLERY",
				ActiveConstants.ACTIVE.getIsActive());
		if (uploadEntity == null) {
			throw new LMSPortalException(ErrorCode.UPMS_ERROR_CODE002.name());
		}
		String filePath = uploadEntity.getFilePath();
		String fileName = uploadEntity.getFileName();

		try {
			Files.deleteIfExists(
					Paths.get(new StringBuffer().append(uploadBaseDir).append(filePath).append(fileName).toString()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new LMSPortalException(ErrorCode.UPMS_ERROR_CODE003.name());
		}
		if (uploadTypeMaster.getUploadTypeId().equals(uploadEntity.getUploadTypeId())) {
			try {
				Files.deleteIfExists(Paths
						.get(new StringBuffer().append(uploadBaseDir).append(filePath).append(fileName.split("\\.")[0])
								.append("-").append("thumbnail.").append(fileName.split("\\.")[1]).toString()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new LMSPortalException(ErrorCode.UPMS_ERROR_CODE004.name());
			}
		}
		uploadMasterRepository.deleteById(uploadDocId);
		return MessageCode.UPMS_ERROR_CODE001.message();

	}

}
