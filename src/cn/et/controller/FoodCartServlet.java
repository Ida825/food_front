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
		//获取session
		HttpSession session  = request.getSession();
		PrintWriter pw = response.getWriter();
		
		String flag = request.getParameter("flag");
		if("delete".equals(flag)){
			//获取当前要删除的菜品id
			String fid = request.getParameter("fid");
			session.removeAttribute(fid);
			request.getRequestDispatcher("/detail/clientCart.jsp").forward(request, response);
		}else if("order".equals(flag)){
			//从session中获取deskid
			String deskid = session.getAttribute("deskid").toString();
			int state  = 0;
			//获取订单编号
			try {
				Integer orderid = order.saveOrder(deskid, state);
				//循环session中的键cart_
				Enumeration<String> keys = session.getAttributeNames();
				while(keys.hasMoreElements()){
					String key = keys.nextElement();
					if(key.startsWith("cart_")){
						//获取去掉前缀cart_的fid
						String fid = key.split("cart_")[1];
						Map map = (Map)session.getAttribute(key);
						Integer count = (Integer)map.get("count");
						order.saveOrderDetail(orderid, Integer.parseInt(fid), count);
					}	
				}
				
				pw.write("<script>alert('下单成功')</script>");
				//下单成功1秒跳回主菜单
				response.setHeader("refresh","1;url="+request.getContextPath()+"/showDesk");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}else{
			//获取参数
			String fid = request.getParameter("fid");
			String fname = request.getParameter("fname");
			String price = request.getParameter("price");
			//通过fid键从session中获取菜品对象
			Object food = session.getAttribute("cart_"+fid);
			//如果第一次放入餐车，session不存在，就将该菜品放入session中，数量为1
			if(food==null){
				Map map = new HashMap();
				map.put("fid", fid);
				map.put("fname", fname);
				map.put("price", price);
				map.put("count", 1);
				map.put("total", price);
				//并放入session中
				session.setAttribute("cart_"+fid, map);
			}else{
				//否则说明session中已经有值，在原来基础上将数量加1
				Map map = (Map)food;
				Integer cs = (Integer)map.get("count");
				map.put("count",cs+1);
				map.put("total", (Integer.parseInt(price))*(cs+1));
				//写回session
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
