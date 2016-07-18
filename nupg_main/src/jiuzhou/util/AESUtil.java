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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.crypto.Cipher;
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
     * 加密,密钥必须是16位的；待加密内容的长度必须是16的倍数.
     * @author yajun.liu
     * @create 2015-2-2 上午10:43:01
     * @since
     * @param content 需要加密的内容 
     * @param password  加密密码
     * @return byte[]
     */
    public static byte[] encrypt(byte[] content/*, String password*/) {  
        try {
            SecretKeySpec key = new SecretKeySpec(keyBytes, Algorithm);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key); //初始化
            
            int blockSize = cipher.getBlockSize(); //16字节
            int plaintextLength = content.length;
            if (plaintextLength % blockSize != 0) { //若不是16的倍数，数组自动补0
                plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
            }
            byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(content, 0, plaintext, 0, content.length);
            
            byte[] result = cipher.doFinal(plaintext);
            return result; // 加密
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 解密.密钥必须是16位的；待解密密内容的长度必须是16的倍数
     * @author yajun.liu
     * @create 2015-2-2 上午10:42:36
     * @since
     * @param content  待解密内容 
     * @param password 解密密钥 
     * @return
     */
    public static byte[] decrypt(byte[] content/*, String password*/) {  
        try {
            SecretKeySpec key = new SecretKeySpec(keyBytes, Algorithm);  
            Cipher cipher = Cipher.getInstance(ALGORITHM);  
            cipher.init(Cipher.DECRYPT_MODE, key);// 初始化   
            
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

    /**
     * 文件加密.
     * @author yajun.liu
     * @create 2015-2-2 上午10:41:52
     * @since
     * @param sourceFilePath 待加密文件路径
     * @param destFilePath 加密后文件路径
     * @return
     */
    public static void encryptFile(String sourceFilePath, String destFilePath) throws Exception {
        File sourceFile = new File(sourceFilePath);
        File destFile = new File(destFilePath); 
        if (sourceFile.exists() && sourceFile.isFile()) {
            if (!destFile.getParentFile().exists()) {
                destFile.getParentFile().mkdirs();
            }
            destFile.createNewFile();
            FileInputStream fis = new FileInputStream(sourceFile);
            FileOutputStream fos = new FileOutputStream(destFile);
            
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                baos.write(b, 0, n);
            }
            fis.close();
            baos.close();
            byte[] bt = baos.toByteArray(); //读取文件转换成字节数组，判断是否是16的倍数，若不是16的倍数，则补0
            
            SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, Algorithm); 
            Cipher cipher = Cipher.getInstance(ALGORITHM); 
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            
            int blockSize = cipher.getBlockSize(); //16字节
            int plaintextLength = bt.length;
            if (plaintextLength % blockSize != 0) { //若不是16的倍数，数组自动补0
                plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
            }
            byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(bt, 0, plaintext, 0, bt.length);
            
            byte[] result = cipher.doFinal(plaintext);

            fos.write(result); //将加密结果写入文件中
            fos.flush();
            fos.close();
        }
    }

    
    public static byte[] encryptString(byte[] bt) throws Exception {
        //byte[] bt = baos.toByteArray(); //读取文件转换成字节数组，判断是否是16的倍数，若不是16的倍数，则补0
        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, Algorithm); 
        Cipher cipher = Cipher.getInstance(ALGORITHM); 
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        
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
     * 文件解密.
     * @author yajun.liu
     * @create 2015-2-2 上午10:41:10
     * @since
     * @param sourceFilePath 带解密文件路径
     * @param destFilePath 解密后文件路径
     * @return
     */
    public static void decryptFile(String sourceFilePath, String destFilePath) throws Exception {
        File sourceFile = new File(sourceFilePath);
        File destFile = new File(destFilePath); 
        if (sourceFile.exists() && sourceFile.isFile()) {
            if (!destFile.getParentFile().exists()) {
                destFile.getParentFile().mkdirs();
            }
            destFile.createNewFile();
            FileInputStream fis = new FileInputStream(sourceFile);
            FileOutputStream fos = new FileOutputStream(destFile);
            
            
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                baos.write(b, 0, n);
            }
            fis.close();
            baos.close();
            byte[] bt = baos.toByteArray(); //读取文件转换成字节数组，判断是否是16的倍数，若不是16的倍数，则补0
            
            SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, Algorithm); 
            Cipher cipher = Cipher.getInstance(ALGORITHM); 
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            
            int blockSize = cipher.getBlockSize(); //16字节
            int plaintextLength = bt.length;
            if (plaintextLength % blockSize != 0) { //若不是16的倍数，数组自动补0
                plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
            }
            byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(bt, 0, plaintext, 0, bt.length);
            
            byte[] result = cipher.doFinal(plaintext);

            fos.write(result); //将解密结果写入文件中
            fos.flush();
            fos.close();
        }
    }
 
    /**
     * 将二进制转换成16进制 .
     * @author yajun.liu
     * @create 2015-2-2 上午10:51:22
     * @since
     * @param buf[] 字节数组
     * @return String
     */
    public static String parseByte2HexStr(byte buf[]) {  
        StringBuffer sb = new StringBuffer();  
        for (int i = 0; i < buf.length; i++) {  
            String hex = Integer.toHexString(buf[i] & 0xFF);  
            if (hex.length() == 1) {  
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }  
        return sb.toString();
    }

    /**
     * 将16进制转换为二进制 .
     * @author yajun.liu
     * @create 2015-2-2 上午10:51:50
     * @since
     * @param hexStr 16进制字符串
     * @return byte[]
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1) {
            return null;  
        }
        byte[] result = new byte[hexStr.length() / 2];  
        for (int i = 0; i < hexStr.length() / 2; i++) {  
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);  
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);  
            result[i] = (byte) (high * 16 + low);  
        }  
        return result;
    }
    
}
