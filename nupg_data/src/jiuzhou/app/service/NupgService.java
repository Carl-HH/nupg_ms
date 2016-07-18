/**
 * Program  : NupgService.java
 * Author   : dh.shen
 * Create   : 2015-11-26 上午11:17:58
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
import java.text.SimpleDateFormat;
import java.util.Calendar;

import jiuzhou.app.dao.UpgDao;
import jiuzhou.util.AESUtil;
import jiuzhou.util.CommonUtil;

/**
 * service.
 * @author dh.shen
 * @since
 * @param
 */
public class NupgService {

	private UpgDao upgDao;
	
	private String modelName;
	
	private String dynamiccode;
	
	static long SessionPeriod = 1800000;	 //session有效期为30分钟
	public NupgService() {
		upgDao = new UpgDao();
	}
	
	
	/**
	 * 验证数据库中有无此型号.
	 * @author dh.shen
	 * @create 2015-12-31 下午2:24:14
	 * @since
	 * @param
	 * @return
	 */
	public boolean validateModel(String model){
		try {
			Connection dbConn = upgDao.createConnect();
			Statement sql = dbConn.createStatement();
			String sqlStr = "select count(*) from model where model=\'"+model+"\'";
			ResultSet rs = sql.executeQuery(sqlStr);
			if(rs.next()){
				if(rs.getString(1).equals("1")){
					dbConn.close();
					return true;
				}
			}
			dbConn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return false;
	}
	
	/**
	 * 第一步校验session以及动态码是否有效.
	 * @author dh.shen
	 * @create 2016-1-4 上午9:42:55
	 * @since
	 * @param
	 * @return
	 * @throws Exception 
	 */
	public boolean validateSessionCode(String sessionId,String code,String datapath){
		boolean tag = false;
		try {
			Connection dbConn = upgDao.createConnect();
			Statement sql = dbConn.createStatement();
			long nowtime = System.currentTimeMillis();
			String sqlStr = "";
			ResultSet rs = null;
			sqlStr = "select * from session where sessionid=\'"+sessionId+"\'";
			rs = sql.executeQuery(sqlStr);
			if(rs.next()){
				//检验session是否还在有效期
				long value =  nowtime-Long.parseLong(rs.getString(3));
				int status = Integer.parseInt(rs.getString(5));
				if(status==0){
					if(value>0 && value<SessionPeriod){
						//session有效
						setModelName(rs.getString(6));
						//加密动态码与code比较
						String dynamiccode = rs.getString(4);
						String hexcoded;
						boolean ostype = CommonUtil.getOSType();
						String ivpath = datapath+"\\"+modelName+"\\aes128.IV";
						String keypath = datapath+"\\"+modelName+"\\aes128.key";
						if(!ostype){
							ivpath = datapath + "/"+modelName+"/aes128.IV";
							keypath = datapath+"/"+modelName+"/aes128.key";
						}
						
						byte[] aeskey = CommonUtil.readFileContents(keypath);
						byte[] aesiv = CommonUtil.readFileContents(ivpath);
						if((aeskey.length==16)&&(aesiv.length==16)){
							//hexcoded = CommonUtil.byte2hexStr(AESUtil.encrypt(dynamiccode.getBytes()));
							hexcoded = CommonUtil.byte2hexStr(AESUtil.AESCBCEncrypt(aeskey, aesiv, dynamiccode.getBytes()));
							System.out.println("Encrypted dynamiccode:"+hexcoded);
							if(code.equals(hexcoded)){
								//将session状态设置为已验证
								sqlStr = "update session set sessionstatus=1,lasttime="+nowtime+" where sessionid=\""+sessionId+"\"";
								sql.executeUpdate(sqlStr);
								tag = true;
							}
						}else{
							System.out.println("Encrypt key err!");
						}
					}else{
						//session过期,删除过期的session
						sqlStr = "delete from session where sessionid=\""+sessionId+"\"";
						sql.executeUpdate(sqlStr);
					}
				}
			}
			dbConn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tag;
	}
	
	/**
	 * 机顶盒与数据服务器交互第一步处理.
	 * @author dh.shen
	 * @create 2015-12-31 下午3:01:37
	 * @since
	 * @param
	 * @return	
	 */
	public boolean FirstStepFunc(String sessionid,String code,String datapath){
		//1.验证session与code是否有效
		if(!validateSessionCode(sessionid,code,datapath)){
			return false;
		}		
		return true;
	}
	
	/**
	 * 验证session是否过期.（第二步和后面的升级文件请求需要验证）
	 * @author dh.shen
	 * @create 2016-1-4 上午10:57:51
	 * @since
	 * @param
	 * @return
	 */
	public boolean sessionValidate(String sessionId,int type){
		boolean tag = false;
		try {
			Connection dbConn = upgDao.createConnect();
			Statement sql = dbConn.createStatement();
			long nowtime = System.currentTimeMillis();
			String sqlStr = "";
			ResultSet rs = null;
			sqlStr = "select * from session where sessionid=\'"+sessionId+"\'";
			rs = sql.executeQuery(sqlStr);
			if(rs.next()){
				if(Integer.parseInt(rs.getString(5))==(type-1)){
					setDynamiccode(rs.getString(4));
					setModelName(rs.getString(6));
					//检验session是否还在有效期
					long value =  nowtime-Long.parseLong(rs.getString(3));
					if(value>0 && value<SessionPeriod){
						//有效，更新最后登录时间
						sqlStr = "update session set lasttime="+nowtime+",sessionstatus="+type+" where sessionid=\""+sessionId+"\"";
						sql.executeUpdate(sqlStr);
						tag = true;
					}else{
						//session过期,删除过期的session
						sqlStr = "delete from session where sessionid=\""+sessionId+"\"";
						sql.executeUpdate(sqlStr);
					}
				}
			}
			dbConn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return tag;
	}
	
	/**
	 * 更新机顶盒版本信息.
	 * @author dh.shen
	 * @create 2016-1-13 下午4:52:51
	 * @since
	 * @param
	 * @return
	 */
	public void updateSTBVersion(String sessionid){
		try {
			Connection dbConn = upgDao.createConnect();
			Statement sql = dbConn.createStatement();
			String sqlStr = "";
			ResultSet rs = null;
			sqlStr = "select * from session where sessionid=\'"+sessionid+"\'";
			rs = sql.executeQuery(sqlStr);
			if(rs.next()){
				int id = Integer.parseInt(rs.getString(7));
				sqlStr = "select * from model where model=\'"+modelName+"\'";
				rs = sql.executeQuery(sqlStr);
				if(rs.next()){
					String version = rs.getString(5);
					Calendar c = Calendar.getInstance(); 
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String timeStr = sdf.format(c.getTime());
					sqlStr = "update stb set version=\'"+version+"\',updatetime=\'"+timeStr+"\' where id="+id;
					sql.executeUpdate(sqlStr);
				}
			}
			//删除session
			sqlStr = "delete from session where sessionid=\""+sessionid+"\"";
			sql.executeUpdate(sqlStr);
			dbConn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}
	
	
	/**
	 * 根据sessionid获取型号名称.
	 * @author dh.shen
	 * @create 2016-1-19 下午5:11:16
	 * @since
	 * @param
	 * @return
	 */
	public void backStatus(String sessionid){
		try {
			Connection dbConn = upgDao.createConnect();
			Statement sql = dbConn.createStatement();
			String sqlStr = "update session set sessionstatus=2 where sessionid=\'"+sessionid+"\'";
			sql.executeUpdate(sqlStr);
			dbConn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}

	public UpgDao getUpgDao() {
		return upgDao;
	}
	public void setUpgDao(UpgDao upgDao) {
		this.upgDao = upgDao;
	}
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	public String getDynamiccode() {
		return dynamiccode;
	}
	public void setDynamiccode(String dynamiccode) {
		this.dynamiccode = dynamiccode;
	}
}
