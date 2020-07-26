package com.ephoenix.lmsportal.dto;

import java.util.List;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import com.ephoenix.lmsportal.util.AttributeEncryptor;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserTransactionTO extends BaseTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7401562440220774535L;


	private Long tId;
	
	private String tNumber;
	
	private Long userId;
	
	private Double amount;
	private String status;

}
