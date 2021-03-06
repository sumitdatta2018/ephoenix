package com.ephoenix.lmsportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ephoenix.lmsportal.entities.SubjectMaster;

public interface SubjectMasterRepository
		extends JpaRepository<SubjectMaster, Long>, JpaSpecificationExecutor<SubjectMaster> {
	SubjectMaster findBySubjectNameAndIsActive(String subjectName, char isActive);

	List<SubjectMaster> findByIsActive(Character character);

	List<SubjectMaster> findBySubjectIdInAndIsActive(List<Long> subjectIds, Character isActive);

}