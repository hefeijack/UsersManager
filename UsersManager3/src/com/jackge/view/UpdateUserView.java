package com.jackge.view;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jackge.domain.User;

public class UpdateUserView extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();

		//��ȡ�ӿ��������ݵ�user����
		User user = (User)request.getAttribute("userinfo");
		//��ʾ
		out.println("<img src='imgs/1.jpg' /><a href='/UsersManager3/LoginServlet'>����������</a> <a href='/UsersManager3/LoginServlet'>��ȫ�˳�</a><hr />");
		out.println("<h1>�޸��û�</h1>");
		out.println("<form action='/UsersManager3/UserClServlet?type=update' method='post'>");
		out.println("<table border=1 bordercolor=green cellspacing=0 width=200px>");
		
		out.println("<tr><td>id</td><td><input type='text' name='id' readonly value='"+user.getId()+"'/></td></tr>");
		out.println("<tr><td>�û���</td><td><input type='text' name='username' value='"+user.getName()+"'/></td></tr>");
		out.println("<tr><td>email</td><td><input type='text' name='email' value='"+user.getEmail()+"'/></td></tr>");
		out.println("<tr><td>����</td><td><input type='text' name='grade' value='"+user.getGrade()+"'/></td></tr>");
		out.println("<tr><td>����</td><td><input type='text' name='passwd' value='"+user.getPwd()+"'/></td></tr>");
		out.println("<tr><td><input type='submit' value='�޸��û�' /></td><td><input type='reset' value='������д' /></td></tr>");
		out.println("</table>");
		out.println("</form>");
		out.println("<hr /><img src='imgs/2.jpg' />");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doGet(request, response);
	}

}
