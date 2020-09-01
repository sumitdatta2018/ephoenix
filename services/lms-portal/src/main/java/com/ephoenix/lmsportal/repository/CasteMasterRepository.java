package com.ephoenix.lmsportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ephoenix.lmsportal.entities.CasteMaster;


public interface CasteMasterRepository extends JpaRepository<CasteMaster, Long>, JpaSpecificationExecutor<CasteMaster> {


	List<CasteMaster> findByIsActive(Character isActive);

	CasteMaster findByCasteId(Long casteId);
	
	

}