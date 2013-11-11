package com.jackge.util;

/**
 * 这是一个操作数据库的工具类
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
	
	//定义需要的变量
	private static Connection ct = null;
	//在大多数情况下，我们使用的是 PreparedStatement 来代替 Statement，这样可以防止 sql 注入
	private static PreparedStatement ps = null;
	private static ResultSet rs = null;
	private static CallableStatement cs = null;
	
	//连接数据库的参数
	private static String url = "";
	private static String username = "";
	private static String driver = "";
	private static String password = "";
	
	//读配置文件
	private static Properties pp = null;
	private static InputStream fis = null;
	
	//加载驱动，只需要一次
	static{
		try{
			//从 dbinfo.properties 文件中读取配置信息
			pp = new Properties();
//			fis = new FileInputStream("dbinfo.properties");	//=>tomcat的主目录
			//当我们使用java web的时候，读取文件要使用类加载器（因为类加载器去读取资源的时候，默认的主目录是 src 目录）
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
	
	//得到连接
	public static Connection getConnection(){
		try{
			ct = DriverManager.getConnection(url, username, password);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return ct;
	}
	
	//分页问题？
	public static ResultSet executeQuery2(){
		return null;
	}
	
//	//调用存储过程，有返回Result
//	//sql call 过程（？，？，？）
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
//			//给out参数赋值
//			if(outparameters != null){
//				for(int i=0; i<outparameters.length; i++){
//					cs.registerOutParameter(inparameters.length+1+i, sqlType)
//				}
//			}
//		}
//	}
	
	//统一的select
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
		//1，创建一个ps
		try{
			ct = getConnection();
			ps = ct.prepareStatement(sql);
			//给？赋值
			if(parameters != null){
				for(int i=0; i<parameters.length; i++){
					ps.setString(i+1, parameters[i]);
				}
			}
			//执行
			ps.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();	//开发阶段
			// 抛出异常，抛出运行异常，可以给调用该函数的函数一个选择
			//可以处理，也可以放弃处理
			throw new RuntimeException(e.getMessage());
		}finally{
			//关闭资源
//			close(rs, ps, ct);
		}
	}
	
	//关闭资源的函数
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
