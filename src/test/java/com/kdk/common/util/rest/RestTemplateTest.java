package com.kdk.common.util.rest;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.kdk.app.common.util.spring.RestTemplateUtil;

public class RestTemplateTest {

	@SuppressWarnings("unchecked")
	@Test
	public void test() {
		final String BASE_URL = "https://jsonplaceholder.typicode.com/posts";

		String getUrl = BASE_URL + "/1";
		Map<String, Object> getMap = null;

		ResponseEntity<Object> getRes = RestTemplateUtil.get(false, getUrl, MediaType.APPLICATION_JSON, null, Map.class);
		if ( HttpStatus.OK == getRes.getStatusCode() ) {
			getMap = (Map<String, Object>) getRes.getBody();
		}
		System.out.println(getMap);
	}

}
