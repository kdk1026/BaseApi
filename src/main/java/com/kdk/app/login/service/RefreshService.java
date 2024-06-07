package com.kdk.app.login.service;

import com.kdk.app.login.vo.LoginResVo;
import com.kdk.app.login.vo.RefreshParamVo;

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
public interface RefreshService {

	/**
	 * 토큰 갱신 처리
	 * @param refreshParamVo
	 * @return
	 * @throws Exception
	 */
	public LoginResVo refreshToken(RefreshParamVo refreshParamVo) throws Exception;

}
