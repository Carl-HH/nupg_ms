/**
 * Program  : JsonUtil.java
 * Author   : dh.shen
 * Create   : 2015-7-27 下午2:36:12
 *
 *
 * Copyright 2014 by Shenzhen JIUZHOU Electronic CO.,LTD,
 * All rights reserved.
 *
 *
 * (文档描述).
 */
package jiuzhou.util;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * @author ruanxf
 * @date 2011-8-6 上午11:39:55
 * @version V1.0
 */

public class JsonUtil {
	
	private static ObjectMapper om = null;
	private Logger logger = Logger.getLogger(this.getClass().getName());
	static{
		om = new ObjectMapper();
	}
	
	/**
	 * 
	 * @author ruanxf
	 * @date 2011-8-11 下午4:37:52
	 * @version V1.0
	 * @param object
	 * @return    
	 * @return String
	 */
	public static String toJsonString(Object object){
		try {
			return om.writeValueAsString(object);	
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("json error!",e);
		} 
	}
	/**
	 * 
	 * @author ruanxf
	 * @date 2011-8-11 下午4:37:59
	 * @version V1.0
	 * @return    
	 * @return ObjectMapper
	 */
	public static ObjectMapper getObjectMapper(){
		return om;
	}
	
	/**
	 * 
	 * @author ruanxf
	 * @date 2011-8-11 下午4:37:56
	 * @version V1.0
	 * @param json
	 * @param clazz
	 * @return    
	 * @return T
	 */
	public static <T> T toObjectByJson(String json,Class<T> clazz) {
		try {
			return om.readValue(json, clazz);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} 
	}
}

