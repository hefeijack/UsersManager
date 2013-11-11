package com.jackge.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.jackge.domain.User;
import com.jackge.util.SqlHelper;

public class UsersService {
	
	//获取pageCount
	public int getPageCount(int pageSize){
		String sql = "select count(*) from users";
		
		ResultSet rs = SqlHelper.executeQuery(sql, null);
		
		int rowCount = 0;
		try {
			rs.next();
			rowCount = rs.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			SqlHelper.close(rs, SqlHelper.getPs(), SqlHelper.getCt());
		}
		
		return (rowCount-1)/pageSize+1;
	}
	
	//按照分页来获取用户
	public ArrayList getUsersByPage(int pageNow, int pageSize){
		
		ArrayList<User> al = new ArrayList<User>();
		
		//查询sql
//		String sql = "select * from (select t.*, rownum rn from (select * from users order by id) t where rownum<=" + pageSize*pageNow + ") where rn>=" + (pageSize*(pageNow-1)+1);
		String sql = "select * from (select t.*, rownum rn from (select * from users order by id) t where rownum<=" + pageSize*pageNow + ") where rn>=" + (pageSize*(pageNow-1)+1);
		ResultSet rs = SqlHelper.executeQuery(sql, null);
		
		//二次封装 : 把ResultSet -> User 对象 -> ArrayList（集合）
		
		try {
			while(rs.next()){
				User u = new User();
				u.setId(rs.getInt(1));
				u.setName(rs.getString(2));
				u.setEmail(rs.getString(3));
				u.setGrade(rs.getInt(4));
				//千万别忘记把  u 放入ArrayList中
				al.add(u);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			SqlHelper.close(rs, SqlHelper.getPs(), SqlHelper.getCt());
		}
		
		return al;
	}
	
	//通过id获取用户数据
	public User getUserById(String id){
		
		User user = new User();
		
		String sql = "select * from users where id=?";
		String parameters[] = {id};
		ResultSet rs = SqlHelper.executeQuery(sql, parameters);
		
		try {
			if(rs.next()){
				//二次封装
				user.setId(rs.getInt(1));
				user.setName(rs.getString(2));
				user.setEmail(rs.getString(3));
				user.setGrade(rs.getInt(4));
				user.setPwd(rs.getString(5));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			SqlHelper.close(rs, SqlHelper.getPs(), SqlHelper.getCt());
		}
		
		return user;
	}
	
	//添加用户
	public boolean addUser(User user){
		
		boolean b = true;
		String sql = "insert into users values(users_seq.nextval,?,?,?,?)";
		String parameters[] = {user.getName(), user.getEmail(), user.getGrade()+"", user.getPwd()};
		
		try{
			SqlHelper.executeQuery(sql, parameters);
		}catch(Exception e){
			b = false;
			e.printStackTrace();
		}
		
		return b;
		
	}
	
	//修改用户
	public boolean updUser(User user){
		
		boolean b = true;
		String sql = "update users set username=?,email=?,grade=?,passwd=? where id=?";
		String parameters[] = {user.getName(), user.getEmail(), user.getGrade()+"", user.getPwd(), user.getId()+""};
		
		try{
			SqlHelper.executeQuery(sql, parameters);
		}catch(Exception e){
			b = false;
			e.printStackTrace();
		}
		
		return b;
		
	}
	
	//删除用户
	public boolean delUser(String id){
		
		boolean b = true;
		String sql = "delete from users where id=?";
		String parameters[] = {id};
		
		try {
			SqlHelper.executeUpdate(sql, parameters);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			b = false;
			e.printStackTrace();
		}
		
		return b;
	}
	
	//写一个验证用户是否合法的函数
	public boolean checkUser(User user){
		
		boolean b = false;
		
		//使用SqlHelper来完成查询任务
		String sql = "select * from users where id=? and passwd=?";
		String parameters[] = {user.getId() + "", user.getPwd()};
		ResultSet rs = SqlHelper.executeQuery(sql, parameters);
		
		//根据rs来判断该用户是否存在
		try{
			if(rs.next()){
				b = true;
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			SqlHelper.close(rs, SqlHelper.getPs(), SqlHelper.getCt());
		}
		return b;
		
//		//连接数据库
//		try{
//			
//			//1，加载驱动
//			Class.forName("oracle.jdbc.driver.OracleDriver");
//			//2，得到连接
//			ct = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:orcl","scott","tiger");
//			//3，创建PreparedStatement
//			ps = ct.prepareStatement("select * from users where id=? and passwd=?");
//			//给?赋值
//			ps.setObject(1, user.getId());
//			ps.setObject(2, user.getPwd());
//			rs=ps.executeQuery();
//			if(rs.next()){
//				b = true;
//			}
//			
//		}catch(Exception e){
//			e.printStackTrace();
//		}finally{
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
//		return b;
	}
}
