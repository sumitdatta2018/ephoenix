package com.ephoenix.lmsportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ephoenix.lmsportal.entities.ClassMaster;
import com.ephoenix.lmsportal.entities.StateMaster;


public interface StateMasterRepository extends JpaRepository<StateMaster, Long>, JpaSpecificationExecutor<StateMaster> {

	List<StateMaster> findByIsActive(Character character);
	
	

}