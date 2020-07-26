package com.ephoenix.lmsportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ephoenix.lmsportal.entities.StudyPlanTypeMaster;

public interface StudyPlanTypeRepository
		extends JpaRepository<StudyPlanTypeMaster, Long>, JpaSpecificationExecutor<StudyPlanTypeMaster> {

	List<StudyPlanTypeMaster> findByStudyPlanTypeIdInAndIsActive(List<Long> studyTypeIds, Character isActive);

	StudyPlanTypeMaster findByStudyPlanTypeIdAndIsActive(Long studyPlanTypeId, Character isActive);
}