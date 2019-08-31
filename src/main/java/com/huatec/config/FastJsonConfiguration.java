package com.huatec.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
@Configuration
public class FastJsonConfiguration implements WebMvcConfigurer {

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
		FastJsonConfig config = new FastJsonConfig();
		config.setSerializerFeatures(
				 // 防止循环引用
				SerializerFeature.DisableCircularReferenceDetect,
				// 空集合返回[],不返回null
				SerializerFeature.WriteNullListAsEmpty,
				 // 空字符串返回"",不返回null
				SerializerFeature.WriteNullStringAsEmpty,
				SerializerFeature.WriteMapNullValue
				);
		converter.setFastJsonConfig(config);
		//处理中文乱码问题
		List<MediaType> mediaTypes = new ArrayList<MediaType>();
		mediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
		converter.setSupportedMediaTypes(mediaTypes);
		converters.add(converter);
//		WebMvcConfigurer.super.configureMessageConverters(converters);
	}
}
