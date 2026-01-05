package com.kdk.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kdk.app.common.component.SpringBootProperty;
import com.kdk.app.common.interceptor.JwtWebInterceptor;
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
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {

	@Value("${upload.folder}")
	private String uploadFolder;

	@Value("${cors.origins}")
	private String corsOrigins;

	private final SpringBootProperty springBootProperty;
	private final Environment env;

	public WebMvcConfig(SpringBootProperty springBootProperty, Environment env) {
		this.springBootProperty = springBootProperty;
		this.env = env;
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		/*
		 * allowCredentials(true) 쿠키 인증 요청 허용
		 * allowedOrigins("*") 와 allowCredentials(true) 동시 사용 불가
		 */
		registry
			.addMapping("/**")
			.allowedOriginPatterns(corsOrigins)
			.allowedMethods("GET", "POST", "PUT", "DELETE")
			.allowedHeaders("*")
			.exposedHeaders("Content-Disposition")
			.allowCredentials(true)
			.maxAge(3600);
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addRedirectViewController("/", "/swagger-ui/index.html");
	}

	@Bean
	JwtWebInterceptor jwtWebInterceptor() {
		return new JwtWebInterceptor(springBootProperty, env);
	};

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor( new SwaggerInterceptor(springBootProperty) )
			.addPathPatterns("/swagger-ui/index.html");

		registry.addInterceptor( this.jwtWebInterceptor() )
			.addPathPatterns("/**")
			.excludePathPatterns("/", "/test/exAuth", "/login/**", "/test/get-media", "/upload/**", "/capsule-config/**", "/valid/**", "/sse/**", "/sample/**");
	}

	@Bean
    MappingJackson2HttpMessageConverter jsonEscapeConverter() {
		ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json().build();
		objectMapper.getFactory().setCharacterEscapes(new HTMLCharacterEscapes());
		return new MappingJackson2HttpMessageConverter(objectMapper);
	}

	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.removeIf(MappingJackson2HttpMessageConverter.class::isInstance);
        HttpMessageConverter<?> httpMessageConverter = this.jsonEscapeConverter();
        converters.add(httpMessageConverter);
	}

	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		configurer
			.ignoreAcceptHeader(true)
			.defaultContentType(MediaType.APPLICATION_JSON);
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry
			.addResourceHandler("/upload/**")
			.addResourceLocations(uploadFolder);
	}

}
