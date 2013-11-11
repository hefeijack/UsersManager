package com.jackge.controller;

import java.io.IOException;
import java.sql.*;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jackge.domain.User;
import com.jackge.service.UsersService;

public class LoginClServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
//		response.setContentType("text/html;charset=utf-8");
//		PrintWriter out = response.getWriter();

		//�����û��ύ���û���������
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		
		//���￴����û�н��յ�����
//		System.out.println(username + "  " + password);
		
//		if("jackge".equals(username) && "123".equals(password)){
//			//��ת����һ��ҳ�棨servlet�ṩ���֣�SendRedirect ��  forward��
//			//sendRedirect��url��д����/webӦ����/servlet url
//			response.sendRedirect("/UsersManager/MainFrame");
//		}else{
//			//����
//			response.sendRedirect("/UsersManager/LoginServlet");
//		}
//		
		
		//����UsersService������ɵ����ݿ����֤
		UsersService usersService = new UsersService();
		User user = new User();
		user.setId(Integer.parseInt(id));
		user.setPwd(password);
		
		//5�����ݽ��������
		if(usersService.checkUser(user)){
			//˵�����û��Ϸ�
			request.getRequestDispatcher("/MainFrame").forward(request, response);
		}else{
			request.setAttribute("err", "�û�id������������");
			request.getRequestDispatcher("/LoginServlet").forward(request, response);
		}
		
		
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doGet(request, response);
	}

}
