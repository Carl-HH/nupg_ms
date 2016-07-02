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

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import jiuzhou.app.dao.UpgDao;
import jiuzhou.app.servicebean.DataStatusBean;
import jiuzhou.app.servicebean.LogBean;
import jiuzhou.app.servicebean.ModelBean;
import jiuzhou.app.servicebean.StbBean;
import jiuzhou.util.JsonUtil;
import jiuzhou.util.LogUtil;
import jiuzhou.util.PageBean;

import com.csvreader.CsvWriter;

/**
 * 网络升级service.
 * @author dh.shen
 * @since
 * @param
 */
public class UpgService {
	private UpgDao upgDao;
	
	private static int pageSize = 12;
	
	public UpgService(){
		upgDao = new UpgDao();
	}
	
	/**
	 * 管理员登陆.
	 * @author dh.shen
	 * @create 2015-10-27 上午9:59:23
	 * @since
	 * @param
	 * @return
	 */
	public boolean validateUser(String user,String pwd){
		boolean tag = false;
		try {
			Connection dbConn = upgDao.createConnect();
			Statement sql = dbConn.createStatement();
			String sqlStr = "select * from user where username='"+user+"'";
			ResultSet rs = sql.executeQuery(sqlStr);
			if(rs.next())
			{
				if(rs.getString(3).equals(pwd)){
					tag = true;
				}
			}else{
				tag = false;
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
	 * 添加型号.
	 * @author dh.shen
	 * @create 2015-10-28 上午11:49:11
	 * @since
	 * @param
	 * @return
	 */
	public String addModel(String corpname,String modelname,String hosturl,String version){
		String resStr = "ok";
		try {
			Connection dbConn = upgDao.createConnect();
			Statement sql = dbConn.createStatement();
			
			
			String sqlStr = "select count(*) from model where model=\""+modelname+"\"";
			ResultSet rs = sql.executeQuery(sqlStr);
			if(rs.next()){
				if(Integer.valueOf(rs.getString(1))==0){
					sqlStr = "insert into model(corporation,model,hosturl,version) values(\""+corpname+"\",\""+modelname+"\",\""+hosturl+"\",\""+version+"\");";
					sql.executeUpdate(sqlStr);
				}else{
					resStr = "exist";
				}
			}
			dbConn.close();
		} catch (ClassNotFoundException e) {
			resStr = "sqlerr";
			e.printStackTrace();
		} catch (SQLException e) {
			resStr = "sqlerr";
			e.printStackTrace();
		}
		return resStr;
	}
	
	
	/**
	 * 根据关键字、页码读取型号信息.
	 * @author dh.shen
	 * @create 2015-10-28 上午11:56:57
	 * @since
	 * @param
	 * @return
	 */
	public String getModelTab(int currentPage,String keyword){
		List<ModelBean> mbl = new ArrayList<ModelBean>();
		int top = pageSize*currentPage;		//获取前top条记录
		int start = pageSize*(currentPage-1);	// 从第start条记录+1开始
		int count = 0;	//总记录数
		String sqlStr = "";
		Connection dbConn;
		try {
			dbConn = upgDao.createConnect();
			Statement sql = dbConn.createStatement();
			sqlStr = "select count(*) from model where model like '%"+keyword+"%' or hosturl like '%"+keyword+"%'";
			//System.out.println("Sql:"+sqlStr);
			ResultSet rs = sql.executeQuery(sqlStr);
			if(rs.next()){
				count = Integer.valueOf(rs.getString(1));
			}
			sqlStr = "select * from model where model like '%"+keyword+"%' or hosturl like '%"+keyword+"%'  order by id desc limit "+top;
			//System.out.println("Sql2:"+sqlStr);
			rs = sql.executeQuery(sqlStr);
			if(start>0){
				for(int i=0;i<start;i++){
					rs.next();
				}
			}
			while(rs.next())
			{
				ModelBean mb = new ModelBean();
				mb.setId(Integer.valueOf(rs.getString(1)));
				mb.setCorpname(rs.getString(2));
				mb.setModel(rs.getString(3));
				mb.setHosturl(rs.getString(4));
				mb.setVersion(rs.getString(5));
				mb.setIsforce(Integer.valueOf(rs.getString(7)));
				mb.setSum(getModelSum(rs.getString(3)));
				mbl.add(mb);
			}
			dbConn.close();                    
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PageBean pb = PageBean.getInstance(currentPage, pageSize,count, mbl);
		return JsonUtil.toJsonString(pb);
	}
	
	/**
	 * 修改型号.
	 * @author dh.shen
	 * @create 2015-10-28 下午4:52:10
	 * @since
	 * @param
	 * @return
	 */
	public String modifyModelById(int id,String corpname,String model,String hosturl,String version){
		String resStr = "ok";
		try {
			Connection dbConn = upgDao.createConnect();
			Statement sql = dbConn.createStatement();
			String sqlStr = "update model set corporation = \""+corpname+"\",model=\""+model+"\",hosturl=\""+hosturl+"\",version=\""+version+"\" where id="+id;
			sql.executeUpdate(sqlStr);
			dbConn.close();
		} catch (ClassNotFoundException e) {
			resStr = "sqlerr";
			e.printStackTrace();
		} catch (SQLException e) {
			resStr = "sqlerr";
			e.printStackTrace();
		}
		return resStr;
	}
	

	/**
	 * 修改升级设置.
	 * @author dh.shen
	 * @create 2015-10-28 下午4:52:10
	 * @since
	 * @param
	 * @return
	 */
	public String modifyUptSettingById(int id,int isforceStr){
		String resStr = "ok";
		try {
			Connection dbConn = upgDao.createConnect();
			Statement sql = dbConn.createStatement();
			String sqlStr = "update model set isforce="+isforceStr+" where id="+id;
			System.out.println("UpgService.modifyUptSettingById()sql:"+sqlStr);
			sql.executeUpdate(sqlStr);
			dbConn.close();
		} catch (ClassNotFoundException e) {
			resStr = "sqlerr";
			e.printStackTrace();
		} catch (SQLException e) {
			resStr = "sqlerr";
			e.printStackTrace();
		}
		return resStr;
	}
	
	/**
	 * 根据Id删除型号记录.
	 * @author dh.shen
	 * @create 2015-10-28 下午4:07:19
	 * @since
	 * @param
	 * @return
	 */
	public String deleModelById(int id){
		String resStr = "ok";
		String modelname = LogUtil.getModeNameById(id);
		try {
			Connection dbConn = upgDao.createConnect();
			Statement sql = dbConn.createStatement();
			String sqlStr = "delete from model where id="+id;
			sql.executeUpdate(sqlStr);
			sqlStr = "delete from stb where model=\""+modelname+"\"";
			System.out.println(sqlStr);
			sql.executeUpdate(sqlStr);
			dbConn.close();
		} catch (ClassNotFoundException e) {
			resStr = "sqlerr";
			e.printStackTrace();
		} catch (SQLException e) {
			resStr = "sqlerr";
			e.printStackTrace();
		}
		return resStr;
	}
	
	
	/**
	 * 获取某种型号机顶盒的数量.
	 * @author dh.shen
	 * @create 2015-10-28 下午3:45:09
	 * @since
	 * @param
	 * @return
	 */
	public int getModelSum(String model){
		int resInt = 0;
		try {
			Connection dbConn = upgDao.createConnect();
			Statement sql = dbConn.createStatement();
			String sqlStr = "select count(*) from stb where model=\""+model+"\"";
			ResultSet rs = sql.executeQuery(sqlStr);
			if(rs.next()){
				resInt = Integer.valueOf(rs.getString(1));
			}
			dbConn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resInt;
	}
	
	
	/**
	 * 根据关键字返回匹配的型号.
	 * @author dh.shen
	 * @create 2015-12-16 上午9:35:19
	 * @since
	 * @param
	 * @return
	 */
	public String getModelByKeyWord(String keyword){
		List<ModelBean> mbl = new ArrayList<ModelBean>();
		try {
			Connection dbConn = upgDao.createConnect();
			Statement sql = dbConn.createStatement();
			String sqlStr = "select * from model where model like '%"+keyword+"%'";
			ResultSet rs = sql.executeQuery(sqlStr);
			int count = 0;
			while(rs.next())
			{
				count++;
				ModelBean mb = new ModelBean();
				mb.setId(Integer.valueOf(rs.getString(1)));
				mb.setModel(rs.getString(2));
				mb.setHosturl(rs.getString(3));
				mb.setVersion(rs.getString(4));
				mb.setSum(getModelSum(rs.getString(2)));
				mbl.add(mb);
				if(count==10){
					break;
				}
			}
			dbConn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return JsonUtil.toJsonString(mbl);
	}
	
	/**
	 * 根据页码和关键字获取日志表信息.
	 * @author dh.shen
	 * @create 2015-12-21 上午9:18:57
	 * @since
	 * @param
	 * @return
	 */
	public String getLogList(int currentPage,String keyword){
		List<LogBean> lbl = new ArrayList<LogBean>();
		int top = pageSize*currentPage;		//获取前top条记录
		int start = pageSize*(currentPage-1);	// 从第start条记录+1开始
		int count = 0;	//总记录数
		String sqlStr = "";
		Connection dbConn;
		try {
			dbConn = upgDao.createConnect();
			Statement sql = dbConn.createStatement();
			sqlStr = "select count(*) from logs where logcontent like '%"+keyword+"%'";
			//System.out.println("Sql:"+sqlStr);
			ResultSet rs = sql.executeQuery(sqlStr);
			if(rs.next()){
				count = Integer.valueOf(rs.getString(1));
			}
			sqlStr = "select * from logs where logcontent like '%"+keyword+"%' order by id desc limit "+top;
			//System.out.println("Sql2:"+sqlStr);
			rs = sql.executeQuery(sqlStr);
			if(start>0){
				for(int i=0;i<start;i++){
					rs.next();
				}
			}
			while(rs.next())
			{
				LogBean lb = new LogBean();
				lb.setId(Integer.valueOf(rs.getString(1)));
				lb.setDatetime(rs.getString(2));
				lb.setLogcontent(rs.getString(3));
				lbl.add(lb);
			}
			dbConn.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PageBean pb = PageBean.getInstance(currentPage, pageSize,count, lbl);
		return JsonUtil.toJsonString(pb);
	}
	
	/**
	 * 获取型号数据的相关信息.
	 * @author dh.shen
	 * @create 2015-12-28 下午7:01:54
	 * @since
	 * @param
	 * @return
	 */
	public String getModelDataStatus(int modelid){
		DataStatusBean dsb = new DataStatusBean();
		dsb.setId(modelid);
		String modelname = "";
		try {
			Connection dbConn = upgDao.createConnect();
			Statement sql = dbConn.createStatement();
			String sqlStr = "select model,havedata from model where id="+modelid;
			ResultSet rs = sql.executeQuery(sqlStr);
			if(rs.next())
			{
				modelname = rs.getString(1);
				dsb.setDatastatus(Integer.parseInt(rs.getString(2)));
			}else{
				dbConn.close();
				return "err";
			}
			if(dsb.getDatastatus()==1){
				dsb.setDatapath("uploadfile\\model_data\\"+modelname+".bin");
			}else{
				dsb.setDatapath("null");
			}
			sqlStr = "select count(*) from stb where model=\'"+modelname+"\'";
			rs = sql.executeQuery(sqlStr);
			if(rs.next())
			{
				dsb.setCount(Integer.parseInt(rs.getString(1)));
			}
			dbConn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return JsonUtil.toJsonString(dsb);
	}
	
	/**
	 * 清空机顶盒信息.
	 * @author dh.shen
	 * @create 2015-12-28 下午7:03:04
	 * @since
	 * @param
	 * @return
	 */
	public String emptyStbData(String modelname){
		String resStr = "ok";
		try {
			Connection dbConn = upgDao.createConnect();
			Statement sql = dbConn.createStatement();
			String sqlStr = "delete from stb where model=\'"+modelname+"\'";
			sql.executeUpdate(sqlStr);
			dbConn.close();
		} catch (ClassNotFoundException e) {
			resStr = "sqlerr";
			e.printStackTrace();
		} catch (SQLException e) {
			resStr = "sqlerr";
			e.printStackTrace();
		}
		return resStr;
	}
	
	/**
	 * 删除型号数据.
	 * @author dh.shen
	 * @create 2015-12-28 下午7:04:09
	 * @since
	 * @param
	 * @return
	 */
	public String deleteModelData(int modelid,String path){
		String resStr = "ok";
		File tmpfile = new File(path+"\\"+LogUtil.getModeNameById(modelid)+".bin");
		if(tmpfile.exists()){
			tmpfile.delete();
		}
		try {
			Connection dbConn = upgDao.createConnect();
			Statement sql = dbConn.createStatement();
			String sqlStr = "update model set havedata=0 where id="+modelid;
			sql.executeUpdate(sqlStr);
			dbConn.close();
		} catch (ClassNotFoundException e) {
			resStr = "SqlErr";
			e.printStackTrace();
		} catch (SQLException e) {
			resStr = "SqlErr";
			e.printStackTrace();
		}
		return resStr;
	}
	
	public String makeReports(String modelname,String path) throws IOException{
		path = path+"\\"+modelname+".csv";
		String resStr = "uploadfile\\report_file\\"+modelname+".csv";
		List<StbBean> sbl = new ArrayList<StbBean>();
		try {
			Connection dbConn = upgDao.createConnect();
			Statement sql = dbConn.createStatement();
			String sqlStr = "select id,mac,sn,chipid from stb where model=\'"+modelname+"\'";
			ResultSet rs = sql.executeQuery(sqlStr);
			while(rs.next())
			{
				StbBean sb = new StbBean();
				sb.setId(Integer.parseInt(rs.getString(1)));
				sb.setMac(rs.getString(2));
				sb.setSn(rs.getString(3));
				sb.setChipid(rs.getString(4));
				sbl.add(sb);
			}
			dbConn.close();
		} catch (ClassNotFoundException e) {
			resStr = "err";
			e.printStackTrace();
		} catch (SQLException e) {
			resStr = "err";
			e.printStackTrace();
		}
		int count = sbl.size();
		CsvWriter wr =new CsvWriter(path,',',Charset.forName("SJIS"));
		String[] contents = {"SN","MAC","CHIPID"};                    
		wr.writeRecord(contents);
		for(int i=0;i<count;i++){
			String[] record = {sbl.get(i).getSn(),sbl.get(i).getMac(),sbl.get(i).getChipid()};
			wr.writeRecord(record);
		}
		wr.close();
		return resStr;
	}
	
	
}

