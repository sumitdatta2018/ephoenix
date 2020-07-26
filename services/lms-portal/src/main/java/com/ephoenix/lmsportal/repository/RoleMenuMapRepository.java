package com.ephoenix.lmsportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ephoenix.lmsportal.entities.RoleMenuMap;


public interface RoleMenuMapRepository extends JpaRepository<RoleMenuMap, Long>, JpaSpecificationExecutor<RoleMenuMap> {

	List<RoleMenuMap> findByRoleIdAndIsActive(Long roleId, char c);

	List<RoleMenuMap> findByRoleIdInAndIsActive(List<Long> roleIds, Character isActive);

}