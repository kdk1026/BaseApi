package com.kdk.app.test.controller;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.kdk.app.common.util.spring.SseEmitterUtil;
import com.kdk.app.common.vo.CommonResVo;
import com.kdk.app.common.vo.ResponseCodeEnum;

/**
 * <pre>
 * -----------------------------------
 * 개정이력
 * -----------------------------------
 * 2025. 10. 26. 김대광	최초작성
 * </pre>
 *
 *
 * @author 김대광
 */
@RestController
@RequestMapping("/sse")
public class SseController {

	@GetMapping(value = "/subscribe", produces = "text/event-stream")
	public SseEmitter subscribe(String id) {
		return SseEmitterUtil.subscribe(id);
	}

	@PostMapping("/send")
	public ResponseEntity<CommonResVo> send(@ParameterObject String id) {
		CommonResVo resVo = new CommonResVo();

		SseEmitterUtil.sendEvent(id, "notification", "새로운 글을 업로드했습니다!");

		resVo.setCode(ResponseCodeEnum.SUCCESS.getCode());
		resVo.setMessage(ResponseCodeEnum.SUCCESS.getMessage());
		return ResponseEntity.status(HttpStatus.OK).body(resVo);
	}

}
