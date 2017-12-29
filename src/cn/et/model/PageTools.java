package cn.et.model;

import java.util.List;

/**
 * 分页的参数
 * @author Administrator
 *
 */
public class PageTools {
	/**
	 * 构造参数
	 * @param curPage 页面传入的当前页
	 * @param totalCount 数据库查询的总记录数
	 * @param pageCount 每页显示的条数
	 */
	public PageTools(Integer curPage,Integer totalCount,Integer pageCount){
		this.curPage=curPage;
		this.totalCount=totalCount;
		this.pageCount=pageCount==null?10:this.pageCount;
		this.prePage=curPage==1?1:curPage-1;
		this.totalPage=totalCount%this.pageCount==0?totalCount/this.pageCount:totalCount/this.pageCount+1;
		this.nextPage=curPage==totalPage?totalPage:curPage+1;
		this.startIndex=(curPage-1)*this.pageCount+1;
		this.endIndex=curPage*this.pageCount;
	}
	/**
	 * 当前页（动态由页面传递）
	 */
	private Integer curPage;
	
	/**
	 * 每页显示的条数（默认10条）
	 */
	private Integer pageCount=10;
	
	/**
	 *  上一页:prePage=curPage==1?1:curPage-1;
	 */
	private Integer prePage;
	
	/**
	 * 下一页:nextPage=curPage==totalPage?totalPage:curPage+1;
	 */
	private Integer nextPage;
	 
	/**
	 * 总页数:totalPage=totalCount%pageCount==0?totalCount/pageCount:totalCount/pageCount+1;
	 */
	private Integer totalPage;
	
	/**
	 * 总记录数（来自数据库）
	 */
	private Integer totalCount;
	
	/**
	 * 开始索引位置： startIndex=(curPage-1)*pageCount+1;
	 */
	private Integer startIndex;
	
	/**
	 * 结束索引位置:endIndex=curPage*pageCount;
	 */
	private Integer endIndex;
	
	/**
	 * 用来存储查到的数据
	 */
	private List data;

	public Integer getCurPage() {
		return curPage;
	}

	public void setCurPage(Integer curPage) {
		this.curPage = curPage;
	}

	public Integer getPageCount() {
		return pageCount;
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

	public Integer getPrePage() {
		return prePage;
	}

	public void setPrePage(Integer prePage) {
		this.prePage = prePage;
	}

	public Integer getNextPage() {
		return nextPage;
	}

	public void setNextPage(Integer nextPage) {
		this.nextPage = nextPage;
	}

	public Integer getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	
	public List getData() {
		return data;
	}

	public void setData(List data) {
		this.data = data;
	}

	public Integer getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(Integer startIndex) {
		this.startIndex = startIndex;
	}

	public Integer getEndIndex() {
		return endIndex;
	}

	public void setEndIndex(Integer endIndex) {
		this.endIndex = endIndex;
	}

	/**
	 * 测试
	 * @param args
	 */
	public static void main(String[] args) {
		int curPage = 2;
		int totalCount = 26;
		int pageCount = 5;
		PageTools pt = new PageTools(curPage, totalCount, pageCount);
		//获取上一页
		System.out.println(pt.getPrePage());
		//获取下一页
		System.out.println(pt.getNextPage());
		//获取总页数
		System.out.println(pt.getTotalPage());
		//获取开始页
		System.out.println(pt.getStartIndex());
		//获取结束页
		System.out.println(pt.getEndIndex());
	}
	
}
