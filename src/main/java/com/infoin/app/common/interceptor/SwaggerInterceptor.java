package com.infoin.app.common.interceptor;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.web.servlet.HandlerInterceptor;

import com.infoin.app.common.LogDeclare;
import com.infoin.app.common.ResponseCodeEnum;
import com.infoin.app.common.util.RequestUtil;
import com.infoin.app.common.util.json.GsonUtil;
import com.infoin.app.common.vo.CommonResVo;

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
public class SwaggerInterceptor extends LogDeclare implements HandlerInterceptor {

	private static final String PRIVATE_IP = "192.168.1";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		String[] sAllowIps = {"127.0.0.1", "0:0:0:0:0:0:0:1", "175.209.116.226"};
		List<String> listAllowIp = Arrays.asList(sAllowIps);

		String sReqIp = RequestUtil.getRequestIpAddress(request);

		if ( !listAllowIp.contains(sReqIp) || sReqIp.startsWith(PRIVATE_IP) ) {
			CommonResVo commonResVo = new CommonResVo();
			commonResVo.setCode(ResponseCodeEnum.ERROR.getCode());
			commonResVo.setMessage("유효하지 않은 접근입니다.");

			String sJson = GsonUtil.ToJson.converterObjToJsonStr(commonResVo);

			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
			response.getWriter().write(sJson);

			return false;
		}

		return true;
	}

}
