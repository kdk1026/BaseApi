package com.kdk.app.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * <pre>
 * -----------------------------------
 * 개정이력
 * -----------------------------------
 * 2024. 11. 12. 김대광	최초작성
 * </pre>
 *
 *
 * @author 김대광
 */
@ControllerAdvice
public class GlobalErrorController {

//	@Value("${front.url}")
//	private String frontUrl;

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public String handleNotFound(NoHandlerFoundException ex, Model model) {
//    	model.addAttribute("frontUrl", frontUrl);
        return "error/404";
    }

}
