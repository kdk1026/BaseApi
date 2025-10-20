package com.kdk.app.test.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kdk.app.common.vo.CommonResVo;
import com.kdk.app.common.vo.ResponseCodeEnum;
import com.kdk.app.test.vo.ValidParamVo;

import jakarta.validation.Valid;

/**
 * <pre>
 * -----------------------------------
 * 개정이력
 * -----------------------------------
 * 2025. 10. 20. 김대광	최초작성
 * </pre>
 *
 *
 * @author 김대광
 */
@RestController
@RequestMapping("/valid")
public class ValidController {

	// ArrayList도 체크 가능
	@PostMapping
	public ResponseEntity<CommonResVo> valid(@Valid @RequestBody ValidParamVo paramVo, BindingResult bindingResult) {
		CommonResVo resVo = new CommonResVo();

		if ( bindingResult.hasErrors() ) {
			resVo.setCode(ResponseCodeEnum.NO_INPUT.getCode());
			resVo.setMessage( (bindingResult.getAllErrors()).get(0).getDefaultMessage() );
			return ResponseEntity.status(HttpStatus.OK).body(resVo);
		}

		resVo.setCode(ResponseCodeEnum.SUCCESS.getCode());
		resVo.setMessage(ResponseCodeEnum.SUCCESS.getMessage());
		return ResponseEntity.status(HttpStatus.OK).body(resVo);
	}

}
