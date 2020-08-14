package com.ephoenix.lmsportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ephoenix.lmsportal.entities.ClassMaster;
import com.ephoenix.lmsportal.entities.DistrictMaster;


public interface DistrictMasterRepository extends JpaRepository<DistrictMaster, Long>, JpaSpecificationExecutor<ClassMaster> {

	List<DistrictMaster> findByIsActive(Character character);
	
	

}