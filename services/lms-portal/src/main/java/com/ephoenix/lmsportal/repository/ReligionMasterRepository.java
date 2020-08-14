package com.ephoenix.lmsportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ephoenix.lmsportal.entities.ReligionMaster;


public interface ReligionMasterRepository extends JpaRepository<ReligionMaster, Long>, JpaSpecificationExecutor<ReligionMaster> {

	List<ReligionMaster> findByIsActive(Character character);

}