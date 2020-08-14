package com.ephoenix.lmsportal.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ephoenix.lmsportal.entities.NoticeEntity;


public interface NoticeRepository extends JpaRepository<NoticeEntity, Long>, JpaSpecificationExecutor<NoticeEntity> {



	

	NoticeEntity findByNoticeIdAndIsActive(Long noticeId, Character isActive);

	List<NoticeEntity> findAllByStartDateLessThanEqualAndEndDateGreaterThanEqualAndIsActive(Date currentDate,
			Date currentDate2, Character isActive);
	
	

}