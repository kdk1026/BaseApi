package com.infoin.app.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

/**
 * <pre>
 * -----------------------------------
 * 개정이력
 * -----------------------------------
 * 2023. 2. 21. kdk	최초작성
 * </pre>
 *
 *
 * @author kdk
 */
public class CommonController extends LogDeclare {

	@Autowired
	private Environment env;

	/**
	 * 현재 프로파일 가져오기
	 * @return
	 */
	public String getActiveProfile() {
		return env.getActiveProfiles()[0];
	}

}
