package com.kdk.app.common.interceptor;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.servlet.HandlerInterceptor;

import com.kdk.app.common.util.RequestUtil;
import com.kdk.app.common.util.json.GsonUtil;
import com.kdk.app.common.util.spring.SpringBootPropertyUtil;
import com.kdk.app.common.vo.CommonResVo;
import com.kdk.app.common.vo.ResponseCodeEnum;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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
public class SwaggerInterceptor implements HandlerInterceptor {

	private static final String PRIVATE_IP = "192.168.1";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		String[] sAllowIps = SpringBootPropertyUtil.getProperty("swagger.allow.ip").split(",");
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
