package com.neusoft.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.neusoft.bean.Userinfo;
import com.neusoft.dao.IUserDao;
import com.neusoft.dao.UserDaoImp;
import com.neusoft.service.IUserService;
import com.neusoft.service.UserServiceImp;
import com.neusoft.utils.EmailUtil;

/**
 * Servlet implementation class SetUserInfoServlet
 */
@WebServlet("/setuser.do")
public class SetUserInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getSession().getAttribute("userInfo")!=null) {
			Userinfo user=(Userinfo)request.getSession().getAttribute("userInfo");
			int userId=user.getId();
			String email=request.getParameter("email");
			String nickName=request.getParameter("nickName");
			String sex=request.getParameter("sex");
			String city=request.getParameter("city");
			String sign=request.getParameter("sign");
			
//			System.out.println(email+"  /n"+nickName+"  /n"+sex+"  /n"+city+"  /n"+sign);
			
			if(email!=null && nickName!=null && city!=null && sex!=null && sign!=null) {
				email=email.trim();
				nickName=nickName.trim();
				city=city.trim();
				sign=sign.trim();
				int praseSex=Integer.parseInt(sex);
				try {
					praseSex=Integer.parseInt(sex);
				} catch (NumberFormatException e) {
					//直接访问进行非法操作！
					response.sendRedirect(request.getContextPath()+"/jsp/user/set.jsp");
				}
				if(praseSex==0 || praseSex==1) {
					if(EmailUtil.isEmail(email) && nickName.equals("")==false) {
						Userinfo newUserinfo=new Userinfo();
						newUserinfo.setId(userId);
						newUserinfo.setNickname(nickName);
						newUserinfo.setCity(city);
						newUserinfo.setEmail(email);
						newUserinfo.setSex(praseSex);
						newUserinfo.setSignName(sign);
						
						IUserService ius=new UserServiceImp();
						int num=ius.setUserInfo(newUserinfo);
						
						if(num==1) {
							//重新加载用户session信息！
							IUserDao iud= new UserDaoImp();
							request.getSession().setAttribute("userInfo", iud.findUser(email, user.getPassword()));
							response.sendRedirect(request.getContextPath()+"/jsp/user/set.jsp");
						}else {
							//这里应该提示！呀，修改信息失败！
							response.sendRedirect(request.getContextPath()+"/jsp/user/set.jsp");
						}
					}
				}else {
					//直接访问进行非法操作！
					response.sendRedirect(request.getContextPath()+"/jsp/user/set.jsp");
				}
					
			}else {
				response.sendRedirect(request.getContextPath()+"/jsp/user/set.jsp");
			}
		}else {
			response.sendRedirect(request.getContextPath()+"/jsp/login.jsp");
		}
	}
}
