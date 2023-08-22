package com.infoin.app.login.controller;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infoin.app.common.CommonController;
import com.infoin.app.common.ResponseCodeEnum;
import com.infoin.app.common.jwt.JwtTokenProvider;
import com.infoin.app.common.util.spring.SpringBootPropertyUtil;
import com.infoin.app.common.vo.UserVo;
import com.infoin.app.login.vo.LoginParamVo;
import com.infoin.app.login.vo.LoginResVo;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

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
@Api(tags = "login")
@Tag(name = "login", description = "로그인 API")
@RestController
@RequestMapping("/login")
public class LoginController extends CommonController {

	@Operation(summary = "로그인 테스트")
	@PostMapping("/auth")
	public LoginResVo auth(@Valid LoginParamVo loginParamVo, BindingResult bindingResult) {
		logger.info("{}", loginParamVo);

		LoginResVo loginResVo = new LoginResVo();

		if ( bindingResult.hasErrors() ) {
			loginResVo.setCode(ResponseCodeEnum.NO_INPUT.getCode());
			loginResVo.setMessage( (bindingResult.getAllErrors()).get(0).getDefaultMessage() );
			return loginResVo;
		}

		// TODO 테스트용
		if ( "test".equals(loginParamVo.getUserId()) && "1234".equals(loginParamVo.getUserPw()) ) {
			UserVo user = new UserVo();
			user.setUserId("test");
			user.setUserNm("홍길동");
			user.setHpNo("010-1234-5678");

			JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
			String sToken = jwtTokenProvider.generateAccessToken(user);

			loginResVo.setAccessToken(sToken);

			String sAccessTokenExpireMin = SpringBootPropertyUtil.getProperty("jwt.access.expire.minute");
			int nAccessTokenExpireMin = Integer.parseInt(sAccessTokenExpireMin);

			loginResVo.setAccessTokenExpireSecond(nAccessTokenExpireMin * 60);

			String sTokenType = SpringBootPropertyUtil.getProperty("jwt.token.type");
			if ( sTokenType.lastIndexOf(" ") == -1 ) {
				sTokenType = sTokenType + " ";
			}

			loginResVo.setTokenType(sTokenType);

			loginResVo.setUserId(user.getUserId());

			loginResVo.setCode(ResponseCodeEnum.SUCCESS.getCode());
			loginResVo.setMessage(ResponseCodeEnum.SUCCESS.getMessage());
		} else {
			loginResVo.setCode(ResponseCodeEnum.LOGIN_INVALID.getCode());
			loginResVo.setMessage(ResponseCodeEnum.LOGIN_INVALID.getMessage());
		}

		return loginResVo;
	}

}
