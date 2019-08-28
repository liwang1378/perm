package com.huatec.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class QryRo {
	
	private Integer page = 0;
	private Integer size = 10;

}
