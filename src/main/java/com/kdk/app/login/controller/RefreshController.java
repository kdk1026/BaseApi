package com.kdk.app.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kdk.app.common.vo.ResponseCodeEnum;
import com.kdk.app.login.service.RefreshService;
import com.kdk.app.login.vo.LoginResVo;
import com.kdk.app.login.vo.RefreshParamVo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
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
@Tag(name = "login", description = "로그인 API")
@RestController
@RequestMapping("/login")
public class RefreshController {

	@Autowired
	private RefreshService refreshService;

	@Operation(summary = "토큰 갱신 처리", requestBody = @RequestBody(content = @Content(mediaType = MediaType.APPLICATION_FORM_URLENCODED_VALUE, schema = @Schema(allOf = {RefreshParamVo.class}))))
	@PostMapping("/token")
	public LoginResVo token(@Valid RefreshParamVo refreshParamVo, BindingResult bindingResult, HttpServletResponse response) {
		log.info("{}", refreshParamVo);

		LoginResVo loginResVo = new LoginResVo();

		if ( bindingResult.hasErrors() ) {
			loginResVo.setCode(ResponseCodeEnum.NO_INPUT.getCode());
			loginResVo.setMessage( (bindingResult.getAllErrors()).get(0).getDefaultMessage() );
			return loginResVo;
		}

		try {
			loginResVo = refreshService.refreshToken(refreshParamVo);

		} catch (Exception e) {
			log.error("", e);

			loginResVo.setCode(ResponseCodeEnum.ERROR.getCode());
			loginResVo.setMessage(ResponseCodeEnum.ERROR.getMessage());
		}

		return loginResVo;
	}

}
