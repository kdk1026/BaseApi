package com.infoin.app.login.vo;

import com.infoin.app.common.vo.CommonResVo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
@Getter
@Setter
@ToString
public class LoginResVo extends CommonResVo {

	@Schema(description = "토큰 타입", required = true)
	private String tokenType;

	@Schema(description = "접근 토큰", required = true)
	private String accessToken;

	@Schema(description = "접근 토큰 만료시간", required = true)
	private int accessTokenExpireSecond;

	@Schema(description = "아이디", required = true)
	private String userId;

}
