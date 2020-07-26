package com.ephoenix.lmsportal.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import com.ephoenix.lmsportal.dto.UserPreamiumTO;
import com.ephoenix.lmsportal.dto.UserTransactionTO;
import com.ephoenix.lmsportal.entities.UserMaster;
import com.ephoenix.lmsportal.entities.UserStudyMap;
import com.ephoenix.lmsportal.entities.UserTransaction;
import com.ephoenix.lmsportal.excp.LMSPortalException;
import com.ephoenix.lmsportal.generic.code.ActiveConstants;
import com.ephoenix.lmsportal.repository.UserMasterRepository;
import com.ephoenix.lmsportal.repository.StudyPlanMapRepository;
import com.ephoenix.lmsportal.repository.UserTransactionRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TransactionService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private StudyPlanMapRepository userPreamiumRepository;

	@Autowired
	private UserTransactionRepository userTransactionRepository;

	@Autowired
	private UserMasterRepository userMasterRepository;

	public UserTransactionTO savePreamiumTxn(MultiValueMap<String, Object> formParameters) {
		// TODO Auto-generated method stub

		log.info("formParameters---{}", formParameters);
		List<UserPreamiumTO> userPreamiumTOList = new ArrayList<UserPreamiumTO>();
		UserTransactionTO userTransactionTO = new UserTransactionTO();
		
		log.info("amount---{}", formParameters.get("amount"));
		String amount = formParameters.get("amount").toString();
		amount = amount.trim().substring(1, amount.length()-1).trim();
		log.info("amount--final-{}", amount);
		userTransactionTO.setAmount(Double.valueOf(amount));
		
		log.info("payuMoneyId---{}", formParameters.get("payuMoneyId"));
		String payuMoneyId = formParameters.get("payuMoneyId").toString();
		payuMoneyId = payuMoneyId.trim().substring(1, payuMoneyId.length()-1).trim();
		log.info("payuMoneyId--final-{}", payuMoneyId);
		userTransactionTO.setTNumber(payuMoneyId);
		
		log.info("udf1---{}", formParameters.get("udf1"));
		String udf1 = formParameters.get("udf1").toString();
		udf1 = udf1.trim().substring(1, udf1.length()-1).trim();
		log.info("udf1--final-{}", udf1);
		String userLoginId = udf1;
		UserMaster userMaster = userMasterRepository.findByUserLoginIdIgnoreCaseAndIsActive(userLoginId,
				ActiveConstants.ACTIVE.getIsActive());
		
		if (userMaster == null) {
			throw new LMSPortalException("user not found");
		}
		
		log.info("status---{}", formParameters.get("status"));
		String status = formParameters.get("status").toString();
		status = status.trim().substring(1, status.length()-1).trim();
		log.info("status--final-{}", status);
		userTransactionTO.setStatus(status);
		userTransactionTO.setUserId(userMaster.getId());

		UserTransaction userTransaction = new UserTransaction();
		userTransaction.setUserId(userTransactionTO.getUserId());
		userTransaction.setAmount(userTransactionTO.getAmount());
		userTransaction.setTNumber(userTransactionTO.getTNumber());
		userTransaction.setStatus(userTransactionTO.getStatus());
		UserTransaction saveuserTransaction = userTransactionRepository.save(userTransaction);
		userTransactionTO = modelMapper.map(saveuserTransaction, UserTransactionTO.class);

		log.info("udf2---{}", formParameters.get("udf2"));
		String udf2 = formParameters.get("udf2").toString();
		udf2 = udf2.trim().substring(1, udf2.length()-1).trim();
		log.info("udf2--final-{}", udf2);
		
		List<String> studyIdList = Arrays.asList(udf2.split(","));
		studyIdList.stream().forEach(studyId -> {
			UserPreamiumTO userPreamiumTO = new UserPreamiumTO();
			userPreamiumTO.setUserId(userMaster.getId());
			userPreamiumTO.setStudyPlanId(Long.valueOf(studyId));
			userPreamiumTO.setTId(saveuserTransaction.getTId());
			userPreamiumTOList.add(userPreamiumTO);
		});
		for (UserPreamiumTO userPreamiumTO : userPreamiumTOList) {
			UserStudyMap userPreamium = new UserStudyMap();
			userPreamium.setUserId(userPreamiumTO.getUserId());
			userPreamium.setStudyPlanId(userPreamiumTO.getStudyPlanId());
			userPreamium.setTId(saveuserTransaction.getTId());
			userPreamiumRepository.save(userPreamium);
		}
		return userTransactionTO;
	}

}
