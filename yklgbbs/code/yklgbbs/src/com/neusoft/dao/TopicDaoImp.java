package com.neusoft.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.neusoft.bean.Category;
import com.neusoft.bean.CommentEx;
import com.neusoft.bean.TopicInfoEx;
import com.neusoft.bean.Topicinfo;
import com.neusoft.utils.DBUtil;
import com.neusoft.utils.TimeToStringUtil;

public class TopicDaoImp implements ITopicDao{

	@Override
	public List<Category> getCategoryInfo() {
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<Category> list=new ArrayList<Category>();
		
		try {
			conn=DBUtil.getInstance().getConnection();
			ps=conn.prepareStatement("select * from tab_bbs_category");
			rs=ps.executeQuery();
			while(rs.next()) {
				Category c=new Category();
				c.setId(rs.getInt("id"));
				c.setClassname(rs.getString("classname"));
				list.add(c);
//				System.out.println(c.getId());
//				System.out.println(c.getClassname());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtil.getInstance().close(rs);
			DBUtil.getInstance().close(ps);
			DBUtil.getInstance().close(conn);
		}
		return list;
	}

	@Override
	public int addTopic(Topicinfo topicinfo) {
		Connection conn=null;
		PreparedStatement ps=null;
		int num=0;
		
		try {
			conn=DBUtil.getInstance().getConnection();
			conn.setAutoCommit(false);
			
			ps=conn.prepareStatement("insert into tab_bbs_topicinfo(title,content,createtime,category_id,view_count,userid,is_good,is_end,reward_kiss) values(?,?,?,?,?,?,?,?,?)");
			String title = topicinfo.getTitle();
			String content = topicinfo.getContent();
			//1：首先获取java.util.Date类型的
			Date createtime = topicinfo.getCreatetime(); 
			
//			//2：然后获取根据java.util.Date获取java.sql.Timestamp日期类型
//			java.sql.Timestamp dates=new java.sql.Timestamp(createtime.getTime()); 
			
			int category_id = topicinfo.getCategoryId();
			int view_count = topicinfo.getViewCount();
			int userid = topicinfo.getUserid();
			int is_good = topicinfo.getIsGood();
			int is_end = topicinfo.getIsEnd();
			int reward_kiss = topicinfo.getRewardKiss();
			
			ps.setString(1, title);
			ps.setString(2, content);
			//3：通过ps.setTimestamp()将dates存入数据库
			ps.setTimestamp(3, TimeToStringUtil.dateToTiemstamp(createtime));
			ps.setInt(4, category_id);
			ps.setInt(5, view_count);
			ps.setInt(6, userid);
			ps.setInt(7, is_good);
			ps.setInt(8, is_end);
			ps.setInt(9, reward_kiss);

			
			num=ps.executeUpdate();
			
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
				System.out.println("插入或更新数据错误，已回滚");
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
	public List<TopicInfoEx> getAllTopic() {
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<TopicInfoEx> list=new ArrayList<TopicInfoEx>();
		try {
			conn=DBUtil.getInstance().getConnection();
			ps=conn.prepareStatement("select tbt.userid,tbt.id,head_url,title,nickname,createtime,classname,view_count\r\n" + 
					"from tab_bbs_userinfo tbu\r\n" + 
					"join tab_bbs_topicinfo tbt\r\n" + 
					"on tbu.id=tbt.userid\r\n" + 
					"join tab_bbs_category tbc\r\n" + 
					"on tbt.category_id=tbc.id");
			rs=ps.executeQuery();
			while(rs.next()) {
				int userId=rs.getInt("userid");  //获取发表本帖子的用户的id
				String headUrl=rs.getString("head_url");
				String title =rs.getString("title");
				String nickName = rs.getString("nickname");
				
				//首先获取Timestamp对象
				Timestamp createTime=rs.getTimestamp("createtime"); 
				
				
//				//将其转换为java.util.Date
//				Date date=new Date(createTime.getTime());
//				//附加操作：此时就可以转换为字符串了，含时分秒
//				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//				String newCreatTime=sdf.format(date);
				
				
				String classname=rs.getString("classname");
				int viewCount=rs.getInt("view_count");
				int commentCount=0;
				int id=rs.getInt("id");
				
				
				//这里开始存入对象中
				TopicInfoEx topicInfoEx=new TopicInfoEx();
				topicInfoEx.setUserId(userId);
				topicInfoEx.setHeadUrl(headUrl);
				topicInfoEx.setTitle(title);
				topicInfoEx.setNickName(nickName);
				topicInfoEx.setCreateTime(TimeToStringUtil.timestampToStringtime(createTime));
				topicInfoEx.setClassname(classname);
				topicInfoEx.setViewCount(viewCount);
				topicInfoEx.setCommentCount(commentCount);
				topicInfoEx.setId(id);
				
				list.add(topicInfoEx);
			}
			System.out.println("总共有"+list.size()+"条帖子！");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtil.getInstance().close(rs);
			DBUtil.getInstance().close(ps);
			DBUtil.getInstance().close(conn);
		}
		return list;
	}

	@Override
	public TopicInfoEx topicShowView(int id) {
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		TopicInfoEx topicInfoEx=null;
		try {
			conn=DBUtil.getInstance().getConnection();
			ps=conn.prepareStatement("select content,tbt.id id1,head_url,title,nickname,createtime,classname,view_count,reward_kiss,is_top,is_good,is_end,tbu.id id2\r\n" + 
					"from tab_bbs_userinfo tbu\r\n" + 
					"join tab_bbs_topicinfo tbt\r\n" + 
					"on tbu.id=tbt.userid\r\n" + 
					"join tab_bbs_category tbc\r\n" + 
					"on tbt.category_id=tbc.id\r\n" + 
					"where tbt.id=?");
			ps.setInt(1, id);
			rs=ps.executeQuery();
			
			if(rs.next()) {
				String headUrl=rs.getString("head_url");
				String title =rs.getString("title");
				String nickName = rs.getString("nickname");
				
				//首先获取Timestamp对象
				Timestamp createTime=rs.getTimestamp("createtime"); 
//				//将其转换为java.util.Date
//				Date date=new Date(createTime.getTime());
//				//附加操作：此时就可以转换为字符串了，含时分秒
//				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String newCreatTime=TimeToStringUtil.timestampToStringtime(createTime);
				
				String classname=rs.getString("classname");
				int viewCount=rs.getInt("view_count");
				int commentCount=0;
				int id1=rs.getInt("id1");
				String content=rs.getString("content");
				int rewardKiss=rs.getInt("reward_kiss");
				
				
				//这里开始存入对象中
				topicInfoEx=new TopicInfoEx();
				topicInfoEx.setHeadUrl(headUrl);
				topicInfoEx.setTitle(title);
				topicInfoEx.setNickName(nickName);
				topicInfoEx.setCreateTime(newCreatTime);
				topicInfoEx.setClassname(classname);
				topicInfoEx.setViewCount(viewCount);
				topicInfoEx.setCommentCount(commentCount);
				topicInfoEx.setId(id1);
				topicInfoEx.setContent(content);
				topicInfoEx.setRewardKiss(rewardKiss);
				topicInfoEx.setIsTop(rs.getInt("is_top"));
				topicInfoEx.setIsGood(rs.getInt("is_good"));
				topicInfoEx.setIsEnd(rs.getInt("is_end"));
				topicInfoEx.setUserId(rs.getInt("id2"));
//				System.out.println("作者id为"+rs.getInt("id2"));
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtil.getInstance().close(rs);
			DBUtil.getInstance().close(ps);
			DBUtil.getInstance().close(conn);
		}
		return topicInfoEx;
	}

	@Override
	public void viewConutIncrement(int id) {
		Connection conn=null;
		PreparedStatement ps=null;
		
		try {
			conn=DBUtil.getInstance().getConnection();
			conn.setAutoCommit(false);
			ps=conn.prepareStatement("update tab_bbs_topicinfo\r\n" + 
					"set view_count=view_count+1\r\n" + 
					"where id=?");
			ps.setInt(1, id);
			ps.executeUpdate();
			conn.commit();
		} catch (Exception e) {
			
				try {
					conn.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			System.out.println("插入或更新数据错误，已回滚");
		}finally {
			DBUtil.getInstance().close(ps);
			DBUtil.getInstance().close(conn);
		}
	}

	@Override
	public int getTopicNum() {
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		int num=0;
		try {
			conn=DBUtil.getInstance().getConnection();
			ps=conn.prepareStatement("select count(*) as num from tab_bbs_topicinfo");
			rs=ps.executeQuery();
			if(rs.next()) {
				num=rs.getInt("num");
				System.out.println("总共有："+num+"条帖子！");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtil.getInstance().close(rs);
			DBUtil.getInstance().close(ps);
			DBUtil.getInstance().close(conn);
		}
		return num;
	}

	@Override
	public List<TopicInfoEx> getTopicInfoExList(int pageIndex, int pageSize) {
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<TopicInfoEx> list=new ArrayList<TopicInfoEx>();
		try {
			conn=DBUtil.getInstance().getConnection();
			ps=conn.prepareStatement("select tbt.userid,tbt.id,head_url,title,nickname,createtime,classname,view_count,is_good\r\n" + 
					"from tab_bbs_userinfo tbu\r\n" + 
					"join tab_bbs_topicinfo tbt\r\n" + 
					"on tbu.id=tbt.userid\r\n" + 
					"join tab_bbs_category tbc\r\n" + 
					"on tbt.category_id=tbc.id\r\n" + 
					"ORDER BY tbt.id DESC limit	?,?");
			ps.setInt(1, pageIndex*pageSize);
			ps.setInt(2, pageSize);
			rs=ps.executeQuery();
			while(rs.next()) {
				int userId=rs.getInt("userid");  //获取发表本帖子的用户的id
				String headUrl=rs.getString("head_url");
				String title =rs.getString("title");
				String nickName = rs.getString("nickname");
				
				//首先获取Timestamp对象
				Timestamp createTime=rs.getTimestamp("createtime"); 

				String classname=rs.getString("classname");
				int viewCount=rs.getInt("view_count");
				int commentCount=0;
				int id=rs.getInt("id");
				int isGood=rs.getInt("is_good");
				
				
				//这里开始存入对象中
				TopicInfoEx topicInfoEx=new TopicInfoEx();
				topicInfoEx.setUserId(userId);
				topicInfoEx.setHeadUrl(headUrl);
				topicInfoEx.setTitle(title);
				topicInfoEx.setNickName(nickName);
				topicInfoEx.setCreateTime(TimeToStringUtil.timestampToStringtime(createTime));
				topicInfoEx.setClassname(classname);
				topicInfoEx.setViewCount(viewCount);
				topicInfoEx.setCommentCount(commentCount);
				topicInfoEx.setId(id);
				topicInfoEx.setIsGood(isGood);
				
				list.add(topicInfoEx);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtil.getInstance().close(rs);
			DBUtil.getInstance().close(ps);
			DBUtil.getInstance().close(conn);
		}
		return list;
	}

	@Override
	public List<TopicInfoEx> getTopTopicInfoExList() {
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<TopicInfoEx> list=new ArrayList<TopicInfoEx>();
		try {
			conn=DBUtil.getInstance().getConnection();
			ps=conn.prepareStatement("select tbt.userid,tbt.id,head_url,title,nickname,createtime,classname,view_count,is_good\r\n" + 
					"from tab_bbs_userinfo tbu\r\n" + 
					"join tab_bbs_topicinfo tbt\r\n" + 
					"on tbu.id=tbt.userid\r\n" + 
					"join tab_bbs_category tbc\r\n" + 
					"on tbt.category_id=tbc.id\r\n" + 
					"where is_top=1\r\n" + 
					"ORDER BY tbt.id DESC");
			rs=ps.executeQuery();
			while(rs.next()) {
				int userId=rs.getInt("userid");  //获取发表本帖子的用户的id
				String headUrl=rs.getString("head_url");
				String title =rs.getString("title");
				String nickName = rs.getString("nickname");
				
				//首先获取Timestamp对象
				Timestamp createTime=rs.getTimestamp("createtime"); 

				String classname=rs.getString("classname");
				int viewCount=rs.getInt("view_count");
				int commentCount=0;
				int id=rs.getInt("id");
				int isGood=rs.getInt("is_good");
				
				
				//这里开始存入对象中
				TopicInfoEx topicInfoEx=new TopicInfoEx();
				topicInfoEx.setUserId(userId);
				topicInfoEx.setHeadUrl(headUrl);
				topicInfoEx.setTitle(title);
				topicInfoEx.setNickName(nickName);
				topicInfoEx.setCreateTime(TimeToStringUtil.timestampToStringtime(createTime));
				topicInfoEx.setClassname(classname);
				topicInfoEx.setViewCount(viewCount);
				topicInfoEx.setCommentCount(commentCount);
				topicInfoEx.setId(id);
				topicInfoEx.setIsGood(isGood);
				
				list.add(topicInfoEx);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtil.getInstance().close(rs);
			DBUtil.getInstance().close(ps);
			DBUtil.getInstance().close(conn);
		}
		return list;
	}

	@Override
	public boolean deleteTopic(int topicid) {
		Connection conn=null;
		PreparedStatement ps=null;
		boolean temp=false;
		try {
			conn=DBUtil.getInstance().getConnection();
			conn.setAutoCommit(false);
			ps=conn.prepareStatement("delete tab_bbs_topicinfo,tab_bbs_comment \r\n" + 
					"from tab_bbs_topicinfo\r\n" + 
					"LEFT JOIN tab_bbs_comment\r\n" + 
					"on tab_bbs_topicinfo.id=tab_bbs_comment.topic_or_comment_id\r\n" + 
					"where tab_bbs_topicinfo.id=? or (tab_bbs_topicinfo.id=? and tab_bbs_comment.is_topic=0)");
			ps.setInt(1, topicid);
			ps.setInt(2, topicid);
			int num=ps.executeUpdate();
			conn.commit();
			System.out.println("删除了1个帖子"+(num-1)+"条评论");
			if(num>0) {
				temp=true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
				System.out.println("删除帖子发生错误，已回滚！");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}finally {
			DBUtil.getInstance().close(ps);
			DBUtil.getInstance().close(conn);
		}
		return temp;
	}

	@Override
	public boolean setTopicTopGood(int topicid, int isTop, int isGood) {
		Connection conn=null;
		PreparedStatement ps=null;
		boolean tmep=false;
		try {
			conn=DBUtil.getInstance().getConnection();
			conn.setAutoCommit(false);
			ps=conn.prepareStatement("update tab_bbs_topicinfo\r\n" + 
					"set is_top=?,is_good=?\r\n" + 
					"where id=?");
			ps.setInt(1, isTop);
			ps.setInt(2, isGood);
			ps.setInt(3, topicid);
			int num=ps.executeUpdate();
			if(num==1) {
				tmep=true;
			}
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
				System.out.println("加精顶置出现错误，已回滚！");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}finally {
			DBUtil.getInstance().close(ps);
			DBUtil.getInstance().close(conn);
		}
		return tmep;
	}

	@Override
	public boolean checkTopicBelongAuthor(int topicid, int userid) {
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		boolean temp=false;
		try {
			conn=DBUtil.getInstance().getConnection();
			ps=conn.prepareStatement("select id\r\n" + 
					"from tab_bbs_topicinfo\r\n" + 
					"where id=? and userid=?");
			ps.setInt(1, topicid);
			ps.setInt(2, userid);
			rs=ps.executeQuery();
			if(rs.next()) {
				temp=true;
				System.out.println("此帖子属于当前用户，信息验证成功！");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtil.getInstance().close(rs);
			DBUtil.getInstance().close(ps);
			DBUtil.getInstance().close(conn);
		}
		return temp;
	}

	@Override
	public boolean editTopic(int topicid,String title,String content) {
		Connection conn=null;
		PreparedStatement ps=null;
		boolean temp=false;
		try {
			conn=DBUtil.getInstance().getConnection();
			conn.setAutoCommit(false);
			ps=conn.prepareStatement("update tab_bbs_topicinfo\r\n" + 
					"set title=?,content=?\r\n" + 
					"where id=?");
			ps.setString(1, title);
			ps.setString(2, content);
			ps.setInt(3, topicid);
			int num=ps.executeUpdate();
			if(num==1) {
				temp=true;
			}
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
				System.out.println("编辑帖子出现错误，已回滚！");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}finally {
			DBUtil.getInstance().close(ps);
			DBUtil.getInstance().close(conn);
		}
		return temp;
	}

	@Override
	public TopicInfoEx getEditTopic(int topicid) {
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		TopicInfoEx topicInfoEx=null;
		try {
			conn=DBUtil.getInstance().getConnection();
			ps=conn.prepareStatement("select title,content,classname,reward_kiss\r\n" + 
					"from tab_bbs_topicinfo tbt,tab_bbs_category tbc\r\n" + 
					"where tbt.category_id=tbc.id and tbt.id=?");
			ps.setInt(1, topicid);
			rs=ps.executeQuery();
			if(rs.next()) {
				topicInfoEx=new TopicInfoEx();
				topicInfoEx.setTitle(rs.getString("title"));
				topicInfoEx.setContent(rs.getString("content"));
				topicInfoEx.setClassname(rs.getString("classname"));
				topicInfoEx.setRewardKiss(rs.getInt("reward_kiss"));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtil.getInstance().close(rs);
			DBUtil.getInstance().close(ps);
			DBUtil.getInstance().close(conn);
		}
		return topicInfoEx;
	}

	@Override
	public boolean findTopic(int topicid) {
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		boolean temp=false;
		try {
			conn=DBUtil.getInstance().getConnection();
			ps=conn.prepareStatement("select id\r\n" + 
					"from tab_bbs_topicinfo\r\n" + 
					"where id=?");
			ps.setInt(1, topicid);
			rs=ps.executeQuery();
			temp=rs.next();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtil.getInstance().close(rs);
			DBUtil.getInstance().close(ps);
			DBUtil.getInstance().close(conn);
		}
		return temp;
	}

	@Override
	public boolean collectTopic(int topicid, int userid) {
		Connection conn=null;
		PreparedStatement ps=null;
		boolean temp=false;
		try {
			conn=DBUtil.getInstance().getConnection();
			conn.setAutoCommit(false);
			ps=conn.prepareStatement("insert into tab_bbs_collect(userid,topicid,collect_time) values(?,?,?)");
			ps.setInt(1, userid);
			ps.setInt(2, topicid);
			ps.setTimestamp(3, TimeToStringUtil.dateToTiemstamp(new Date()));
			int num=ps.executeUpdate();
			if(num==1) {
				temp=true;
			}
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
				System.out.println("收藏帖子失败，已回滚！");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}finally {
			DBUtil.getInstance().close(ps);
			DBUtil.getInstance().close(conn);
		}
		return temp;
	}

	@Override
	public boolean cancleCollectTopic(int topicid, int userid) {
		Connection conn=null;
		PreparedStatement ps=null;
		boolean temp=false;
		try {
			conn=DBUtil.getInstance().getConnection();
			conn.setAutoCommit(false);
			ps=conn.prepareStatement("delete from tab_bbs_collect\r\n" + 
					"where userid=? and topicid=?");
			ps.setInt(1, userid);
			ps.setInt(2, topicid);
			int num=ps.executeUpdate();
			if(num==1) {
				temp=true;
			}
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
				System.out.println("取消收藏帖子失败，已回滚！");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}finally {
			DBUtil.getInstance().close(ps);
			DBUtil.getInstance().close(conn);
		}
		return temp;
	}

	@Override
	public boolean checkRepeatCollect(int topicid, int userid) {
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		boolean temp=false;
		try {
			conn=DBUtil.getInstance().getConnection();
			ps=conn.prepareStatement("select id\r\n" + 
					"from tab_bbs_collect\r\n" + 
					"where userid=? and topicid=?");
			ps.setInt(1, userid);
			ps.setInt(2, topicid);
			rs=ps.executeQuery();
			temp=rs.next();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtil.getInstance().close(rs);
			DBUtil.getInstance().close(ps);
			DBUtil.getInstance().close(conn);
		}
		return temp;
	}

	@Override
	public boolean checkTopicIsend(int topicid) {
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		boolean temp=false;
		try {
			conn=DBUtil.getInstance().getConnection();
			ps=conn.prepareStatement("select is_end\r\n" + 
					"from tab_bbs_topicinfo\r\n" + 
					"where id=?");
			ps.setInt(1, topicid);
			rs=ps.executeQuery();
			if(rs.next()) {
				if(rs.getInt("is_end")==0) {
					temp=false;
					System.out.println("获取评论过程中，验证帖子未结贴~~~");
				}else if(rs.getInt("is_end")==1) {
					temp=true;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtil.getInstance().close(rs);
			DBUtil.getInstance().close(ps);
			DBUtil.getInstance().close(conn);
		}
		return temp;
	}

	@Override
	public boolean checkTopicIsaccepted(int topicid) {
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		boolean temp=false;
		try {
			conn=DBUtil.getInstance().getConnection();
			ps=conn.prepareStatement("select id\r\n" + 
					"from tab_bbs_comment\r\n" + 
					"where topic_or_comment_id=? and is_accepted=1");
			ps.setInt(1, topicid);
			rs=ps.executeQuery();
			if(rs.next()) {
				temp=true;
				System.out.println("获取评论过程中，验证帖子已采纳最佳评论~~~");		
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtil.getInstance().close(rs);
			DBUtil.getInstance().close(ps);
			DBUtil.getInstance().close(conn);
		}
		return temp;
	}

	@Override
	public int geTopictRewardKiss(int topicid) {
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		int temp=0;
		try {
			conn=DBUtil.getInstance().getConnection();
			ps=conn.prepareStatement("select reward_kiss\r\n" + 
					"from tab_bbs_topicinfo\r\n" + 
					"where id=?");
			ps.setInt(1, topicid);
			rs=ps.executeQuery();
			if(rs.next()) {
				temp=rs.getInt("reward_kiss");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtil.getInstance().close(rs);
			DBUtil.getInstance().close(ps);
			DBUtil.getInstance().close(conn);
		}
		return temp;
	}

	@Override
	public List<TopicInfoEx> getNotAcceptTopicInfoExList(int pageIndex, int pageSize) {
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<TopicInfoEx> list=new ArrayList<TopicInfoEx>();
		try {
			conn=DBUtil.getInstance().getConnection();
			ps=conn.prepareStatement("select tbt.userid,tbt.id,head_url,title,nickname,createtime,classname,view_count,is_good\r\n" + 
					"from tab_bbs_userinfo tbu\r\n" + 
					"join tab_bbs_topicinfo tbt\r\n" + 
					"on tbu.id=tbt.userid\r\n" + 
					"join tab_bbs_category tbc\r\n" + 
					"on tbt.category_id=tbc.id\r\n" + 
					"where is_accepted=0\r\n" + 
					"ORDER BY tbt.id DESC limit	?,?");
			ps.setInt(1, pageIndex*pageSize);
			ps.setInt(2, pageSize);
			rs=ps.executeQuery();
			while(rs.next()) {
				int userId=rs.getInt("userid");  //获取发表本帖子的用户的id
				String headUrl=rs.getString("head_url");
				String title =rs.getString("title");
				String nickName = rs.getString("nickname");
				
				//首先获取Timestamp对象
				Timestamp createTime=rs.getTimestamp("createtime"); 

				String classname=rs.getString("classname");
				int viewCount=rs.getInt("view_count");
				int commentCount=0;
				int id=rs.getInt("id");
				int isGood=rs.getInt("is_good");
				
				
				//这里开始存入对象中
				TopicInfoEx topicInfoEx=new TopicInfoEx();
				topicInfoEx.setUserId(userId);
				topicInfoEx.setHeadUrl(headUrl);
				topicInfoEx.setTitle(title);
				topicInfoEx.setNickName(nickName);
				topicInfoEx.setCreateTime(TimeToStringUtil.timestampToStringtime(createTime));
				topicInfoEx.setClassname(classname);
				topicInfoEx.setViewCount(viewCount);
				topicInfoEx.setCommentCount(commentCount);
				topicInfoEx.setId(id);
				topicInfoEx.setIsGood(isGood);
				
				list.add(topicInfoEx);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtil.getInstance().close(rs);
			DBUtil.getInstance().close(ps);
			DBUtil.getInstance().close(conn);
		}
		return list;
	}

	@Override
	public List<TopicInfoEx> getAcceptTopicInfoExList(int pageIndex, int pageSize) {
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<TopicInfoEx> list=new ArrayList<TopicInfoEx>();
		try {
			conn=DBUtil.getInstance().getConnection();
			ps=conn.prepareStatement("select tbt.userid,tbt.id,head_url,title,nickname,createtime,classname,view_count,is_good\r\n" + 
					"from tab_bbs_userinfo tbu\r\n" + 
					"join tab_bbs_topicinfo tbt\r\n" + 
					"on tbu.id=tbt.userid\r\n" + 
					"join tab_bbs_category tbc\r\n" + 
					"on tbt.category_id=tbc.id\r\n" + 
					"where is_accepted=1\r\n" + 
					"ORDER BY tbt.id DESC limit	?,?");
			ps.setInt(1, pageIndex*pageSize);
			ps.setInt(2, pageSize);
			rs=ps.executeQuery();
			while(rs.next()) {
				int userId=rs.getInt("userid");  //获取发表本帖子的用户的id
				String headUrl=rs.getString("head_url");
				String title =rs.getString("title");
				String nickName = rs.getString("nickname");
				
				//首先获取Timestamp对象
				Timestamp createTime=rs.getTimestamp("createtime"); 

				String classname=rs.getString("classname");
				int viewCount=rs.getInt("view_count");
				int commentCount=0;
				int id=rs.getInt("id");
				int isGood=rs.getInt("is_good");
				
				
				//这里开始存入对象中
				TopicInfoEx topicInfoEx=new TopicInfoEx();
				topicInfoEx.setUserId(userId);
				topicInfoEx.setHeadUrl(headUrl);
				topicInfoEx.setTitle(title);
				topicInfoEx.setNickName(nickName);
				topicInfoEx.setCreateTime(TimeToStringUtil.timestampToStringtime(createTime));
				topicInfoEx.setClassname(classname);
				topicInfoEx.setViewCount(viewCount);
				topicInfoEx.setCommentCount(commentCount);
				topicInfoEx.setId(id);
				topicInfoEx.setIsGood(isGood);
				
				list.add(topicInfoEx);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtil.getInstance().close(rs);
			DBUtil.getInstance().close(ps);
			DBUtil.getInstance().close(conn);
		}
		return list;
	}

	@Override
	public List<TopicInfoEx> getNiceTopicInfoExList(int pageIndex, int pageSize) {
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<TopicInfoEx> list=new ArrayList<TopicInfoEx>();
		try {
			conn=DBUtil.getInstance().getConnection();
			ps=conn.prepareStatement("select tbt.userid,tbt.id,head_url,title,nickname,createtime,classname,view_count,is_good\r\n" + 
					"from tab_bbs_userinfo tbu\r\n" + 
					"join tab_bbs_topicinfo tbt\r\n" + 
					"on tbu.id=tbt.userid\r\n" + 
					"join tab_bbs_category tbc\r\n" + 
					"on tbt.category_id=tbc.id\r\n" + 
					"where is_good=1\r\n" + 
					"ORDER BY tbt.id DESC limit	?,?");
			ps.setInt(1, pageIndex*pageSize);
			ps.setInt(2, pageSize);
			rs=ps.executeQuery();
			while(rs.next()) {
				int userId=rs.getInt("userid");  //获取发表本帖子的用户的id
				String headUrl=rs.getString("head_url");
				String title =rs.getString("title");
				String nickName = rs.getString("nickname");
				
				//首先获取Timestamp对象
				Timestamp createTime=rs.getTimestamp("createtime"); 

				String classname=rs.getString("classname");
				int viewCount=rs.getInt("view_count");
				int commentCount=0;
				int id=rs.getInt("id");
				int isGood=rs.getInt("is_good");
				
				
				//这里开始存入对象中
				TopicInfoEx topicInfoEx=new TopicInfoEx();
				topicInfoEx.setUserId(userId);
				topicInfoEx.setHeadUrl(headUrl);
				topicInfoEx.setTitle(title);
				topicInfoEx.setNickName(nickName);
				topicInfoEx.setCreateTime(TimeToStringUtil.timestampToStringtime(createTime));
				topicInfoEx.setClassname(classname);
				topicInfoEx.setViewCount(viewCount);
				topicInfoEx.setCommentCount(commentCount);
				topicInfoEx.setId(id);
				topicInfoEx.setIsGood(isGood);
				
				list.add(topicInfoEx);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtil.getInstance().close(rs);
			DBUtil.getInstance().close(ps);
			DBUtil.getInstance().close(conn);
		}
		return list;
	}

	@Override
	public List<TopicInfoEx> getMyTopicTopicInfoExList(int pageIndex, int pageSize,int userid) {
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<TopicInfoEx> list=new ArrayList<TopicInfoEx>();
		try {
			conn=DBUtil.getInstance().getConnection();
			ps=conn.prepareStatement("select tbt.userid,tbt.id,head_url,title,nickname,createtime,classname,view_count,is_good\r\n" + 
					"from tab_bbs_userinfo tbu\r\n" + 
					"join tab_bbs_topicinfo tbt\r\n" + 
					"on tbu.id=tbt.userid\r\n" + 
					"join tab_bbs_category tbc\r\n" + 
					"on tbt.category_id=tbc.id\r\n" + 
					"where userid=?\r\n" + 
					"ORDER BY tbt.id DESC limit	?,?");
			ps.setInt(1, userid);
			ps.setInt(2, pageIndex*pageSize);
			ps.setInt(3, pageSize);
			rs=ps.executeQuery();
			while(rs.next()) {
				int userId=rs.getInt("userid");  //获取发表本帖子的用户的id
				String headUrl=rs.getString("head_url");
				String title =rs.getString("title");
				String nickName = rs.getString("nickname");
				
				//首先获取Timestamp对象
				Timestamp createTime=rs.getTimestamp("createtime"); 

				String classname=rs.getString("classname");
				int viewCount=rs.getInt("view_count");
				int commentCount=0;
				int id=rs.getInt("id");
				int isGood=rs.getInt("is_good");
				
				
				//这里开始存入对象中
				TopicInfoEx topicInfoEx=new TopicInfoEx();
				topicInfoEx.setUserId(userId);
				topicInfoEx.setHeadUrl(headUrl);
				topicInfoEx.setTitle(title);
				topicInfoEx.setNickName(nickName);
				topicInfoEx.setCreateTime(TimeToStringUtil.timestampToStringtime(createTime));
				topicInfoEx.setClassname(classname);
				topicInfoEx.setViewCount(viewCount);
				topicInfoEx.setCommentCount(commentCount);
				topicInfoEx.setId(id);
				topicInfoEx.setIsGood(isGood);
				
				list.add(topicInfoEx);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtil.getInstance().close(rs);
			DBUtil.getInstance().close(ps);
			DBUtil.getInstance().close(conn);
		}
		return list;
	}

	@Override
	public List<TopicInfoEx> getSearchTopicInfoExList(int pageIndex, int pageSize, String SearchStr) {
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<TopicInfoEx> list=new ArrayList<TopicInfoEx>();
		try {
			conn=DBUtil.getInstance().getConnection();
			ps=conn.prepareStatement("select tbt.userid,tbt.id,head_url,title,nickname,createtime,classname,view_count,is_good\r\n" + 
					"from tab_bbs_userinfo tbu\r\n" + 
					"join tab_bbs_topicinfo tbt\r\n" + 
					"on tbu.id=tbt.userid\r\n" + 
					"join tab_bbs_category tbc\r\n" + 
					"on tbt.category_id=tbc.id\r\n" + 
					"where title like ?\r\n" + 
					"ORDER BY tbt.id DESC limit	?,?\r\n" + 
					"");
			ps.setString(1, "%"+SearchStr+"%");
			ps.setInt(2, pageIndex*pageSize);
			ps.setInt(3, pageSize);
			rs=ps.executeQuery();
			while(rs.next()) {
				int userId=rs.getInt("userid");  //获取发表本帖子的用户的id
				String headUrl=rs.getString("head_url");
				String title =rs.getString("title");
				String nickName = rs.getString("nickname");
				
				//首先获取Timestamp对象
				Timestamp createTime=rs.getTimestamp("createtime"); 

				String classname=rs.getString("classname");
				int viewCount=rs.getInt("view_count");
				int commentCount=0;
				int id=rs.getInt("id");
				int isGood=rs.getInt("is_good");
				
				
				//这里开始存入对象中
				TopicInfoEx topicInfoEx=new TopicInfoEx();
				topicInfoEx.setUserId(userId);
				topicInfoEx.setHeadUrl(headUrl);
				topicInfoEx.setTitle(title);
				topicInfoEx.setNickName(nickName);
				topicInfoEx.setCreateTime(TimeToStringUtil.timestampToStringtime(createTime));
				topicInfoEx.setClassname(classname);
				topicInfoEx.setViewCount(viewCount);
				topicInfoEx.setCommentCount(commentCount);
				topicInfoEx.setId(id);
				topicInfoEx.setIsGood(isGood);
				
				list.add(topicInfoEx);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtil.getInstance().close(rs);
			DBUtil.getInstance().close(ps);
			DBUtil.getInstance().close(conn);
		}
		return list;
	}

	@Override
	public int getNotAcceptTopicNum() {
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		int num=0;
		try {
			conn=DBUtil.getInstance().getConnection();
			ps=conn.prepareStatement("select count(id) num\r\n" + 
					"from tab_bbs_topicinfo\r\n" + 
					"where is_accepted=0");
			rs=ps.executeQuery();
			if(rs.next()) {
				num=rs.getInt("num");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtil.getInstance().close(rs);
			DBUtil.getInstance().close(ps);
			DBUtil.getInstance().close(conn);
		}
		return num;
	}

	@Override
	public int getAcceptTopicNum() {
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		int num=0;
		try {
			conn=DBUtil.getInstance().getConnection();
			ps=conn.prepareStatement("select count(id) num\r\n" + 
					"from tab_bbs_topicinfo\r\n" + 
					"where is_accepted=1");
			rs=ps.executeQuery();
			if(rs.next()) {
				num=rs.getInt("num");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtil.getInstance().close(rs);
			DBUtil.getInstance().close(ps);
			DBUtil.getInstance().close(conn);
		}
		return num;
	}

	@Override
	public int getNiceTopicNum() {
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		int num=0;
		try {
			conn=DBUtil.getInstance().getConnection();
			ps=conn.prepareStatement("select count(id) num\r\n" + 
					"from tab_bbs_topicinfo\r\n" + 
					"where is_good=1");
			rs=ps.executeQuery();
			if(rs.next()) {
				num=rs.getInt("num");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtil.getInstance().close(rs);
			DBUtil.getInstance().close(ps);
			DBUtil.getInstance().close(conn);
		}
		return num;
	}

	@Override
	public int getMyTopicTopicNum(int userid) {
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		int num=0;
		try {
			conn=DBUtil.getInstance().getConnection();
			ps=conn.prepareStatement("select count(id) num \r\n" + 
					"from tab_bbs_topicinfo\r\n" + 
					"where userid=?");
			ps.setInt(1, userid);
			rs=ps.executeQuery();
			if(rs.next()) {
				num=rs.getInt("num");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtil.getInstance().close(rs);
			DBUtil.getInstance().close(ps);
			DBUtil.getInstance().close(conn);
		}
		return num;
	}

	@Override
	public int getSearchTopicNum(String SearchStr) {
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		int num=0;
		try {
			conn=DBUtil.getInstance().getConnection();
			ps=conn.prepareStatement("select count(id) num \r\n" + 
					"from tab_bbs_topicinfo\r\n" + 
					"where title like ?");
			ps.setString(1, "%"+SearchStr+"%");
			rs=ps.executeQuery();
			if(rs.next()) {
				num=rs.getInt("num");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtil.getInstance().close(rs);
			DBUtil.getInstance().close(ps);
			DBUtil.getInstance().close(conn);
		}
		return num;
	}

	@Override
	public boolean acceptTopic(int topicid) {
		Connection conn=null;
		PreparedStatement ps=null;
		boolean temp=false;
		try {
			conn=DBUtil.getInstance().getConnection();
			conn.setAutoCommit(false);
			ps=conn.prepareStatement("update tab_bbs_topicinfo\r\n" + 
					"set is_accepted=1\r\n" + 
					"where id=?");
			ps.setInt(1, topicid);
			int num=ps.executeUpdate();
			if(num==1) {
				temp=true;
			}
			conn.commit();
		} catch (Exception e) {
			try {
				conn.rollback();
				System.out.println("给topicinfo表"+topicid+"号帖子设置已采纳失败，已回滚，这里是TopicDaoImp");
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
	public List<TopicInfoEx> getWeekAnswerList() {
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<TopicInfoEx> list=new ArrayList<TopicInfoEx>();
		try {
			conn=DBUtil.getInstance().getConnection();
			ps=conn.prepareStatement("select tbu.id,nickname,head_url,count(tbc.id) num\r\n" + 
					"from tab_bbs_userinfo tbu\r\n" + 
					"join tab_bbs_comment tbc\r\n" + 
					"on tbu.id=tbc.userid and is_topic=0 and comment_time>=( select subdate(curdate(),if(date_format(curdate(),'%w')=0,7,date_format(curdate(),'%w'))-1) )\r\n" + 
					"GROUP BY tbu.id,nickname,head_url\r\n" + 
					"ORDER BY num DESC\r\n" + 
					"LIMIT 0,8");
			rs=ps.executeQuery();
			while(rs.next()) {
				//这里开始存入对象中
				TopicInfoEx topicInfoEx=new TopicInfoEx();
				topicInfoEx.setUserId(rs.getInt("id"));
				topicInfoEx.setNickName(rs.getString("nickname"));
				topicInfoEx.setHeadUrl(rs.getString("head_url"));
				topicInfoEx.setAnswerNum(rs.getInt("num"));
				list.add(topicInfoEx);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtil.getInstance().close(rs);
			DBUtil.getInstance().close(ps);
			DBUtil.getInstance().close(conn);
		}
		return list;
	}

	@Override
	public boolean addTopicAnswerNum(int topicid) {
		Connection conn=null;
		PreparedStatement ps=null;
		boolean temp=false;
		try {
			conn=DBUtil.getInstance().getConnection();
			conn.setAutoCommit(false);
			ps=conn.prepareStatement("update tab_bbs_topicinfo\r\n" + 
					"set answer_num=answer_num+1\r\n" + 
					"where id=?");
			ps.setInt(1, topicid);
			int num=ps.executeUpdate();
			if(num==1) {
				temp=true;
			}
			conn.commit();
		} catch (Exception e) {
			try {
				conn.rollback();
				System.out.println("给topicinfo表"+topicid+"号帖子增加answer_num失败，已回滚，这里是TopicDaoImp");
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
	public boolean decTopicAnswerNum(int topicid) {
		Connection conn=null;
		PreparedStatement ps=null;
		boolean temp=false;
		try {
			conn=DBUtil.getInstance().getConnection();
			conn.setAutoCommit(false);
			ps=conn.prepareStatement("update tab_bbs_topicinfo\r\n" + 
					"set answer_num=answer_num-1\r\n" + 
					"where id=?");
			ps.setInt(1, topicid);
			int num=ps.executeUpdate();
			if(num==1) {
				temp=true;
			}
			conn.commit();
		} catch (Exception e) {
			try {
				conn.rollback();
				System.out.println("给topicinfo表"+topicid+"号帖子减少answer_num失败，已回滚，这里是TopicDaoImp");
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
	public int getUseridByTopicid(int topicid) {
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		int num=0;
		try {
			conn=DBUtil.getInstance().getConnection();
			ps=conn.prepareStatement("select userid\r\n" + 
					"from tab_bbs_topicinfo\r\n" + 
					"where id=?");
			ps.setInt(1, topicid);
			rs=ps.executeQuery();
			if(rs.next()) {
				num=rs.getInt("userid");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtil.getInstance().close(rs);
			DBUtil.getInstance().close(ps);
			DBUtil.getInstance().close(conn);
		}
		return num;
	}

	@Override
	public List<TopicInfoEx> getHotViewList() {
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<TopicInfoEx> list=new ArrayList<TopicInfoEx>();
		try {
			conn=DBUtil.getInstance().getConnection();
			ps=conn.prepareStatement("select id,if(LENGTH(title)>50,CONCAT(left(title,17),'...'),title) title,view_count\r\n" + 
					"from tab_bbs_topicinfo tbt\r\n" + 
					"where createtime>=( select date_sub(curdate(), interval 9 day) )\r\n" + 
					"ORDER BY view_count DESC\r\n" + 
					"limit 0,10");
			rs=ps.executeQuery();
			while(rs.next()) {
				//这里开始存入对象中
				TopicInfoEx topicInfoEx=new TopicInfoEx();
				topicInfoEx.setId(rs.getInt("id"));
				topicInfoEx.setTitle(rs.getString("title"));
				topicInfoEx.setViewCount(rs.getInt("view_count"));
				list.add(topicInfoEx);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtil.getInstance().close(rs);
			DBUtil.getInstance().close(ps);
			DBUtil.getInstance().close(conn);
		}
		return list;
	}

	@Override
	public List<TopicInfoEx> getHotReplyList() {
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<TopicInfoEx> list=new ArrayList<TopicInfoEx>();
		try {
			conn=DBUtil.getInstance().getConnection();
			ps=conn.prepareStatement("select id,if(LENGTH(title)>50,CONCAT(left(title,17),'...'),title) title,num\r\n" + 
					"from tab_bbs_topicinfo tbt\r\n" + 
					"join ( select topic_or_comment_id,count(topic_or_comment_id) num\r\n" + 
					"																									 from tab_bbs_comment tbc\r\n" + 
					"																									 where is_topic=0 and comment_time>=( select date_sub(curdate(), interval 9 day) )\r\n" + 
					"																									 GROUP BY topic_or_comment_id\r\n" + 
					"																									 ORDER BY num DESC\r\n" + 
					"																									 LIMIT 0,10 )temp1\r\n" + 
					"where tbt.id=temp1.topic_or_comment_id\r\n" + 
					"ORDER BY num DESC\r\n" + 
					"limit 0,10");
			rs=ps.executeQuery();
			while(rs.next()) {
				//这里开始存入对象中
				TopicInfoEx topicInfoEx=new TopicInfoEx();
				topicInfoEx.setId(rs.getInt("id"));
				topicInfoEx.setTitle(rs.getString("title"));
				topicInfoEx.setAnswerNum(rs.getInt("num"));
				list.add(topicInfoEx);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtil.getInstance().close(rs);
			DBUtil.getInstance().close(ps);
			DBUtil.getInstance().close(conn);
		}
		return list;
	}

	@Override
	public List<TopicInfoEx> getKissNumList() {
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<TopicInfoEx> list=new ArrayList<TopicInfoEx>();
		try {
			conn=DBUtil.getInstance().getConnection();
			ps=conn.prepareStatement("select id,nickname,head_url,kiss_num\r\n" + 
					"from tab_bbs_userinfo\r\n" + 
					"ORDER BY kiss_num DESC\r\n" + 
					"limit 0,4");
			rs=ps.executeQuery();
			while(rs.next()) {
				//这里开始存入对象中
				TopicInfoEx topicInfoEx=new TopicInfoEx();
				topicInfoEx.setUserId(rs.getInt("id"));
				topicInfoEx.setNickName(rs.getString("nickname"));
				topicInfoEx.setHeadUrl(rs.getString("head_url"));
				topicInfoEx.setKissNum(rs.getInt("kiss_num"));
				list.add(topicInfoEx);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtil.getInstance().close(rs);
			DBUtil.getInstance().close(ps);
			DBUtil.getInstance().close(conn);
		}
		return list;
	}

	@Override
	public List<TopicInfoEx> getUserNearQuestionList(int userid) {
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<TopicInfoEx> list=new ArrayList<TopicInfoEx>();
		try {
			conn=DBUtil.getInstance().getConnection();
			ps=conn.prepareStatement("select id,is_good,if(LENGTH(title)>50,CONCAT(left(title,17),'...'),title) title,createtime,answer_num,view_count\r\n" + 
					"from tab_bbs_topicinfo\r\n" + 
					"where userid=? and createtime>=( select date_sub(curdate(), interval 6 day) )\r\n" + 
					"GROUP BY id DESC");
			ps.setInt(1, userid);
			rs=ps.executeQuery();
			while(rs.next()) {
				//这里开始存入对象中
				TopicInfoEx topicInfoEx=new TopicInfoEx();
				topicInfoEx.setId(rs.getInt("id"));
				topicInfoEx.setIsGood(rs.getInt("is_good"));
				topicInfoEx.setTitle(rs.getString("title"));
				topicInfoEx.setCreateTime(TimeToStringUtil.timestampToStringtime(rs.getTimestamp("createtime")));
				topicInfoEx.setAnswerNum(rs.getInt("answer_num"));
				topicInfoEx.setViewCount(rs.getInt("view_count"));
				list.add(topicInfoEx);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtil.getInstance().close(rs);
			DBUtil.getInstance().close(ps);
			DBUtil.getInstance().close(conn);
		}
		return list;
	}

	@Override
	public List<CommentEx> getUserNearAnswerList(int userid) {
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<CommentEx> list=new ArrayList<CommentEx>();
		try {
			conn=DBUtil.getInstance().getConnection();
			ps=conn.prepareStatement("select comment_time,tbt.id,if(LENGTH(title)>50,CONCAT(left(title,17),'...'),title) title,comment_content\r\n" + 
					"from tab_bbs_topicinfo tbt\r\n" + 
					"join tab_bbs_comment tbc\r\n" + 
					"on tbc.topic_or_comment_id=tbt.id\r\n" + 
					"where tbc.is_topic=0 and tbc.userid=? and comment_time>=( select date_sub(curdate(), interval 2 day) )\r\n" + 
					"GROUP BY tbc.id DESC");
			ps.setInt(1, userid);
			rs=ps.executeQuery();
			while(rs.next()) {
				//这里开始存入对象中
				CommentEx commentEx=new CommentEx();
				commentEx.setCreateTime(TimeToStringUtil.timestampToStringtime(rs.getTimestamp("comment_time")));
				commentEx.setTopicOrCommentId(rs.getInt("id"));
				commentEx.setTopicTitle(rs.getString("title"));
				commentEx.setContent(rs.getString("comment_content"));
				list.add(commentEx);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtil.getInstance().close(rs);
			DBUtil.getInstance().close(ps);
			DBUtil.getInstance().close(conn);
		}
		return list;
	}

	@Override
	public List<TopicInfoEx> getMyTopicList(int userid, int pageIndex, int pageSize) {
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<TopicInfoEx> list=new ArrayList<TopicInfoEx>();
		try {
			conn=DBUtil.getInstance().getConnection();
			ps=conn.prepareStatement("select id,title,createtime,view_count,answer_num\r\n" + 
					"from tab_bbs_topicinfo tbt\r\n" + 
					"where userid=?\r\n" + 
					"GROUP BY id DESC\r\n" + 
					"limit ?,?");
			ps.setInt(1, userid);
			ps.setInt(2, pageIndex*pageSize);
			ps.setInt(3, pageSize);
			rs=ps.executeQuery();
			while(rs.next()) {
				//这里开始存入对象中
				TopicInfoEx topicInfoEx=new TopicInfoEx();
				topicInfoEx.setId(rs.getInt("id"));
				topicInfoEx.setTitle(rs.getString("title"));
				topicInfoEx.setCreateTime(TimeToStringUtil.timestampToStringtime(rs.getTimestamp("createtime")));
				topicInfoEx.setViewCount(rs.getInt("view_count"));
				topicInfoEx.setAnswerNum(rs.getInt("answer_num"));
				list.add(topicInfoEx);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtil.getInstance().close(rs);
			DBUtil.getInstance().close(ps);
			DBUtil.getInstance().close(conn);
		}
		return list;
	}

	@Override
	public List<TopicInfoEx> getMyCollectTopicList(int userid, int pageIndex, int pageSize) {
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<TopicInfoEx> list=new ArrayList<TopicInfoEx>();
		try {
			conn=DBUtil.getInstance().getConnection();
			ps=conn.prepareStatement("select tbt.id,title,collect_time\r\n" + 
					"from tab_bbs_topicinfo tbt\r\n" + 
					"join tab_bbs_collect tbc\r\n" + 
					"on tbt.id=tbc.topicid\r\n" + 
					"where tbc.userid=?\r\n" + 
					"GROUP BY tbt.id DESC\r\n" + 
					"limit ?,?");
			ps.setInt(1, userid);
			ps.setInt(2, pageIndex*pageSize);
			ps.setInt(3, pageSize);
			rs=ps.executeQuery();
			while(rs.next()) {
				//这里开始存入对象中
				TopicInfoEx topicInfoEx=new TopicInfoEx();
				topicInfoEx.setId(rs.getInt("id"));
				topicInfoEx.setTitle(rs.getString("title"));
				topicInfoEx.setCollectTopicTime(TimeToStringUtil.timestampToStringtime(rs.getTimestamp("collect_time")));
				list.add(topicInfoEx);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtil.getInstance().close(rs);
			DBUtil.getInstance().close(ps);
			DBUtil.getInstance().close(conn);
		}
		return list;
	}

	@Override
	public int getMyCollectNum(int userid) {
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		int num=0;
		try {
			conn=DBUtil.getInstance().getConnection();
			ps=conn.prepareStatement("select count(id) num\r\n" + 
					"from tab_bbs_collect\r\n" + 
					"where userid=?");
			ps.setInt(1, userid);
			rs=ps.executeQuery();
			if(rs.next()) {
				num=rs.getInt("num");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtil.getInstance().close(rs);
			DBUtil.getInstance().close(ps);
			DBUtil.getInstance().close(conn);
		}
		return num;
	}
}
