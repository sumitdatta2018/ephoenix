package com.ephoenix.lmsportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ephoenix.lmsportal.entities.RoleMenuMap;
import com.ephoenix.lmsportal.entities.UserClassMap;


public interface UserClassMapRepository extends JpaRepository<UserClassMap, Long>, JpaSpecificationExecutor<UserClassMap> {

	

}