package cn.et.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class MyOrder {
	/**
	 * 获取订单编号
	 * @return
	 * @throws SQLException
	 */
	public Integer getOrderId() throws SQLException{
		String sql = "select (nvl(max(orderid),0)+1) as myid from FOODORDER ";
		List<Map> ls = DbUtils.query(sql);
		Integer myid = Integer.parseInt(ls.get(0).get("MYID").toString());
		return myid;
	}
	
	/**
	 * 将订单信息存入数据库
	 * @return
	 * @throws SQLException 
	 */
	public Integer saveOrder(String deskid,int state) throws SQLException{
		Integer orderid = getOrderId();
		String sql = "insert into FOODORDER values('"+orderid+"','"+deskid+"',sysdate,'"+state+"')";
		DbUtils.execute(sql);	
		return orderid;
	}
	
	/**
	 * 将订单详细存入数据库
	 * @param orderid
	 * @param detail
	 * @throws SQLException 
	 */
	public void saveOrderDetail(Integer orderid,Integer foodid,Integer count) throws SQLException{	
		String sql = "insert into FOODORDERDETAIL values((select (nvl(max(detailid),0)+1) as myid from FOODORDERDETAIL),"+foodid+","+orderid+","+count+")";
		DbUtils.execute(sql);	
	}
	
}
