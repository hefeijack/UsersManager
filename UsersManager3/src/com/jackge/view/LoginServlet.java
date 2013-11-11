package com.jackge.view;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
//		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		//����һ�����棨html������
		out.println("<img src='imgs/1.jpg' /><hr />");
		out.println("<h1>�û���¼</h1>");	
		//action��д����  /webӦ����/Servlet��url
		out.println("<form action='/UsersManager3/LoginClServlet' method='post'>");
		
		out.println("�û�id�� <input type='text' name='id' /><br />");
		out.println("�� &nbsp    �룺 <input type='password' name='password' /><br />");
		out.println("<input type='submit' value='��¼' /><br />");
		
		out.println("</form>");
		
		String errInfo = (String)request.getAttribute("err");
		if(errInfo != null){
			out.println("<font color='red'>" + errInfo + "</font>");
		}
		out.println("<hr /><img src='imgs/2.jpg' />");
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		this.doGet(request, response);
		
	}

}
