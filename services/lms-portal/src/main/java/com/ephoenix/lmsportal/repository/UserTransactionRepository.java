package com.ephoenix.lmsportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ephoenix.lmsportal.entities.UserMaster;
import com.ephoenix.lmsportal.entities.UserTransaction;

public interface UserTransactionRepository extends JpaRepository<UserTransaction, Long>, JpaSpecificationExecutor<UserTransaction> {

	
}