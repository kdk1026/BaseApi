package com.infoin.app.login.vo;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
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
@ToString(exclude = "userPw")
public class LoginParamVo {

	@ApiModelProperty(required = true, value = "아이디")
	@NotBlank(message = "아이디는 필수 항목입니다.")
	private String userId;

	@ApiModelProperty(required = true, value = "비밀번호")
	@NotBlank(message = "비밀번호는 필수 항목입니다.")
	private String userPw;

}
