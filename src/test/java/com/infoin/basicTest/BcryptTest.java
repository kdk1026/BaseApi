package com.infoin.basicTest;

import org.mindrot.jbcrypt.BCrypt;

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
public class BcryptTest {

	public static void main(String[] args) {
		String sPlanText = "qwer1234";
		String sHashText = BCrypt.hashpw(sPlanText, BCrypt.gensalt());

		System.out.println(sHashText);

		boolean isCheck = BCrypt.checkpw(sPlanText, sHashText);
		System.out.println(isCheck);
	}

}
