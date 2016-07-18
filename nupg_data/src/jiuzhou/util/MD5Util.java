/**
 * Program  : MD5Util.java
 * Author   : dh.shen
 * Create   : 2015-10-23 下午5:08:44
 *
 *
 * Copyright 2014 by Shenzhen JIUZHOU Electronic CO.,LTD,
 * All rights reserved.
 *
 *
 * (文档描述).
 */
package jiuzhou.util;

import java.security.MessageDigest;

/**
 * MD5.
 * @author dh.shen
 * @since
 * @param
 */
public class MD5Util {
	
	public static byte[] MD5Encrypt(byte[] ipt){
		MessageDigest md5 = null;
		try{
			md5 = MessageDigest.getInstance("MD5");
		}catch (Exception e){
			e.printStackTrace();
			return null;
		}
		byte[] resbyte = md5.digest(ipt);
		return resbyte;
	}
	
	
	
	/***
	 * MD5加码 生成32位md5码
	 */
	public static String string2MD5(String inStr){
		MessageDigest md5 = null;
		try{
			md5 = MessageDigest.getInstance("MD5");
		}catch (Exception e){
			System.out.println(e.toString());
			e.printStackTrace();
			return "";
		}
		char[] charArray = inStr.toCharArray();
		byte[] byteArray = new byte[charArray.length];

		for (int i = 0; i < charArray.length; i++){
			byteArray[i] = (byte) charArray[i];
		}
		byte[] md5Bytes = md5.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++){
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16){
				hexValue.append("0");
			}
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();

	}
	
	public static void main(String args[]) {
		System.out.print("MD5:");
		byte[] res = MD5Encrypt("hi".getBytes());
		
		System.out.println(CommonUtil.byte2hexStr(res));
		
		//System.out.println(string2MD5("hi"));
	}
}
