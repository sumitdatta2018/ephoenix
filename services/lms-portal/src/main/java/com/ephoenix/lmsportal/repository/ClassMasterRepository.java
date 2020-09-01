package com.ephoenix.lmsportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ephoenix.lmsportal.entities.ClassMaster;


public interface ClassMasterRepository extends JpaRepository<ClassMaster, Long>, JpaSpecificationExecutor<ClassMaster> {
	
	ClassMaster findByClassNameAndIsActive(String className, char isActive);

	List<ClassMaster> findByIsActive(Character character);

	List<ClassMaster> findByClassIdInAndIsActive(List<Long> classIds, Character isActive);
	
	

}