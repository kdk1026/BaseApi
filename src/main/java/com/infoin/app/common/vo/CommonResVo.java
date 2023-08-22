package com.infoin.app.common.vo;

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
public class CommonResVo {

	@Schema(description = "응답 코드", required = true)
	private String code;

	@Schema(description = "응답 메시지", required = true)
	private String message;

}
