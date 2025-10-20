package com.kdk.app.test.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kdk.app.common.util.FileMimeTypeUtil;
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

		UserVo user = (UserVo) ContextUtil.Request.getAttrFromRequest("user");
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

	/**
	 * 동영상은 swagger ui에서 멈춤
	 *
	 * <pre>
	 * view는 미디어 파일 한정
	 * download는 모든 파일 가능
	 * </pre>
	 *
	 * @param mode
	 * @param fileSeq
	 * @return
	 * @throws IOException
	 */
	@GetMapping("/get-media")
	public ResponseEntity<?> getMedia(String mode, int fileSeq) throws IOException {
		String sMediaFileNm = "";

		switch (fileSeq) {
		case 1:
			sMediaFileNm = "istockphoto-1301592082-1024x1024.jpg";
			break;
		case 2:
			sMediaFileNm = "i-miss-the-rage-wav-194890.mp3";
			break;
		case 3:
			sMediaFileNm = "videoplayback.mp4";
			break;

		default:
			break;
		}

		ClassPathResource resource = new ClassPathResource("files/" + sMediaFileNm);
		File file = resource.getFile();
		Path path = file.toPath();

		byte[] byteFile = Files.readAllBytes(path);
		InputStream is = new BufferedInputStream(new FileInputStream(file));

		String sMimeType = FileMimeTypeUtil.getFileMimeTypeTika(is);

		String sFileNm = new String(sMediaFileNm.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);

		if ( "view".equalsIgnoreCase(mode) ) {
			return ResponseEntity.status(HttpStatus.OK)
				.header(HttpHeaders.CONTENT_TYPE, sMimeType)
				.body(byteFile);
		} else if ( "download".equalsIgnoreCase(mode) ) {
			return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + sFileNm + "\"")
				.header(HttpHeaders.CONTENT_TYPE, sMimeType)
				.body(byteFile);
		} else {
			return ResponseEntity.badRequest().body("Invalid mode parameter");
		}
	}

}
