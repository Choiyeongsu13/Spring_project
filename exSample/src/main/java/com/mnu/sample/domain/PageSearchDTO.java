package com.mnu.sample.domain;

import lombok.Data;

@Data
public class PageSearchDTO {
	private int offset;
	private int maxlist;
	private String search;
	private String key;

}
