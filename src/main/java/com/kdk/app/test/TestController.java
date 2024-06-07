package com.kdk.app.test;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kdk.app.common.util.spring.ContextUtil;
import com.kdk.app.common.vo.CommonResVo;
import com.kdk.app.common.vo.ResponseCodeEnum;
import com.kdk.app.common.vo.UserVo;

import lombok.extern.slf4j.Slf4j;

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
@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

	@PostMapping("/auth")
	public CommonResVo auth() {
		CommonResVo commonResVo = new CommonResVo();

		UserVo user = (UserVo) ContextUtil.getInstance().getAttrFromRequest("user");
		log.debug("=== {}", user);

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
