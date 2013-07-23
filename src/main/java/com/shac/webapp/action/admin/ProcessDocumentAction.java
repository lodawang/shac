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
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.shac.dao.hibernate.DictDataDaoHibernate;
import com.shac.dao.hibernate.IssueTaskDaoHibernate;
import com.shac.dao.hibernate.MessageDaoHibernate;
import com.shac.dao.hibernate.MnoDaoHibernate;
import com.shac.dao.hibernate.SnoDaoHibernate;
import com.shac.dao.hibernate.TaskDeptItemDaoHibernate;
import com.shac.dao.hibernate.UserDaoHibernate;
import com.shac.dao.hibernate.ZnoDaoHibernate;
import com.shac.model.DictData;
import com.shac.model.IssueTask;
import com.shac.model.Message;
import com.shac.model.Mno;
import com.shac.model.Role;
import com.shac.model.Sno;
import com.shac.model.TaskDeptItem;
import com.shac.model.User;
import com.shac.model.Zno;
import com.shac.service.MailEngine;
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
import java.util.ArrayList;
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
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.util.WebUtils;

public class ProcessDocumentAction extends ActionSupport
  implements Preparable
{
  public static final UUIDGenerator uuidmaker = UUIDGenerator.getInstance();
  private Integer totalCount;
  private Page pager;
  private Integer page = new Integer(1);
  private String id;
  private IssueTask docu;
  private IssueTaskDaoHibernate issueTaskDao;
  private DictDataDaoHibernate dictDataDao;
  private List<DictData> deptList;
  private List<DictData> workshopList;
  private TaskDeptItemDaoHibernate taskDeptItemDao;
  private List clientList;
  private List modelList;
  private List procDocClassList;
  private List procModeList;
  private List docList;
  private String docType;
  private List historyList;
  private String querydep;
  private String querywork;
  private MessageDaoHibernate messageDao;
  private transient File attach;
  private transient String attachFileName;
  private transient String attachContentType;
  private String deptId;
  private MailEngine mailEngine;
  private UserDaoHibernate userDao;
  private List<String> deptSelected;
  private String is_engineer;
  private String is_super;
  private String notype;
  private Integer nocount;
  private ZnoDaoHibernate znoDao;
  private SnoDaoHibernate snoDao;
  private MnoDaoHibernate mnoDao;
  private List noList;
  private List mnoList;
  private String initclient;
  private String initprocDocClass;
  private String initmodelCode;
  private String initprocMode;
  private String initprocessIn;
  private String inittaskdep;
  private String initcltPartNumb;
  private String initassembTitle;
  private String[] initseleteddep;

  public String getQuerydep()
  {
    return this.querydep;
  }

  public void setQuerydep(String querydep) {
    this.querydep = querydep;
  }

  public String getQuerywork() {
    return this.querywork;
  }

  public void setQuerywork(String querywork) {
    this.querywork = querywork;
  }

  public String getIs_engineer()
  {
    return this.is_engineer;
  }

  public void setIs_engineer(String is_engineer) {
    this.is_engineer = is_engineer;
  }

  public String getNotype()
  {
    return this.notype;
  }

  public void setNotype(String notype) {
    this.notype = notype;
  }

  public Integer getNocount() {
    return this.nocount;
  }

  public void setNocount(Integer nocount) {
    this.nocount = nocount;
  }

  public ZnoDaoHibernate getZnoDao()
  {
    return this.znoDao;
  }

  public void setZnoDao(ZnoDaoHibernate znoDao) {
    this.znoDao = znoDao;
  }

  public SnoDaoHibernate getSnoDao()
  {
    return this.snoDao;
  }

  public void setSnoDao(SnoDaoHibernate snoDao) {
    this.snoDao = snoDao;
  }

  public MnoDaoHibernate getMnoDao() {
    return this.mnoDao;
  }

  public void setMnoDao(MnoDaoHibernate mnoDao) {
    this.mnoDao = mnoDao;
  }

  public List getNoList()
  {
    return this.noList;
  }

  public void setNoList(List noList) {
    this.noList = noList;
  }

  public List getMnoList()
  {
    return this.mnoList;
  }

  public void setMnoList(List mnoList) {
    this.mnoList = mnoList;
  }

  public String getInitclient()
  {
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

  public String getInittaskdep() {
    return this.inittaskdep;
  }

  public void setInittaskdep(String inittaskdep) {
    this.inittaskdep = inittaskdep;
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

  public String[] getInitseleteddep() {
    return this.initseleteddep;
  }

  public void setInitseleteddep(String[] initseleteddep) {
    this.initseleteddep = initseleteddep;
  }

  @Actions({@Action(value="editProcessDocument", results={@org.apache.struts2.convention.annotation.Result(name="success", location="editProcessDocument.jsp")}), @Action(value="editProcDocForUpdate", results={@org.apache.struts2.convention.annotation.Result(name="success", location="editProcDocForUpdate.jsp")})})
  public String edit()
  {
    this.clientList = this.dictDataDao.findByDictType("client");
    this.modelList = this.dictDataDao.findByDictType("model");
    this.procDocClassList = this.dictDataDao.findByDictType("procdoc");
    this.procModeList = this.dictDataDao.findByDictType("procmode");
    String result = "";
    if (this.id != null) {
      this.docu = ((IssueTask)this.issueTaskDao.get(this.id));
      Map filterMap = new HashMap();

      filterMap.put("sysUID", this.docu.getSysUID());
      filterMap.put("history", "1");
      Map orderMap = new HashMap();
      orderMap.put("createTime", "desc");
      this.historyList = this.issueTaskDao.findByAll(filterMap, orderMap);
    } else {
      this.docu = new IssueTask();
      this.docu.setClient(this.initclient);
      this.docu.setProcDocClass(this.initprocDocClass);
      this.docu.setModelCode(this.initmodelCode);
      this.docu.setProcessIn(this.initprocessIn);
      this.docu.setProcMode(this.initprocMode);
      this.docu.setTaskdep(this.inittaskdep);
      this.docu.setCltPartNumb(this.initcltPartNumb);
      this.docu.setAssembTitle(this.initassembTitle);
    }
    return result;
  }

  @Actions({@Action(value="showProcessDocument", results={@org.apache.struts2.convention.annotation.Result(name="success", location="showProcessDocument.jsp")}), @Action(value="issueProcessDocument", results={@org.apache.struts2.convention.annotation.Result(name="success", location="issueProcessDocument.jsp")})})
  public String view()
  {
	  if(id!=null){
			docu = issueTaskDao.get(id);
			deptList = dictDataDao.findByDictType(Constants.DICTTYPE_REGION);
			workshopList = dictDataDao.findByDictType(Constants.DICTTYPE_WORKSHOP);
			Map filterMap = new HashMap();
			filterMap.put("taskid", docu.getId());
			Map orderMap = new HashMap();
			List<TaskDeptItem> taskDepts = taskDeptItemDao.findByAll(filterMap, orderMap);
			List<DictData> dellist = new ArrayList<DictData>();//用来装需要删除的元素
			for(DictData data:deptList){				
				if(data.getScope() != null){					
					if(docu.getDocType().equals("0")||docu.getDocType().equals("1")){					
						if(data.getScope().equals("2")){																				
							dellist.add(data);
							continue;							
						}
					}else if (docu.getDocType().equals("2")){
						if(data.getScope().equals("1")){
							dellist.add(data);
							continue;
						}
					}
				}
				for(TaskDeptItem item:taskDepts){
					if(item.getWorkshop()==null && item.getDept().getId().equals(data.getId())){
						data.setSent(item.getStatus());
						data.setSignTime(item.getSignTime());
					}
				}
			}
			deptList.removeAll(dellist);
			
			for(DictData dt:workshopList){
				for(TaskDeptItem item:taskDepts){
					if(item.getWorkshop()!=null && item.getWorkshop().getId().equals(dt.getId())){
						dt.setSent(item.getStatus());
						dt.setSignTime(item.getSignTime());
						dt.setDestroyTime(item.getDestroyTime());
					}
				}
			}
		}
		return SUCCESS;	  
  }

  @Action(value="removeProcDoc", results={@org.apache.struts2.convention.annotation.Result(name="success", location="quickProcDocList.do", type="redirect")})
  public String remove()
  {
    this.issueTaskDao.remove(this.id);
    return "success";
  }

  @Action(value="saveProcDocIssue", results={@org.apache.struts2.convention.annotation.Result(name="success", location="quickProcDocList.do", type="redirect")})
  public String issue()
  {
    UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    User currentUser = this.userDao.findByLoginID(userDetails.getUsername());

    for (String dept : this.deptSelected) {
      DictData data = new DictData();
      data.setId(dept);
      TaskDeptItem deptItem = new TaskDeptItem();
      deptItem.setCreateTime(new Date());
      deptItem.setDept(data);
    //如果是技术中心就自动签收
		if(dept.equalsIgnoreCase("5")){
			deptItem.setStatus("1");
		}else{
			deptItem.setStatus("0");	
		}		
      deptItem.setHistory("0");
      deptItem.setTask(this.docu);
      User user = this.userDao.findWriteUserByDept(dept);
      this.docu = ((IssueTask)this.issueTaskDao.get(this.docu.getId()));
      if (user != null) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(currentUser.getEmail());
        mailMessage.setTo(user.getEmail());
        String subject = "";
        if (this.docu.getUpdated().equals("1"))
          subject = "文档发放通知--" + this.docu.getPartid() + "已升级";
        else {
          subject = "文档发放通知--" + this.docu.getPartid();
        }
        mailMessage.setSubject(subject);
        String bodyText = "编号:" + this.docu.getPartid() + "版本:" + this.docu.getDocVersion() + "的文档已发放,请到技术文档发布系统中查收";
        mailMessage.setText(bodyText);
        this.mailEngine.sendAsync(mailMessage);
      }
      this.taskDeptItemDao.saveTaskDeptItem(deptItem);
    }

    defaulIssue(this.deptSelected, this.docu.getId());

    updateMessage(this.docu.getId());
    this.issueTaskDao.updateIssueTaskStatus(this.docu.getId(), "1");
    return "success";
  }

  public void updateMessage(String id)
  {
    Map filterMap = new HashMap();
    Map orderMap = new HashMap();
    filterMap.put("tskid", id);
    List<Message> messages = this.messageDao.findByAll(filterMap, orderMap);
    for (Message t : messages) {
      t.setTaskStatus("1");
      this.messageDao.updateMessage(t);
    }
  }

  public void defaulIssue(List<String> deptSelected, String tskid)
  {
    boolean flag = false;

    if ((deptSelected != null) && 
      (!deptSelected.contains("8afcd82e3d8ff425013dc34ec368126f")))
    {
      if (!checkalreadyin("8afcd82e3d8ff425013dc34ec368126f", tskid)) {
        flag = true;
      }

    }

    if (flag) {
      DictData data = new DictData();
      data.setId("8afcd82e3d8ff425013dc34ec368126f");
      TaskDeptItem deptItem = new TaskDeptItem();
      deptItem.setCreateTime(new Date());
      deptItem.setDept(data);
      deptItem.setStatus("1");
      deptItem.setHistory("0");
      deptItem.setTask(this.docu);
      this.taskDeptItemDao.saveTaskDeptItem(deptItem);
    }
  }

  private boolean checkalreadyin(String deptID, String depitemId)
  {
    boolean flag = false;
    HashMap filter = new HashMap();
    HashMap orderMap = new HashMap();
    filter.put("taskid", depitemId);
    filter.put("dept", deptID);

    List taskList = this.taskDeptItemDao.findByAll(filter, orderMap);
    if (taskList.size() > 0) {
      flag = true;
    }
    return flag;
  }

  @Action(value="undoProcDocIssue", results={@org.apache.struts2.convention.annotation.Result(name="success", location="issueProcessDocument.do?id=${id}", type="redirect")})
  public String undoIssue()
  {
    this.taskDeptItemDao.removeItemByTaskAndDept(this.docu.getId(), this.deptId);
    this.id = this.docu.getId();
    return "success";
  }

  @Action(value="saveProcessDoc", results={@org.apache.struts2.convention.annotation.Result(name="success", location="quickProcDocList.jsp"), @org.apache.struts2.convention.annotation.Result(name="input", location="editProcessDocument.jsp")}, interceptorRefs={@org.apache.struts2.convention.annotation.InterceptorRef(params={"allowedTypes", "image/png,image/gif,image/jpeg,image/pjpeg,image/bmp,image/x-png,text/plain,application/msword,application/vnd.openxmlformats-officedocument.wordprocessingml.document,application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/pdf", "maximumSize", "102400000"}, value="fileUpload"), @org.apache.struts2.convention.annotation.InterceptorRef("params"), @org.apache.struts2.convention.annotation.InterceptorRef("defaultStack")})
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
      if (foundDocu != null) {
        addActionError("文档ID：[" + this.docu.getPartid() + "] 的记录 已存在");
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

      if (this.docu.getDocType().equals("2")) {
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
                System.out.println("加水印并转换");
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

    if (isNew) {
      String sysUID = uuidmaker.generateTimeBasedUUID().toString().replaceAll("-", "");
      this.docu.setSysUID(sysUID);
    }

    this.issueTaskDao.save(this.docu);

    HttpServletRequest request = ServletActionContext.getRequest();
    UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    User currentUser = this.userDao.findByLoginID(userDetails.getUsername());

    if ((currentUser.getIs_engineer() != null) && (currentUser.getIs_engineer().equalsIgnoreCase("1")))
      this.is_engineer = currentUser.getIs_engineer();
    else {
      this.is_engineer = "0";
    }

    if ((currentUser.getLoginID() != null) && (currentUser.getLoginID().equalsIgnoreCase("super")))
      this.is_super = "1";
    else {
      this.is_super = "0";
    }

    this.clientList = this.dictDataDao.findByDictType("client");
    this.modelList = this.dictDataDao.findByDictType("model");
    this.procDocClassList = this.dictDataDao
      .findByDictType("procdoc");
    this.procModeList = this.dictDataDao.findByDictType("procmode");

    Map filterMap = WebUtils.getParametersStartingWith(request, "docu.");
    Map orderMap = new HashMap();
    filterMap.put("history", "0");
    orderMap.put("createTime", "desc");
    filterMap.put("docType", "2");

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
  
  
  @Action(value="updateProcDocVer", results={@org.apache.struts2.convention.annotation.Result(name="success", location="quickProcDocList.do", type="redirect"), @org.apache.struts2.convention.annotation.Result(name="error", location="docManaError.jsp")}, interceptorRefs={@org.apache.struts2.convention.annotation.InterceptorRef(params={"allowedTypes", "image/png,image/gif,image/jpeg,image/pjpeg,image/bmp,image/x-png,text/plain,application/msword,application/vnd.openxmlformats-officedocument.wordprocessingml.document,application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/pdf", "maximumSize", "102400000"}, value="fileUpload"), @org.apache.struts2.convention.annotation.InterceptorRef("params"), @org.apache.struts2.convention.annotation.InterceptorRef("defaultStack")})
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

      if (newVersion.getDocType().equals("2")) {
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
        	  Process process = Runtime.getRuntime().exec("C:\\SWFTools\\pdf2swf.exe " + waterMarkFileName + " " + pdfFile.getParent() + File.separator + fileName + ".swf  -f -T 9 -t -s bitmap -s languagedir=C:\\xpdf\\xpdf-chinese-simplified");
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
    if (oldDocu.getDocType().equals("2"))
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

  @Action(value="quickProcDocList", results={@org.apache.struts2.convention.annotation.Result(name="success", location="quickProcDocList.jsp")})
  public String quickList()
  {
    HttpServletRequest request = ServletActionContext.getRequest();
    UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    User currentUser = this.userDao.findByLoginID(userDetails.getUsername());

    if ((currentUser.getIs_engineer() != null) && (currentUser.getIs_engineer().equalsIgnoreCase("1")))
      this.is_engineer = currentUser.getIs_engineer();
    else {
      this.is_engineer = "0";
    }

    if ((currentUser.getLoginID() != null) && (currentUser.getLoginID().equalsIgnoreCase("super")))
      this.is_super = "1";
    else {
      this.is_super = "0";
    }

    this.clientList = this.dictDataDao.findByDictType("client");
    this.modelList = this.dictDataDao.findByDictType("model");
    this.procDocClassList = this.dictDataDao
      .findByDictType("procdoc");
    this.procModeList = this.dictDataDao.findByDictType("procmode");

    Map filterMap = WebUtils.getParametersStartingWith(request, "docu.");
    Map orderMap = new HashMap();
    filterMap.put("history", "0");
    orderMap.put("createTime", "desc");
    filterMap.put("docType", "2");

    this.docList = this.issueTaskDao.findBy(filterMap, orderMap, (this.page.intValue() - 1) * 10, 10);
    this.totalCount = Integer.valueOf(this.issueTaskDao.getCount(filterMap).intValue());
    return "success";
  }

  @Action(value="showNolist", results={@org.apache.struts2.convention.annotation.Result(name="success", location="shownoList.jsp")})
  public String showNolist()
  {
    HttpServletRequest request = ServletActionContext.getRequest();
    UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    User currentUser = this.userDao.findByLoginID(userDetails.getUsername());

    Map filterMap = new HashMap();
    filterMap.put("user", currentUser);

    Map orderMap = new HashMap();
    orderMap.put("noTime", "desc");

    this.mnoList = this.mnoDao.findBy(filterMap, orderMap, (this.page.intValue() - 1) * 10, 10);

    this.totalCount = Integer.valueOf(this.mnoDao.getCount(filterMap).intValue());
    return "success";
  }

  @Action(value="getDocNO", results={@org.apache.struts2.convention.annotation.Result(name="success", location="getNO.jsp")})
  public String getNO()
  {
    return "success";
  }

  public String creatNo(int nocount, String notype, User user)
  {
    String tempnos = "";
    this.noList = new ArrayList();

    for (int i = 0; i < nocount; i++) {
      if (notype.equalsIgnoreCase("J")) {
        Zno temp = new Zno();
        temp.setUser(user);
        temp.setNoTime(new Date());
        this.znoDao.saveZno(temp);
        tempnos = tempnos + "G" + String.format("%08d", new Object[] { Integer.valueOf(temp.getId()) }) + 
          ",";
        this.noList.add(String.valueOf("G" + String.format("%08d", new Object[] { Integer.valueOf(temp.getId()) })));
      } else if (notype.equalsIgnoreCase("P")) {
        Sno temp = new Sno();
        temp.setUser(user);
        temp.setNoTime(new Date());
        this.snoDao.saveSno(temp);
        tempnos = tempnos + "P" + String.format("%08d", new Object[] { Integer.valueOf(temp.getId()) }) + 
          ",";
        this.noList.add(String.valueOf("P" + String.format("%08d", new Object[] { Integer.valueOf(temp.getId()) })));
      }
    }

    return tempnos;
  }

  @Action(value="showDocNO", results={@org.apache.struts2.convention.annotation.Result(name="success", location="getNO.jsp")})
  public String showDocNO()
  {
    UserDetails userDetails = (UserDetails)
      SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    User user = this.userDao.findByLoginID(userDetails.getUsername());

    if (this.nocount.intValue() > 50) {
      this.nocount = Integer.valueOf(50);
    }

    String nos = "";

    nos = creatNo(this.nocount.intValue(), this.notype, user);

    ActionContext actionContext = ActionContext.getContext();
    Map session = actionContext.getSession();

    String user_jobno = "";
    if (session.get("USER_JOBNO") != null) {
      user_jobno = String.valueOf(session.get("USER_JOBNO"));
    }

    Mno mno_temp = new Mno();
    mno_temp.setNocount(this.nocount);
    mno_temp.setNoTime(new Date());
    mno_temp.setNotype(this.notype);
    mno_temp.setNos(nos);
    mno_temp.setUser(user);
    mno_temp.setJobno(user_jobno);
    this.mnoDao.saveMno(mno_temp);

    return "success";
  }

  @Action(value="complexProcDocList", params={"docType", "2"}, results={@org.apache.struts2.convention.annotation.Result(name="success", location="complexProcDocList.jsp")})
  public String complexList()
  {
    this.clientList = this.dictDataDao.findByDictType("client");
    this.modelList = this.dictDataDao.findByDictType("model");
    this.procDocClassList = this.dictDataDao.findByDictType("procdoc");
    this.procModeList = this.dictDataDao.findByDictType("procmode");

    this.deptList = this.dictDataDao.findByDictType("dept");
    this.workshopList = this.dictDataDao.findByDictType("workshop");

    HttpServletRequest request = ServletActionContext.getRequest();
    Map filterMap = WebUtils.getParametersStartingWith(request, "docu.");
    Map orderMap = new HashMap();
    filterMap.put("history", "0");
    filterMap.put("docType", this.docType);

    if ((this.querydep != null) && (!this.querydep.equals(""))) {
      filterMap.put("taskStatus", "1");

      filterMap.put("dept", this.querydep);
      if ((this.querywork != null) && (!this.querywork.equals(""))) {
        filterMap.put("querywork", this.querywork);
      }
    }

    orderMap.put("createTime", "desc");
    this.docList = this.issueTaskDao.findBy(filterMap, orderMap, (this.page.intValue() - 1) * 500, 500);
    this.totalCount = Integer.valueOf(this.issueTaskDao.getCount(filterMap).intValue());
    return "success";
  }

  @Action(value="recvProcDocList", params={"docType", "2"}, results={@org.apache.struts2.convention.annotation.Result(name="success", location="recvProcDocList.jsp")})
  public String recvList()
  {
    this.clientList = this.dictDataDao.findByDictType("client");
    this.modelList = this.dictDataDao.findByDictType("model");
    this.procDocClassList = this.dictDataDao.findByDictType("procdoc");
    this.procModeList = this.dictDataDao.findByDictType("procmode");
    UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    User user = this.userDao.findByLoginID(userDetails.getUsername());
    String dept = user.getRegion().getId();

    HttpServletRequest request = ServletActionContext.getRequest();
    Map filterMap = WebUtils.getParametersStartingWith(request, "docu.");
    Map orderMap = new HashMap();

    filterMap.put("dept", dept);
    filterMap.put("status", "1");
    filterMap.put("docType", this.docType);
    filterMap.put("history", "0");

    List<Role> roles = this.userDao.findRolesByUid(user.getId());
    boolean recvAdmin = false;
    for (Role role : roles) {
      if (role.getCode().equals("ROLE_RECVADMIN")) {
        recvAdmin = true;
      }
    }
    if (recvAdmin) {
      filterMap.put("role", "ROLE_RECVADMIN");
    } else {
      filterMap.put("role", "ROLE_RECV");
      filterMap.put("workshop", user.getWorkshop());
    }
    orderMap.put("createTime", "desc");
    this.docList = this.taskDeptItemDao.findBy(filterMap, orderMap, (this.page.intValue() - 1) * 500, 500);
    this.totalCount = Integer.valueOf(this.taskDeptItemDao.getCount(filterMap).intValue());
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
    this.procDocClassList = this.dictDataDao.findByDictType("procdoc");
    this.procModeList = this.dictDataDao.findByDictType("procmode");
    if (!this.docu.getId().equals(""))
      this.docu = ((IssueTask)this.issueTaskDao.get(this.docu.getId()));
  }

  public List getHistoryList()
  {
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

  public List getProcDocClassList() {
    return this.procDocClassList;
  }

  public void setProcDocClassList(List procDocClassList) {
    this.procDocClassList = procDocClassList;
  }

  public List getProcModeList() {
    return this.procModeList;
  }

  public void setProcModeList(List procModeList) {
    this.procModeList = procModeList;
  }

  public MailEngine getMailEngine() {
    return this.mailEngine;
  }

  public void setMailEngine(MailEngine mailEngine) {
    this.mailEngine = mailEngine;
  }

  public UserDaoHibernate getUserDao() {
    return this.userDao;
  }

  public void setUserDao(UserDaoHibernate userDao) {
    this.userDao = userDao;
  }

  public List<String> getDeptSelected() {
    return this.deptSelected;
  }

  public void setDeptSelected(List<String> deptSelected) {
    this.deptSelected = deptSelected;
  }

  public String getIs_super() {
    return this.is_super;
  }

  public void setIs_super(String is_super) {
    this.is_super = is_super;
  }

  public List<DictData> getWorkshopList() {
    return this.workshopList;
  }

  public void setWorkshopList(List<DictData> workshopList) {
    this.workshopList = workshopList;
  }

  public String getDeptId() {
    return this.deptId;
  }

  public void setDeptId(String deptId) {
    this.deptId = deptId;
  }

  public MessageDaoHibernate getMessageDao() {
    return this.messageDao;
  }

  public void setMessageDao(MessageDaoHibernate messageDao) {
    this.messageDao = messageDao;
  }
}