package cn.et.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.et.model.MyFood;
import cn.et.model.MyFoodType;

/**
 * Servlet implementation class ShowFood
 */
public class ShowFood extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowFood() {
        super();
        // TODO Auto-generated constructor stub
    }

    MyFoodType mft = new MyFoodType();
    MyFood mf = new MyFood();
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		try {

			//如果第一次进入菜单，没有带菜系参数，查所有菜品
			//点击菜系，传入菜系编号
			String typeid = "";
			if(request.getParameter("typeid")!=null){
				typeid = request.getParameter("typeid");
			}
			
			//获取session
			HttpSession session = request.getSession();
			if(request.getParameter("deskid")!=null){
				//如果请求参数中有deskid,就将当前的deskid存入session中
				session.setAttribute("deskid", request.getParameter("deskid"));
			}
			
			//菜系
			request.setAttribute("list", mft.getAllFoodType());		
			String curPage = request.getParameter("curPage");
			Integer curPageVar = 1;
			if(curPage!=null){
				curPageVar = Integer.parseInt(curPage);
			}
			
		
			//查所有菜品
			request.setAttribute("ls",mf.getTableListPage(curPageVar,typeid));
			request.getRequestDispatcher("/detail/caidan.jsp").forward(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
