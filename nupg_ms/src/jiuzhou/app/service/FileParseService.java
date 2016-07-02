/**
 * Program  : FileParseService.java
 * Author   : dh.shen
 * Create   : 2015-12-22 下午2:27:28
 *
 *
 * Copyright 2014 by Shenzhen JIUZHOU Electronic CO.,LTD,
 * All rights reserved.
 *
 *
 * (文档描述).
 */
package jiuzhou.app.service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import jiuzhou.app.dao.UpgDao;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

/**
 * 解析报盘文件和模型数据的service.
 * @author dh.shen
 * @since
 * @param
 */
public class FileParseService {
	
	private UpgDao upgDao;
	public FileParseService(){
		upgDao = new UpgDao();
	}
	
	
	/**
	 * 解析报盘文件.
	 * 默认CSV格式文件包含三个字段，分别是:SN、MAC、CHIPID，不区分大小写
	 * CSV文件的解析需导入javacsv.jar
	 * @author dh.shen
	 * @create 2015-12-22 下午2:29:07
	 * @since
	 * @param
	 * @return
	 */
	public String parseReport(String modelname,String path){
		String resStr = "ok";
		ArrayList<String[]> csvList = new ArrayList<String[]>(); //用来保存数据  
		int mac_num = -1;	//mac地址所在的字段序号
        int sn_num = -1;	//序列号所在的字段序号
        int chipid_num = -1;	//chipid所在的字段序号
		try {      
            CsvReader reader = new CsvReader(path,',',Charset.forName("SJIS"));    //一般用这编码读就可以了
            reader.readRecord();
            String[] header = reader.getValues();
            int list_count = header.length;
            for(int i=0;i<list_count;i++){
	           	 if(header[i].equalsIgnoreCase("MAC")){
	           		 mac_num = i;
	           		 continue;
	           	 }
	           	 if(header[i].equalsIgnoreCase("SN")){
	           		 sn_num = i;
	           		 continue;
	           	 }
	           	 if(header[i].equalsIgnoreCase("CHIPID")){
	           		 chipid_num = i;
	           		 continue;
	           	 }
            }
            if((mac_num==-1)||(sn_num==-1)||(chipid_num==-1)){
            	File tmpfile = new File(path);
        		if(tmpfile.exists()){
        			tmpfile.delete();
        		}
           	 return "解析表头失败，没有解析到所需的字段！";
            }
            while(reader.readRecord()){ //逐行读入除表头的数据      
                csvList.add(reader.getValues());  
            }              
            reader.close();  
        }catch(Exception ex){  
            System.out.println(ex);
            return "异常";
        } 
		try {
			Connection dbConn = upgDao.createConnect();
			Statement sql = dbConn.createStatement();
			String sqlStr = "";
			String[] cell;
			for(int row=0;row<csvList.size();row++){  
				cell = csvList.get(row);
	            sqlStr = "insert into stb(mac,sn,model,chipid) values(\""+cell[mac_num]+"\",\""+cell[sn_num]+"\",\""+modelname+"\",\""+cell[chipid_num]+"\");";
				//System.out.println(sqlStr);
	            sql.executeUpdate(sqlStr);
	        }
			dbConn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			resStr = "异常2";
		} catch (SQLException e) {
			e.printStackTrace();
			resStr = "异常3";
		}
		File tmpfile = new File(path);
		if(tmpfile.exists()){
			tmpfile.delete();
		}
		return resStr;
	}
	
	/**
	 * 存放型号数据文件.
	 * @author dh.shen
	 * @create 2015-12-22 下午2:29:46
	 * @since
	 * @param
	 * @return
	 */
	public boolean changeDataState(int modelid,int value){
		
		//String modelname = LogUtil.getModeNameById(modelid);
		try {
			Connection dbConn = upgDao.createConnect();
			Statement sql = dbConn.createStatement();
			String sqlStr = "update model set havedata="+value+" where id="+modelid;
			sql.executeUpdate(sqlStr);
			dbConn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	
	public static void main(String[] args) throws IOException {
		
		CsvWriter wr =new CsvWriter("D:\\test.csv",',',Charset.forName("SJIS"));
		String[] contents = {"Lily","五一","90","女"};                    
		wr.writeRecord(contents);
		wr.close();
		
	}
		
	
	
}
