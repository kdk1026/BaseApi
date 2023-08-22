package com.infoin.app.test;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infoin.app.common.CommonController;
import com.infoin.app.common.ResponseCodeEnum;
import com.infoin.app.common.util.spring.ContextUtil;
import com.infoin.app.common.vo.CommonResVo;
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
@RestController
@RequestMapping("/test")
public class TestController extends CommonController {

	@PostMapping("/auth")
	public CommonResVo auth() {
		CommonResVo commonResVo = new CommonResVo();

		UserVo user = (UserVo) ContextUtil.getInstance().getAttrFromRequest("user");
		logger.debug("=== {}", user);

		commonResVo.setCode(ResponseCodeEnum.SUCCESS.getCode());
		commonResVo.setMessage(ResponseCodeEnum.SUCCESS.getMessage());
		return commonResVo;
	}

	@PostMapping("/exAuth")
	public CommonResVo exAuth() {
		CommonResVo commonResVo = new CommonResVo();

		commonResVo.setCode(ResponseCodeEnum.SUCCESS.getCode());
		commonResVo.setMessage(ResponseCodeEnum.SUCCESS.getMessage());
		return commonResVo;
	}

}
