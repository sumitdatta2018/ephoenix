package com.ephoenix.lmsportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ephoenix.lmsportal.entities.ClassMaster;


public interface ClassMasterRepository extends JpaRepository<ClassMaster, Long>, JpaSpecificationExecutor<ClassMaster> {
	
	ClassMaster findByClassNameAndIsActive(String className, char isActive);
	
	

}