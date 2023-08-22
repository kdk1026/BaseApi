package com.infoin.app.common;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import com.infoin.app.common.util.spring.ContextUtil;
import com.infoin.app.common.vo.UserVo;

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
public class CommonService extends LogDeclare {

	@Autowired
	private Environment env;

	/**
	 * 현재 프로파일 가져오기
	 * @return
	 */
	public String getActiveProfile() {
		return env.getActiveProfiles()[0];
	}

	/**
	 * 사용자 정보 가져오기
	 * @return
	 */
	public UserVo getUserInfo() {
		HttpServletRequest request = ContextUtil.getInstance().getRequest();
		return (UserVo) request.getAttribute("user");
	}

}
