package com.yzfar.www.base.support.security;

import java.security.Security;

/**
 * 
 * @author ShenHuaJie
 * @version 1.0
 * @since 1.0
 */
public abstract class SecurityCoder {
	private static Byte ADDFLAG = 0;
	static {
		if (ADDFLAG == 0) {
			Security.addProvider(new BouncyCastleProvider());
			ADDFLAG = 1;
		}
	}
}
