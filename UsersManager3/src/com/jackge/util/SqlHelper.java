package com.jackge.util;

/**
 * ����һ���������ݿ�Ĺ�����
 * @author jackge
 *
 */

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;
import java.util.concurrent.Callable;


public class SqlHelper {
	
	//������Ҫ�ı���
	private static Connection ct = null;
	//�ڴ��������£�����ʹ�õ��� PreparedStatement ������ Statement���������Է�ֹ sql ע��
	private static PreparedStatement ps = null;
	private static ResultSet rs = null;
	private static CallableStatement cs = null;
	
	//�������ݿ�Ĳ���
	private static String url = "";
	private static String username = "";
	private static String driver = "";
	private static String password = "";
	
	//�������ļ�
	private static Properties pp = null;
	private static InputStream fis = null;
	
	//����������ֻ��Ҫһ��
	static{
		try{
			//�� dbinfo.properties �ļ��ж�ȡ������Ϣ
			pp = new Properties();
//			fis = new FileInputStream("dbinfo.properties");	//=>tomcat����Ŀ¼
			//������ʹ��java web��ʱ�򣬶�ȡ�ļ�Ҫʹ�������������Ϊ�������ȥ��ȡ��Դ��ʱ��Ĭ�ϵ���Ŀ¼�� src Ŀ¼��
			fis = SqlHelper.class.getClassLoader().getResourceAsStream("dbinfo.properties");
			pp.load(fis);
			url = pp.getProperty("url");
			username = pp.getProperty("username");
			driver = pp.getProperty("driver");
			password = pp.getProperty("password");
			Class.forName(driver);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				fis.close();
			}catch(IOException e){
				e.printStackTrace();
			}
			fis = null;
		}
	}
	
	//�õ�����
	public static Connection getConnection(){
		try{
			ct = DriverManager.getConnection(url, username, password);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return ct;
	}
	
	//��ҳ���⣿
	public static ResultSet executeQuery2(){
		return null;
	}
	
//	//���ô洢���̣��з���Result
//	//sql call ���̣�������������
//	public static CallableStatement callPro2(String sql, String[] inparameters, Integer[] outparameters){
//		try{
//			ct = getConnection();
//			cs = ct.prepareCall(sql);
//			if(inparameters != null){
//				for(int i=0; i<inparameters.length; i++){
//					cs.setObject(i+1, inparameters[i]);
//				}
//			}
//			
//			//��out������ֵ
//			if(outparameters != null){
//				for(int i=0; i<outparameters.length; i++){
//					cs.registerOutParameter(inparameters.length+1+i, sqlType)
//				}
//			}
//		}
//	}
	
	//ͳһ��select
	//ResultSet -> ArrayList
	public static ResultSet executeQuery(String sql, String []parameters){
		try{
			ct = getConnection();
			ps = ct.prepareStatement(sql);
			if(parameters != null && !parameters.equals("")){
				for(int i=0; i<parameters.length; i++){
					ps.setString(i+1, parameters[i]);
				}
			}
			rs = ps.executeQuery();
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}finally{
			//close(rs, ps, ct)
		}
		return rs;
	}
	
	public static void executeUpdate(String sql, String[] parameters){
		//1������һ��ps
		try{
			ct = getConnection();
			ps = ct.prepareStatement(sql);
			//������ֵ
			if(parameters != null){
				for(int i=0; i<parameters.length; i++){
					ps.setString(i+1, parameters[i]);
				}
			}
			//ִ��
			ps.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();	//�����׶�
			// �׳��쳣���׳������쳣�����Ը����øú����ĺ���һ��ѡ��
			//���Դ���Ҳ���Է�������
			throw new RuntimeException(e.getMessage());
		}finally{
			//�ر���Դ
//			close(rs, ps, ct);
		}
	}
	
	//�ر���Դ�ĺ���
	public static void close(ResultSet rs, Statement ps, Connection ct){
		if(rs != null){
			try{
				rs.close();
			}catch(Exception e){
				e.printStackTrace();
			}
			rs = null;
		}
		
		if(ps != null){
			try{
				ps.close();
			}catch(Exception e){
				e.printStackTrace();
			}
			ps = null;
		}
		
		if(ct != null){
			try{
				ct.close();
			}catch(Exception e){
				e.printStackTrace();
			}
			ct = null;
		}
	}
	
	public static Connection getCt(){
		return ct;
	}
	
	public static PreparedStatement getPs(){
		return ps;
	}
	
	public static ResultSet getRs(){
		return rs;
	}
	
	public static CallableStatement getCs(){
		return cs;
	}
}
