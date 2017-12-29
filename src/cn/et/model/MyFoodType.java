package cn.et.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class MyFoodType {
	public List<Map> getAllFoodType() throws SQLException{
		String sql = "select * from foodtype";
		return DbUtils.query(sql);
	}
	
	public List<Map> getAllFood() throws SQLException{
		String sql = "select * from food";
		return DbUtils.query(sql);
	}
}
