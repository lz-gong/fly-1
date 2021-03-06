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
import com.neusoft.bean.Image;
import com.neusoft.bean.Userinfo;
import com.neusoft.service.IUserService;
import com.neusoft.service.UserServiceImp;

/**
 * Servlet implementation class ImgUploadServlet
 */
@WebServlet("/imgUploadServlet.do")
public class ImgUploadServlet extends HttpServlet {
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
							String targetPath=request.getServletContext().getRealPath("")+"/images/";
							//图headImg目录不存在，创建目录
							File file=new File(targetPath);
							if(!file.exists()) {
								file.mkdir();
							}
//							System.out.println("图片大小为："+fileItem.getSize());  //这种判断方法很不靠谱，实际上需要读取整个文件，才能判断文件大小
							InputStream is=fileItem.getInputStream();
							OutputStream os = new FileOutputStream(targetPath+uuidFileName);
							byte buffer[] = new byte[1024];
							int len=is.read(buffer);
							int imgSize=0; //这个用于记录文件的大小
							
							while(len!=-1) {
								imgSize++;
								if(imgSize>500) {
									break;
								}
								os.write(buffer,0,len);
								len=is.read(buffer, 0, len);
							}
							os.close();
							is.close();
							if(imgSize<=500) {
								Image img=new Image();
								img.setStatus(0);
								img.setUrl(request.getContextPath()+"/images/"+uuidFileName);
								PrintWriter out = response.getWriter();
								out.println(JSON.toJSONString(img));
							}else {//说明图片大于500k
								System.out.println(uuidFileName);
								File whileDelete=new File(request.getServletContext().getRealPath("")+"/images/"+uuidFileName);
								System.out.println("上传图片大于500k，删除是否成功："+whileDelete.delete());
								Image img=new Image();
								img.setStatus(2);
								img.setMsg("图片最大不能超过500kb！");
								PrintWriter out = response.getWriter();
								out.println(JSON.toJSONString(img));
							}
						}else { //给user.js中的回传一个上传图片错误对象
							Image img=new Image();
							img.setStatus(1);
							img.setMsg("请上传png/jpg/gif格式的图片！");
							PrintWriter out = response.getWriter();
							out.println(JSON.toJSONString(img));
						}
					}
				}
			} catch (FileUploadException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else { //跳转到登录界面
			System.out.println("未登录");
			Image img=new Image();
			img.setStatus(3);
			PrintWriter out = response.getWriter();
			out.println(JSON.toJSONString(img));
		}
	}

}
