package com.jackge.view;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jackge.domain.User;
import com.jackge.service.UsersService;

public class ManageUsers extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		out.println("<script type='text/javascript' language='javascript'>");
		out.println("function gotoPageNow(){ " +
				"var pageNow=document.getElementById('pageNow');" +
				"window.open('/UsersManager3/ManageUsers?pageNow='+pageNow.value, '_self'); }" +
				"function confirmOpen(){ return window.confirm('真的要删除该用户吗？'); }");
		out.println("</script>");

		out.println("<img src='imgs/1.jpg' /> 欢迎 xx 登录 <a href='/UsersManager3/LoginServlet'>返回主界面</a> <a href='/UsersManager3/LoginServlet'>安全退出</a><hr />");
		out.println("<h1>管理用户</h1>");
//		//从数据库中取出用户数据并显示
//		Connection ct = null;
//		ResultSet rs = null;
//		PreparedStatement ps = null;
		
		//定义分页需要的变量
		int pageNow = 1;	//当前页
		
		//接收用户的pageNow
		String sPageNow = request.getParameter("pageNow");
		
		if(sPageNow != null){
			pageNow = Integer.parseInt(sPageNow);
		}
		
		int pageSize = 3;	//指定每页显示3条记录
		int pageCount = 1;	//該值是计算出来的
//		int rowCount = 1;	//該值表示共有多少行记录，从数据库查询出来
		
		//因为servlet本身就是一个java类，因此我们servlet操作数据库和普通java类是一样的
		try{
//			//1，加载驱动
//			Class.forName("oracle.jdbc.driver.OracleDriver");
//			//2，得到连接
//			ct = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:orcl","scott","tiger");
//			
//			//算出共有多少列
//			//①查询rowCount
//			ps = ct.prepareStatement("select count(*) from users");
//			rs = ps.executeQuery();
//			rs.next();		//把游标下移
//			rowCount = rs.getInt(1);
//			
//			pageCount = rowCount%pageSize==0 ? rowCount/pageSize : rowCount/pageSize+1;
			
//			//3，创建PreparedStatement
//			ps = ct.prepareStatement("select * from (select t.*, rownum rn from (select * from users order by id) t where rownum<=" + pageSize*pageNow + ") where rn>=" + (pageSize*(pageNow-1)+1));
//			
//			//4，执行操作
//			rs = ps.executeQuery();
			
			UsersService usersService = new UsersService();
			
			pageCount = usersService.getPageCount(pageSize);
			
			ArrayList<User> al = usersService.getUsersByPage(pageNow, pageSize);
			
			out.println("<table border=1 bordercolor=green cellspacing=0 width=500px>");
			out.println("<tr><th>id</th><th>用户名</th><th>email</th><th>级别</th><th>删除用户</th><th>修改用户</th></tr>");
			//循环显示所有用户信息
			for(User u: al){
				out.println("<tr><td>" + u.getId() + 
						"</td><td>" + u.getName() + 
						"</td><td>" + u.getEmail() + 
						"</td><td>" + u.getGrade() + 
						"</td><td><a onClick='return confirmOpen()' href='/UsersManager3/UserClServlet?type=del&id="+u.getId()+"'>删除用户</a></td>" +
								"<td><a href='/UsersManager3/UserClServlet?type=gotoUpdView&id="+u.getId()+"'>修改用户</a></td></tr>");
			}
			
//			//循环显示所有用户信息
//			while(rs.next()){
//				out.println("<tr><td>" + rs.getInt(1) + 
//						"</td><td>" + rs.getString(2) + 
//						"</td><td>" + rs.getString(3) + 
//						"</td><td>" + rs.getInt(4) + 
//						"</td></tr>");
//			}
			
			out.println("</table><br />");
			
			//显示上一页
			if(pageNow != 1){
				out.println("<a href='/UsersManager3/ManageUsers?pageNow=" + (pageNow-1) + "'>上一页</a>");
			}
			
			//显示分页
			for(int i=1; i<=pageCount; i++){
				out.println("<a href='/UsersManager3/ManageUsers?pageNow=" + i + "'><" + i + "></a>");
			}
			
			//显示下一页
			if(pageNow != pageCount){
				out.println("<a href='/UsersManager3/ManageUsers?pageNow=" + (pageNow+1) + "'>下一页</a>");
			}
			
			//显示分页信息
			out.println("&nbsp;&nbsp;&nbsp;当前页" + pageNow + "/总页数" + pageCount + "<br />");
			
			out.println("跳转到： <input type='text' id='pageNow' name='pageNow' /> &nbsp;  <input type='button' onclick='gotoPageNow()' value='跳' />"); 
			
		}catch(Exception e){
			request.setAttribute("err", "您输入的id不是数字！");
			request.getRequestDispatcher("/LoginServlet").forward(request, response);
			e.printStackTrace();
		}
//		finally{
//			//关闭资源
//			if(rs != null){
//				try {
//					rs.close();
//				} catch (SQLException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				rs = null;
//			}
//			
//			if(ps != null){
//				try {
//					ps.close();
//				} catch (SQLException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				ps = null;
//			}
//			
//			if(ct != null){
//				try {
//					ct.close();
//				} catch (SQLException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				ct = null;
//			} 	
//		}
		out.println("<hr /><img src='imgs/2.jpg' />");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doGet(request, response);
	}

}
