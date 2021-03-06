package com.neusoft.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.alibaba.fastjson.JSON;
import com.neusoft.bean.HeadImg;
import com.neusoft.bean.Userinfo;
import com.neusoft.service.IUserService;
import com.neusoft.service.UserServiceImp;



/**
 * Servlet implementation class UploadServlet
 */
@WebServlet("/upload.do")
public class HeadImgUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Userinfo user=(Userinfo)request.getSession().getAttribute("userInfo");
		if(user!=null) {
			DiskFileItemFactory factory=new DiskFileItemFactory();
			ServletFileUpload fileUpload=new ServletFileUpload(factory);
			
			try {
				List<FileItem> fileItems=fileUpload.parseRequest(request);
				for(FileItem fileItem:fileItems) {
					if(!fileItem.isFormField()) { //如果不是文本域，就是文件域
						String fileName=fileItem.getName();
//						System.out.println(fileName);  //输出上传的文件名
						if(fileName.endsWith(".JPG") || fileName.endsWith(".PNG") || fileName.endsWith(".GIF") || fileName.endsWith(".jpg") || fileName.endsWith(".png") || fileName.endsWith(".gif")) {
							UUID uuid=UUID.randomUUID();
							String uuidFileName=uuid+fileName;
							String targetPath=request.getServletContext().getRealPath("")+"/headimg/";
							//图headImg目录不存在，创建目录
							File file=new File(targetPath);
							if(!file.exists()) {
								file.mkdir();
							}
							InputStream is=fileItem.getInputStream();
							OutputStream os = new FileOutputStream(targetPath+uuidFileName);
							byte buffer[] = new byte[1024];
							int len=is.read(buffer);
							int imgSize=0; //这个用于记录文件的大小
							
							while(len!=-1) {
								os.write(buffer,0,len);
								len=is.read(buffer, 0, len);
								imgSize++;
								if(imgSize>50) {
									break;
								}
							}
							os.close();
							is.close();
							if(imgSize<=50) {
								HeadImg hp=new HeadImg();
								hp.setStatus(0);
								hp.setMsg("这是上传头像的错误信息！");
								hp.setSrc(request.getContextPath()+"/headimg/"+uuidFileName);
								PrintWriter out = response.getWriter();
								out.println(JSON.toJSONString(hp));
								boolean isDefaultHeadimg=false;
								if(user.getHeadUrl().startsWith("/yklgbbs/res/images/avatar/")) { //这是默认图片的路径
									isDefaultHeadimg=true;
								}
								IUserService ius=new UserServiceImp();
								//注：因为下面传的是要删除的文件的名称，所以要写服务器的绝对路径：request.getServletContext().getRealPath("")+user.getHeadUrl().substring(8)
								boolean isok=ius.setHeadImg(user.getId(), hp.getSrc(), request.getServletContext().getRealPath("")+user.getHeadUrl().substring(8), isDefaultHeadimg);
								if(isok) {
									user.setHeadUrl(hp.getSrc());
								}else {
									System.out.println("修改头像失败，这里是HeadImgUploadServlet");
								}
								
							}else {//说明图片大于50k
								HeadImg hp=new HeadImg();
								hp.setStatus(3); //3代表图片大于50k
								PrintWriter out = response.getWriter();
								out.println(JSON.toJSONString(hp));
							}
						}else { //给user.js中的回传一个上传图片错误对象
							HeadImg hp=new HeadImg();
							hp.setStatus(2); //2代表长传头像错误
							PrintWriter out = response.getWriter();
							out.println(JSON.toJSONString(hp));
						}
					}
				}
			} catch (FileUploadException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			response.sendRedirect(request.getContextPath()+"/jsp/login.jsp");
		}
	}

}
