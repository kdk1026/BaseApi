package com.infoin.unitTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.infoin.app.temp.service.TempService;

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
@SpringBootTest
public class TempTest {

	@Autowired
	private TempService tempService;

	@Test
	public void test() {
		System.out.println( tempService.selectNow() );
	}

}
