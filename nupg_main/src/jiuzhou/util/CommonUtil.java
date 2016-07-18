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
	public static byte[] readFileContents(String filename) throws IOException{
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(filename));     
        ByteArrayOutputStream out = new ByteArrayOutputStream(1024);     
        System.out.println("Available bytes:" + in.available());     
        byte[] temp = new byte[1024];     
        int size = 0;     
        while ((size = in.read(temp)) != -1) {     
            out.write(temp, 0, size);     
        }     
        in.close();     
        byte[] content = out.toByteArray();     
        System.out.println("Readed bytes count:" + content.length);   
        return content;
	}
	
	
	public static void writeFileContents(String path, byte[] content) throws IOException {  
        FileOutputStream fos = new FileOutputStream(path);  
        fos.write(content);  
        fos.close();  
    }  
	
	public static String byte2hex(byte[] src){         
        char[] res = new char[src.length*2];    
        final char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};    
        for(int i=0,j=0; i<src.length; i++){    
            res[j++] = hexDigits[src[i] >>>4 & 0x0f];    
            res[j++] = hexDigits[src[i] & 0x0f];    
        }    
        return new String(res);    
    }   
	
	public static byte[] byteMerger(byte[] byte_1, byte[] byte_2){  
        byte[] byte_3 = new byte[byte_1.length+byte_2.length];  
        System.arraycopy(byte_1, 0, byte_3, 0, byte_1.length);  
        System.arraycopy(byte_2, 0, byte_3, byte_1.length, byte_2.length);  
        return byte_3;  
    }  
}
