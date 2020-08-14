package com.ephoenix.lmsportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ephoenix.lmsportal.entities.UploadTypeMaster;


public interface UploadTypeMasterRepository extends JpaRepository<UploadTypeMaster, Long>, JpaSpecificationExecutor<UploadTypeMaster> {
	
	public UploadTypeMaster findByUploadTypeAndIsActive(String typeName, char c);

	public List<UploadTypeMaster> findByIsActive(Character character);
	
	

}