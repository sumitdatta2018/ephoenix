package com.ephoenix.lmsportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ephoenix.lmsportal.entities.StudyPlanEntity;

public interface StudyPlanRepository
		extends JpaRepository<StudyPlanEntity, Long>, JpaSpecificationExecutor<StudyPlanEntity> {

	List<StudyPlanEntity> findByClassIdAndSubjectIdAndIsActive(Long classId, Long subId, char isActive);

	List<StudyPlanEntity> findByIsDefaultAndIsActive(char isDefault, char isActive);

	List<StudyPlanEntity> findByClassIdAndSubjectIdInAndIsActive(Long clsId, List<Long> subIds, Character isActive);

	List<StudyPlanEntity> findByStudyPlanIdInAndIsActive(List<Long> subIds, Character isActive);
	//StudyPlanEntity findByStudyPlanIdAndIsActive(Long studyPlanId, Character isActive);

	StudyPlanEntity findByClassIdAndSubjectIdAndIsActive(Long clsId, Long subIds, Character isActive);
}