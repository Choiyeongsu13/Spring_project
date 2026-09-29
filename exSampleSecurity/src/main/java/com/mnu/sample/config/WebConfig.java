package com.mnu.sample.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.util.pattern.PathPatternParser;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	//URL 대소문자 구분 안함 (/join/user_insert 로 접속해도 /Join/user_insert 매핑으로 연결)
	@Override
	public void configurePathMatch(PathMatchConfigurer configurer) {
		PathPatternParser parser = new PathPatternParser();
		parser.setCaseSensitive(false);
		configurer.setPatternParser(parser);
	}
}
