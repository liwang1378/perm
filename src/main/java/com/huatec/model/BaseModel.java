package com.huatec.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.huatec.utils.serialize.JackJsonDateTimeFormat;

import lombok.Data;
@Data
@MappedSuperclass
public class BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;
	private String addBy;
	private String updateBy;
	@JsonSerialize(using=JackJsonDateTimeFormat.class)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date addTime;
	@JsonSerialize(using=JackJsonDateTimeFormat.class)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date updateTime;
}
