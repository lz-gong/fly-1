package com.neusoft.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.neusoft.bean.Userinfo;
import com.neusoft.utils.DBUtil;
import com.neusoft.utils.GradeUtil;
import com.neusoft.utils.TimeToStringUtil;

public class HomeDaoImp implements IHomeDao{

	@Override
	public Userinfo getHomeUserinfo(int userId) {
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		Userinfo userinfo=null;
		
		try {
			conn=DBUtil.getInstance().getConnection();
			ps=conn.prepareStatement("select nickname,city,sex,head_url,sign_name,kiss_num,jointime,is_manager,experience\r\n" + 
					"from tab_bbs_userinfo\r\n" + 
					"where id=?");
			ps.setInt(1, userId);
			rs=ps.executeQuery();
			if(rs.next()) {
				userinfo=new Userinfo();
				userinfo.setCity(rs.getString("city"));
				userinfo.setHeadUrl(rs.getString("head_url"));
				userinfo.setJoinTime(TimeToStringUtil.timestampToStringtime(rs.getTimestamp("jointime")));
				userinfo.setKissNum(rs.getInt("kiss_num"));
				userinfo.setNickname(rs.getString("nickname"));
				userinfo.setSex(rs.getInt("sex"));
				userinfo.setSignName(rs.getString("sign_name"));
				userinfo.setIsManager(rs.getInt("is_manager"));
				int experience=rs.getInt("experience");
				userinfo.setExperience(experience);
				userinfo.setGrade(GradeUtil.getGrade(experience));
//				System.out.println("您跳转的用户主页存在！");
			}
//			System.out.println(userinfo.getCity());
//			System.out.println(userinfo.getKissNum());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtil.getInstance().close(rs);
			DBUtil.getInstance().close(ps);
			DBUtil.getInstance().close(conn);
		}
		return userinfo;
	}

}
