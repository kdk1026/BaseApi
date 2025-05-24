package com.kdk.app.login.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kdk.app.common.component.SpringBootProperty;
import com.kdk.app.common.jwt.JwtTokenProvider;
import com.kdk.app.common.vo.ResponseCodeEnum;
import com.kdk.app.common.vo.UserVo;
import com.kdk.app.login.vo.LoginParamVo;
import com.kdk.app.login.vo.LoginResVo;

import io.swagger.v3.oas.annotations.Operation;
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
public class LoginController {

	private SpringBootProperty springBootProperty;

	public LoginController(SpringBootProperty springBootProperty) {
		this.springBootProperty = springBootProperty;
	}

	// requestBody = @RequestBody(content = @Content(mediaType = MediaType.APPLICATION_FORM_URLENCODED_VALUE, schema = @Schema(allOf = {LoginParamVo.class})))
	@Operation(summary = "로그인 테스트")
	@PostMapping("/auth")
	public ResponseEntity<LoginResVo> auth(@Valid LoginParamVo loginParamVo, BindingResult bindingResult,
			HttpServletResponse response) {
		log.info("{}", loginParamVo);

		LoginResVo loginResVo = new LoginResVo();

		if ( bindingResult.hasErrors() ) {
			loginResVo.setCode(ResponseCodeEnum.NO_INPUT.getCode());
			loginResVo.setMessage( (bindingResult.getAllErrors()).get(0).getDefaultMessage() );
			return ResponseEntity.status(HttpStatus.OK).body(loginResVo);
		}

		// XXX 테스트용
		if ( "test".equals(loginParamVo.getUserId()) && "1234".equals(loginParamVo.getUserPw()) ) {
			UserVo user = new UserVo();
			user.setUserId("test");
			user.setUserNm("홍길동");
			user.setHpNo("010-1234-5678");

			JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(springBootProperty);

			String sToken = jwtTokenProvider.generateAccessToken(user);
			loginResVo.setAccessToken(sToken);

			String sAccessTokenExpireMin = springBootProperty.getProperty("jwt.access.expire.minute");
			int nAccessTokenExpireMin = Integer.parseInt(sAccessTokenExpireMin);
			loginResVo.setAccessTokenExpireSecond(nAccessTokenExpireMin * 60);

			String sRefreshToken = jwtTokenProvider.generateRefreshToken(user);
			loginResVo.setRefreshToken(sRefreshToken);

			String sRefreshTokenExpireMin = springBootProperty.getProperty("jwt.refresh.expire.minute");
			int nRefreshTokenExpireMin = Integer.parseInt(sRefreshTokenExpireMin);
			loginResVo.setRefreshTokenExpireSecond(nRefreshTokenExpireMin * 60);

//			CookieUtil.addCookie(response, CommonConstants.Jwt.REFRESH_TOKEN, sRefreshToken, nRefreshTokenExpireMin*60, false, false, null);
//			SpringCookieUtil.getInstance().addCookie(response, CommonConstants.Jwt.REFRESH_TOKEN, sRefreshToken, nRefreshTokenExpireMin*60, false, true, null);

			String sTokenType = springBootProperty.getProperty("jwt.token.type");
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

		return ResponseEntity.status(HttpStatus.OK).body(loginResVo);
	}

}
