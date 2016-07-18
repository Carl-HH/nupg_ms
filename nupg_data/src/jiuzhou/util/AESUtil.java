/**
 * Program  : AESUtil.java
 * Author   : dh.shen
 * Create   : 2015-10-23 上午9:19:47
 *
 *
 * Copyright 2014 by Shenzhen JIUZHOU Electronic CO.,LTD,
 * All rights reserved.
 *
 *
 * (文档描述).
 */
package jiuzhou.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES加密解密.
 * @author dh.shen
 * @since
 * @param
 */
public class AESUtil {
	/** 加密算法. */
    private static final String Algorithm = "AES"; //定义 加密算法
    /** 加密算法/加密方式/填充方式. */
    private static final String ALGORITHM = "AES/CBC/NoPadding"; //定义 加密算法
    /** 定义 加密算法/加密方式/填充方式 ZeroBytePadding不足16字节以0填充，网上说加入bcprov.jar即可使用，试验并不可以用. 最终采用Nopadding,无填充，不足16字节的手动补0*/
    //private static final String ALGORITHM = "AES/ECB/ZeroBytePadding"; 
    /** 16字节秘钥. */
    private static final byte[] keyBytes = {0x11, 0x22, 0x4F, 
        0x58, (byte) 0x88, 0x10, 0x40, 0x38, 0x67, 0x59, 0x47, 
        0x26, 0x38, (byte) 0x95, 0x34, 0x35};

    /**
     * 解密.密钥必须是16位的；待解密密内容的长度必须是16的倍数.
     * @author dh.shen
     * @create 2016-1-13 上午9:51:38
     * @since
     * @param content  待解密内容 
     * @param password 解密密钥 
     * @return
     */
    public static byte[] decrypt(byte[] content/*, String password*/) {  
        try {
            SecretKeySpec key = new SecretKeySpec(keyBytes, Algorithm);  
            Cipher cipher = Cipher.getInstance(ALGORITHM);  
            cipher.init(Cipher.DECRYPT_MODE, key,new IvParameterSpec(getIV()));// 初始化   
            
            int blockSize = cipher.getBlockSize(); //16字节
            int plaintextLength = content.length;
            if (plaintextLength % blockSize != 0) { //若不是16的倍数，数组自动补0
                plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
            }
            byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(content, 0, plaintext, 0, content.length);

            byte[] result = cipher.doFinal(plaintext);
            return result;   
        } catch (Exception e) {  
            e.printStackTrace();  
        } 
        return null;  
    }
    
    static byte[] getIV() {  
        //String iv = "1234567812345678"; //IV length: must be 16 bytes long  
        byte[] iv = {0x01, 0x02, 0x03,0x04,0x05, 0x06, 0x07, 0x08, 0x01, 0x02, 0x03,0x04,0x05, 0x06, 0x07, 0x08};
        return iv;
    }  

    
    public static byte[] encrypt(byte[] bt) throws Exception {
        //byte[] bt = baos.toByteArray(); //读取文件转换成字节数组，判断是否是16的倍数，若不是16的倍数，则补0
        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, Algorithm); 
        Cipher cipher = Cipher.getInstance(ALGORITHM); 
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec,new IvParameterSpec(getIV()));
        int blockSize = cipher.getBlockSize(); //16字节
        int plaintextLength = bt.length;
        if (plaintextLength % blockSize != 0) { //若不是16的倍数，数组自动补0
            plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
        }
        byte[] plaintext = new byte[plaintextLength];
        System.arraycopy(bt, 0, plaintext, 0, bt.length);
        byte[] result = cipher.doFinal(plaintext);
        return result;
    }
    
    
    /**
     * AES-CBC加密.
     * @author dh.shen
     * @create 2016-1-13 下午2:16:00
     * @since
     * @param
     * @return
     */
    public static byte[] CBCEncrypt(byte[] key,byte[] txt){
    	 try {
	    	 if(key.length!=16){
	    		 return null;
	    	 }
	    	 SecretKeySpec secretKeySpec = new SecretKeySpec(key,"AES"); 
	         Cipher cipher = Cipher.getInstance(ALGORITHM); 
	         cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec,new IvParameterSpec(getIV()));
	         int blockSize = cipher.getBlockSize(); //16字节
	         int plaintextLength = txt.length;
	         if (plaintextLength % blockSize != 0) { //若不是16的倍数，数组自动补0
	             plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
	         }
	         byte[] plaintext = new byte[plaintextLength];
	         System.arraycopy(txt, 0, plaintext, 0, txt.length);
	         byte[] result = cipher.doFinal(plaintext);
	         return result;
    	 } catch (Exception e) {  
             e.printStackTrace();  
         } 
    	 return null;
    }
    
    /**
     * AES-CBC解密.
     * @author dh.shen
     * @create 2016-1-13 下午2:15:43
     * @since
     * @param
     * @return
     */
    public static byte[] CBCDecrypt(byte[] keybyte,byte[] txt){
    	try {
            SecretKeySpec key = new SecretKeySpec(keybyte, "AES");  
            Cipher cipher = Cipher.getInstance(ALGORITHM);  
            cipher.init(Cipher.DECRYPT_MODE, key,new IvParameterSpec(getIV()));// 初始化   
            int blockSize = cipher.getBlockSize(); //16字节
            int plaintextLength = txt.length;
            if (plaintextLength % blockSize != 0) { //若不是16的倍数，数组自动补0
                plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
            }
            byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(txt, 0, plaintext, 0, txt.length);
            byte[] result = cipher.doFinal(plaintext);
            return result;   
        } catch (Exception e) {  
            e.printStackTrace();  
        } 
        return null;  
    }
    
    
    /**
     * AES-CBC加密.
     * @author dh.shen
     * @create 2016-1-13 下午2:16:00
     * @since
     * @param
     * @return
     */
    public static byte[] AESCBCEncrypt(byte[] key,byte[] iv,byte[] txt){
    	 try {
	    	 if(key.length!=16){
	    		 return null;
	    	 }
	    	 SecretKeySpec secretKeySpec = new SecretKeySpec(key,"AES"); 
	         Cipher cipher = Cipher.getInstance(ALGORITHM); 
	         cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec,new IvParameterSpec(iv));
	         int blockSize = cipher.getBlockSize(); //16字节
	         int plaintextLength = txt.length;
	         if (plaintextLength % blockSize != 0) { //若不是16的倍数，数组自动补0
	             plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
	         }
	         byte[] plaintext = new byte[plaintextLength];
	         System.arraycopy(txt, 0, plaintext, 0, txt.length);
	         byte[] result = cipher.doFinal(plaintext);
	         return result;
    	 } catch (Exception e) {  
             e.printStackTrace();  
         } 
    	 return null;
    }
    
    /**
     * AES-CBC解密.
     * @author dh.shen
     * @create 2016-1-13 下午2:15:43
     * @since
     * @param
     * @return
     */
    public static byte[] AESCBCDecrypt(byte[] keybyte,byte[] iv,byte[] txt){
    	try {
            SecretKeySpec key = new SecretKeySpec(keybyte, "AES");  
            Cipher cipher = Cipher.getInstance(ALGORITHM);  
            cipher.init(Cipher.DECRYPT_MODE, key,new IvParameterSpec(iv));// 初始化   
            int blockSize = cipher.getBlockSize(); //16字节
            int plaintextLength = txt.length;
            if (plaintextLength % blockSize != 0) { //若不是16的倍数，数组自动补0
                plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
            }
            byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(txt, 0, plaintext, 0, txt.length);
            byte[] result = cipher.doFinal(plaintext);
            return result;   
        } catch (Exception e) {  
            e.printStackTrace();  
        } 
        return null;  
    }
    
    public static void main(String[] args) throws Exception {
    	/*byte[] str = "6YVv6YiZ96AQmvcs".getBytes();
    	System.out.println("length:"+str.length);
    	System.out.println("原文:"+CommonUtil.byte2hexStr(str));
    	byte[] encodedStr = AESUtil.CBCEncrypt(keyBytes, str);	//---------------------
    	String hexcoded = CommonUtil.byte2hexStr(encodedStr);
    	System.out.println("密文:"+hexcoded);
    	System.out.println("---------------------------------------------");
    	byte[] decodeStr = AESUtil.CBCDecrypt(keyBytes, encodedStr);
    	System.out.println("解密:"+CommonUtil.byte2hexStr(decodeStr));*/
    	
    	
    	//String str = "http://192.168.162.19:8080/nupg_data/\nsessionid=kjhasdfkjdhaskjghf\ndynamiccode=kjasdhfkjdhask";
    	//System.out.println(str);
    	
    	byte[] str = "6YVv6YiZ96AQmvcs".getBytes();
    	System.out.println("原文:"+CommonUtil.byte2hexStr(str));
    	byte[] encodedStr = AESUtil.CBCEncrypt(CommonUtil.hexStr2bytes("C84AFBD503CD923F27D02C1910EF626B"), str);	//---------------------
    	String hexcoded = CommonUtil.byte2hexStr(encodedStr);
    	System.out.println("密文:"+hexcoded);
    	System.out.println("---------------------------------------------");
    	byte[] decodeStr = AESUtil.CBCDecrypt(CommonUtil.hexStr2bytes("C84AFBD503CD923F27D02C1910EF626B"), encodedStr);
    	System.out.println("解密:"+CommonUtil.byte2hexStr(decodeStr));
    	
	}
    
    
}
