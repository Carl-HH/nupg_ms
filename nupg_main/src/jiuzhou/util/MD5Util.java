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
	private static final String MACSTR = "14SZJZSTBMAC";
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
	
	/**
	 * 转换mac地址.
	 * 说明：将mac地址中的每个字节之间加上固定的字符，形成12个字节
	 *     这些固定字符分别是14 SZ JZ ST BM AC
	 * @author yajun.liu
	 * @create 2014-12-29 上午10:23:41
	 * @since
	 * @param
	 * @return
	 */
	public static String convertMAC(String mac) {
	    String convertMac = "";
	    if (mac.length() != 12) {
            mac = mac.replaceAll(":", "");
            mac = mac.replaceAll("-", "");
        }
	    //System.out.println(mac);
	    if (mac.length() == 12) { //mac为6个字节，01 00 5E 01 01 05
	        StringBuffer sb = new StringBuffer();
	        sb.append(mac.substring(0, 1)); //0
	        sb.append(MACSTR.substring(0, 2));//14
	        sb.append(mac.substring(1, 3));//10
            sb.append(MACSTR.substring(2, 4));//SZ
            sb.append(mac.substring(3, 5));//05
            sb.append(MACSTR.substring(4, 6));//JZ
            sb.append(mac.substring(5, 7));//E0
            sb.append(MACSTR.substring(6, 8));//ST
            sb.append(mac.substring(7, 9));//10
            sb.append(MACSTR.substring(8, 10));//BM
            sb.append(mac.substring(9, 11));//10
            sb.append(MACSTR.substring(10, 12));//AC
            sb.append(mac.substring(11));//5
            
            convertMac = sb.toString();
            System.out.println(convertMac);
	    }
	    return convertMac;
	}

	/**
	 * 加密解密算法 执行一次加密，两次解密
	 */ 
	public static String convertMD5(String inStr){

		char[] a = inStr.toCharArray();
		for (int i = 0; i < a.length; i++){
			a[i] = (char) (a[i] ^ 't');
		}
		String s = new String(a);
		return s;

	}

	public static void main(String args[]) {
		String s = new String("00:15:C0:3C:90:B8");
		
		//String s = new String("00:15:C0:11:96:22");
		System.out.println("原始：" + s);
		System.out.println("convertMac: " + convertMAC(s));
		s = string2MD5(convertMAC(s));
		System.out.println("MD5后：" + s);
		System.out.println("加密的：" + convertMD5(s));
		System.out.println("解密的：" + convertMD5(convertMD5(s)));

	}
}
