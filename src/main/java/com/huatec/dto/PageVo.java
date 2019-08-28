package com.huatec.dto;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PageVo<T> {

	private Long total;
	private Integer page;
	private List<T> data;
}
