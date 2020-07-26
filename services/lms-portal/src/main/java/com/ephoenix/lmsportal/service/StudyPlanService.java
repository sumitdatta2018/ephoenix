package com.ephoenix.lmsportal.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.ephoenix.lmsportal.dto.StudyPlanTo;
import com.ephoenix.lmsportal.entities.StudyPlanEntity;
import com.ephoenix.lmsportal.entities.StudyPlanTypeMaster;
import com.ephoenix.lmsportal.entities.UserStudyMap;
import com.ephoenix.lmsportal.excp.LMSPortalException;
import com.ephoenix.lmsportal.generic.code.ActiveConstants;
import com.ephoenix.lmsportal.generic.code.ErrorCode;
import com.ephoenix.lmsportal.repository.ClassMasterRepository;
import com.ephoenix.lmsportal.repository.DistrictMasterRepository;
import com.ephoenix.lmsportal.repository.StateMasterRepository;
import com.ephoenix.lmsportal.repository.StudyPlanRepository;
import com.ephoenix.lmsportal.repository.StudyPlanTypeRepository;
import com.ephoenix.lmsportal.repository.SubjectMasterRepository;
import com.ephoenix.lmsportal.repository.TypeMasterRepository;
import com.ephoenix.lmsportal.repository.StudyPlanMapRepository;
import com.ephoenix.lmsportal.repository.UserTransactionRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class StudyPlanService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private TypeMasterRepository typeMasterRepository;

	@Autowired
	private ClassMasterRepository classMasterRepository;

	@Autowired
	private SubjectMasterRepository subjectMasterRepository;

	@Autowired
	private StudyPlanRepository studyPlanRepository;

	@Autowired
	private StudyPlanTypeRepository studyPlanTypeRepository;

	@Autowired
	private StateMasterRepository stateMasterRepository;

	@Autowired
	private DistrictMasterRepository districtMasterRepository;

	@Autowired
	private StudyPlanMapRepository userPreamiumRepository;

	@Autowired
	private UserTransactionRepository userTransactionRepository;

	public List<StudyPlanTo> fetchStudyPlans(Long clsId, List<Long> subIds, Long userId) {

		List<StudyPlanTo> studyPlanTos = new ArrayList<>();

		List<StudyPlanEntity> studyPlanEntities = new ArrayList<>();
		final AtomicReference<Map<Long, String>> studyPlanTypeMasterMapTemp = new AtomicReference<>();
		if (!StringUtils.isEmpty(clsId) && !CollectionUtils.isEmpty(subIds)) {

			studyPlanEntities = studyPlanRepository.findByClassIdAndSubjectIdInAndIsActive(clsId, subIds,
					ActiveConstants.ACTIVE.getIsActive());
			List<Long> studyTypeIds = studyPlanEntities.stream().map(StudyPlanEntity::getStudyPlanId)
					.collect(Collectors.toList());

			List<StudyPlanTypeMaster> studyPlanTypeMasters = studyPlanTypeRepository
					.findByStudyPlanTypeIdInAndIsActive(studyTypeIds, ActiveConstants.ACTIVE.getIsActive());
			Map<Long, String> studyPlanTypeMasterMap = studyPlanTypeMasters.stream().collect(
					Collectors.toMap(StudyPlanTypeMaster::getStudyPlanTypeId, StudyPlanTypeMaster::getStudyPlanType));
			studyPlanTypeMasterMapTemp.set(studyPlanTypeMasterMap);

		} else if (userId != null) {
			List<UserStudyMap> userPremiums = userPreamiumRepository.findByUserIdAndIsActive(userId,
					ActiveConstants.ACTIVE.getIsActive());
			List<Long> studyPlanIds = userPremiums.stream().map(UserStudyMap::getStudyPlanId)
					.collect(Collectors.toList());
			studyPlanEntities = studyPlanRepository.findByStudyPlanIdInAndIsActive(studyPlanIds,
					ActiveConstants.ACTIVE.getIsActive());
			List<Long> studyTypeIds = studyPlanEntities.stream().map(StudyPlanEntity::getStudyPlanId)
					.collect(Collectors.toList());

			List<StudyPlanTypeMaster> studyPlanTypeMasters = studyPlanTypeRepository
					.findByStudyPlanTypeIdInAndIsActive(studyTypeIds, ActiveConstants.ACTIVE.getIsActive());
			Map<Long, String> studyPlanTypeMasterMap = studyPlanTypeMasters.stream().collect(
					Collectors.toMap(StudyPlanTypeMaster::getStudyPlanTypeId, StudyPlanTypeMaster::getStudyPlanType));
			studyPlanTypeMasterMapTemp.set(studyPlanTypeMasterMap);

		} else

		{
			studyPlanEntities = studyPlanRepository.findByIsDefaultAndIsActive(ActiveConstants.ACTIVE.getIsActive(),
					ActiveConstants.ACTIVE.getIsActive());
			List<Long> studyTypeIds = studyPlanEntities.stream().map(StudyPlanEntity::getStudyPlanId)
					.collect(Collectors.toList());

			List<StudyPlanTypeMaster> studyPlanTypeMasters = studyPlanTypeRepository
					.findByStudyPlanTypeIdInAndIsActive(studyTypeIds, ActiveConstants.ACTIVE.getIsActive());
			Map<Long, String> studyPlanTypeMasterMap = studyPlanTypeMasters.stream().collect(
					Collectors.toMap(StudyPlanTypeMaster::getStudyPlanTypeId, StudyPlanTypeMaster::getStudyPlanType));
			studyPlanTypeMasterMapTemp.set(studyPlanTypeMasterMap);

		}
		studyPlanEntities.stream().forEach(studyPlanEntity -> {
			Map<Long, String> studyPlanTypeMasterMap = studyPlanTypeMasterMapTemp.get();
			StudyPlanTo studyPlanTo = modelMapper.map(studyPlanEntity, StudyPlanTo.class);
			String stringTypeName = studyPlanTypeMasterMap.get(studyPlanEntity.getStudyPlanTypeId());
			if (stringTypeName != null) {
				studyPlanTo.setStudyPlanType(stringTypeName);
			}

			studyPlanTos.add(studyPlanTo);
		});

		// TODO Auto-generated method stub
		return studyPlanTos;
	}

	public List<StudyPlanTo> createStudyPlans(List<StudyPlanTo> studyPlanTos) {
		List<StudyPlanEntity> studyPlanEntities = new ArrayList<>();
		List<StudyPlanTo> savedStudyPlanTos = new ArrayList<>();
		studyPlanTos.stream().forEach(studyPlanTo -> {
			StudyPlanEntity studyPlanEntity = modelMapper.map(studyPlanTo, StudyPlanEntity.class);
			if (studyPlanEntity.getClassId() != null && studyPlanEntity.getSubjectId() != null) {
				StudyPlanEntity existingStudyPlanEntity = studyPlanRepository.findByClassIdAndSubjectIdAndIsActive(
						studyPlanEntity.getClassId(), studyPlanEntity.getSubjectId(),
						ActiveConstants.ACTIVE.getIsActive());
				if (existingStudyPlanEntity == null) {
					studyPlanEntity.setIsActive(ActiveConstants.ACTIVE.getIsActive());
					studyPlanEntities.add(studyPlanEntity);
				}
			} else if (studyPlanEntity.getStudyPlanTypeId() != null) {
				StudyPlanTypeMaster studyPlanTypeMaster = studyPlanTypeRepository.findByStudyPlanTypeIdAndIsActive(
						studyPlanEntity.getStudyPlanTypeId(), ActiveConstants.ACTIVE.getIsActive());
				if (studyPlanTypeMaster.getStudyPlanTypeId() != null) {
					studyPlanEntity.setIsActive(ActiveConstants.ACTIVE.getIsActive());
					studyPlanEntities.add(studyPlanEntity);
				}
			}

		});

		List<StudyPlanEntity> savedEntities = studyPlanRepository.saveAll(studyPlanEntities);
		savedEntities.stream().forEach(savedEntity -> {
			StudyPlanTo studyPlanTo = modelMapper.map(savedEntity, StudyPlanTo.class);
			savedStudyPlanTos.add(studyPlanTo);
		});
		return savedStudyPlanTos;
	}

	public StudyPlanTo updateStudyPlans(Long studyPlanId, StudyPlanTo stPlanTo) {
		StudyPlanEntity savedStudyPlanEnt = new StudyPlanEntity();

		if (studyPlanId != null) {
			StudyPlanEntity existingStudyPlanEntity = studyPlanRepository.findById(studyPlanId).get();
			if (existingStudyPlanEntity != null) {
				existingStudyPlanEntity.setPriceRateYearly(stPlanTo.getPriceRateYearly());
				existingStudyPlanEntity.setIsActive(stPlanTo.getIsActive());

				// existingStudyPlanEntity.setClassId(stPlanTo.getClassId());
				// existingStudyPlanEntity.setSubjectId(stPlanTo.getSubjectId());
				if (stPlanTo.getStudyPlanTypeId() != null) {
					StudyPlanTypeMaster studyPlanTypeMaster = studyPlanTypeRepository.findByStudyPlanTypeIdAndIsActive(
							stPlanTo.getStudyPlanTypeId(), ActiveConstants.ACTIVE.getIsActive());
					if (studyPlanTypeMaster.getStudyPlanType().equalsIgnoreCase("OTHERS")) {
						existingStudyPlanEntity.setIsDefault('Y');
					} else {
						existingStudyPlanEntity.setIsDefault('N');
					}
				}

				savedStudyPlanEnt = studyPlanRepository.save(existingStudyPlanEntity);
			}
		}
		StudyPlanTo updatedStudyPlanTo = modelMapper.map(savedStudyPlanEnt, StudyPlanTo.class);
		return updatedStudyPlanTo;

	}

}
