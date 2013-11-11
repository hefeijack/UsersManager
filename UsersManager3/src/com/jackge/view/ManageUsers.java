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
				"function confirmOpen(){ return window.confirm('���Ҫɾ�����û���'); }");
		out.println("</script>");

		out.println("<img src='imgs/1.jpg' /> ��ӭ xx ��¼ <a href='/UsersManager3/LoginServlet'>����������</a> <a href='/UsersManager3/LoginServlet'>��ȫ�˳�</a><hr />");
		out.println("<h1>�����û�</h1>");
//		//�����ݿ���ȡ���û����ݲ���ʾ
//		Connection ct = null;
//		ResultSet rs = null;
//		PreparedStatement ps = null;
		
		//�����ҳ��Ҫ�ı���
		int pageNow = 1;	//��ǰҳ
		
		//�����û���pageNow
		String sPageNow = request.getParameter("pageNow");
		
		if(sPageNow != null){
			pageNow = Integer.parseInt(sPageNow);
		}
		
		int pageSize = 3;	//ָ��ÿҳ��ʾ3����¼
		int pageCount = 1;	//ԓֵ�Ǽ��������
//		int rowCount = 1;	//ԓֵ��ʾ���ж����м�¼�������ݿ��ѯ����
		
		//��Ϊservlet�������һ��java�࣬�������servlet�������ݿ����ͨjava����һ����
		try{
//			//1����������
//			Class.forName("oracle.jdbc.driver.OracleDriver");
//			//2���õ�����
//			ct = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:orcl","scott","tiger");
//			
//			//������ж�����
//			//�ٲ�ѯrowCount
//			ps = ct.prepareStatement("select count(*) from users");
//			rs = ps.executeQuery();
//			rs.next();		//���α�����
//			rowCount = rs.getInt(1);
//			
//			pageCount = rowCount%pageSize==0 ? rowCount/pageSize : rowCount/pageSize+1;
			
//			//3������PreparedStatement
//			ps = ct.prepareStatement("select * from (select t.*, rownum rn from (select * from users order by id) t where rownum<=" + pageSize*pageNow + ") where rn>=" + (pageSize*(pageNow-1)+1));
//			
//			//4��ִ�в���
//			rs = ps.executeQuery();
			
			UsersService usersService = new UsersService();
			
			pageCount = usersService.getPageCount(pageSize);
			
			ArrayList<User> al = usersService.getUsersByPage(pageNow, pageSize);
			
			out.println("<table border=1 bordercolor=green cellspacing=0 width=500px>");
			out.println("<tr><th>id</th><th>�û���</th><th>email</th><th>����</th><th>ɾ���û�</th><th>�޸��û�</th></tr>");
			//ѭ����ʾ�����û���Ϣ
			for(User u: al){
				out.println("<tr><td>" + u.getId() + 
						"</td><td>" + u.getName() + 
						"</td><td>" + u.getEmail() + 
						"</td><td>" + u.getGrade() + 
						"</td><td><a onClick='return confirmOpen()' href='/UsersManager3/UserClServlet?type=del&id="+u.getId()+"'>ɾ���û�</a></td>" +
								"<td><a href='/UsersManager3/UserClServlet?type=gotoUpdView&id="+u.getId()+"'>�޸��û�</a></td></tr>");
			}
			
//			//ѭ����ʾ�����û���Ϣ
//			while(rs.next()){
//				out.println("<tr><td>" + rs.getInt(1) + 
//						"</td><td>" + rs.getString(2) + 
//						"</td><td>" + rs.getString(3) + 
//						"</td><td>" + rs.getInt(4) + 
//						"</td></tr>");
//			}
			
			out.println("</table><br />");
			
			//��ʾ��һҳ
			if(pageNow != 1){
				out.println("<a href='/UsersManager3/ManageUsers?pageNow=" + (pageNow-1) + "'>��һҳ</a>");
			}
			
			//��ʾ��ҳ
			for(int i=1; i<=pageCount; i++){
				out.println("<a href='/UsersManager3/ManageUsers?pageNow=" + i + "'><" + i + "></a>");
			}
			
			//��ʾ��һҳ
			if(pageNow != pageCount){
				out.println("<a href='/UsersManager3/ManageUsers?pageNow=" + (pageNow+1) + "'>��һҳ</a>");
			}
			
			//��ʾ��ҳ��Ϣ
			out.println("&nbsp;&nbsp;&nbsp;��ǰҳ" + pageNow + "/��ҳ��" + pageCount + "<br />");
			
			out.println("��ת���� <input type='text' id='pageNow' name='pageNow' /> &nbsp;  <input type='button' onclick='gotoPageNow()' value='��' />"); 
			
		}catch(Exception e){
			request.setAttribute("err", "�������id�������֣�");
			request.getRequestDispatcher("/LoginServlet").forward(request, response);
			e.printStackTrace();
		}
//		finally{
//			//�ر���Դ
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
