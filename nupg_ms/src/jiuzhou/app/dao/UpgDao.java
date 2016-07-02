/**
 * Program  : UtilDao.java
 * Author   : dh.shen
 * Create   : 2015-10-26 上午9:26:14
 *
 *
 * Copyright 2014 by Shenzhen JIUZHOU Electronic CO.,LTD,
 * All rights reserved.
 *
 *
 * (文档描述).
 */
package jiuzhou.app.dao;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 数据库操作类.
 * @author dh.shen
 * @since
 * @param
 */
public class UpgDao {
	private String jdbcDriver;
	private String dbUrl;
	private String dbUsername;
	private String dbPassword;
	
	public UpgDao() {
        try {
            String szJdbcDriver = readValue("jdbc.driver");
            String szDbUrl = readValue("jdbc.url");
            String szDbUsername = readValue("jdbc.username");
            String szDbPassword = readValue("jdbc.password");

            if (szJdbcDriver != null && !szJdbcDriver.equals(""))
                this.jdbcDriver = szJdbcDriver;

            if (szDbUrl != null && !szDbUrl.equals(""))
                this.dbUrl = szDbUrl;

            if (szDbUsername != null && !szDbUsername.equals(""))
                this.dbUsername = szDbUsername;

            if (szDbPassword != null && !szDbPassword.equals(""))
                this.dbPassword = szDbPassword;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	/**
	 * 创建连接.
	 * @author dh.shen
	 * @create 2015-7-22 下午2:36:21
	 * @since
	 * @param
	 * @return
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	public Connection createConnect() throws ClassNotFoundException, SQLException{
		Connection dbConn = null;
		Class.forName(jdbcDriver);  
		dbConn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
		return dbConn;
	}
	
	
	/**
     * 
     * 读取配置文件对应属性
     * 
     * @return 配置文件对应属性值
     */
    private String readValue(String key) {
        Properties props = new Properties();
        try {
            InputStream in = getClass().getResourceAsStream("/jdbc.properties");
            props.load(in);
            String value = props.getProperty(key);
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
