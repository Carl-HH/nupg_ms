/**
 * Program  : CommonUtil.java
 * Author   : dh.shen
 * Create   : 2015-10-23 上午9:28:04
 *
 *
 * Copyright 2014 by Shenzhen JIUZHOU Electronic CO.,LTD,
 * All rights reserved.
 *
 *
 * (文档描述).
 */
package jiuzhou.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 通用工具方法.
 * @author dh.shen
 * @since
 * @param
 */
public class CommonUtil {
	
	/**
	 * 从文件读取数据到byte[].
	 * @author dh.shen
	 * @create 2016-1-13 下午2:20:03
	 * @since
	 * @param
	 * @return
	 */
	public static byte[] readFileContents(String filename) throws IOException{
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(filename));     
        ByteArrayOutputStream out = new ByteArrayOutputStream(1024);     
        //System.out.println("Available bytes:" + in.available());     
        byte[] temp = new byte[1024];     
        int size = 0;     
        while ((size = in.read(temp)) != -1) {     
            out.write(temp, 0, size);     
        }     
        in.close();     
        byte[] content = out.toByteArray();     
        //System.out.println("Readed bytes count:" + content.length);   
        return content;
	}
	
	/**
	 * 将byte[]的数据写入到文件.
	 * @author dh.shen
	 * @create 2016-1-13 下午2:20:18
	 * @since
	 * @param
	 * @return
	 */
	public static void writeFileContents(String path, byte[] content) throws IOException {  
        FileOutputStream fos = new FileOutputStream(path);  
        fos.write(content);  
        fos.close();  
    }  
	
	/**
	 * 将byte[]转换成十六进制字符串.
	 * @author dh.shen
	 * @create 2016-1-13 下午2:19:34
	 * @since
	 * @param
	 * @return
	 */
	/*public static String byte2hex(byte[] src){         
        char[] res = new char[src.length*2];    
        final char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};    
        for(int i=0,j=0; i<src.length; i++){    
            res[j++] = hexDigits[src[i] >>>4 & 0x0f];    
            res[j++] = hexDigits[src[i] & 0x0f];    
        }    
        return new String(res);    
    }   */
	
	/**
	 * 合并两个byte[].
	 * @author dh.shen
	 * @create 2016-1-13 下午2:19:19
	 * @since
	 * @param
	 * @return
	 */
	public static byte[] byteMerger(byte[] byte_1, byte[] byte_2){  
        byte[] byte_3 = new byte[byte_1.length+byte_2.length];  
        System.arraycopy(byte_1, 0, byte_3, 0, byte_1.length);  
        System.arraycopy(byte_2, 0, byte_3, byte_1.length, byte_2.length);  
        return byte_3;  
    }  
	
	
	/**
     * 将byte[]转换成16进制.
     * @author dh.shen
     * @create 2016-1-13 上午9:12:15
     * @since
     * @param
     * @return
     */
    public static String byte2hexStr(byte buf[]) {  
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
     * 将16进制转换为二进制.
     * @author dh.shen
     * @create 2016-1-13 上午9:11:47
     * @since
     * @param
     * @return
     */
    public static byte[] hexStr2bytes(String hexStr) {
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
	
    public static boolean getOSType(){
		String os_name = System.getProperties().get("os.name").toString().toLowerCase();
        if(os_name.indexOf("windows")!=-1){
            return true;
        }else if(os_name.indexOf("linux")!=-1){
            return false;
        }
        return false;
	}
    
    public static void main(String[] args) {
		System.out.println(getOSType());
	}
	
}
