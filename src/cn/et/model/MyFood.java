package cn.et.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class MyFood {
	/**
	 * ��ȡ����������
	 * @param typeid
	 * @return
	 * @throws SQLException
	 */
	public Integer getTableListCount(String typeid) throws SQLException{
	
		//������ѯ�����ݵ�SQL���
		String sql = "select count(rowid) as cr from food where typeid like '%"+typeid+"%'";
		//��ȡ�����ݿ��ѯ��������
		List<Map> list = DbUtils.query(sql);
		return Integer.parseInt(list.get(0).get("CR").toString());
	}
	
	/**
	 * ��ȡ�鵽�����ݼ���
	 * @param curPage
	 * @param typeid
	 * @return
	 * @throws SQLException
	 */
	public PageTools getTableListPage(Integer curPage,String typeid) throws SQLException{
		//��ȡ����������
		Integer totalCount = getTableListCount(typeid);
		
		//��ȡ��ҳ�����Ķ���
		PageTools pt = new PageTools(curPage, totalCount, 6);
		//��ѯ��ҳ��Ҫ��ʾ�����ݲ����뼯��
		String sql = "select * from "
				+ "(select t.*,rownum as rn from food t inner join foodtype ft on t.typeid=ft.typeid where t.typeid like '%"+typeid+"%' order by t.foodid asc)"
				+ " where rn>="+pt.getStartIndex()+" and rn<="+pt.getEndIndex();
		List<Map> list = DbUtils.query(sql);
		//�����ݴ��뼯��
		pt.setData(list);
		return pt;
	}
	
	
}
