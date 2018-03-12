package org.zjw.blog.util;

import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * 密码加密util
 * @author 周家伟
 *
 */
public class CryptographyUtil {


	/**
	 * MD5加密
	 * @param str 要加密的字符串
	 * @param salt 加盐
	 * @return
	 */
	public static String md5(String str,String salt){
		return new Md5Hash(str,salt).toString();
	}
	
	public static void main(String[] args) {
		String password="123456";
		System.out.println(md5(password, "zjw2"));
	}
}
