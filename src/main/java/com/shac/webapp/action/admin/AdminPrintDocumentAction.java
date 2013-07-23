package com.shac.webapp.action.admin;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.opensymphony.xwork2.ActionSupport;
import com.shac.dao.hibernate.IssueTaskDaoHibernate;
import com.shac.model.IssueTask;

public class AdminPrintDocumentAction extends ActionSupport{
	
	private String id;
	private IssueTask docu;
	private IssueTaskDaoHibernate issueTaskDao;
	private String ftype;
	
	
	
	@Action(value="viewAdminPrintFile",results={@Result(name="success",location="viewAdminPrintFile.jsp"),@Result(name="redirect",type="redirect",location="redirectToPringFile.do?id=${id}&tp=ad")})
	public String viewAdminPrintFile(){
		docu = issueTaskDao.get(id);
		if(docu.getAdminPrintFile()==null){
				String pdfPath = docu.getAttachFile();
				String realPath = ServletActionContext.getServletContext().getRealPath(pdfPath);
				File pdfFile = new File(realPath);
				String fileFullName = pdfFile.getName();
				String fileName = fileFullName.substring(0,fileFullName.lastIndexOf("."));
				String securityFileName = pdfFile.getParent()+File.separator+fileName+"-sct.pdf";
				String waterMarkFileName = pdfFile.getParent()+File.separator+fileName+"-prt.pdf";
				File waterMarkFile = new File(waterMarkFileName);
				FileOutputStream os = null;
				try {
					os = new FileOutputStream(waterMarkFile);
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
				
				try {
					PdfReader reader = new PdfReader(securityFileName,null);
					PdfStamper writer = new PdfStamper(reader,os);
					Image image = Image.getInstance(ServletActionContext.getServletContext().getRealPath("/img")+ "/mark_history.png");
					
					
					int pageSize = reader.getNumberOfPages();
					BaseFont base = null;
					//base = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);   
					base =  BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
					for(int i=1;i<=pageSize;i++){
						 Rectangle rectangle = reader.getPageSizeWithRotation(i);
						 float x = rectangle.getWidth()/2;   
						 float y = rectangle.getHeight()/2;
						 int fontSize = (int)(x*0.1);
						 
						 PdfContentByte content = writer.getOverContent(i);
						 content.saveState();   
					     //透明处理  
					     PdfGState gs = new PdfGState();   
					     gs.setFillOpacity(0.1f);//透明度 
					     content.setGState(gs);
					     
						 //图片水印-start
					     //image.setAbsolutePosition(0,189);
						 //content.addImage(image);
					     //图片水印-end
					     
					     //文字水印-start
					     content.beginText();   
					     content.setColorFill(BaseColor.RED);   
					     content.setFontAndSize(base, fontSize);   
					     content.showTextAligned(Element.ALIGN_CENTER, "纸质文件,仅供参考", x, y-(fontSize+20)*2, 35);//35度倾斜   
					     content.endText();
					     
					     //文字水印-版本
					     PdfGState gs_dark = new PdfGState();   
					     gs_dark.setFillOpacity(0.3f);//透明度 
					     content.setGState(gs_dark);
					     content.beginText();   
					     content.setColorFill(BaseColor.RED);   
					     content.setFontAndSize(base, fontSize*0.6f);   
					     content.showTextAligned(Element.ALIGN_CENTER, "版本："+docu.getPartid()+" / "+docu.getDocVersion(), x, y-(fontSize+20)*3, 35);//35度倾斜   
					     content.endText();
					     
					     content.restoreState();
					     //文字水印-end
					}
					
					
				    reader.close();
				    writer.close();
				    
				    try{
						Process process =Runtime.getRuntime().exec("C:\\SWFTools\\pdf2swf.exe "+waterMarkFileName+" "+pdfFile.getParent()+File.separator+fileName+"-prt.swf  -f -T 9 -t -s bitmap -s zoom=100 -s languagedir=C:\\xpdf\\xpdf-chinese-simplified");
					}
					catch(Exception e){
					    e.printStackTrace();
						System.out.println(e);
					}
				    
				} catch (IOException e) {
					e.printStackTrace();
				} catch (DocumentException e) {
					e.printStackTrace();
				}
				issueTaskDao.updatePrintFile(id, pdfPath.substring(0,pdfPath.lastIndexOf("/"))+"/"+fileName+"-prt.swf");
				return "redirect";
		}
		return SUCCESS;
	}
	
	@Action(value="viewPrintFile",results={@Result(name="success",location="viewPrintFile.jsp"),@Result(name="redirect",type="redirect",location="redirectToPringFile.do?id=${id}&tp=od")})
	public String viewPrintFile(){
		docu = issueTaskDao.get(id);
		if(docu.getPrintFile()==null){
				String pdfPath = docu.getAttachFile();
				String realPath = ServletActionContext.getServletContext().getRealPath(pdfPath);
				File pdfFile = new File(realPath);
				String fileFullName = pdfFile.getName();
				String fileName = fileFullName.substring(0,fileFullName.lastIndexOf("."));
				String securityFileName = pdfFile.getParent()+File.separator+fileName+"-sct.pdf";
				String waterMarkFileName = pdfFile.getParent()+File.separator+fileName+"-prt-ord.pdf";
				File waterMarkFile = new File(waterMarkFileName);
				FileOutputStream os = null;
				try {
					os = new FileOutputStream(waterMarkFile);
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
				
				try {
					PdfReader reader = new PdfReader(securityFileName,null);
					PdfStamper writer = new PdfStamper(reader,os);
					Image image = Image.getInstance(ServletActionContext.getServletContext().getRealPath("/img")+ "/mark_history.png");
					
					
					int pageSize = reader.getNumberOfPages();
					BaseFont base = null;
					//base = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);   
					base =  BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
					for(int i=1;i<=pageSize;i++){
						 Rectangle rectangle = reader.getPageSizeWithRotation(i);
						 float x = rectangle.getWidth()/2;   
						 float y = rectangle.getHeight()/2;
						 int fontSize = (int)(x*0.1);
						 
						 PdfContentByte content = writer.getOverContent(i);
						 content.saveState();
					     
					     //文字水印-版本
					     //透明处理  
					     PdfGState gs_dark = new PdfGState();   
					     gs_dark.setFillOpacity(0.3f);//透明度 
					     content.setGState(gs_dark);
					     content.beginText();   
					     content.setColorFill(BaseColor.RED);   
					     content.setFontAndSize(base, fontSize*0.6f);   
					     content.showTextAligned(Element.ALIGN_CENTER, "版本："+docu.getPartid()+" / "+docu.getDocVersion(), x, y-(fontSize+20)*3, 35);//35度倾斜   
					     content.endText();
					     
					     content.restoreState();
					     //文字水印-end
					}
					
					
				    reader.close();
				    writer.close();
				    
				    try{
						Process process =Runtime.getRuntime().exec("C:\\SWFTools\\pdf2swf.exe "+waterMarkFileName+" "+pdfFile.getParent()+File.separator+fileName+"-prt-ord.swf  -f -T 9 -t -s bitmap -s zoom=100 -s languagedir=C:\\xpdf\\xpdf-chinese-simplified");
					}
					catch(Exception e){
					    e.printStackTrace();
						System.out.println(e);
					}
				    
				} catch (IOException e) {
					e.printStackTrace();
				} catch (DocumentException e) {
					e.printStackTrace();
				}
				issueTaskDao.updateOrdinaryPrintFile(id, pdfPath.substring(0,pdfPath.lastIndexOf("/"))+"/"+fileName+"-prt-ord.swf");
				return "redirect";
		}
		return SUCCESS;
	}
	
	@Actions({
	@Action(value="adminPrintFileRead",params={"ftype","ad"},results={@Result(name="success",location="/")}),
	@Action(value="printFileRead",params={"ftype","od"},results={@Result(name="success",location="/")})}
	)
	public String fileRead(){
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String referer = request.getHeader("Referer");
		if(referer==null || !referer.endsWith("FlexPaperViewer.swf")){
			try {
				response.sendError(404);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		IssueTask task = issueTaskDao.get(id);
		String filePath = null;
		if(ftype.equals("ad")){
			filePath = task.getAdminPrintFile();
		}else if(ftype.equals("od")){
			filePath = task.getPrintFile();
		}
		
		String realPath = ServletActionContext.getServletContext().getRealPath(filePath);
		File swfFile = new File(realPath);
		FileInputStream fis = null;
		BufferedOutputStream fos = null;
		try {
			response.setHeader("Pragma","No-cache");
			response.setHeader("Cache-Control","no-cache");
			response.setDateHeader("Expires",0);
			OutputStream output = response.getOutputStream();
			fos = new BufferedOutputStream(output);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		try {
			fis = new FileInputStream(swfFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		int bytesRead = 0;
		byte[] buffer = new byte[1024];
		try {
			while ((bytesRead = fis.read(buffer, 0, 1024)) != -1) {
				fos.write(buffer, 0, bytesRead);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			fis.close();
			fos.flush();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public IssueTask getDocu() {
		return docu;
	}

	public void setDocu(IssueTask docu) {
		this.docu = docu;
	}

	public IssueTaskDaoHibernate getIssueTaskDao() {
		return issueTaskDao;
	}

	public void setIssueTaskDao(IssueTaskDaoHibernate issueTaskDao) {
		this.issueTaskDao = issueTaskDao;
	}

	public String getFtype() {
		return ftype;
	}

	public void setFtype(String ftype) {
		this.ftype = ftype;
	}
	
}
