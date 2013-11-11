package com.jackge.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jackge.domain.User;
import com.jackge.service.UsersService;

public class UserClServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();

		UsersService usersService = new UsersService();
		
		//����type
		String type = request.getParameter("type");
		if("del".equals(type)){
			//����id
			String id = request.getParameter("id");
			//����UserService���ɾ��
			if(usersService.delUser(id)){
				//��ת���ɹ�ҳ��
				request.setAttribute("info", "ɾ���ɹ���");
				request.getRequestDispatcher("/Ok").forward(request, response);
			}else{
				//��תʧ��
				request.setAttribute("info", "ɾ��ʧ�ܣ�");
				request.getRequestDispatcher("/Err").forward(request, response);
			}
		}else if("gotoUpdView".equals(type)){
			//Ҫȥ�޸Ľ���
			//�õ�id
			String id = request.getParameter("id");
			//ͨ��id��ȡһ��UserBean
			User user = usersService.getUserById(id);
			
			//Ϊ������һ��Servlet�����棩ʹ�����ǵ�user�������ǿ��԰Ѹ�user������뵽request������
			request.setAttribute("userinfo", user);
			//����ת��
			request.getRequestDispatcher("/UpdateUserView").forward(request, response);
			
		}else if("update".equals(type)){
			//�����û��µ���Ϣ  ������û��ύ�����ݸ�ʽ����ȷ����һ����֤��
			String id = request.getParameter("id");
			String username = request.getParameter("username");
			String email = request.getParameter("email");
			String grade = request.getParameter("grade");
			String passwd = request.getParameter("passwd");
			
			//�ѽ��յ�����Ϣ��װ��һ��User����
			User user = new User(Integer.parseInt(id), username, email, Integer.parseInt(grade), passwd);
						
			//�޸��û�
			if(usersService.updUser(user)){
				//��ת���ɹ�ҳ��
				request.setAttribute("info", "�޸ĳɹ���");
				request.getRequestDispatcher("/Ok").forward(request, response);
			}else{
				//��תʧ��
				request.setAttribute("info", "�޸�ʧ�ܣ�");
				request.getRequestDispatcher("/Err").forward(request, response);
			}
			
		}else if("gotoAddUser".equals(type)){
			//����û��ʲôҪ�����
			request.getRequestDispatcher("/AddUserView").forward(request, response);
		}else if("add".equals(type)){
			addUser(request, response, usersService);
			
		}
		
	
		
	}

	//����û��ķ���
	private void addUser(HttpServletRequest request,
			HttpServletResponse response, UsersService usersService)
			throws ServletException, IOException {
		//�����û���Ϣ
		String username = request.getParameter("username");
		String email = request.getParameter("email");
		String grade = request.getParameter("grade");
		String passwd = request.getParameter("passwd");
		
		//�ѽ��յ�����Ϣ��װ��һ��User����
		User user = new User();
		user.setEmail(email);
		user.setGrade(Integer.parseInt(grade));
		user.setName(username);
		user.setPwd(passwd);
		
		if(usersService.addUser(user)){
			//��ת���ɹ�ҳ��
			request.setAttribute("info", "��ӳɹ���");
			request.getRequestDispatcher("/Ok").forward(request, response);
		}else{
			//��תʧ��
			request.setAttribute("info", "���ʧ�ܣ�");
			request.getRequestDispatcher("/Err").forward(request, response);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doGet(request, response);
	}

}
