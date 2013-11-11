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

		//接收用户提交的用户名和密码
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		
		//这里看看有没有接收到数据
//		System.out.println(username + "  " + password);
		
//		if("jackge".equals(username) && "123".equals(password)){
//			//跳转到下一个页面（servlet提供两种：SendRedirect 和  forward）
//			//sendRedirect的url的写法：/web应用名/servlet url
//			response.sendRedirect("/UsersManager/MainFrame");
//		}else{
//			//跳回
//			response.sendRedirect("/UsersManager/LoginServlet");
//		}
//		
		
		//创建UsersService对象，完成到数据库的验证
		UsersService usersService = new UsersService();
		User user = new User();
		user.setId(Integer.parseInt(id));
		user.setPwd(password);
		
		//5，根据结果做处理
		if(usersService.checkUser(user)){
			//说明该用户合法
			request.getRequestDispatcher("/MainFrame").forward(request, response);
		}else{
			request.setAttribute("err", "用户id或者密码有误！");
			request.getRequestDispatcher("/LoginServlet").forward(request, response);
		}
		
		
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doGet(request, response);
	}

}
