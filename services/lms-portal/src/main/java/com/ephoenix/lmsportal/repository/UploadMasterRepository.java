package com.ephoenix.lmsportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ephoenix.lmsportal.entities.UploadEntity;

public interface UploadMasterRepository
		extends JpaRepository<UploadEntity, Long>, JpaSpecificationExecutor<UploadEntity> {

	public UploadEntity findByFileNameAndFilePathAndIsActive(String fileName, String filePath, char isActive);

	public UploadEntity findByFileNameAndFilePathAndStudyPlanIdAndIsActive(String fileName, String filePath,
			Long studyPlanId, char isActive);

	public List<UploadEntity> findByUploadTypeIdInAndIsActive(List<Long> uploadTypeIds, char isActive);

	public List<UploadEntity> findByStudyPlanIdInAndIsActive(List<Long> studyPlanIds, char isActive);
	public List<UploadEntity> findByIsActive(char isActive);

}