package com.kdk.app.common.filter;

import java.io.IOException;

import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;

/**
 * <pre>
 * -----------------------------------
 * 개정이력
 * -----------------------------------
 * 2025. 9. 6. 김대광	최초작성
 * </pre>
 *
 *
 * @author 김대광
 */
@Component
public class SecurityHeaderFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;

		/** Cache Control */
		httpServletResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		httpServletResponse.setHeader("Pragma", "no-cache");
		httpServletResponse.setHeader("Expires", "0");

		/** Content Type Options */
		httpServletResponse.setHeader("X-Content-Type-Options", "nosniff");

		/** HTTP Strict Transport Security (HSTS)
		 * 		- HTTPS 에서만 동작 (HTTP 에서는 무시)
		 * */
		httpServletResponse.setHeader("Strict-Transport-Security", "max-age=31536000; includeSubDomains");

		/**	X-Frame-Options
		 * 		- DENY, SAMEORIGIN, ALLO-FROM origin
		 * */
		httpServletResponse.addHeader("X-Frame-Options", "SAMEORIGIN");

		/** X-XSS-Protection */
		httpServletResponse.setHeader("X-XSS-Protection", "1; mode=block");

		chain.doFilter(request, response);
	}

}
