package cn.et.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class MyDesk {
	
	/**
	 * ��ȡ���в���
	 * @return
	 * @throws SQLException
	 */
	public List<Map> getTableListAll() throws SQLException{
		String sql = "select * from desk";
		
		return DbUtils.query(sql);
	}
	
}
