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
					if(!fileItem.isFormField()) { //��������ı��򣬾����ļ���
						String fileName=fileItem.getName();
//						System.out.println(fileName);  //����ϴ����ļ���
						if(fileName.endsWith(".JPG") || fileName.endsWith(".PNG") || fileName.endsWith(".GIF") || fileName.endsWith(".jpg") || fileName.endsWith(".png") || fileName.endsWith(".gif")) {
							UUID uuid=UUID.randomUUID();
							String uuidFileName=uuid+fileName;
							String targetPath=request.getServletContext().getRealPath("")+"/headimg/";
							//ͼheadImgĿ¼�����ڣ�����Ŀ¼
							File file=new File(targetPath);
							if(!file.exists()) {
								file.mkdir();
							}
							InputStream is=fileItem.getInputStream();
							OutputStream os = new FileOutputStream(targetPath+uuidFileName);
							byte buffer[] = new byte[1024];
							int len=is.read(buffer);
							int imgSize=0; //������ڼ�¼�ļ��Ĵ�С
							
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
								hp.setMsg("�����ϴ�ͷ��Ĵ�����Ϣ��");
								hp.setSrc(request.getContextPath()+"/headimg/"+uuidFileName);
								PrintWriter out = response.getWriter();
								out.println(JSON.toJSONString(hp));
								boolean isDefaultHeadimg=false;
								if(user.getHeadUrl().startsWith("/yklgbbs/res/images/avatar/")) { //����Ĭ��ͼƬ��·��
									isDefaultHeadimg=true;
								}
								IUserService ius=new UserServiceImp();
								//ע����Ϊ���洫����Ҫɾ�����ļ������ƣ�����Ҫд�������ľ���·����request.getServletContext().getRealPath("")+user.getHeadUrl().substring(8)
								boolean isok=ius.setHeadImg(user.getId(), hp.getSrc(), request.getServletContext().getRealPath("")+user.getHeadUrl().substring(8), isDefaultHeadimg);
								if(isok) {
									user.setHeadUrl(hp.getSrc());
								}else {
									System.out.println("�޸�ͷ��ʧ�ܣ�������HeadImgUploadServlet");
								}
								
							}else {//˵��ͼƬ����50k
								HeadImg hp=new HeadImg();
								hp.setStatus(3); //3����ͼƬ����50k
								PrintWriter out = response.getWriter();
								out.println(JSON.toJSONString(hp));
							}
						}else { //��user.js�еĻش�һ���ϴ�ͼƬ�������
							HeadImg hp=new HeadImg();
							hp.setStatus(2); //2��������ͷ�����
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