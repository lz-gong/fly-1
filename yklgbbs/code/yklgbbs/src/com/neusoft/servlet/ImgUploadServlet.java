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
					if(!fileItem.isFormField()) { //��������ı��򣬾����ļ���
						String fileName=fileItem.getName();
//						System.out.println(fileName);  //����ϴ����ļ���
						if(fileName.endsWith(".JPG") || fileName.endsWith(".PNG") || fileName.endsWith(".GIF") || fileName.endsWith(".jpg") || fileName.endsWith(".png") || fileName.endsWith(".gif")) {
							UUID uuid=UUID.randomUUID();
							String uuidFileName=uuid+fileName;
							String targetPath=request.getServletContext().getRealPath("")+"/images/";
							//ͼheadImgĿ¼�����ڣ�����Ŀ¼
							File file=new File(targetPath);
							if(!file.exists()) {
								file.mkdir();
							}
//							System.out.println("ͼƬ��СΪ��"+fileItem.getSize());  //�����жϷ����ܲ����ף�ʵ������Ҫ��ȡ�����ļ��������ж��ļ���С
							InputStream is=fileItem.getInputStream();
							OutputStream os = new FileOutputStream(targetPath+uuidFileName);
							byte buffer[] = new byte[1024];
							int len=is.read(buffer);
							int imgSize=0; //������ڼ�¼�ļ��Ĵ�С
							
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
							}else {//˵��ͼƬ����500k
								System.out.println(uuidFileName);
								File whileDelete=new File(request.getServletContext().getRealPath("")+"/images/"+uuidFileName);
								System.out.println("�ϴ�ͼƬ����500k��ɾ���Ƿ�ɹ���"+whileDelete.delete());
								Image img=new Image();
								img.setStatus(2);
								img.setMsg("ͼƬ����ܳ���500kb��");
								PrintWriter out = response.getWriter();
								out.println(JSON.toJSONString(img));
							}
						}else { //��user.js�еĻش�һ���ϴ�ͼƬ�������
							Image img=new Image();
							img.setStatus(1);
							img.setMsg("���ϴ�png/jpg/gif��ʽ��ͼƬ��");
							PrintWriter out = response.getWriter();
							out.println(JSON.toJSONString(img));
						}
					}
				}
			} catch (FileUploadException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else { //��ת����¼����
			System.out.println("δ��¼");
			Image img=new Image();
			img.setStatus(3);
			PrintWriter out = response.getWriter();
			out.println(JSON.toJSONString(img));
		}
	}

}