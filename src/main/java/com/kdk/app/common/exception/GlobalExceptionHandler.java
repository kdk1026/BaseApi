package com.kdk.app.common.exception;

import java.io.FileNotFoundException;
import java.nio.file.AccessDeniedException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * -----------------------------------
 * 개정이력
 * -----------------------------------
 * 2024. 9. 10. 김대광	최초작성
 * </pre>
 *
 *
 * @author 김대광
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(ArithmeticException.class)
	public ResponseEntity<String> handleArithmeticException(ArithmeticException e) {
		log.error("", e);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
	}

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException e) {
    	log.error("", e);

    	String sResponseMessage = "Illegal Argument";
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(sResponseMessage);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleIAccessDenied(AccessDeniedException e) {
    	log.error("", e);

    	String sResponseMessage = "File Access Denied";
    	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(sResponseMessage);
    }

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<String> handleFileNotFound(FileNotFoundException e) {
    	log.error("", e);

    	String sResponseMessage = "File Not Found";
    	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(sResponseMessage);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
    	log.error("", e);

    	String sResponseMessage = e.getMessage();
    	String sExceptionMessage = e.getMessage();

    	if ( sExceptionMessage.indexOf("Could not open JDBC Connection") > -1 ) {
    		sResponseMessage = "Could not Database Connection";
    	}

    	if ( sExceptionMessage.indexOf("MailConnectException") > -1 ) {
    		sResponseMessage = "Could not Mail Connection";
    	}

    	if ( sExceptionMessage.indexOf("Unresolved compilation problem") > -1 ) {
    		sResponseMessage = "Unreachable code";
    	}

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(sResponseMessage);
    }


}
