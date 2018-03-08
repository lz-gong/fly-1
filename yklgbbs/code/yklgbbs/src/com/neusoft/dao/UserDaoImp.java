package com.neusoft.dao;

import java.io.File;
import java.sql.Connection;  //需要注意，还是用原来的，和mysql没有关系

import java.sql.PreparedStatement;  //需要注意，还是用原来的，和mysql没有关系
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.neusoft.bean.Userinfo;
import com.neusoft.utils.DBUtil;
import com.neusoft.utils.GradeUtil;
import com.neusoft.utils.TimeToStringUtil;

public class UserDaoImp implements IUserDao{
	public int checkEmailRepeat(String email) {
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		int num=0;
		
		try {
			conn=DBUtil.getInstance().getConnection();
			String sql="select email from tab_bbs_userinfo where email=?";
			ps=conn.prepareStatement(sql);
			ps.setString(1, email);
			rs=ps.executeQuery();
			if(rs.next()) {
				num=0;
				return num;
			}else {
				num=1;
				return num;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtil.getInstance().close(rs);
			DBUtil.getInstance().close(ps);
			DBUtil.getInstance().close(conn);
		}
		return 0;
	}
	
	@Override
	public int addUser(Userinfo user) {
		// TODO Auto-generated method stub
		Connection conn=null;
		PreparedStatement ps=null;
		int num=0;
		try {
			if(checkEmailRepeat(user.getEmail())==0) {  //返回值如果为0，说明有邮箱已被注册！
				return 0;
			}
			conn=DBUtil.getInstance().getConnection();
			String sql="insert into tab_bbs_userinfo(email,nickname,password,jointime,head_url,sign_time) values(?,?,?,?,?,?)";
			conn.setAutoCommit(false);
			ps=conn.prepareStatement(sql);
			ps.setString(1, user.getEmail());
			ps.setString(2, user.getNickname());
			ps.setString(3, user.getPassword());
			ps.setTimestamp(4,TimeToStringUtil.dateToTiemstamp(new Date()));
			ps.setString(5, user.getHeadUrl());
			ps.setDate(6, new java.sql.Date(946656000000l)); //设置默认签到时间为2000年1月1日
//			System.out.println(user.getEmail()+"  "+user.getNickname()+"  "+user.getPassword());
			num=ps.executeUpdate();
			conn.commit();
			if(num==1) {
				System.out.println("ok!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
				System.out.println("注册用户插入错误，已进行回滚！");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}finally {
			DBUtil.getInstance().close(ps);
			DBUtil.getInstance().close(conn);
		}
		return num;
	}
	

	@Override
	public Userinfo findUser(String email, String password) {
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		Userinfo user=null;
		
		try {
			conn=DBUtil.getInstance().getConnection();
			String sql="select * from tab_bbs_userinfo where email=? and password=?";
			ps=conn.prepareStatement(sql);
			ps.setString(1, email);
			ps.setString(2, password);
			rs=ps.executeQuery();
			if(rs.next()) {
				int id=rs.getInt("id");
				String email1=rs.getString("email");
				String nickname=rs.getString("nickname");
				String city=rs.getString("city");
				int sex=rs.getInt("sex");
				String head_url=rs.getString("head_url");
				String password1=rs.getString("password");
				String sign_name=rs.getString("sign_name");
				int kiss_num=rs.getInt("kiss_num");
				int isManager=rs.getInt("is_manager");
				Date signTime=new Date(rs.getTimestamp("sign_time").getTime());
				int experience=rs.getInt("experience");
				
				user=new Userinfo();  //这里重要
				user.setId(id);
				user.setEmail(email1);
				user.setNickname(nickname);
				user.setCity(city);
				user.setSex(sex);
				user.setHeadUrl(head_url);
				user.setPassword(password1);
				user.setSignName(sign_name);
				user.setKissNum(kiss_num);
				user.setIsManager(isManager);
				user.setSignTime(signTime);
				user.setExperience(experience);
				//下面这个属性不是在数据库中获取的
				user.setGrade(GradeUtil.getGrade(experience));
//				if(isManager==1) {
//					System.out.println("欢迎您管理员！");
//				}
				
//				System.out.println(user);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtil.getInstance().close(rs);
			DBUtil.getInstance().close(ps);
			DBUtil.getInstance().close(conn);
		}
		return user;
	}

	@Override
	public int setUserInfo(Userinfo userInfo) {
		Connection conn=null;
		PreparedStatement ps=null;
		int num=0;
		
		try {
			conn=DBUtil.getInstance().getConnection();
			conn.setAutoCommit(false);
			ps=conn.prepareStatement("UPDATE tab_bbs_userinfo\r\n" + 
					"set email=?,nickname=?,city=?,sex=?,sign_name=?\r\n" + 
					"where id=?");
			ps.setString(1, userInfo.getEmail());
			ps.setString(2, userInfo.getNickname());
			ps.setString(3, userInfo.getCity());
			ps.setInt(4, userInfo.getSex());
			ps.setString(5, userInfo.getSignName());
			ps.setInt(6, userInfo.getId());
			num=ps.executeUpdate();
			conn.commit();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println("修改个人信息出现错误，已回滚！");
			e.printStackTrace();
		}finally {
			DBUtil.getInstance().close(ps);
			DBUtil.getInstance().close(conn);
		}
		return num;
	}

	@Override
	public boolean changePass(int userid,String newpass) {
		Connection conn=null;
		PreparedStatement ps=null;
		boolean temp=false;
		
		try {
			conn=DBUtil.getInstance().getConnection();
			conn.setAutoCommit(false);
			ps=conn.prepareStatement("update tab_bbs_userinfo set password=? where id=?");
			ps.setString(1, newpass);
			ps.setInt(2, userid);
			int num=ps.executeUpdate();
			conn.commit();
			if(num==1) {
				temp=true;
				System.out.println("这是dao层，修改密码成功。");
			}
		} catch (Exception e) {
			try {
				conn.rollback();
				System.out.println("dao层修改密码出现错误，已进行回滚。");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally {
			DBUtil.getInstance().close(ps);
			DBUtil.getInstance().close(conn);
		}

		return temp;
	}

	@Override
	public boolean setHeadImg(int userid, String newHeadImg) {
		Connection conn=null;
		PreparedStatement ps=null;
		boolean temp=false;
		try {
			conn=DBUtil.getInstance().getConnection();
			conn.setAutoCommit(false);
			ps=conn.prepareStatement("update tab_bbs_userinfo\r\n" + 
					"set head_url=?\r\n" + 
					"where id=?");
			ps.setString(1, newHeadImg);
			ps.setInt(2, userid);
			int num=ps.executeUpdate();
			conn.commit();
			if(num==1) {
				temp=true;
			}
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println("数据库设置头像出现异常，已回滚！");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtil.getInstance().close(ps);
			DBUtil.getInstance().close(conn);
		}
		
		return temp;
	}

	//在这删除头像可能不正规，dao是不是只用来操作数据库的？
	@Override
	public boolean deleteoldHeadimg(String oldHeadImg) {
		File file=new File(oldHeadImg);
		return file.delete();
	}

	@Override
	public boolean decreaseKissNum(int userid, int deKissnum) {
		Connection conn=null;
		PreparedStatement ps=null;
		boolean temp=false;
		try {
			conn=DBUtil.getInstance().getConnection();
			conn.setAutoCommit(false);
			ps=conn.prepareStatement("update tab_bbs_userinfo\r\n" + 
					"set kiss_num=kiss_num-?\r\n" + 
					"where id=?");
			ps.setInt(1, deKissnum);
			ps.setInt(2, userid);
			int num=ps.executeUpdate();
			conn.commit();
			if(num==1) {
				temp=true;
			}
		} catch (Exception e) {
			try {
				conn.rollback();
				System.out.println("减少飞吻数失败，已进行回滚。这里是UserDaoImp");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally {
			DBUtil.getInstance().close(ps);
			DBUtil.getInstance().close(conn);
		}

		return temp;
	}

	@Override
	public boolean increaseKissNum(int userid, int inKissnum) {
		Connection conn=null;
		PreparedStatement ps=null;
		boolean temp=false;
		try {
			conn=DBUtil.getInstance().getConnection();
			conn.setAutoCommit(false);
			ps=conn.prepareStatement("update tab_bbs_userinfo\r\n" + 
					"set kiss_num=kiss_num+?\r\n" + 
					"where id=?");
			ps.setInt(1, inKissnum);
			ps.setInt(2, userid);
			int num=ps.executeUpdate();
			conn.commit();
			if(num==1) {
				temp=true;
			}
		} catch (Exception e) {
			try {
				conn.rollback();
				System.out.println("增加飞吻数失败，已进行回滚。这里是UserDaoImp");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally {
			DBUtil.getInstance().close(ps);
			DBUtil.getInstance().close(conn);
		}
		return temp;
	}

	@Override
	public boolean updateSignDate(int userid) {
		Connection conn=null;
		PreparedStatement ps=null;
		boolean temp=false;
		try {
			conn=DBUtil.getInstance().getConnection();
			conn.setAutoCommit(false);
			ps=conn.prepareStatement("update tab_bbs_userinfo\r\n" + 
					"set sign_time=?\r\n" + 
					"where id=?");
			ps.setTimestamp(1, TimeToStringUtil.dateToTiemstamp(new Date()));
			ps.setInt(2, userid);
			int num=ps.executeUpdate();
			conn.commit();
			if(num==1) {
				temp=true;
			}
		} catch (Exception e) {
			try {
				conn.rollback();
				System.out.println("更新签到时间失败，已进行回滚。用户id为："+userid+"这里是UserDaoImp");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally {
			DBUtil.getInstance().close(ps);
			DBUtil.getInstance().close(conn);
		}
		return temp;
	}

	@Override
	public boolean increaseExperience(int userid, int incExperience) {
		Connection conn=null;
		PreparedStatement ps=null;
		boolean temp=false;
		try {
			conn=DBUtil.getInstance().getConnection();
			conn.setAutoCommit(false);
			ps=conn.prepareStatement("update tab_bbs_userinfo\r\n" + 
					"set experience=experience+?\r\n" + 
					"where  id=?");
			ps.setInt(1, incExperience);
			ps.setInt(2, userid);
			int num=ps.executeUpdate();
			conn.commit();
			if(num==1) {
				temp=true;
			}
		} catch (Exception e) {
			try {
				conn.rollback();
				System.out.println("给用户增加经验失败，已进行回滚。用户id为："+userid+"这里是UserDaoImp");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally {
			DBUtil.getInstance().close(ps);
			DBUtil.getInstance().close(conn);
		}
		return temp;
	}

}
