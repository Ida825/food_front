package cn.et.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.et.model.MyOrder;

/**
 * Servlet implementation class FoodCartServlet
 */
public class FoodCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FoodCartServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    MyOrder order = new MyOrder();
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		//��ȡsession
		HttpSession session  = request.getSession();
		PrintWriter pw = response.getWriter();
		
		String flag = request.getParameter("flag");
		if("delete".equals(flag)){
			//��ȡ��ǰҪɾ���Ĳ�Ʒid
			String fid = request.getParameter("fid");
			session.removeAttribute(fid);
			request.getRequestDispatcher("/detail/clientCart.jsp").forward(request, response);
		}else if("order".equals(flag)){
			//��session�л�ȡdeskid
			String deskid = session.getAttribute("deskid").toString();
			int state  = 0;
			//��ȡ�������
			try {
				Integer orderid = order.saveOrder(deskid, state);
				//ѭ��session�еļ�cart_
				Enumeration<String> keys = session.getAttributeNames();
				while(keys.hasMoreElements()){
					String key = keys.nextElement();
					if(key.startsWith("cart_")){
						//��ȡȥ��ǰ׺cart_��fid
						String fid = key.split("cart_")[1];
						Map map = (Map)session.getAttribute(key);
						Integer count = (Integer)map.get("count");
						order.saveOrderDetail(orderid, Integer.parseInt(fid), count);
					}	
				}
				
				pw.write("<script>alert('�µ��ɹ�')</script>");
				//�µ��ɹ�1���������˵�
				response.setHeader("refresh","1;url="+request.getContextPath()+"/showDesk");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}else{
			//��ȡ����
			String fid = request.getParameter("fid");
			String fname = request.getParameter("fname");
			String price = request.getParameter("price");
			//ͨ��fid����session�л�ȡ��Ʒ����
			Object food = session.getAttribute("cart_"+fid);
			//�����һ�η���ͳ���session�����ڣ��ͽ��ò�Ʒ����session�У�����Ϊ1
			if(food==null){
				Map map = new HashMap();
				map.put("fid", fid);
				map.put("fname", fname);
				map.put("price", price);
				map.put("count", 1);
				map.put("total", price);
				//������session��
				session.setAttribute("cart_"+fid, map);
			}else{
				//����˵��session���Ѿ���ֵ����ԭ�������Ͻ�������1
				Map map = (Map)food;
				Integer cs = (Integer)map.get("count");
				map.put("count",cs+1);
				map.put("total", (Integer.parseInt(price))*(cs+1));
				//д��session
				session.setAttribute("cart_"+fid, map);
			}
			request.getRequestDispatcher("/detail/clientCart.jsp").forward(request, response);
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
