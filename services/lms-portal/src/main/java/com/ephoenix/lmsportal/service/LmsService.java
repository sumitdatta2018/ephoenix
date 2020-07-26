package com.ephoenix.lmsportal.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ephoenix.lmsportal.dto.ClassTo;
import com.ephoenix.lmsportal.dto.DistrictTo;
import com.ephoenix.lmsportal.dto.StateTo;
import com.ephoenix.lmsportal.dto.StudyPlanTypeDto;
import com.ephoenix.lmsportal.dto.SubjectTo;
import com.ephoenix.lmsportal.dto.TypeTo;
import com.ephoenix.lmsportal.dto.UploadTypeTo;
import com.ephoenix.lmsportal.entities.ClassMaster;
import com.ephoenix.lmsportal.entities.DistrictMaster;
import com.ephoenix.lmsportal.entities.StateMaster;
import com.ephoenix.lmsportal.entities.StudyPlanTypeMaster;
import com.ephoenix.lmsportal.entities.SubjectMaster;
import com.ephoenix.lmsportal.entities.TypeMaster;
import com.ephoenix.lmsportal.entities.UploadTypeMaster;
import com.ephoenix.lmsportal.repository.ClassMasterRepository;
import com.ephoenix.lmsportal.repository.DistrictMasterRepository;
import com.ephoenix.lmsportal.repository.StateMasterRepository;
import com.ephoenix.lmsportal.repository.StudyPlanTypeRepository;
import com.ephoenix.lmsportal.repository.SubjectMasterRepository;
import com.ephoenix.lmsportal.repository.TypeMasterRepository;
import com.ephoenix.lmsportal.repository.UploadTypeMasterRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LmsService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private TypeMasterRepository typeMasterRepository;

	@Autowired
	private ClassMasterRepository classMasterRepository;

	@Autowired
	private SubjectMasterRepository subjectMasterRepository;

	@Autowired
	private UploadTypeMasterRepository uploadTypeMasterRepository;

	@Autowired
	private StateMasterRepository stateMasterRepository;

	@Autowired
	private DistrictMasterRepository districtMasterRepository;

	@Autowired
	private StudyPlanTypeRepository studyPlanTypeRepository;

	public Object fetchMasterData(String types) {
		Map<String, Object> masterData = new HashMap<>();

		Arrays.asList(types.split(",")).stream().forEach(type -> {
			switch (type.toUpperCase()) {
			case "CLASS":
				List<ClassTo> classTos = new ArrayList<>();
				List<ClassMaster> classMasterEnts = classMasterRepository.findAll();
				classMasterEnts.stream().forEach(classMasterEnt -> {
					ClassTo classTo = modelMapper.map(classMasterEnt, ClassTo.class);
					classTos.add(classTo);
				});
				masterData.put("CLASS", classTos);
				break;

			case "SUBJECT":
				List<SubjectTo> subjectTos = new ArrayList<>();
				List<SubjectMaster> subjectMasterEnts = subjectMasterRepository.findAll();
				subjectMasterEnts.stream().forEach(subjectMasterEnt -> {
					SubjectTo subjectTo = modelMapper.map(subjectMasterEnt, SubjectTo.class);
					subjectTos.add(subjectTo);
				});
				masterData.put("SUBJECT", subjectTos);
				break;

			case "UPLOAD_TYPE":
				List<UploadTypeTo> uploadTypeTos = new ArrayList<>();
				List<UploadTypeMaster> uploadTypeMasterEnts = uploadTypeMasterRepository.findAll();
				uploadTypeMasterEnts.stream().forEach(uploadTypeMasterEnt -> {
					UploadTypeTo uploadTypeTo = modelMapper.map(uploadTypeMasterEnt, UploadTypeTo.class);
					uploadTypeTos.add(uploadTypeTo);
				});
				masterData.put("UPLOAD_TYPE", uploadTypeTos);
				break;

			case "USER":
				List<TypeTo> typeTos = new ArrayList<>();
				List<TypeMaster> typeMasters = typeMasterRepository.findAll();
				typeMasters.stream().forEach(typeMaster -> {
					TypeTo typeTo = modelMapper.map(typeMaster, TypeTo.class);
					typeTos.add(typeTo);
				});
				masterData.put("USER", typeTos);
				break;

			case "STATE":
				List<StateTo> stateTos = new ArrayList<>();
				List<StateMaster> stateMasters = stateMasterRepository.findAll();
				stateMasters.stream().forEach(stateMaster -> {
					StateTo stateTo = modelMapper.map(stateMaster, StateTo.class);
					stateTos.add(stateTo);
				});
				masterData.put("STATE", stateTos);
				break;

			case "DISTRICT":
				List<DistrictTo> districtTos = new ArrayList<>();
				List<DistrictMaster> districtMasters = districtMasterRepository.findAll();
				districtMasters.stream().forEach(districtMaster -> {
					DistrictTo districtTo = modelMapper.map(districtMaster, DistrictTo.class);
					districtTos.add(districtTo);
				});
				masterData.put("DISTRICT", districtTos);
				break;

			case "STUDY_PLAN_TYPE":
				List<StudyPlanTypeDto> studyPlanTypeDtos = new ArrayList<>();

				List<StudyPlanTypeMaster> studyPlanTypeMasters = studyPlanTypeRepository.findAll();
				studyPlanTypeMasters.stream().forEach(studyPlanTypeMaster -> {
					StudyPlanTypeDto studyPlanTypeDto = modelMapper.map(studyPlanTypeMaster, StudyPlanTypeDto.class);
					studyPlanTypeDtos.add(studyPlanTypeDto);
				});
				masterData.put("STUDY_PLAN_TYPE", studyPlanTypeDtos);
				break;

			default:
				break;
			}
		});

		// TODO Auto-generated method stub
		return masterData;
	}

}
