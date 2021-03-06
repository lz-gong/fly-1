package com.neusoft.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.neusoft.bean.Comment;
import com.neusoft.bean.CommentEx;
import com.neusoft.bean.MessageEx;
import com.neusoft.bean.TopicInfoEx;
import com.neusoft.utils.DBUtil;
import com.neusoft.utils.TimeToStringUtil;

public class CommentDaoImp implements ICommentDao{

	@Override
	public int[] addComment(Comment comment) {
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		int arr[]=new int[2];
		try {
			conn=DBUtil.getInstance().getConnection();
			conn.setAutoCommit(false);
			ps=conn.prepareStatement("insert into tab_bbs_comment(topic_or_comment_id,is_topic,comment_content,userid,comment_time) values(?,?,?,?,?)");
			
			int topic_or_comment_id=comment.getTopicOrCommentId();
			int is_topic=comment.getIsTopic();
			String comment_content=comment.getCommentContent();
			int userid=comment.getUserid();
			Date temp=comment.getCommentTime();
			java.sql.Timestamp comment_time=new java.sql.Timestamp(temp.getTime());
			
			ps.setInt(1, topic_or_comment_id);
			ps.setInt(2, is_topic);
			ps.setString(3, comment_content);
			ps.setInt(4, userid);
			ps.setTimestamp(5, comment_time);
			
			arr[0]=ps.executeUpdate();
			conn.commit();
			
			DBUtil.getInstance().close(ps);
			ps=conn.prepareStatement("SELECT LAST_INSERT_ID() lastid");
			rs=ps.executeQuery();
			if(rs.next()) {
				arr[1]=rs.getInt("lastid");
			}
			
		} catch (Exception e) {
			try {
				conn.rollback();
				System.out.println("插入评论出现错误，或查询最后一个id出现错误，已进行回滚！这里是CommentDaoImp");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}finally {
			DBUtil.getInstance().close(ps);
			DBUtil.getInstance().close(conn);
		}
		return arr;
	}

	
	@Override
	public boolean checkIsEnd(int topicId) {
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		boolean temp=false;
		try {
			conn=DBUtil.getInstance().getConnection();
			ps=conn.prepareStatement("select is_end\r\n" + 
					"FROM tab_bbs_topicinfo\r\n" + 
					"where id=?");
			ps.setInt(1, topicId);
			rs=ps.executeQuery();
			if(rs.next()) {
				int is_end=rs.getInt("is_end");
				if(is_end==0) {
					temp=false;
					System.out.println("评论被插入帖子中，帖子未结帖！");
				}
				if(is_end==1) {
					temp=true;
					System.out.println("帖子已结帖！不允许插入评论");
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
	public List<CommentEx> getAllComment(int topicId) {
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<CommentEx> list=new ArrayList<CommentEx>();
		
		try {
			conn=DBUtil.getInstance().getConnection();
			ps=conn.prepareStatement("select tbc.id,head_url,nickname,comment_time,comment_content,is_accepted,support_num,userid,is_topic,topic_or_comment_id,experience\r\n" + 
					"from tab_bbs_comment tbc,tab_bbs_userinfo tbu\r\n" + 
					"where tbu.id=tbc.userid and is_topic=0 and tbc.id in (select temp1.id from(select id from tab_bbs_comment where is_topic=0 and topic_or_comment_id=? limit 0,100) as temp1)\r\n" + 
					"or tbu.id=tbc.userid and is_topic=1 and topic_or_comment_id in (select temp2.id from (select id from tab_bbs_comment where is_topic=0 and topic_or_comment_id=? limit 0,100) as temp2)"
					);
			ps.setInt(1, topicId);
			ps.setInt(2, topicId);
			rs=ps.executeQuery();
			while(rs.next()) {
				int id=rs.getInt("id");
				String headUrl=rs.getString("head_url");
				String nickName=rs.getString("nickname");
				Timestamp temp=rs.getTimestamp("comment_time");
				String createTime =TimeToStringUtil.timestampToStringtime(temp);
				String content=rs.getString("comment_content");
				int isAccepted=rs.getInt("is_accepted");
				int supportNum=rs.getInt("support_num");
				int isTopic=rs.getInt("is_topic");
				int topicOrCommentId=rs.getInt("topic_or_comment_id");
				CommentEx ce=new CommentEx();
				
				ce.setContent(content);
				ce.setCreateTime(createTime);
				ce.setHeadUrl(headUrl);
				ce.setId(id);
				ce.setIsAccepted(isAccepted);
				ce.setNickName(nickName);
				ce.setSupportNum(supportNum);
				ce.setUserId(rs.getInt("userid"));
				ce.setIsTopic(isTopic);
				ce.setTopicOrCommentId(topicOrCommentId);
				//为了获取评论用户的等级
				ce.setExperience(rs.getInt("experience"));
				
				list.add(ce);
			}
			System.out.println("找到了"+list.size()+"条评论！");
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
	public int getCommentNum(int topicId) {
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		int num=0;
		try {
			conn=DBUtil.getInstance().getConnection();
			ps=conn.prepareStatement("select count(topic_or_comment_id) num\r\n" + 
					"from tab_bbs_comment\r\n" + 
					"where is_topic=0 and topic_or_comment_id=?");
			ps.setInt(1, topicId);
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
	public boolean increaseSupportNum(int commentid) {
		Connection conn=null;
		PreparedStatement ps=null;
		boolean temp=false;
		try {
			conn=DBUtil.getInstance().getConnection();
			conn.setAutoCommit(false);
			ps=conn.prepareStatement("update tab_bbs_comment\r\n" + 
					"set support_num=support_num+1\r\n" + 
					"where id=?");
			ps.setInt(1, commentid);
			int num=ps.executeUpdate();
			if(num==1) {
				temp=true;
				System.out.println("点赞成功！这里是CommentDaoImp");		
			}
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
				System.out.println("点赞失败，这里是CommentDaoImp");
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
	public boolean checkCommentBelongAuthor(int commentid, int userid) {
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		boolean temp=false;
		try {
			conn=DBUtil.getInstance().getConnection();
			ps=conn.prepareStatement("select id\r\n" + 
					"from tab_bbs_comment\r\n" + 
					"where id=?  and userid=?");
			ps.setInt(1, commentid);
			ps.setInt(2, userid);
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
	public boolean acceptComment(int commentid) {
		Connection conn=null;
		PreparedStatement ps=null;
		boolean temp=false;
		try {
			conn=DBUtil.getInstance().getConnection();
			conn.setAutoCommit(false);
			ps=conn.prepareStatement("update tab_bbs_comment\r\n" + 
					"set is_accepted=1\r\n" + 
					"where id=?");
			ps.setInt(1, commentid);
			int num=ps.executeUpdate();
			if(num==1) {
				temp=true;
				System.out.println("奖励已到对方账户！这里是CommentDaoImp");		
			}
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
				System.out.println("采纳发放奖励失败，这里是CommentDaoImp");
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
	public int getCommentUserid(int commentid) {
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		int temp=0;
		try {
			conn=DBUtil.getInstance().getConnection();
			ps=conn.prepareStatement("select userid\r\n" + 
					"from tab_bbs_comment\r\n" + 
					"where id=?");
			ps.setInt(1, commentid);
			rs=ps.executeQuery();
			if(rs.next()) {
				temp=rs.getInt("userid");
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
	public boolean deleteComment(int commentid) {
		Connection conn=null;
		PreparedStatement ps=null;
		boolean temp=false;
		try {
			conn=DBUtil.getInstance().getConnection();
			conn.setAutoCommit(false);
			ps=conn.prepareStatement("delete from tab_bbs_comment\r\n" + 
					"where id=?");
			ps.setInt(1, commentid);
			int num=ps.executeUpdate();
			if(num==1) {
				temp=true;	
			}
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
				System.out.println("删除评论失败，这里是CommentDaoImp");
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
	public String getCommentContent(int commentid) {
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		String temp="";
		try {
			conn=DBUtil.getInstance().getConnection();
			ps=conn.prepareStatement("select comment_content\r\n" + 
					"from tab_bbs_comment\r\n" + 
					"where id=?");
			ps.setInt(1, commentid);
			rs=ps.executeQuery();
			if(rs.next()) {
				temp=rs.getString("comment_content");
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
	public boolean editComment(int commentid, String newContent) {
		Connection conn=null;
		PreparedStatement ps=null;
		boolean temp=false;
		try {
			conn=DBUtil.getInstance().getConnection();
			conn.setAutoCommit(false);
			ps=conn.prepareStatement("update tab_bbs_comment\r\n" + 
					"set comment_content=?\r\n" + 
					"where id=? and is_accepted=0");
			ps.setString(1, newContent);
			ps.setInt(2, commentid);
			int num=ps.executeUpdate();
			if(num==1) {
				temp=true;	
			}
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
				System.out.println("修改评论失败，已回滚，这里是CommentDaoImp");
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
	public boolean addMessage(int commentid, int topicUserid) {
		Connection conn=null;
		PreparedStatement ps=null;
		boolean temp=false;
		try {
			conn=DBUtil.getInstance().getConnection();
			conn.setAutoCommit(false);
			ps=conn.prepareStatement("insert into tab_bbs_message(comment_id,topic_userid) values(?,?)");
			ps.setInt(1, commentid);
			ps.setInt(2, topicUserid);
			int num=ps.executeUpdate();
			if(num==1) {
				temp=true;
			}
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
				System.out.println("插入message失败！，这里是CommentDaoImp");
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
	public List<MessageEx> getMyMessageList(int userid, int pageIndex, int pageSize) {
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<MessageEx> list=new ArrayList<MessageEx>();
		try {
			conn=DBUtil.getInstance().getConnection();
			ps=conn.prepareStatement("select tbc.userid,tbu.nickname,tbt.title,tbt.id tbtid,tbc.id tbcid,tbc.comment_time,tbm.id tbmid\r\n" + 
					"from tab_bbs_comment tbc\r\n" + 
					"join tab_bbs_userinfo tbu\r\n" + 
					"on tbc.userid=tbu.id\r\n" + 
					"join tab_bbs_topicinfo tbt\r\n" + 
					"on tbt.id=tbc.topic_or_comment_id\r\n" + 
					"join tab_bbs_message tbm\r\n" + 
					"on tbm.comment_id=tbc.id\r\n" + 
					"where is_topic=0 and topic_userid=?\r\n" + 
					"order by tbm.id desc limit ?,?");
			ps.setInt(1, userid);
			ps.setInt(2, pageIndex*pageSize);
			ps.setInt(3, pageSize);
			rs=ps.executeQuery();
			while(rs.next()) {
				//这里开始存入对象中
				MessageEx messageEx=new MessageEx();
				messageEx.setUserid(rs.getInt("userid"));
				messageEx.setNickname(rs.getString("nickname"));
				messageEx.setTitle(rs.getString("title"));
				messageEx.setTopicid(rs.getInt("tbtid"));
				messageEx.setCommentid(rs.getInt("tbcid"));
				messageEx.setCommentTime(TimeToStringUtil.timestampToStringtime(rs.getTimestamp("comment_time")));
				messageEx.setId(rs.getInt("tbmid"));
				list.add(messageEx);
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
	public int getMyMessageNum(int userid) {
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		int temp=0;
		try {
			conn=DBUtil.getInstance().getConnection();
			ps=conn.prepareStatement("select count(id) num\r\n" + 
					"from tab_bbs_message\r\n" + 
					"where topic_userid=?");
			ps.setInt(1, userid);
			rs=ps.executeQuery();
			if(rs.next()) {
				temp=rs.getInt("num");
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
	public boolean checkMessageIsBelongNowUser(int messageid,int userid) {
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		boolean temp=false;
		try {
			conn=DBUtil.getInstance().getConnection();
			ps=conn.prepareStatement("select id\r\n" + 
					"from tab_bbs_message\r\n" + 
					"where id=? and topic_userid=?");
			ps.setInt(1, messageid);
			ps.setInt(2, userid);
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
	public boolean deleteOneMessage(int messageid) {
		Connection conn=null;
		PreparedStatement ps=null;
		boolean temp=false;
		try {
			conn=DBUtil.getInstance().getConnection();
			conn.setAutoCommit(false);
			ps=conn.prepareStatement("delete from tab_bbs_message where id=?");
			ps.setInt(1, messageid);
			int num=ps.executeUpdate();
			if(num==1) {
				temp=true;	
			}
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
				System.out.println("删除单个消息失败，已回滚，消息id为"+messageid+"这里是CommentDaoImp");
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
	public boolean deleteAllMessage(int userid) {
		Connection conn=null;
		PreparedStatement ps=null;
		boolean temp=false;
		try {
			conn=DBUtil.getInstance().getConnection();
			conn.setAutoCommit(false);
			ps=conn.prepareStatement("delete from tab_bbs_message where topic_userid=?");
			ps.setInt(1, userid);
			int num=ps.executeUpdate();
			if(num>0) {
				temp=true;	
			}else {
				temp=true;
				System.out.println("用户的全部消息为0条，所以只删除了0条，但是也设置为true了");
			}
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
				System.out.println("删除用户全部消息失败，已回滚，用户id为"+userid+"这里是CommentDaoImp");
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
}
