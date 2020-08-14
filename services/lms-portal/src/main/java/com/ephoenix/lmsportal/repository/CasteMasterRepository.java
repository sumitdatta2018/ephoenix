package com.ephoenix.lmsportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ephoenix.lmsportal.entities.CasteMaster;
import com.ephoenix.lmsportal.entities.ClassMaster;
import com.ephoenix.lmsportal.entities.StateMaster;


public interface CasteMasterRepository extends JpaRepository<CasteMaster, Long>, JpaSpecificationExecutor<StateMaster> {


	List<CasteMaster> findByIsActive(Character isActive);
	
	

}