/**
 * Program  : UpgService.java
 * Author   : dh.shen
 * Create   : 2015-10-26 上午9:58:13
 *
 *
 * Copyright 2014 by Shenzhen JIUZHOU Electronic CO.,LTD,
 * All rights reserved.
 *
 *
 * (文档描述).
 */
package jiuzhou.app.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.util.logging.Logger;

import jiuzhou.app.dao.UpgDao;

/**
 * 网络升级service.
 * @author dh.shen
 * @since
 * @param
 */
public class UpgService {
	private UpgDao upgDao;
	private int stbid;
	private String modelName;
	
	public UpgService(){
		upgDao = new UpgDao();
		validateStatus = false;
	}
	
	private boolean validateStatus;
	

	
	/**
	 * 主调度服务器验证处理.
	 * @author dh.shen
	 * @create 2016-1-13 下午3:58:03
	 * @since
	 * @param 
	 * @return
	 */
	public String mainTask(String mac,String sn,String version){
		String resStr = validateSTB(mac, sn, version);
		if(!validateStatus){
			//如果不需要升级或者认证错误
			return resStr;
		} 
		//需要升级，创建session
		validateStatus = false;
		String codeStr = createSession();
		if(!validateStatus){
			return "ERR_AUTH";
		}
		return resStr+codeStr;
	}
	
	
	/**
	 * 验证sn是否在范围内.
	 * @author dh.shen
	 * @create 2016-1-13 下午3:58:03
	 * @since
	 * @param 
	 * @return
	 */
	public boolean checkSN(String sn,String snstart,String snend) {
		if(null == snstart || null == snend){
			return true;
		}
		System.out.println("UpgService.checkSN()sn:"+sn+" snstart:"+snstart+" snend:"+snend);
		if(sn.compareTo(snstart) < 0) {
			return false;
		}
		if(sn.compareTo(snend) > 0){
			return false;
		}
		return true;
	}
	
	/**
	 * 机顶盒登录验证.
	 * @author dh.shen
	 * @create 2015-10-28 上午10:40:39
	 * @since
	 * @param
	 * @return
	 */
	public String validateSTB(String mac,String sn,String version){
		String resStr = "ERR_AUTH";
		String snStr = "";
		String model = "";
		try {
			Connection dbConn = upgDao.createConnect();
			Statement sql = dbConn.createStatement();
			String sqlStr = "select * from stb where mac=\""+mac+"\"";
			ResultSet rs = sql.executeQuery(sqlStr);
			if(rs.next()){
				stbid = Integer.parseInt(rs.getString(1));
				snStr = rs.getString(3);
				model = rs.getString(4);
				setModelName(model);
				if(snStr.equals(sn)){		//校验序列号
					sqlStr = "select version,hosturl,isforce,issnupt,snstart,snend from model where model=\""+model+"\"";
					rs = sql.executeQuery(sqlStr);
					if(rs.next()){
						float verdb = Float.parseFloat(rs.getString(1));
						float verstb = Float.parseFloat(version);
						int force = rs.getInt(3);  //强制升级标志位
						int issnupt = rs.getInt(4); //sn控制位
						String snStart = rs.getString(5);
						String snEnd = rs.getString(6);
						System.out.println("UpgService.validateSTB():"+force);
						if(1 == issnupt){
							if(!checkSN(snStr,snStart,snEnd)){
								return resStr;
							}
						}
					    if(force == 0) {
					    	
							if(verstb<verdb){	//stb版本号小于db中的版本号则升级
								resStr = rs.getString(2) + ";NORMALUPT";
								setValidateStatus(true);
							} else {
								resStr = "NONEWVERSION";
							}
						} else {
							resStr = rs.getString(2) + ";FORCEUPT" ;
							setValidateStatus(true);
						} 
					}
				}
			}
			dbConn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resStr;
	}

	/**
	 * 创建会话记录.
	 * @author dh.shen
	 * @create 2015-11-26 下午3:56:40
	 * @since
	 * @param
	 * @return 会话ID
	 */
	public String createSession(){
		String resStr = "";
		String dynamicCode = generateDynamicCode();
		try {
			Connection dbConn = upgDao.createConnect();
			Statement sql = dbConn.createStatement();
			long time = System.currentTimeMillis();
			System.out.println(time);
			String sqlStr = "";
			ResultSet rs = null;
			String sessionId = "";
			boolean tag = true;
			while(tag){
				sessionId = generateDynamicCode();
				sqlStr = "select count(*) from session where sessionid=\""+sessionId+"\"";
				rs = sql.executeQuery(sqlStr);
				if(rs.next()){
					if(Integer.valueOf(rs.getString(1))==0){
						tag = false;
						sqlStr = "insert into session(sessionid,lasttime,dynamiccode,model,stbid) values(\'"+
								sessionId+"\',\'"+String.valueOf(time)+"\',\'"+dynamicCode+"\',\'"+modelName+"\',"+stbid+");";
						sql.executeUpdate(sqlStr);
						resStr = ";"+sessionId+";"+dynamicCode+";";
						setValidateStatus(true);
					}
				}
			}
			dbConn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return resStr;
	}
	
	/**
	 * 随机生成16字节的动态校验码.
	 * @author dh.shen
	 * @create 2015-12-31 下午2:27:23
	 * @since
	 * @param
	 * @return
	 */
	public static String generateDynamicCode(){
		String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";  
        Random random = new Random();  
        StringBuffer sb = new StringBuffer();  
        for(int i = 0 ; i < 16; ++i){  
            int number = random.nextInt(62);//[0,62)  
            sb.append(str.charAt(number));  
        }  
        return sb.toString();  
	}
	
	public UpgDao getUpgDao() {
		return upgDao;
	}
	public void setUpgDao(UpgDao upgDao) {
		this.upgDao = upgDao;
	}
	public boolean isValidateStatus() {
		return validateStatus;
	}
	public void setValidateStatus(boolean validateStatus) {
		this.validateStatus = validateStatus;
	}
	public int getStbid() {
		return stbid;
	}
	public void setStbid(int stbid) {
		this.stbid = stbid;
	}
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
}







