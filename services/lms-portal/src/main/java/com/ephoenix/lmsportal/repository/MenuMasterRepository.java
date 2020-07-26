package com.ephoenix.lmsportal.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ephoenix.lmsportal.entities.MenuMaster;

public interface MenuMasterRepository extends JpaRepository<MenuMaster, Long>, JpaSpecificationExecutor<MenuMaster> {

	//List<MenuMaster> findByIdInAndMobileBrowserAndIsActiveOrderBySequenceAsc(Set<Long> menuIds, Boolean isMobileBrowser, char c);

	//List<MenuMaster> findByIdInAndBrowserAndIsActiveOrderBySequenceAsc(Set<Long> menuIds, Integer isBrowser, char c);

	/*List<MenuMaster> findByIdInAndMobileAndIsActiveOrderBySequenceAsc(Set<Long> menuIds, Boolean isFromMobile, char c);

	List<MenuMaster> findByMobileBrowserAndIsDefaultAndIsActiveOrderBySequenceAsc(Boolean isMobileBrowser, boolean b, char c);

	List<MenuMaster> findByBrowserAndIsDefaultAndIsActiveOrderBySequenceAsc(Boolean isBrowser, boolean b, char c);

	List<MenuMaster> findByMobileAndIsDefaultAndIsActiveOrderBySequenceAsc(Boolean isFromMobile, boolean b, char c);
*/
	List<MenuMaster> findByIsActive(Character isActive);

	List<MenuMaster> findByIdInAndBrowserOrIsDefaultAndIsActiveOrderBySequenceAsc(Set<Long> menuIds, int isBrowser,int isDefault, Character isActive); 
	List<MenuMaster> findByIsDefaultAndIsActiveOrderBySequenceAsc( int isDefault, Character isActive); 

}