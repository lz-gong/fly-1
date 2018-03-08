package com.neusoft.utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class DBUtil {

	private static DBUtil util;
	private String driver;
	private String username;
	private String password;
	private String url;
	
	private DBUtil(){
		Properties pro = new Properties();
		try {
			pro.load(this.getClass().getClassLoader().getResourceAsStream("jdbc.properties"));
			driver = pro.getProperty("driver");
			username = pro.getProperty("username");
			password = pro.getProperty("password");
			url = pro.getProperty("url");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static DBUtil getInstance(){
		
		if(util == null){
			util = new DBUtil();
		}
		
		return util;
	}
	
	public Connection getConnection() throws Exception{
		Class.forName(driver);
		Connection conn  = DriverManager.getConnection(url,username,password);
		return conn;
	}
	
	public void close(Connection conn){
		if(null != conn){
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void close(PreparedStatement stat){
		if(null!= stat) {
			try {
				stat.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void close(ResultSet rs){
		if(null != rs) {
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
