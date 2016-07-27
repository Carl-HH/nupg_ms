/**
 * Program  : LogUtil.java
 * Author   : dh.shen
 * Create   : 2015-12-21 上午9:53:46
 *
 *
 * Copyright 2014 by Shenzhen JIUZHOU Electronic CO.,LTD,
 * All rights reserved.
 *
 *
 * (文档描述).
 */
package jiuzhou.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.log4j.Logger;

import jiuzhou.app.dao.UpgDao;

/**
 * 日志添加类.
 * @author dh.shen
 * @since
 * @param
 */
public class LogUtil {
	private Logger logger = Logger.getLogger(this.getClass().getName());
	public static void addLog(String logcontent){
		Calendar c = Calendar.getInstance(); 
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String timeStr = sdf.format(c.getTime());
		UpgDao upgdao = new UpgDao();
		Connection dbConn;
		Statement sql;
		try {
			dbConn = upgdao.createConnect();
			sql = dbConn.createStatement();
			String sqlStr = "insert into logs(datetime,logcontent)values('"+timeStr+"','"+logcontent+"')";
			sql.executeUpdate(sqlStr);
			dbConn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static String getModeNameById(int modelId){
		UpgDao upgdao = new UpgDao();
		String modelname = "null";
		Connection dbConn;
		try {
			dbConn = upgdao.createConnect();
			Statement sql = dbConn.createStatement();
			String sqlStr = "select model from model where id="+modelId;
			ResultSet rs = sql.executeQuery(sqlStr);
			if(rs.next())
			{
				modelname = rs.getString(1).trim();
			}
			dbConn.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return modelname;
	}
	
	
	public static void main(String[] args) {
		LogUtil.addLog("nihao");
	}
	
}
