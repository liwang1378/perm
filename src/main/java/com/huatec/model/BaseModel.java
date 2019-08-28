package com.huatec.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.alibaba.fastjson.annotation.JSONField;
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
//	@Temporal(TemporalType.TIMESTAMP)
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date addTime;
	@JsonSerialize(using=JackJsonDateTimeFormat.class)
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")//使用 fastjson 转换 Date 格式的数据，默认是时间戳，更改为 年-月-日 
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date updateTime;
}
