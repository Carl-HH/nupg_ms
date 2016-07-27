package jiuzhou.app.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import jiuzhou.app.dao.UpgDao;
import jiuzhou.app.servicebean.FlowsBean;
import jiuzhou.app.servicebean.ModelBean;
import jiuzhou.util.JsonUtil;
import jiuzhou.util.PageBean;

import org.apache.log4j.Logger;

public class FlowsService {
	private UpgDao upgDao;
	
	private Logger logger = Logger.getLogger(this.getClass().getName());
	
	private static int pageSize = 10;
	
	public FlowsService(){
		upgDao = new UpgDao();
	}
	
	/**
	 * 根据model获取流量信息.
	 * @author chen.hua
	 * @create 2016年7月20日 上午9:01:17
	 * @since
	 * @param 
	 * @return 
	 */
	public String getFlowsByModel(String modelname,int currentPage) {
		List<FlowsBean> flows = new ArrayList<FlowsBean>();
		int count = 0;
		int top = pageSize*currentPage;		//获取前top条记录
		int start = pageSize*(currentPage-1);	// 从第start条记录+1开始
		Connection dbConn;
		String sqlStr = "";
		
		try {
			dbConn = upgDao.createConnect();
			Statement sql = dbConn.createStatement();
			//sqlStr = "select count(*) from model where model like '%"+keyword+"%' or hosturl like '%"+keyword+"%'";
			sqlStr = "select count(distinct(version)) from stb where model like '%"+modelname+"%'";
			ResultSet rs = sql.executeQuery(sqlStr);
			if(rs.next()){
				count = Integer.valueOf(rs.getString(1)) + 1; //加上为null的数据
			}
			
			sqlStr = "select version,count(*),model from stb where model like '%"+modelname+"%' group by version limit "+top;
			//System.out.println("Sql:"+sqlStr);
			rs = sql.executeQuery(sqlStr);
			if(start>0){
				for(int i=0;i<start;i++){
					rs.next();
				}
			}
			while(rs.next()){
				FlowsBean flow = new FlowsBean();
				flow.setVersion(rs.getString(1));
				flow.setCount(rs.getString(2));
				flow.setModel(rs.getString(3));
				flows.add(flow);
			}
			dbConn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			
		}
		
		PageBean pb = PageBean.getInstance(currentPage, pageSize,count, flows);
		return JsonUtil.toJsonString(pb);
	
	}
	
	
	/**
	 * 获取所有机型信息.
	 * @author chen.hua
	 * @create 2016年7月21日 下午3:36:04
	 * @since
	 * @param
	 * @return
	 */
	public String getAllModel(){
		List<FlowsBean> flows = new ArrayList<FlowsBean>();
		int count = 0;
	
		Connection dbConn;
		String sqlStr = "";
		
		try {
			dbConn = upgDao.createConnect();
			Statement sql = dbConn.createStatement();
			//sqlStr = "select count(*) from model where model like '%"+keyword+"%' or hosturl like '%"+keyword+"%'";
			sqlStr = "select model,version from model";
			ResultSet rs = sql.executeQuery(sqlStr);
			
			while(rs.next()){
				FlowsBean flow = new FlowsBean();
				flow.setModel(rs.getString(1));
				flows.add(flow);
			}
			dbConn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			
		}
		return JsonUtil.toJsonString(flows);
	}
	
	
	
	public static void main(String[] args) {
		FlowsService service = new FlowsService();
		String pb = service.getAllModel();
		System.out.println("FlowsService.main():"+pb);
	}
	
}
