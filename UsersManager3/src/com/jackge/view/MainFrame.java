package com.jackge.view;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainFrame extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();

		out.println("<img src='imgs/1.jpg' /> ��ӭ xx ��¼ <a href='/UsersManager3/LoginServlet'>�������µ�¼</a><hr />");
		
		out.println("<h3>��ѡ����Ҫ���еĲ���</h3>");
		out.println("<a href='/UsersManager3/ManageUsers'>�����û�</a><br />");
		out.println("<a href='/UsersManager3/UserClServlet?type=gotoAddUser'>����û�</a><br />");
		out.println("<a href=''>�����û�</a><br />");
		out.println("<a href=''>�˳�ϵͳ</a><br />");
		out.println("<hr /><img src='imgs/2.jpg' />");
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doGet(request, response);
	}

}
