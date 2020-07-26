package com.ephoenix.lmsportal.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;


/**
 * 
 * @author mds012
 *
 */
@Getter
@Setter
@Entity
@Table(name = "PASSWORD_HISTORY")
public class PasswordHistory extends BaseEntity {

	private static final long serialVersionUID = 3343235801792370962L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PASSWORD_HIST_ID")
	private Long passwordHistId;

	@Column(name = "PASSWORD")
	private String password;

	@Column(name = "USER_ID")
	private Long userId;

}
