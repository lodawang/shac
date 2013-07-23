package com.shac.webapp.action;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.opensymphony.xwork2.ActionSupport;

public class PdfWatermarkAction extends ActionSupport{
	
	@Action(value="pdfMark",results={@Result(name="success",location="docProtectView.jsp")})
	public String pdfMark() throws IOException{
		HttpServletResponse response = ServletActionContext.getResponse();
        ServletOutputStream os =  response.getOutputStream();
		String realPath =  "C:\\SWFTools\\A0+3.pdf";
        //String realPath =  "C:\\SWFTools\\109_2011_EU-2011-en.pdf";
		try {
			PdfReader reader = new PdfReader(realPath,null);
			PdfStamper writer = new PdfStamper(reader,os);
			
			int pageSize = reader.getNumberOfPages();
			for(int i=1;i<=pageSize;i++){
				PdfContentByte under = writer.getUnderContent(i);
				Image image = Image.getInstance(ServletActionContext.getServletContext().getRealPath("/img")+ "/mark_security.png");
				image.setAbsolutePosition(0,0);
				image.setTransparency(new int[]{0xf,0xf});
				under.addImage(image);
			}
		    reader.close();
		    writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return null;
	}
}
