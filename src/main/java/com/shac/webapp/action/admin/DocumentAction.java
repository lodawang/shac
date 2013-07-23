package com.shac.webapp.action.admin;

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
import com.opensymphony.xwork2.Preparable;
import com.shac.dao.hibernate.DictDataDaoHibernate;
import com.shac.dao.hibernate.IssueTaskDaoHibernate;
import com.shac.dao.hibernate.IssueTaskTCDaoHibernate;
import com.shac.dao.hibernate.TaskDeptItemDaoHibernate;
import com.shac.model.DictData;
import com.shac.model.IssueTask;
import com.shac.model.IssueTaskTC;
import com.shac.model.TaskDeptItem;
import com.shac.util.Constants;
import com.shac.util.Page;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.doomdark.uuid.UUID;
import org.doomdark.uuid.UUIDGenerator;
import org.springframework.web.util.WebUtils;

public class DocumentAction extends ActionSupport
  implements Preparable
{
  public static final UUIDGenerator uuidmaker = UUIDGenerator.getInstance();
  private Integer totalCount;
  private Page pager;
  private Integer page = new Integer(1);
  private String id;
  private IssueTask docu;
  private IssueTaskTC docutc;
  private IssueTaskDaoHibernate issueTaskDao;
  private IssueTaskTCDaoHibernate issueTaskTCDao;
  private DictDataDaoHibernate dictDataDao;
  private List<DictData> deptList;
  private List<DictData> workshopList;
  private TaskDeptItemDaoHibernate taskDeptItemDao;
  private List clientList;
  private List modelList;
  private List drawingSizeList;
  private List techDocClassList;
  private List docList;
  private String docType;
  private List historyList;
  private transient File attach;
  private transient String attachFileName;
  private transient String attachContentType;
  private List<IssueTaskTC> tasktcList;
  private IssueTaskTC updatedocutc;
  private IssueTask updatedocu;
  private String initclient;
  private String initprocDocClass;
  private String initmodelCode;
  private String initprocMode;
  private String initprocessIn;
  private String initcltPartNumb;
  private String initassembTitle;
  private String initdoctype;
  private String initassembly;

  public List<IssueTaskTC> getTasktcList()
  {
    return this.tasktcList;
  }

  public void setTasktcList(List<IssueTaskTC> tasktcList)
  {
    this.tasktcList = tasktcList;
  }

  public IssueTaskTC getUpdatedocutc()
  {
    return this.updatedocutc;
  }

  public void setUpdatedocutc(IssueTaskTC updatedocutc)
  {
    this.updatedocutc = updatedocutc;
  }

  public IssueTask getUpdatedocu()
  {
    return this.updatedocu;
  }

  public void setUpdatedocu(IssueTask updatedocu)
  {
    this.updatedocu = updatedocu;
  }

  public IssueTaskTC getDocutc()
  {
    return this.docutc;
  }

  public void setDocutc(IssueTaskTC docutc) {
    this.docutc = docutc;
  }

  public IssueTaskTCDaoHibernate getIssueTaskTCDao() {
    return this.issueTaskTCDao;
  }

  public void setIssueTaskTCDao(IssueTaskTCDaoHibernate issueTaskTCDao) {
    this.issueTaskTCDao = issueTaskTCDao;
  }

  public String getInitassembly() {
    return this.initassembly;
  }

  public void setInitassembly(String initassembly) {
    this.initassembly = initassembly;
  }

  public String getInitdoctype() {
    return this.initdoctype;
  }

  public void setInitdoctype(String initdoctype) {
    this.initdoctype = initdoctype;
  }

  public String getInitclient() {
    return this.initclient;
  }

  public void setInitclient(String initclient) {
    this.initclient = initclient;
  }

  public String getInitprocDocClass() {
    return this.initprocDocClass;
  }

  public void setInitprocDocClass(String initprocDocClass) {
    this.initprocDocClass = initprocDocClass;
  }

  public String getInitmodelCode() {
    return this.initmodelCode;
  }

  public void setInitmodelCode(String initmodelCode) {
    this.initmodelCode = initmodelCode;
  }

  public String getInitprocMode() {
    return this.initprocMode;
  }

  public void setInitprocMode(String initprocMode) {
    this.initprocMode = initprocMode;
  }

  public String getInitprocessIn() {
    return this.initprocessIn;
  }

  public void setInitprocessIn(String initprocessIn) {
    this.initprocessIn = initprocessIn;
  }

  public String getInitcltPartNumb() {
    return this.initcltPartNumb;
  }

  public void setInitcltPartNumb(String initcltPartNumb) {
    this.initcltPartNumb = initcltPartNumb;
  }

  public String getInitassembTitle() {
    return this.initassembTitle;
  }

  public void setInitassembTitle(String initassembTitle) {
    this.initassembTitle = initassembTitle;
  }

  @Actions({@Action(value="editDocuTC", results={@org.apache.struts2.convention.annotation.Result(name="drawing", location="docTaskEditManu-drawingTC.jsp"), @org.apache.struts2.convention.annotation.Result(name="techdoc", location="docTaskEditManu-techdocTC.jsp")})})
  public String editTC()
  {
    this.clientList = this.dictDataDao.findByDictType("client");
    this.modelList = this.dictDataDao.findByDictType("model");
    String result = "";
    if (this.id != null) {
      this.docutc = ((IssueTaskTC)this.issueTaskTCDao.get(this.id));

      if (this.docutc.getStatus().equalsIgnoreCase("1")) {
        this.updatedocutc = ifUpdateTC(this.docutc);
        if (this.updatedocutc == null) {
          this.updatedocu = ifUpdate(this.docutc);
        }
      }

      if (this.docutc.getDocType().equals("0")) {
        this.drawingSizeList = this.dictDataDao.findByDictType("drawsize");
        result = "drawing";
      }
      if (this.docutc.getDocType().equals("1")) {
        this.techDocClassList = this.dictDataDao.findByDictType("techdoc");
        result = "techdoc";
      }
    }

    return result;
  }

  private IssueTaskTC ifUpdateTC(IssueTaskTC temptc)
  {
    IssueTaskTC flag = null;
    HashMap filter = new HashMap();
    HashMap orderMap = new HashMap();
    filter.put("eqPartid", temptc.getPreviousitem());
    filter.put("eqAssembly", temptc.getPreviousassem());
    filter.put("dealstatus", "0");
    filter.put("docType", temptc.getDocType());

    List<IssueTaskTC> tasktcList = this.issueTaskTCDao.findByAll(filter, orderMap);

    for (IssueTaskTC iss : tasktcList)
    {
      if ((iss.getVeroftc() != null) && (iss.getVeroftc() != "") && (temptc.getVeroftc() != null) && (temptc.getVeroftc() != "") && 
        (Integer.parseInt(iss.getVeroftc()) == Integer.parseInt(temptc.getVeroftc()) - 1)) {
        flag = iss;
        break;
      }

    }

    return flag;
  }

  private IssueTask ifUpdate(IssueTaskTC dealtc)
  {
    IssueTask flag = null;
    if (dealtc.getStatus().equalsIgnoreCase("1")) {
      HashMap filter = new HashMap();
      HashMap orderMap = new HashMap();
      filter.put("eqPartid", dealtc.getPreviousitem());
      filter.put("eqAssembly", dealtc.getPreviousassem());
      filter.put("history", "0");
      filter.put("docType", dealtc.getDocType());

      List taskList = this.issueTaskDao.findByAll(filter, orderMap);
      if (taskList.size() > 0) {
        flag = (IssueTask)taskList.get(0);
      }
    }
    return flag;
  }

  @Actions({@Action(value="editDocu", results={@org.apache.struts2.convention.annotation.Result(name="drawing", location="docTaskEditManu-drawing.jsp"), @org.apache.struts2.convention.annotation.Result(name="techdoc", location="docTaskEditManu-techdoc.jsp")}), @Action(value="editVersionDocu", results={@org.apache.struts2.convention.annotation.Result(name="drawing", location="docTaskUpdVerManu-drawing.jsp"), @org.apache.struts2.convention.annotation.Result(name="techdoc", location="docTaskUpdVerManu-techdoc.jsp")})})
  public String edit()
  {
    this.clientList = this.dictDataDao.findByDictType("client");
    this.modelList = this.dictDataDao.findByDictType("model");
    String result = "";
    if (this.id != null) {
      this.docu = ((IssueTask)this.issueTaskDao.get(this.id));
      if (this.docu.getDocType().equals("0")) {
        this.drawingSizeList = this.dictDataDao.findByDictType("drawsize");
        result = "drawing";
      }
      if (this.docu.getDocType().equals("1")) {
        this.techDocClassList = this.dictDataDao.findByDictType("techdoc");
        result = "techdoc";
      }
      Map filterMap = new HashMap();

      filterMap.put("sysUID", this.docu.getSysUID());
      filterMap.put("history", "1");
      Map orderMap = new HashMap();
      orderMap.put("createTime", "desc");
      this.historyList = this.issueTaskDao.findByAll(filterMap, orderMap);
    } else {
      if (this.initdoctype != null) {
        this.docType = this.initdoctype;
        this.docu = new IssueTask();
        this.docu.setClient(this.initclient);
        this.docu.setModelCode(this.initmodelCode);
        this.docu.setProcessIn(this.initprocessIn);
        this.docu.setAssembly(this.initassembly);
        this.docu.setProcMode(this.initprocMode);
        this.docu.setCltPartNumb(this.initcltPartNumb);
        this.docu.setAssembTitle(this.initassembTitle);
      }
      if (this.docType.equals("0")) {
        this.drawingSizeList = this.dictDataDao.findByDictType("drawsize");
        result = "drawing";
      }
      if (this.docType.equals("1")) {
        this.techDocClassList = this.dictDataDao.findByDictType("techdoc");
        result = "techdoc";
      }
    }
    return result;
  }

  @Action(value="viewDocuMana", results={@org.apache.struts2.convention.annotation.Result(name="success", location="docViewMana.jsp")})
  public String view(){
		if(id!=null){
			docu = issueTaskDao.get(id);
			deptList = dictDataDao.findByDictType(Constants.DICTTYPE_REGION);
			workshopList = dictDataDao.findByDictType(Constants.DICTTYPE_WORKSHOP);
			Map filterMap = new HashMap();
			filterMap.put("taskid", docu.getId());
			Map orderMap = new HashMap();
			List<TaskDeptItem> taskDepts = taskDeptItemDao.findByAll(filterMap, orderMap);
			for(DictData data:deptList){
				for(TaskDeptItem item:taskDepts){
					if(item.getWorkshop()==null && item.getDept().getId().equals(data.getId())){
						data.setSent(item.getStatus());
						data.setSignTime(item.getSignTime());
					}
				}
			}
			for(DictData dt:workshopList){
				for(TaskDeptItem item:taskDepts){
					if(item.getWorkshop()!=null && item.getWorkshop().getId().equals(dt.getId())){
						dt.setSent(item.getStatus());
						dt.setSignTime(item.getSignTime());
					}
				}
			}
		}
		return SUCCESS;
	}

  @Actions({@Action(value="saveDrawDocu", results={@org.apache.struts2.convention.annotation.Result(name="success", location="docListManage.jsp"), @org.apache.struts2.convention.annotation.Result(name="input", location="docTaskEditManu-drawing.jsp")}, interceptorRefs={@org.apache.struts2.convention.annotation.InterceptorRef(params={"allowedTypes", "image/png,image/gif,image/jpeg,image/pjpeg,image/bmp,image/x-png,text/plain,application/msword,application/vnd.openxmlformats-officedocument.wordprocessingml.document,application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/pdf", "maximumSize", "102400000"}, value="fileUpload"), @org.apache.struts2.convention.annotation.InterceptorRef("params"), @org.apache.struts2.convention.annotation.InterceptorRef("defaultStack")}), @Action(value="saveTechDocu", results={@org.apache.struts2.convention.annotation.Result(name="success", location="docListManage.jsp"), @org.apache.struts2.convention.annotation.Result(name="input", location="docTaskEditManu-techdoc.jsp")}, interceptorRefs={@org.apache.struts2.convention.annotation.InterceptorRef(params={"allowedTypes", "image/png,image/gif,image/jpeg,image/pjpeg,image/bmp,image/x-png,text/plain,application/msword,application/vnd.openxmlformats-officedocument.wordprocessingml.document,application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/pdf", "maximumSize", "102400000"}, value="fileUpload"), @org.apache.struts2.convention.annotation.InterceptorRef("params"), @org.apache.struts2.convention.annotation.InterceptorRef("defaultStack")})})
  public String save()
  {
    Date date = new Date();

    boolean isNew = (this.docu.getId() == null) || (this.docu.getId().equals(""));
    if (isNew) {
      this.docu.setCreateTime(date);
      this.docu.setTaskStatus("0");
      this.docu.setTaskType("1");
      this.docu.setHistory("0");
      this.docu.setUpdated("0");
      this.docu.setDocVersion("001");

      IssueTask foundDocu = this.issueTaskDao.findDocuByPartId(this.docu.getPartid(), this.docu.getAssembly());
      if (foundDocu != null)
      {
        addActionError("文档ID：[" + this.docu.getPartid() + "] 总成号:[" + this.docu.getAssembly() + "] 的记录 已存在");
        return "input";
      }

    }

    if (this.attach != null) {
      FileInputStream fis = null;
      FileOutputStream fos = null;
      try {
        fis = new FileInputStream(this.attach);
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }
      Random r = new Random(date.getTime());
      String fileType = getAttachFileName().substring(getAttachFileName().lastIndexOf(".") + 1);
      String newFileNamePrefix = ServletActionContext.getRequest().getSession().getId() + r.nextInt(10000);
      String newFileName = newFileNamePrefix + "." + fileType;
      DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
      String dateDir = formatter.format(date);
      String tmp = File.separator + dateDir + File.separator;
      String uploadDir = ServletActionContext.getServletContext().getRealPath("/outnewsFile") + tmp;
      String viewPathPrefix = "/outnewsFile/" + dateDir + "/";
      String viewPath = viewPathPrefix + newFileName;
      File dirPath = new File(uploadDir);
      if (!dirPath.exists())
        dirPath.mkdirs();
      try
      {
        fos = new FileOutputStream(uploadDir + newFileName);
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }
      int bytesRead = 0;
      byte[] buffer = new byte[1024];
      try {
        while ((bytesRead = fis.read(buffer, 0, 1024)) != -1)
          fos.write(buffer, 0, bytesRead);
      }
      catch (IOException e) {
        e.printStackTrace();
      }
      try {
        fos.close();
        fis.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
      buffer = (byte[])null;
      this.docu.setAttachFile(viewPath);

      if (this.docu.getDocType().equals("0")) {
        System.out.println("加水印并转换");

        String pdfPath = this.docu.getAttachFile();
        String realPath = ServletActionContext.getServletContext().getRealPath(pdfPath);
        File pdfFile = new File(realPath);
        String fileFullName = pdfFile.getName();
        String fileName = fileFullName.substring(0, fileFullName.lastIndexOf("."));
        String waterMarkFileName = pdfFile.getParent() + File.separator + fileName + "-sct.pdf";
        File waterMarkFile = new File(waterMarkFileName);
        FileOutputStream os = null;
        try {
          os = new FileOutputStream(waterMarkFile);
        } catch (FileNotFoundException e1) {
          e1.printStackTrace();
        }

        try
        {
          PdfReader reader = new PdfReader(realPath, null);
          PdfStamper writer = new PdfStamper(reader, os);
          Image image = Image.getInstance(ServletActionContext.getServletContext().getRealPath("/img") + "/mark_security.png");
          int pageSize = reader.getNumberOfPages();
          BaseFont base = null;

          base = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", false);
          for (int i = 1; i <= pageSize; i++) {
            Rectangle rectangle = reader.getPageSizeWithRotation(i);
            float x = rectangle.getWidth() / 2.0F;
            float y = rectangle.getHeight() / 2.0F;
            int fontSize = (int)(x * 0.1D);

            PdfContentByte content = writer.getOverContent(i);
            content.saveState();

            PdfGState gs = new PdfGState();
            gs.setFillOpacity(0.1F);
            content.setGState(gs);

            content.beginText();
            content.setColorFill(BaseColor.RED);
            content.setFontAndSize(base, fontSize);
            content.showTextAligned(1, "机密文件", x, y, 35.0F);
            content.endText();

            content.beginText();
            content.setColorFill(BaseColor.RED);
            content.setFontAndSize(base, fontSize);
            content.showTextAligned(1, this.docu.getProcessIn(), x, y - fontSize - 20.0F, 35.0F);
            content.endText();

            content.restoreState();
          }

          reader.close();
          writer.close();
          try
          {
        	Process process = Runtime.getRuntime().exec("C:\\SWFTools\\pdf2swf.exe " + waterMarkFileName + " " + pdfFile.getParent() + File.separator + fileName + ".swf  -f -T 9 -t -s bitmap -s zoom=100 -s languagedir=C:\\xpdf\\xpdf-chinese-simplified");
          }
          catch (Exception e)
          {
            Process process;
            e.printStackTrace();
            System.out.println(e);
          }
        }
        catch (IOException e) {
          e.printStackTrace();
        } catch (DocumentException e) {
          e.printStackTrace();
        }
        this.docu.setViewFile(pdfPath.substring(0, pdfPath.lastIndexOf("/")) + "/" + fileName + ".swf");
        //增加生成普通打印版本
        String printfile  = createPrint();
        this.docu.setPrintFile(printfile);
      }

      if (!isNew)
      {
       // this.docu.setPrintFile(null);
        this.docu.setAdminPrintFile(null);
      }

    }else{
    	//判断生产状态是否改变了    	
        if (!isNew){
        	IssueTask docuold = issueTaskDao.get(docu.getId());
    		if(!docu.getProcessIn().equalsIgnoreCase(docuold.getProcessIn())){
    			if (this.docu.getDocType().equals("0")) {
    		        System.out.println("加水印并转换-生产状态转变");
    		        String pdfPath = this.docu.getAttachFile();
    		        if(docu.getAttachFile()!=null){
    		        String realPath = ServletActionContext.getServletContext().getRealPath(pdfPath);
    		        File pdfFile = new File(realPath);
    		        String fileFullName = pdfFile.getName();
    		        String fileName = fileFullName.substring(0, fileFullName.lastIndexOf("."));
    		        String waterMarkFileName = pdfFile.getParent() + File.separator + fileName + "-sct.pdf";
    		        File waterMarkFile = new File(waterMarkFileName);
    		        FileOutputStream os = null;
    		        try {
    		          os = new FileOutputStream(waterMarkFile);
    		        } catch (FileNotFoundException e1) {
    		          e1.printStackTrace();
    		        }

    		        try
    		        {
    		          PdfReader reader = new PdfReader(realPath, null);
    		          PdfStamper writer = new PdfStamper(reader, os);
    		          Image image = Image.getInstance(ServletActionContext.getServletContext().getRealPath("/img") + "/mark_security.png");
    		          int pageSize = reader.getNumberOfPages();
    		          BaseFont base = null;

    		          base = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", false);
    		          for (int i = 1; i <= pageSize; i++) {
    		            Rectangle rectangle = reader.getPageSizeWithRotation(i);
    		            float x = rectangle.getWidth() / 2.0F;
    		            float y = rectangle.getHeight() / 2.0F;
    		            int fontSize = (int)(x * 0.1D);

    		            PdfContentByte content = writer.getOverContent(i);
    		            content.saveState();

    		            PdfGState gs = new PdfGState();
    		            gs.setFillOpacity(0.1F);
    		            content.setGState(gs);

    		            content.beginText();
    		            content.setColorFill(BaseColor.RED);
    		            content.setFontAndSize(base, fontSize);
    		            content.showTextAligned(1, "机密文件", x, y, 35.0F);
    		            content.endText();

    		            content.beginText();
    		            content.setColorFill(BaseColor.RED);
    		            content.setFontAndSize(base, fontSize);
    		            content.showTextAligned(1, this.docu.getProcessIn(), x, y - fontSize - 20.0F, 35.0F);
    		            content.endText();

    		            content.restoreState();
    		          }

    		          reader.close();
    		          writer.close();
    		          try
    		          {
    		        	Process process = Runtime.getRuntime().exec("C:\\SWFTools\\pdf2swf.exe " + waterMarkFileName + " " + pdfFile.getParent() + File.separator + fileName + ".swf  -f -T 9 -t -s bitmap -s zoom=100 -s languagedir=C:\\xpdf\\xpdf-chinese-simplified");
    		          }
    		          catch (Exception e)
    		          {
    		            Process process;
    		            e.printStackTrace();
    		            System.out.println(e);
    		          }
    		        }
    		        catch (IOException e) {
    		          e.printStackTrace();
    		        } catch (DocumentException e) {
    		          e.printStackTrace();
    		        }
    		        this.docu.setViewFile(pdfPath.substring(0, pdfPath.lastIndexOf("/")) + "/" + fileName + ".swf");
    		      //增加生成普通打印版本
    		        String printfile  = createPrint();
    		        this.docu.setPrintFile(printfile);
    		        }
    			}
    		}    		 		
    	}
    	
    }

    if (isNew) {
      String sysUID = uuidmaker.generateTimeBasedUUID().toString().replaceAll("-", "");
      this.docu.setSysUID(sysUID);
    }

    this.issueTaskDao.save(this.docu);

    HttpServletRequest request = ServletActionContext.getRequest();

    this.clientList = this.dictDataDao.findByDictType("client");
    this.modelList = this.dictDataDao.findByDictType("model");

    Map filterMap = WebUtils.getParametersStartingWith(request, "docu.");
    Map orderMap = new HashMap();
    filterMap.put("history", "0");
    filterMap.put("drawAndTech", "true");
    orderMap.put("createTime", "desc");

    this.docList = this.issueTaskDao.findBy(filterMap, orderMap, (this.page.intValue() - 1) * 10, 10);
    this.totalCount = Integer.valueOf(this.issueTaskDao.getCount(filterMap).intValue());

    return "success";
  }
  
  
  

  private String createPrint() {
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
		return pdfPath.substring(0,pdfPath.lastIndexOf("/"))+"/"+fileName+"-prt-ord.swf";
  } 

@Actions({@Action(value="saveDrawDocuTC", results={@org.apache.struts2.convention.annotation.Result(name="success", location="issueTaskList.do", type="redirect"), @org.apache.struts2.convention.annotation.Result(name="input", location="docTaskEditManu-drawingTC.jsp")}, interceptorRefs={@org.apache.struts2.convention.annotation.InterceptorRef(params={"allowedTypes", "image/png,image/gif,image/jpeg,image/pjpeg,image/bmp,image/x-png,text/plain,application/msword,application/vnd.openxmlformats-officedocument.wordprocessingml.document,application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/pdf", "maximumSize", "102400000"}, value="fileUpload"), @org.apache.struts2.convention.annotation.InterceptorRef("params"), @org.apache.struts2.convention.annotation.InterceptorRef("defaultStack")}), @Action(value="saveTechDocuTC", results={@org.apache.struts2.convention.annotation.Result(name="success", location="issueTaskList.do", type="redirect"), @org.apache.struts2.convention.annotation.Result(name="input", location="docTaskEditManu-techdocTC.jsp")}, interceptorRefs={@org.apache.struts2.convention.annotation.InterceptorRef(params={"allowedTypes", "image/png,image/gif,image/jpeg,image/pjpeg,image/bmp,image/x-png,text/plain,application/msword,application/vnd.openxmlformats-officedocument.wordprocessingml.document,application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/pdf", "maximumSize", "102400000"}, value="fileUpload"), @org.apache.struts2.convention.annotation.InterceptorRef("params"), @org.apache.struts2.convention.annotation.InterceptorRef("defaultStack")})})
  public String saveTC()
  {
    Date date = new Date();

    boolean isNew = (this.docutc.getId() == null) || (this.docutc.getId().equals(""));
    String fileType;
    if (this.attach != null) {
      FileInputStream fis = null;
      FileOutputStream fos = null;
      try {
        fis = new FileInputStream(this.attach);
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }
      Random r = new Random(date.getTime());
      fileType = getAttachFileName().substring(getAttachFileName().lastIndexOf(".") + 1);
      String newFileNamePrefix = ServletActionContext.getRequest().getSession().getId() + r.nextInt(10000);
      String newFileName = newFileNamePrefix + "." + fileType;
      DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
      String dateDir = formatter.format(date);
      String tmp = File.separator + dateDir + File.separator;
      String uploadDir = ServletActionContext.getServletContext().getRealPath("/outnewsFile") + tmp;
      String viewPathPrefix = "/outnewsFile/" + dateDir + "/";
      String viewPath = viewPathPrefix + newFileName;
      File dirPath = new File(uploadDir);
      if (!dirPath.exists())
        dirPath.mkdirs();
      try
      {
        fos = new FileOutputStream(uploadDir + newFileName);
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }
      int bytesRead = 0;
      byte[] buffer = new byte[1024];
      try {
        while ((bytesRead = fis.read(buffer, 0, 1024)) != -1)
          fos.write(buffer, 0, bytesRead);
      }
      catch (IOException e) {
        e.printStackTrace();
      }
      try {
        fos.close();
        fis.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
      buffer = (byte[])null;
      this.docutc.setAttachFile(viewPath);

      if (this.docutc.getDocType().equals("0")) {
        System.out.println("加水印并转换");

        String pdfPath = this.docutc.getAttachFile();
        String realPath = ServletActionContext.getServletContext().getRealPath(pdfPath);
        File pdfFile = new File(realPath);
        String fileFullName = pdfFile.getName();
        String fileName = fileFullName.substring(0, fileFullName.lastIndexOf("."));
        String waterMarkFileName = pdfFile.getParent() + File.separator + fileName + "-sct.pdf";
        File waterMarkFile = new File(waterMarkFileName);
        FileOutputStream os = null;
        try {
          os = new FileOutputStream(waterMarkFile);
        } catch (FileNotFoundException e1) {
          e1.printStackTrace();
        }

        try
        {
          PdfReader reader = new PdfReader(realPath, null);
          PdfStamper writer = new PdfStamper(reader, os);
          Image image = Image.getInstance(ServletActionContext.getServletContext().getRealPath("/img") + "/mark_security.png");
          int pageSize = reader.getNumberOfPages();
          BaseFont base = null;

          base = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", false);
          for (int i = 1; i <= pageSize; i++) {
            Rectangle rectangle = reader.getPageSizeWithRotation(i);
            float x = rectangle.getWidth() / 2.0F;
            float y = rectangle.getHeight() / 2.0F;
            int fontSize = (int)(x * 0.1D);

            PdfContentByte content = writer.getOverContent(i);
            content.saveState();

            PdfGState gs = new PdfGState();
            gs.setFillOpacity(0.1F);
            content.setGState(gs);

            content.beginText();
            content.setColorFill(BaseColor.RED);
            content.setFontAndSize(base, fontSize);
            content.showTextAligned(1, "机密文件", x, y, 35.0F);
            content.endText();

            content.beginText();
            content.setColorFill(BaseColor.RED);
            content.setFontAndSize(base, fontSize);
            content.showTextAligned(1, this.docutc.getProcessIn(), x, y - fontSize - 20.0F, 35.0F);
            content.endText();

            content.restoreState();
          }

          reader.close();
          writer.close();
          try
          {
        		Process process = Runtime.getRuntime().exec("C:\\SWFTools\\pdf2swf.exe " + waterMarkFileName + " " + pdfFile.getParent() + File.separator + fileName + ".swf  -f -T 9 -t -s bitmap -s zoom=100 -s languagedir=C:\\xpdf\\xpdf-chinese-simplified");
          }
          catch (Exception e)
          {
            Process process;
            e.printStackTrace();
            System.out.println(e);
          }
        }
        catch (IOException e) {
          e.printStackTrace();
        } catch (DocumentException e) {
          e.printStackTrace();
        }
        this.docutc.setViewFile(pdfPath.substring(0, pdfPath.lastIndexOf("/")) + "/" + fileName + ".swf");
      }

      if (!isNew)
      {
        this.docutc.setPrintFile(null);
        this.docutc.setAdminPrintFile(null);
      }

    }

    this.issueTaskTCDao.save(this.docutc);

    Map filterMap = new HashMap();
    Map orderMap = new HashMap();
    filterMap.put("dealstatus", "0");
    orderMap.put("createTime", "desc");
    this.tasktcList = this.issueTaskTCDao.findByAll(filterMap, orderMap);

    for (IssueTaskTC iss : this.tasktcList) {
      if ((iss.getDocType().equalsIgnoreCase("0")) && (iss.getAttachFile() != null) && (!iss.getAttachFile().toUpperCase().endsWith("PDF"))) {
        iss.setErroattach("1");
      }
    }

    return "success";
  }

  @Action(value="updateDocuVer", results={@org.apache.struts2.convention.annotation.Result(name="success", location="listManaDocu.do", type="redirect"), @org.apache.struts2.convention.annotation.Result(name="error", location="docManaError.jsp")}, interceptorRefs={@org.apache.struts2.convention.annotation.InterceptorRef(params={"allowedTypes", "image/png,image/gif,image/jpeg,image/pjpeg,image/bmp,image/x-png,text/plain,application/msword,application/vnd.openxmlformats-officedocument.wordprocessingml.document,application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/pdf", "maximumSize", "102400000"}, value="fileUpload"), @org.apache.struts2.convention.annotation.InterceptorRef("params"), @org.apache.struts2.convention.annotation.InterceptorRef("defaultStack")})
  public String update()
  {
    Date date = new Date();
    String oid = this.docu.getId();
    IssueTask oldDocu = (IssueTask)this.issueTaskDao.get(oid);

    if (oldDocu.getHistory().equals("1")) {
      addActionError("该文档已升级过，并归为历史文档，请在后续版本上执行升级操作！");
      return "error";
    }

    int count = this.issueTaskDao.getCountBySysUID(oldDocu.getSysUID());
    NumberFormat integerFormat = new DecimalFormat("000");
    String ver = integerFormat.format(count + 1);

    IssueTask newVersion = this.docu;
    newVersion.setId(null);
    newVersion.setCreateTime(date);
    newVersion.setTaskStatus("0");
    newVersion.setTaskType("1");
    newVersion.setHistory("0");
    newVersion.setUpdated("1");
    newVersion.setAttachFile(oldDocu.getAttachFile());
    newVersion.setDocVersion(ver);
    newVersion.setSysUID(oldDocu.getSysUID());

    if (this.attach != null) {
      FileInputStream fis = null;
      FileOutputStream fos = null;
      try {
        fis = new FileInputStream(this.attach);
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }
      Random r = new Random(date.getTime());
      String fileType = getAttachFileName().substring(getAttachFileName().lastIndexOf(".") + 1);
      String newFileNamePrefix = ServletActionContext.getRequest().getSession().getId() + r.nextInt(10000);
      String newFileName = newFileNamePrefix + "." + fileType;
      DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
      String dateDir = formatter.format(date);
      String tmp = File.separator + dateDir + File.separator;
      String uploadDir = ServletActionContext.getServletContext().getRealPath("/outnewsFile") + tmp;
      String viewPathPrefix = "/outnewsFile/" + dateDir + "/";
      String viewPath = viewPathPrefix + newFileName;
      File dirPath = new File(uploadDir);
      if (!dirPath.exists())
        dirPath.mkdirs();
      try
      {
        fos = new FileOutputStream(uploadDir + newFileName);
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }
      int bytesRead = 0;
      byte[] buffer = new byte[1024];
      try {
        while ((bytesRead = fis.read(buffer, 0, 1024)) != -1)
          fos.write(buffer, 0, bytesRead);
      }
      catch (IOException e) {
        e.printStackTrace();
      }
      try {
        fos.close();
        fis.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
      buffer = (byte[])null;
      newVersion.setAttachFile(viewPath);

      if (newVersion.getDocType().equals("0")) {
        System.out.println("加水印并转换");
        String pdfPath = newVersion.getAttachFile();
        String realPath = ServletActionContext.getServletContext().getRealPath(pdfPath);
        File pdfFile = new File(realPath);
        String fileFullName = pdfFile.getName();
        String fileName = fileFullName.substring(0, fileFullName.lastIndexOf("."));
        String waterMarkFileName = pdfFile.getParent() + File.separator + fileName + "-sct.pdf";
        File waterMarkFile = new File(waterMarkFileName);
        FileOutputStream os = null;
        try {
          os = new FileOutputStream(waterMarkFile);
        } catch (FileNotFoundException e1) {
          e1.printStackTrace();
        }
        try
        {
          PdfReader reader = new PdfReader(realPath, null);
          PdfStamper writer = new PdfStamper(reader, os);
          Image image = Image.getInstance(ServletActionContext.getServletContext().getRealPath("/img") + "/mark_security.png");

          int pageSize = reader.getNumberOfPages();
          BaseFont base = null;

          base = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", false);
          for (int i = 1; i <= pageSize; i++) {
            Rectangle rectangle = reader.getPageSizeWithRotation(i);
            float x = rectangle.getWidth() / 2.0F;
            float y = rectangle.getHeight() / 2.0F;
            int fontSize = (int)(x * 0.1D);

            PdfContentByte content = writer.getOverContent(i);
            content.saveState();

            PdfGState gs = new PdfGState();
            gs.setFillOpacity(0.1F);
            content.setGState(gs);

            content.beginText();
            content.setColorFill(BaseColor.RED);
            content.setFontAndSize(base, fontSize);
            content.showTextAligned(1, "机密文件", x, y, 35.0F);
            content.endText();

            content.beginText();
            content.setColorFill(BaseColor.RED);
            content.setFontAndSize(base, fontSize);
            content.showTextAligned(1, this.docu.getProcessIn(), x, y - fontSize - 20.0F, 35.0F);
            content.endText();

            content.restoreState();
          }

          reader.close();
          writer.close();
          try
          {
        		Process process = Runtime.getRuntime().exec("C:\\SWFTools\\pdf2swf.exe " + waterMarkFileName + " " + pdfFile.getParent() + File.separator + fileName + ".swf  -f -T 9 -t -s bitmap -s zoom=100 -s languagedir=C:\\xpdf\\xpdf-chinese-simplified");
          }
          catch (Exception e)
          {
            Process process;
            e.printStackTrace();
            System.out.println(e);
          }
        }
        catch (IOException e) {
          e.printStackTrace();
        } catch (DocumentException e) {
          e.printStackTrace();
        }
        newVersion.setViewFile(pdfPath.substring(0, pdfPath.lastIndexOf("/")) + "/" + fileName + ".swf");
      }

    }

    this.issueTaskDao.saveIssueTask(newVersion);
    this.issueTaskDao.updateIssueHisStatus(oid, "1");
    if (oldDocu.getDocType().equals("0"))
    {
      String pdfPath = oldDocu.getAttachFile();
      String realPath = ServletActionContext.getServletContext().getRealPath(pdfPath);
      File pdfFile = new File(realPath);
      String fileFullName = pdfFile.getName();
      String fileName = fileFullName.substring(0, fileFullName.lastIndexOf("."));
      String securityFileName = pdfFile.getParent() + File.separator + fileName + "-sct.pdf";
      String waterMarkFileName = pdfFile.getParent() + File.separator + fileName + "-hst.pdf";
      File waterMarkFile = new File(waterMarkFileName);
      FileOutputStream os = null;
      try {
        os = new FileOutputStream(waterMarkFile);
      } catch (FileNotFoundException e1) {
        e1.printStackTrace();
      }
      try
      {
        PdfReader reader = new PdfReader(securityFileName, null);
        PdfStamper writer = new PdfStamper(reader, os);
        Image image = Image.getInstance(ServletActionContext.getServletContext().getRealPath("/img") + "/mark_history.png");

        int pageSize = reader.getNumberOfPages();
        BaseFont base = null;

        base = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", false);
        for (int i = 1; i <= pageSize; i++) {
          Rectangle rectangle = reader.getPageSizeWithRotation(i);
          float x = rectangle.getWidth() / 2.0F;
          float y = rectangle.getHeight() / 2.0F;
          int fontSize = (int)(x * 0.1D);

          PdfContentByte content = writer.getOverContent(i);
          content.saveState();

          PdfGState gs = new PdfGState();
          gs.setFillOpacity(0.1F);
          content.setGState(gs);

          content.beginText();
          content.setColorFill(BaseColor.RED);
          content.setFontAndSize(base, fontSize);
          content.showTextAligned(1, "历史版本", x, y - (fontSize + 20) * 2, 35.0F);
          content.endText();

          content.restoreState();
        }

        reader.close();
        writer.close();
        try
        {
        	Process process = Runtime.getRuntime().exec("C:\\SWFTools\\pdf2swf.exe " + waterMarkFileName + " " + pdfFile.getParent() + File.separator + fileName + "-hst.swf  -f -T 9 -t -s bitmap -s languagedir=C:\\xpdf\\xpdf-chinese-simplified");
        }
        catch (Exception e)
        {
          Process process;
          e.printStackTrace();
          System.out.println(e);
        }
      }
      catch (IOException e) {
        e.printStackTrace();
      } catch (DocumentException e) {
        e.printStackTrace();
      }
      this.issueTaskDao.updateIssueTaskViewFile(oid, pdfPath.substring(0, pdfPath.lastIndexOf("/")) + "/" + fileName + "-hst.swf");
    }

    return "success";
  }

  @Action(value="listManaDocu", results={@org.apache.struts2.convention.annotation.Result(name="success", location="docListManage.jsp")})
  public String list() {
    HttpServletRequest request = ServletActionContext.getRequest();

    this.clientList = this.dictDataDao.findByDictType("client");
    this.modelList = this.dictDataDao.findByDictType("model");

    Map filterMap = WebUtils.getParametersStartingWith(request, "docu.");
    Map orderMap = new HashMap();
    filterMap.put("history", "0");
    filterMap.put("drawAndTech", "true");
    orderMap.put("createTime", "desc");

    this.docList = this.issueTaskDao.findBy(filterMap, orderMap, (this.page.intValue() - 1) * 10, 10);
    this.totalCount = Integer.valueOf(this.issueTaskDao.getCount(filterMap).intValue());
    return "success";
  }

  public String getId()
  {
    return this.id;
  }

  public void setId(String id)
  {
    this.id = id;
  }

  public IssueTask getDocu()
  {
    return this.docu;
  }

  public void setDocu(IssueTask docu)
  {
    this.docu = docu;
  }

  public IssueTaskDaoHibernate getIssueTaskDao()
  {
    return this.issueTaskDao;
  }

  public void setIssueTaskDao(IssueTaskDaoHibernate issueTaskDao)
  {
    this.issueTaskDao = issueTaskDao;
  }

  public String getDocType() {
    return this.docType;
  }

  public void setDocType(String docType) {
    this.docType = docType;
  }

  public DictDataDaoHibernate getDictDataDao() {
    return this.dictDataDao;
  }

  public void setDictDataDao(DictDataDaoHibernate dictDataDao) {
    this.dictDataDao = dictDataDao;
  }

  public List getClientList() {
    return this.clientList;
  }

  public void setClientList(List clientList) {
    this.clientList = clientList;
  }

  public List getModelList() {
    return this.modelList;
  }

  public void setModelList(List modelList) {
    this.modelList = modelList;
  }

  public List getDocList() {
    return this.docList;
  }

  public void setDocList(List docList) {
    this.docList = docList;
  }

  public File getAttach() {
    return this.attach;
  }

  public void setAttach(File attach) {
    this.attach = attach;
  }

  public String getAttachFileName() {
    return this.attachFileName;
  }

  public void setAttachFileName(String attachFileName) {
    this.attachFileName = attachFileName;
  }

  public String getAttachContentType() {
    return this.attachContentType;
  }

  public void setAttachContentType(String attachContentType) {
    this.attachContentType = attachContentType;
  }

  public void prepare() throws Exception
  {
  }

  public void prepareSave() throws Exception {
    this.clientList = this.dictDataDao.findByDictType("client");
    this.modelList = this.dictDataDao.findByDictType("model");
    this.drawingSizeList = this.dictDataDao.findByDictType("drawsize");
    this.techDocClassList = this.dictDataDao.findByDictType("techdoc");
    if (!this.docu.getId().equals(""))
      this.docu = ((IssueTask)this.issueTaskDao.get(this.docu.getId()));
  }

  public List getDrawingSizeList()
  {
    return this.drawingSizeList;
  }

  public void setDrawingSizeList(List drawingSizeList) {
    this.drawingSizeList = drawingSizeList;
  }

  public List getTechDocClassList() {
    return this.techDocClassList;
  }

  public void setTechDocClassList(List techDocClassList) {
    this.techDocClassList = techDocClassList;
  }

  public List getHistoryList() {
    return this.historyList;
  }

  public void setHistoryList(List historyList) {
    this.historyList = historyList;
  }

  public List<DictData> getDeptList() {
    return this.deptList;
  }

  public void setDeptList(List<DictData> deptList) {
    this.deptList = deptList;
  }

  public TaskDeptItemDaoHibernate getTaskDeptItemDao() {
    return this.taskDeptItemDao;
  }

  public void setTaskDeptItemDao(TaskDeptItemDaoHibernate taskDeptItemDao) {
    this.taskDeptItemDao = taskDeptItemDao;
  }

  public Integer getTotalCount() {
    return this.totalCount;
  }

  public void setTotalCount(Integer totalCount) {
    this.totalCount = totalCount;
  }

  public Page getPager() {
    return this.pager;
  }

  public void setPager(Page pager) {
    this.pager = pager;
  }

  public Integer getPage() {
    return this.page;
  }

  public void setPage(Integer page) {
    this.page = page;
  }

  public List<DictData> getWorkshopList() {
    return this.workshopList;
  }

  public void setWorkshopList(List<DictData> workshopList) {
    this.workshopList = workshopList;
  }
}