package com.ephoenix.lmsportal.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.ephoenix.lmsportal.client.KeycloakInvoker;
import com.ephoenix.lmsportal.dto.UserFeedbackTo;
import com.ephoenix.lmsportal.dto.UserTO;
import com.ephoenix.lmsportal.entities.RoleMaster;
import com.ephoenix.lmsportal.entities.RoleUserMap;
import com.ephoenix.lmsportal.entities.TypeMaster;
import com.ephoenix.lmsportal.entities.UserFeedback;
import com.ephoenix.lmsportal.entities.UserMaster;
import com.ephoenix.lmsportal.excp.LMSPortalException;
import com.ephoenix.lmsportal.generic.code.ActiveConstants;
import com.ephoenix.lmsportal.generic.code.ErrorCode;
import com.ephoenix.lmsportal.generic.code.ICommonConstants;
import com.ephoenix.lmsportal.repository.RoleMasterRepository;
import com.ephoenix.lmsportal.repository.RoleUserMapRepository;
import com.ephoenix.lmsportal.repository.TypeMasterRepository;
import com.ephoenix.lmsportal.repository.UserFeedbackRepository;
import com.ephoenix.lmsportal.repository.UserMasterRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserFeedbackService {

	@Autowired
	private UserFeedbackRepository userFeedbackRepository;
	@Autowired
	private ModelMapper modelMapper;

	public UserFeedbackTo createFeedback(UserFeedbackTo userFeedbackTo) {

		UserFeedback saveduserFeedback = userFeedbackRepository.findByCreatedByAndIsActive(userFeedbackTo.getUserId(),
				ActiveConstants.ACTIVE.getIsActive());
		if (saveduserFeedback == null) {
			UserFeedback userFeedback = modelMapper.map(userFeedbackTo, UserFeedback.class);
			userFeedback.setCreatedBy(userFeedbackTo.getUserId());

			saveduserFeedback = userFeedbackRepository.save(userFeedback);
		} else {
			saveduserFeedback.setRating(userFeedbackTo.getRating());
			saveduserFeedback.setSuggestion(userFeedbackTo.getSuggestion());
			saveduserFeedback.setDescription(userFeedbackTo.getDescription());
			saveduserFeedback = userFeedbackRepository.save(saveduserFeedback);

		}
		UserFeedbackTo savedUserFeedbackTo = modelMapper.map(saveduserFeedback, UserFeedbackTo.class);
		return savedUserFeedbackTo;
	}

	public UserFeedbackTo getFeedbackByUserId(Long userId) {
		UserFeedback saveduserFeedback = userFeedbackRepository.findByCreatedByAndIsActive(userId,
				ActiveConstants.ACTIVE.getIsActive());
		if (saveduserFeedback == null) {
			return new UserFeedbackTo();
		}

		UserFeedbackTo savedUserFeedbackTo = modelMapper.map(saveduserFeedback, UserFeedbackTo.class);
		return savedUserFeedbackTo;
	}

	public List<UserFeedbackTo> getAllfeedback(Long rating) {
		List<UserFeedbackTo> userFeedbackTos = new ArrayList<>();
		List<UserFeedback> userFeedbacks = new ArrayList<>();
		if (rating == null) {
			 userFeedbacks = userFeedbackRepository
					.findByIsActive(ActiveConstants.ACTIVE.getIsActive());
		}
		else {
			userFeedbacks = userFeedbackRepository
					.findByRatingAndIsActive(rating,ActiveConstants.ACTIVE.getIsActive());
		}
		userFeedbacks.stream().forEach(userFeedback ->{
			UserFeedbackTo userFeedbackTo = modelMapper.map(userFeedback, UserFeedbackTo.class);
			userFeedbackTos.add(userFeedbackTo);
		});
		
		return userFeedbackTos;
	}

}
