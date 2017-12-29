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

			//�����һ�ν���˵���û�д���ϵ�����������в�Ʒ
			//�����ϵ�������ϵ���
			String typeid = "";
			if(request.getParameter("typeid")!=null){
				typeid = request.getParameter("typeid");
			}
			
			//��ȡsession
			HttpSession session = request.getSession();
			if(request.getParameter("deskid")!=null){
				//��������������deskid,�ͽ���ǰ��deskid����session��
				session.setAttribute("deskid", request.getParameter("deskid"));
			}
			
			//��ϵ
			request.setAttribute("list", mft.getAllFoodType());		
			String curPage = request.getParameter("curPage");
			Integer curPageVar = 1;
			if(curPage!=null){
				curPageVar = Integer.parseInt(curPage);
			}
			
		
			//�����в�Ʒ
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
