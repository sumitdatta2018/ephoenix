package com.ephoenix.lmsportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ephoenix.lmsportal.entities.TypeMaster;


public interface TypeMasterRepository extends JpaRepository<TypeMaster, Long>, JpaSpecificationExecutor<TypeMaster> {
	
	TypeMaster findByTypeNameIgnoreCaseAndIsActive(String typeName, char c);
	List<TypeMaster> findByTypeNameInAndIsActive(List<String> typeName, char c);
	List<TypeMaster> findByIsActive(Character character);

}