package cn.et.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class MyFood {
	/**
	 * 获取数据总条数
	 * @param typeid
	 * @return
	 * @throws SQLException
	 */
	public Integer getTableListCount(String typeid) throws SQLException{
	
		//创建查询总数据的SQL语句
		String sql = "select count(rowid) as cr from food where typeid like '%"+typeid+"%'";
		//获取从数据库查询到的数据
		List<Map> list = DbUtils.query(sql);
		return Integer.parseInt(list.get(0).get("CR").toString());
	}
	
	/**
	 * 获取查到的数据集合
	 * @param curPage
	 * @param typeid
	 * @return
	 * @throws SQLException
	 */
	public PageTools getTableListPage(Integer curPage,String typeid) throws SQLException{
		//获取数据总条数
		Integer totalCount = getTableListCount(typeid);
		
		//获取分页参数的对象
		PageTools pt = new PageTools(curPage, totalCount, 6);
		//查询出页面要显示的数据并存入集合
		String sql = "select * from "
				+ "(select t.*,rownum as rn from food t inner join foodtype ft on t.typeid=ft.typeid where t.typeid like '%"+typeid+"%' order by t.foodid asc)"
				+ " where rn>="+pt.getStartIndex()+" and rn<="+pt.getEndIndex();
		List<Map> list = DbUtils.query(sql);
		//将数据存入集合
		pt.setData(list);
		return pt;
	}
	
	
}
