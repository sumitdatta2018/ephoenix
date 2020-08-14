package com.ephoenix.lmsportal.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ephoenix.lmsportal.dto.CasteTo;
import com.ephoenix.lmsportal.dto.ClassTo;
import com.ephoenix.lmsportal.dto.DistrictTo;
import com.ephoenix.lmsportal.dto.Notice;
import com.ephoenix.lmsportal.dto.ReligionTo;
import com.ephoenix.lmsportal.dto.StateTo;
import com.ephoenix.lmsportal.dto.StudyPlanTypeDto;
import com.ephoenix.lmsportal.dto.SubjectTo;
import com.ephoenix.lmsportal.dto.TypeTo;
import com.ephoenix.lmsportal.dto.UploadTypeTo;
import com.ephoenix.lmsportal.entities.CasteMaster;
import com.ephoenix.lmsportal.entities.ClassMaster;
import com.ephoenix.lmsportal.entities.DistrictMaster;
import com.ephoenix.lmsportal.entities.NoticeEntity;
import com.ephoenix.lmsportal.entities.ReligionMaster;
import com.ephoenix.lmsportal.entities.StateMaster;
import com.ephoenix.lmsportal.entities.StudyPlanTypeMaster;
import com.ephoenix.lmsportal.entities.SubjectMaster;
import com.ephoenix.lmsportal.entities.TypeMaster;
import com.ephoenix.lmsportal.entities.UploadTypeMaster;
import com.ephoenix.lmsportal.excp.LMSPortalException;
import com.ephoenix.lmsportal.generic.code.ActiveConstants;
import com.ephoenix.lmsportal.generic.code.ErrorCode;
import com.ephoenix.lmsportal.repository.CasteMasterRepository;
import com.ephoenix.lmsportal.repository.ClassMasterRepository;
import com.ephoenix.lmsportal.repository.DistrictMasterRepository;
import com.ephoenix.lmsportal.repository.NoticeRepository;
import com.ephoenix.lmsportal.repository.ReligionMasterRepository;
import com.ephoenix.lmsportal.repository.StateMasterRepository;
import com.ephoenix.lmsportal.repository.StudyPlanTypeRepository;
import com.ephoenix.lmsportal.repository.SubjectMasterRepository;
import com.ephoenix.lmsportal.repository.TypeMasterRepository;
import com.ephoenix.lmsportal.repository.UploadTypeMasterRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class NoticeService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private NoticeRepository noticeRepository;

	public List<Notice> fetchNotice() {

		Date currentDate = new Date();
		List<Notice> noticeList = new ArrayList<>();
		List<NoticeEntity> NoticeEntities = noticeRepository
				.findAllByStartDateLessThanEqualAndEndDateGreaterThanEqualAndIsActive(currentDate, currentDate,
						ActiveConstants.ACTIVE.getIsActive());
		NoticeEntities.stream().forEach(noticeEntity -> {
			Notice notice = modelMapper.map(noticeEntity, Notice.class);
			noticeList.add(notice);
		});
		return noticeList;
	}

	public Notice createNotice(Notice notice) {
		
		if(notice.getEndDate().compareTo(new Date())<0) {
			throw new LMSPortalException(ErrorCode.NMS_ERROR_CODE002.name());
		}
		NoticeEntity noticeEntity = modelMapper.map(notice, NoticeEntity.class);
		NoticeEntity savedNoticeEntity = noticeRepository.save(noticeEntity);
		Notice savedNotice = modelMapper.map(savedNoticeEntity, Notice.class);
		return savedNotice;

	}
	
	public Notice updateNotice(Notice notice,Long noticeId) {
		NoticeEntity savedNoticeEntity = noticeRepository.findByNoticeIdAndIsActive(noticeId,ActiveConstants.ACTIVE.getIsActive());
		if(savedNoticeEntity == null) {
			throw new LMSPortalException(ErrorCode.NMS_ERROR_CODE001.name());
		}
		savedNoticeEntity.setBody(notice.getBody());
		savedNoticeEntity.setIsActive(notice.getIsActive());
		savedNoticeEntity.setSubTitle(notice.getSubTitle());
		savedNoticeEntity.setTitle(notice.getTitle());
		savedNoticeEntity.setStartDate(notice.getStartDate());
		savedNoticeEntity.setEndDate(notice.getEndDate());
		NoticeEntity updatedNoticeEntity = noticeRepository.saveAndFlush(savedNoticeEntity);
		Notice updatedNotice = modelMapper.map(updatedNoticeEntity, Notice.class);
		return updatedNotice;

	}

}
