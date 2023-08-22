package com.infoin.app.common.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.HandlerInterceptor;

import com.infoin.app.common.CommonConstants;
import com.infoin.app.common.ResponseCodeEnum;
import com.infoin.app.common.jwt.JwtTokenProvider;
import com.infoin.app.common.util.json.GsonUtil;
import com.infoin.app.common.vo.CommonResVo;
import com.infoin.app.common.vo.UserVo;

import lombok.extern.slf4j.Slf4j;

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
@Slf4j
public class JwtInterceptor implements HandlerInterceptor {

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.HandlerInterceptor#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		String sUri = request.getRequestURI();

		if ( sUri.contains("swagger") || sUri.contains("api-docs") || sUri.contains("webjars") ) {
			return true;
		}

		if ( HttpMethod.OPTIONS.matches(request.getMethod()) ) {
			return true;
		}

		CommonResVo commonResVo = new CommonResVo();

		// ------------------------------------------------------------------------
		// 토큰 가져오기
		// ------------------------------------------------------------------------
		JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();

		String sAccessToken = jwtTokenProvider.getTokenFromReqHeader(request);

		// ------------------------------------------------------------------------
		// 토큰 유효성 검증
		// ------------------------------------------------------------------------
		if ( StringUtils.isBlank(sAccessToken) ) {
			log.error("No AccessToken");

			commonResVo.setCode(ResponseCodeEnum.ACCESS_DENIED.getCode());
			commonResVo.setMessage(ResponseCodeEnum.ACCESS_DENIED.getMessage());
			this.responseJson(response, commonResVo);
			return false;
		}

		int nValid = jwtTokenProvider.isValidateJwtToken(sAccessToken);

		switch (nValid) {
		case 0:
			commonResVo.setCode(ResponseCodeEnum.ACCESS_TOEKN_INVALID.getCode());
			commonResVo.setMessage(ResponseCodeEnum.ACCESS_TOEKN_INVALID.getMessage());
			break;
		case 2:
			commonResVo.setCode(ResponseCodeEnum.ACCESS_TOKEN_EXPIRED.getCode());
			commonResVo.setMessage(ResponseCodeEnum.ACCESS_TOKEN_EXPIRED.getMessage());
			break;

		default:
			break;
		}

		if ( !StringUtils.isBlank(commonResVo.getCode()) ) {
			this.responseJson(response, commonResVo);
			return false;
		}

		// ------------------------------------------------------------------------
		// 토큰에 이상이 없는 경우, access_token인지 검증
		// ------------------------------------------------------------------------
		String sTokenKind = jwtTokenProvider.getTokenKind(sAccessToken);

		if ( !CommonConstants.Jwt.ACCESS_TOKEN.equals(sTokenKind) ) {
			log.error("Is Not AccessToken");

			commonResVo.setCode(ResponseCodeEnum.ACCESS_TOEKN_INVALID.getCode());
			commonResVo.setMessage(ResponseCodeEnum.ACCESS_TOEKN_INVALID.getMessage());
			this.responseJson(response, commonResVo);
			return false;
		}

		// ------------------------------------------------------------------------
		// 토큰에 이상이 없는 경우, 사용자 정보 추출하여 검증
		// ------------------------------------------------------------------------
		UserVo user = jwtTokenProvider.getAuthUserFromJwt(sAccessToken);

		if ( (user == null) || StringUtils.isBlank(user.getUserId()) ) {
			log.error("UserInfo Invalid");

			commonResVo.setCode(ResponseCodeEnum.ACCESS_TOEKN_INVALID.getCode());
			commonResVo.setMessage(ResponseCodeEnum.ACCESS_TOEKN_INVALID.getMessage());
			this.responseJson(response, commonResVo);
			return false;
		}

		// ------------------------------------------------------------------------
		// 편하게 사용하기 위해, request에 담음
		// - 세션이 아니므로 addPathPatterns 대상 URI에서만 가지고 올 수 있음
		// ------------------------------------------------------------------------
		request.setAttribute("user", user);

		return true;
	}

	private void responseJson(HttpServletResponse response, CommonResVo commonResVo) throws IOException {
		String sJson = GsonUtil.ToJson.converterObjToJsonStr(commonResVo);

		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding(CommonConstants.Encoding.UTF_8);
		response.getWriter().write(sJson);
	}

}
