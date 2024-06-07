package com.kdk.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kdk.app.common.interceptor.JwtInterceptor;
import com.kdk.app.common.interceptor.SwaggerInterceptor;
import com.kdk.config.mvc.HTMLCharacterEscapes;

/**
 * <pre>
 * -----------------------------------
 * 개정이력
 * -----------------------------------
 * 2024. 6. 7. kdk	최초작성
 * </pre>
 *
 *
 * @author kdk
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry
			.addMapping("/**")
			.allowedOrigins("*")
			.allowedMethods("GET", "POST", "PUT", "DELETE")
			.allowedHeaders("*")
			.maxAge(3600);
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addRedirectViewController("/", "/swagger-ui/index.html");
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor( new SwaggerInterceptor() )
			.addPathPatterns("/swagger-ui/index.html");

		registry.addInterceptor( new JwtInterceptor() )
			.addPathPatterns("/**")
			.excludePathPatterns("/", "/test/exAuth", "/login/**");
	}

	@Bean
	public MappingJackson2HttpMessageConverter jsonEscapeConverter() {
		ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json().build();
		objectMapper.getFactory().setCharacterEscapes(new HTMLCharacterEscapes());
		return new MappingJackson2HttpMessageConverter(objectMapper);
	}

}
