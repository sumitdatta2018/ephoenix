package com.ephoenix.lmsportal.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

/**
 * @author mds012
 *
 */
@Getter
@Setter
@Entity
@Table(name = "MST_UPLOAD_TYPE")
public class UploadTypeMaster extends BaseEntity {

	private static final long serialVersionUID = -2108971121222802003L;

	@Id
	@Column(name = "UPLOAD_TYPE_ID")
	private Long uploadTypeId;

	@NotNull
	@Column(name = "UPLOAD_TYPE")
	private String uploadType;

}
