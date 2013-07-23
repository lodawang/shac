package com.shac.webapp.action.admin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Result;
import org.doomdark.uuid.UUIDGenerator;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.util.WebUtils;

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
import com.shac.dao.hibernate.RuleRecipientDaoHibernate;
import com.shac.dao.hibernate.TaskDeptItemDaoHibernate;
import com.shac.dao.hibernate.UserDaoHibernate;
import com.shac.model.DictData;
import com.shac.model.IssueRule;
import com.shac.model.IssueTask;
import com.shac.model.Role;
import com.shac.model.TaskDeptItem;
import com.shac.model.User;
import com.shac.service.MailEngine;
import com.shac.util.Constants;
import com.shac.util.Page;
/**
 * 各厂可以通过系统仅对正式工艺文件升版发放，发放范围是本厂的车间和其它科室以及技术中心。升版时，ID号不可变。
 * 由于正式工艺文件第一版是技术中心发放的，所以各厂升版后回发给技术中心后，在技术中心方可以看到该文件的所有版本，并且系统支持技术中心对该文件转厂发放。
 * @author win7
 *
 */
public class FactoryDocumentManageAction extends ActionSupport implements Preparable{
	public static final UUIDGenerator uuidmaker = UUIDGenerator.getInstance();
	//分页参数
	private Integer totalCount;
	private Page pager;
	private Integer page = new Integer(1);
	
	private String id;
	private IssueTask docu;
	private IssueTaskDaoHibernate issueTaskDao;
	private DictDataDaoHibernate dictDataDao;
	
	private List<DictData> deptList;
	private TaskDeptItemDaoHibernate taskDeptItemDao;
	private RuleRecipientDaoHibernate ruleRecipientDao;
	
	private List clientList;
	private List modelList;
	private List procDocClassList;//工艺文档分类
	private List procModeList;//工艺方式
	private List docList;
	private String docType;
	private List historyList;
	
	private transient File attach;
	private transient String attachFileName;
	private transient String attachContentType;
	
	private String deptId;
	private MailEngine mailEngine;
	private UserDaoHibernate userDao;
	private List<String>  deptSelected;
	private List<DictData> recipients;
	
	@Action(value="factoryDocMngList",params={"docType",Constants.DOC_DOCTYPE_PROC},results={@Result(name="success",location="factoryDocMngList.jsp")})
	public String list(){
		clientList = dictDataDao.findByDictType(Constants.DICTTYPE_CLIENT);
		modelList = dictDataDao.findByDictType(Constants.DICTTYPE_MODEL);
		procDocClassList = dictDataDao.findByDictType(Constants.DICTTYPE_PROCDOCCLASS);
		procModeList = dictDataDao.findByDictType(Constants.DICTTYPE_PROCMODE);
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userDao.findByLoginID(userDetails.getUsername());
		String dept = user.getRegion().getId();
		
		
		HttpServletRequest request = ServletActionContext.getRequest();
		Map filterMap = WebUtils.getParametersStartingWith(request, "docu.");
		Map orderMap = new HashMap();
		orderMap.put("createTime", "desc");
		
		filterMap.put("dept", dept);
		filterMap.put("status", "1");
		filterMap.put("docType", docType);
		filterMap.put("history", "0");
		//filterMap.put("processIn","正式生产");
		
		//兼顾全厂接收和部分车间接收的文档
		List<Role> roles = userDao.findRolesByUid(user.getId());
		boolean recvAdmin = false;
		for(Role role:roles){
			if(role.getCode().equals(Constants.ROLE_RECVADMIN)){
				recvAdmin = true;
			}
		}
		if(recvAdmin){
			filterMap.put("role", Constants.ROLE_RECVADMIN);
		}else{
			filterMap.put("role", Constants.ROLE_RECV);
			filterMap.put("workshop", user.getWorkshop());
		}
		
		docList = taskDeptItemDao.findBy(filterMap, orderMap,(page.intValue() - 1) * 10, 10);
		totalCount = taskDeptItemDao.getCount(filterMap).intValue();
		return SUCCESS;
	}
	
	/**
	 * 修改、升级界面
	 * @return
	 */
	@Actions({
		@Action(value="factoryDocMngEditForSave",results={@Result(name="success",location="factoryDocMngEditForSave.jsp")}),
		@Action(value="factoryDocMngEditForUpdate",results={@Result(name="success",location="factoryDocMngEditForUpdate.jsp")})
	})
	public String edit(){
		clientList = dictDataDao.findByDictType(Constants.DICTTYPE_CLIENT);
		modelList = dictDataDao.findByDictType(Constants.DICTTYPE_MODEL);
		procDocClassList = dictDataDao.findByDictType(Constants.DICTTYPE_PROCDOCCLASS);
		procModeList = dictDataDao.findByDictType(Constants.DICTTYPE_PROCMODE);
		String result = "";
		if(id!=null){
			docu = issueTaskDao.get(id);
			Map filterMap = new HashMap();
			/* 原查询历史版本条件
			filterMap.put("eqPartid", docu.getPartid());
			filterMap.put("history", "1");
			*/
			filterMap.put("sysUID", docu.getSysUID());
			filterMap.put("history", "1");
			Map orderMap = new HashMap();
			orderMap.put("createTime", "desc");
			historyList = issueTaskDao.findByAll(filterMap, orderMap);
		}else{
			procDocClassList = new ArrayList();
			DictData data = new DictData();
			data.setDictValue("更改通知单");
			procDocClassList.add(data);
		}
		return result;
	}
	
	/**
	 * 修改保存文档
	 * @return
	 */
	@Action(value="factoryDocMngSave",results={@Result(name="success",location="factoryDocMngList.do",type="redirect"),@Result(name="input",location="factoryDocMngEditForSave.jsp")},
			interceptorRefs={@InterceptorRef(params={"allowedTypes","image/png,image/gif,image/jpeg,image/pjpeg,image/bmp,image/x-png,text/plain,application/msword," +
					"application/vnd.openxmlformats-officedocument.wordprocessingml.document,application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/pdf","maximumSize","102400000"},
					value = "fileUpload"),@InterceptorRef("params"),@InterceptorRef("defaultStack")})
	public String save(){
		Date date = new Date();
		
		boolean isNew = docu.getId()==null||docu.getId().equals("");
		if(isNew){
			docu.setCreateTime(date);
			docu.setTaskStatus(Constants.ISSUE_TASK_WAIT);
			docu.setTaskType(Constants.ISSUE_TASKTYPE_MANU);
			docu.setHistory("0");
			docu.setUpdated("0");
			docu.setDocVersion("001");
			
			IssueTask foundDocu = issueTaskDao.findDocuByPartId(docu.getPartid(),docu.getAssembly());
			if(foundDocu!=null){
				addActionError("文档ID：["+docu.getPartid()+"] 的记录 已存在");
				return INPUT;
			}
		}
		
		
		if(attach!=null){
			FileInputStream fis = null;
			FileOutputStream fos = null;
			try {
				fis = new FileInputStream(attach);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			Random r = new Random(date.getTime());
			String fileType = getAttachFileName().substring(getAttachFileName().lastIndexOf(".") + 1);
			String newFileNamePrefix = ServletActionContext.getRequest().getSession().getId()+ r.nextInt(10000) ;
			String newFileName = newFileNamePrefix+ "." + fileType;
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String dateDir = formatter.format(date);
			String tmp = File.separator + dateDir + File.separator;
			String uploadDir = ServletActionContext.getServletContext().getRealPath("/"+Constants.DOCU_UPLOAD_PATH)+ tmp;
			String viewPathPrefix = "/"+Constants.DOCU_UPLOAD_PATH+"/"+dateDir+"/";
			String viewPath = viewPathPrefix + newFileName;
			File dirPath = new File(uploadDir);
			if (!dirPath.exists()) {
				dirPath.mkdirs();
			}
			try {
				fos = new FileOutputStream(uploadDir + newFileName);
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
				fos.close();
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			buffer = null;
			docu.setAttachFile(viewPath);
			
			//水印处理+文件转换
			if(docu.getDocType().equals(Constants.DOC_DOCTYPE_PROC)){
					System.out.println("加水印并转换");
					
					String pdfPath = docu.getAttachFile();
					String realPath = ServletActionContext.getServletContext().getRealPath(pdfPath);
					File pdfFile = new File(realPath);
					String fileFullName = pdfFile.getName();
					String fileName = fileFullName.substring(0,fileFullName.lastIndexOf("."));
					String waterMarkFileName = pdfFile.getParent()+File.separator+fileName+"-sct.pdf";
					File waterMarkFile = new File(waterMarkFileName);
					FileOutputStream os = null;
					try {
						os = new FileOutputStream(waterMarkFile);
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					}
					
					try {
						PdfReader reader = new PdfReader(realPath,null);
						PdfStamper writer = new PdfStamper(reader,os);
						Image image = Image.getInstance(ServletActionContext.getServletContext().getRealPath("/img")+ "/mark_security.png");
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
						     //image.setAbsolutePosition(0,0);
							 //content.addImage(image);
						     //图片水印-end
						     
						     //文字水印-start
						     content.beginText();   
						     content.setColorFill(BaseColor.RED);   
						     content.setFontAndSize(base, fontSize);   
						     content.showTextAligned(Element.ALIGN_CENTER, "机密文件", x, y, 35);//35度倾斜   
						     content.endText();  
						     
						     //正式生产，试生产
						     content.beginText();
						     content.setColorFill(BaseColor.RED);   
						     content.setFontAndSize(base, fontSize);   
						     content.showTextAligned(Element.ALIGN_CENTER, docu.getProcessIn(), x, y-fontSize-20, 35);//35度倾斜   
						     content.endText();
						     
						     content.restoreState();
						     //文字水印-end
						}
					    reader.close();
					    writer.close();
					    
					    try{
							//-s poly2bitmap 加了文字水印的pdf转换成swf后打印出来的水印文字不符合要求（颜色较深），改成-s bitmap后解决
					    	Process process =Runtime.getRuntime().exec("C:\\SWFTools\\pdf2swf.exe "+waterMarkFileName+" "+pdfFile.getParent()+File.separator+fileName+".swf  -f -T 9 -t -s bitmap -s zoom=100 -s languagedir=C:\\xpdf\\xpdf-chinese-simplified");
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
					docu.setViewFile(pdfPath.substring(0,pdfPath.lastIndexOf("/"))+"/"+fileName+".swf");
			}
			//水印处理+文件转换
			
			if(!isNew){
				//当附件重新上传后,置空printFile 和 adminPrintFile,
				//点击 纸质提示打印版本 和 普通打印版本会重新生成相应文件
				docu.setPrintFile(null);
				docu.setAdminPrintFile(null);
			}
		}
		
		//sysUID
		if(isNew){
			String sysUID = uuidmaker.generateTimeBasedUUID().toString().replaceAll("-","");
			docu.setSysUID(sysUID);
		}
		
		docu = issueTaskDao.save(docu);
		
		if(isNew){
			//厂部管理员给自己所在厂部的接收表中写入一条,issueType=1,workshop=null的记录
			UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			User currentUser = userDao.findByLoginID(userDetails.getUsername());
			TaskDeptItem deptItem = new TaskDeptItem();
			deptItem.setCreateTime(new Date());
			deptItem.setDept(currentUser.getRegion());//厂部
			deptItem.setStatus("1");//直接签收掉
			deptItem.setHistory("0");
			deptItem.setTask(docu);
			deptItem.setIssueType("1");//表示需要二次发放,该标记确保厂部普通用户不会看到重复记录
			taskDeptItemDao.saveTaskDeptItem(deptItem);
			//同时在文档表中标记状态为已发放
			issueTaskDao.updateIssueTaskStatus(docu.getId(),"1");
			//签收
			taskDeptItemDao.updateHistoryBySysUID(docu, currentUser.getRegion().getId(),Constants.ROLE_RECVADMIN,null);
			
			//发送邮件通知super
			User releaseUser = userDao.findByLoginID(Constants.USER_RELEASEOP_LOGINID);
			SimpleMailMessage noticeMail = new SimpleMailMessage();
			noticeMail.setFrom(currentUser.getEmail());
			noticeMail.setTo(releaseUser.getEmail());
			String subject = subject = "厂部新增工艺文档通知["+currentUser.getRegion().getDictValue()+"]--"+docu.getPartid();
			noticeMail.setSubject(subject);
			String bodyText = currentUser.getRegion().getDictValue()+"--"+currentUser.getLoginID()+" 用户新建了 "+"编号:"+docu.getPartid()+" 版本:"+docu.getDocVersion()+"的工艺文档";
			noticeMail.setText(bodyText);
	        mailEngine.sendAsync(noticeMail);
			
		}
		return SUCCESS;
	}
	
	public void prepareSave() throws Exception {
		clientList = dictDataDao.findByDictType(Constants.DICTTYPE_CLIENT);
		modelList = dictDataDao.findByDictType(Constants.DICTTYPE_MODEL);
		procDocClassList = dictDataDao.findByDictType(Constants.DICTTYPE_PROCDOCCLASS);
		procModeList = dictDataDao.findByDictType(Constants.DICTTYPE_PROCMODE);
		if(!docu.getId().equals("")){
			docu = issueTaskDao.get(docu.getId());
		}else{
			procDocClassList = new ArrayList();
			DictData data = new DictData();
			data.setDictValue("更改通知单");
			procDocClassList.add(data);
		}
	}
	
	/**
	 * 升级文档
	 * @return
	 */
	@Action(value="factoryDocMngUpdate",results={@Result(name="success",location="factoryDocMngList.do",type="redirect"),@Result(name="error",location="docManaError.jsp")},
			interceptorRefs={@InterceptorRef(params={"allowedTypes","image/png,image/gif,image/jpeg,image/pjpeg,image/bmp,image/x-png,text/plain,application/msword," +
					"application/vnd.openxmlformats-officedocument.wordprocessingml.document,application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/pdf","maximumSize","102400000"},
					value = "fileUpload"),@InterceptorRef("params"),@InterceptorRef("defaultStack")})
	public String update(){
		Date date = new Date();
		String oid = docu.getId();
		IssueTask oldDocu = issueTaskDao.get(oid);
		
		//防止IE后退键引起的在历史旧文件上进行的升级操作
		if(oldDocu.getHistory().equals("1")){
			addActionError("该文档已升级过，并归为历史文档，请在后续版本上执行升级操作！");
			return "error";
		}
		
		//int count = issueTaskDao.getCountByPartIdOnly(oldDocu.getPartid());
		int count = issueTaskDao.getCountBySysUID(oldDocu.getSysUID()); 
		NumberFormat integerFormat = new DecimalFormat("000");
		String ver = integerFormat.format(count+1);
		
		IssueTask newVersion = docu;
		newVersion.setId(null);
		newVersion.setCreateTime(date);
		newVersion.setTaskStatus(Constants.ISSUE_TASK_WAIT);
		newVersion.setTaskType(Constants.ISSUE_TASKTYPE_MANU);
		newVersion.setHistory("0");
		newVersion.setUpdated("1");
		newVersion.setAttachFile(oldDocu.getAttachFile());
		newVersion.setDocVersion(ver);
		newVersion.setSysUID(oldDocu.getSysUID());
		
		if(attach!=null){
			FileInputStream fis = null;
			FileOutputStream fos = null;
			try {
				fis = new FileInputStream(attach);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			Random r = new Random(date.getTime());
			String fileType = getAttachFileName().substring(getAttachFileName().lastIndexOf(".") + 1);
			String newFileNamePrefix = ServletActionContext.getRequest().getSession().getId()+ r.nextInt(10000) ;
			String newFileName = newFileNamePrefix+ "." + fileType;
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String dateDir = formatter.format(date);
			String tmp = File.separator + dateDir + File.separator;
			String uploadDir = ServletActionContext.getServletContext().getRealPath("/"+Constants.DOCU_UPLOAD_PATH)+ tmp;
			String viewPathPrefix = "/"+Constants.DOCU_UPLOAD_PATH+"/"+dateDir+"/";
			String viewPath = viewPathPrefix + newFileName;
			File dirPath = new File(uploadDir);
			if (!dirPath.exists()) {
				dirPath.mkdirs();
			}
			try {
				fos = new FileOutputStream(uploadDir + newFileName);
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
				fos.close();
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			buffer = null;
			newVersion.setAttachFile(viewPath);
			
			
			//水印处理+文件转换
			if(newVersion.getDocType().equals(Constants.DOC_DOCTYPE_PROC)){
					System.out.println("加水印并转换");
					String pdfPath = newVersion.getAttachFile();
					String realPath = ServletActionContext.getServletContext().getRealPath(pdfPath);
					File pdfFile = new File(realPath);
					String fileFullName = pdfFile.getName();
					String fileName = fileFullName.substring(0,fileFullName.lastIndexOf("."));
					String waterMarkFileName = pdfFile.getParent()+File.separator+fileName+"-sct.pdf";
					File waterMarkFile = new File(waterMarkFileName);
					FileOutputStream os = null;
					try {
						os = new FileOutputStream(waterMarkFile);
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					}
					
					try {
						PdfReader reader = new PdfReader(realPath,null);
						PdfStamper writer = new PdfStamper(reader,os);
						Image image = Image.getInstance(ServletActionContext.getServletContext().getRealPath("/img")+ "/mark_security.png");
						
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
						     //image.setAbsolutePosition(0,0);
							 //content.addImage(image);
						     //图片水印-end
						     
						     //文字水印-start
						     content.beginText();   
						     content.setColorFill(BaseColor.RED);   
						     content.setFontAndSize(base, fontSize);   
						     content.showTextAligned(Element.ALIGN_CENTER, "机密文件", x, y, 35);//35度倾斜   
						     content.endText();  
						     
						     //正式生产，试生产
						     content.beginText();
						     content.setColorFill(BaseColor.RED);   
						     content.setFontAndSize(base, fontSize);   
						     content.showTextAligned(Element.ALIGN_CENTER, docu.getProcessIn(), x, y-fontSize-20, 35);//35度倾斜   
						     content.endText();
						     
						     content.restoreState();
						     //文字水印-end
						}
						
					    reader.close();
					    writer.close();
					    
					    try{
							Process process =Runtime.getRuntime().exec("C:\\SWFTools\\pdf2swf.exe "+waterMarkFileName+" "+pdfFile.getParent()+File.separator+fileName+".swf  -f -T 9 -t -s bitmap -s languagedir=C:\\xpdf\\xpdf-chinese-simplified");
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
					newVersion.setViewFile(pdfPath.substring(0,pdfPath.lastIndexOf("/"))+"/"+fileName+".swf");
			}
			//水印处理+文件转换
		}
		
		issueTaskDao.saveIssueTask(newVersion);
		issueTaskDao.updateIssueHisStatus(oid, "1");//老版本归档
		if(oldDocu.getDocType().equals(Constants.DOC_DOCTYPE_PROC)){
			//老版本viewFile加历史水印-start
			String pdfPath = oldDocu.getAttachFile();
			String realPath = ServletActionContext.getServletContext().getRealPath(pdfPath);
			File pdfFile = new File(realPath);
			String fileFullName = pdfFile.getName();
			String fileName = fileFullName.substring(0,fileFullName.lastIndexOf("."));
			String securityFileName = pdfFile.getParent()+File.separator+fileName+"-sct.pdf";
			String waterMarkFileName = pdfFile.getParent()+File.separator+fileName+"-hst.pdf";
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
				     content.showTextAligned(Element.ALIGN_CENTER, "历史版本", x, y-(fontSize+20)*2, 35);//35度倾斜   
				     content.endText();  
				     
				     content.restoreState();
				     //文字水印-end
				}
				
				
			    reader.close();
			    writer.close();
			    
			    try{
					Process process =Runtime.getRuntime().exec("C:\\SWFTools\\pdf2swf.exe "+waterMarkFileName+" "+pdfFile.getParent()+File.separator+fileName+"-hst.swf  -f -T 9 -t -s bitmap -s languagedir=C:\\xpdf\\xpdf-chinese-simplified");
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
			issueTaskDao.updateIssueTaskViewFile(oid, pdfPath.substring(0,pdfPath.lastIndexOf("/"))+"/"+fileName+"-hst.swf");
			//老版本viewFile加历史水印-end
		}
		
		//厂部管理员给自己所在厂部的接收表中写入一条,issueType=1,workshop=null的记录
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User currentUser = userDao.findByLoginID(userDetails.getUsername());
		TaskDeptItem deptItem = new TaskDeptItem();
		deptItem.setCreateTime(new Date());
		deptItem.setDept(currentUser.getRegion());//厂部
		deptItem.setStatus("1");//直接签收掉
		deptItem.setHistory("0");
		deptItem.setTask(newVersion);
		deptItem.setIssueType("1");//表示需要二次发放,该标记确保厂部普通用户不会看到重复记录
		taskDeptItemDao.saveTaskDeptItem(deptItem);
		//同时在文档表中标记状态为已发放
		issueTaskDao.updateIssueTaskStatus(newVersion.getId(),"1");
		//签收
		taskDeptItemDao.updateHistoryBySysUID(newVersion, currentUser.getRegion().getId(),Constants.ROLE_RECVADMIN,null);
		
		
		//发送邮件通知super
		User releaseUser = userDao.findByLoginID(Constants.USER_RELEASEOP_LOGINID);
		SimpleMailMessage noticeMail = new SimpleMailMessage();
		noticeMail.setFrom(currentUser.getEmail());
		noticeMail.setTo(releaseUser.getEmail());
		String subject = subject = "厂部升级工艺文档通知["+currentUser.getRegion().getDictValue()+"]--"+oldDocu.getPartid();
		noticeMail.setSubject(subject);
		String bodyText = currentUser.getRegion().getDictValue()+"--"+currentUser.getLoginID()+" 用户升级了 "+"原编号:"+oldDocu.getPartid()+" 原版本:"+oldDocu.getDocVersion()+"的工艺文档，新版编号:"+newVersion.getPartid()+" 版本:"+newVersion.getDocVersion();
		noticeMail.setText(bodyText);
        mailEngine.sendAsync(noticeMail);
		return SUCCESS;
	}
	
	/**
	 * 显示发放界面
	 * @return
	 */
	@Actions({
		@Action(value="factoryDocMngView",results={@Result(name="success",location="factoryDocMngView.jsp")}),
		@Action(value="factoryDocMngIssue",results={@Result(name="success",location="factoryDocMngIssue.jsp")})
	})
	public String view(){
		if(id!=null){
			UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			User currentUser = userDao.findByLoginID(userDetails.getUsername());
			
			docu = issueTaskDao.get(id);
			recipients = dictDataDao.findChildren(currentUser.getRegion().getId());
			Map filterMap = new HashMap();
			filterMap.put("taskid", docu.getId());
			Map orderMap = new HashMap();
			List<TaskDeptItem> taskDepts = taskDeptItemDao.findByAll(filterMap, orderMap);
			for(DictData rcp:recipients){
				for(TaskDeptItem depItem:taskDepts){
					if(depItem.getWorkshop()!=null && rcp.getId().equals(depItem.getWorkshop().getId())){
						rcp.setSent(depItem.getStatus());
					}
				}
			}
		}
		return SUCCESS;
	}
	
	/**
	 * 发放操作
	 * @return
	 */
	@Action(value="factoryDocMngIssueSave",results={@Result(name="success",location="factoryDocMngList.do",type="redirect")})
	public String issue(){
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User currentUser = userDao.findByLoginID(userDetails.getUsername());
		User releaseUser = userDao.findByLoginID(Constants.USER_RELEASEOP_LOGINID);
		
		docu = issueTaskDao.get(docu.getId());
		
		if(deptSelected!=null){
			for(String dept:deptSelected){
				DictData rcpt = dictDataDao.get(dept);
				
				TaskDeptItem deptItem = new TaskDeptItem();
				deptItem.setCreateTime(new Date());
				deptItem.setDept(rcpt.getParent());
				deptItem.setWorkshop(rcpt);
				deptItem.setStatus("0");
				deptItem.setHistory("0");
				deptItem.setTask(docu);
				taskDeptItemDao.saveTaskDeptItem(deptItem);
				
				SimpleMailMessage noticeMail = new SimpleMailMessage();
				noticeMail.setFrom(currentUser.getEmail());
				User workshopAdminUser = userDao.findWorkshopAdminUser(rcpt.getId());
				if(workshopAdminUser!=null){
					noticeMail.setTo(workshopAdminUser.getEmail());
				}else{
					noticeMail.setTo(releaseUser.getEmail());
				}
				String subject = subject = "文档发放通知--"+docu.getPartid();
				noticeMail.setSubject(subject);
				String bodyText = "编号:"+docu.getPartid()+"版本:"+docu.getDocVersion()+"的文档已发放,请到技术文档发布系统中查收";
				noticeMail.setText(bodyText);
		        mailEngine.sendAsync(noticeMail);
			}
		}
		return SUCCESS;
	}
	
	/**
	 * 未签收前撤销发放
	 * @return
	 */
	@Action(value="factoryUndoDocIssue",results={@Result(name="success",location="factoryDocMngIssue.do?id=${id}",type="redirect")})
	public String undoIssue(){
		taskDeptItemDao.removeItemByTaskAndWorkshop(docu.getId(),deptId);
		id = docu.getId();
		return SUCCESS;
	}
	
	/**
	 * 回收销毁确认
	 * 历史工艺文档的发放记录回收：destroyProcDocIssue
	 * 最新版本文档的发放记录回收：destroyProcDocAtLatest
	 * @return
	 */
	@Actions({
			@Action(value="destroyProcDocIssue",results={@Result(name="success",location="factoryDocMngView.do?id=${id}",type="redirect")}),
			@Action(value="destroyProcDocAtLatest",results={@Result(name="success",location="factoryDocMngIssue.do?id=${id}",type="redirect")})
	})
	public String destroyIssue(){
		taskDeptItemDao.updateStatusByTaskAndWorkshop(docu.getId(),deptId, "3");
		id = docu.getId();
		return SUCCESS;
	}

	public void prepare() throws Exception {
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Page getPager() {
		return pager;
	}

	public void setPager(Page pager) {
		this.pager = pager;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
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

	public DictDataDaoHibernate getDictDataDao() {
		return dictDataDao;
	}

	public void setDictDataDao(DictDataDaoHibernate dictDataDao) {
		this.dictDataDao = dictDataDao;
	}

	public List<DictData> getDeptList() {
		return deptList;
	}

	public void setDeptList(List<DictData> deptList) {
		this.deptList = deptList;
	}

	public TaskDeptItemDaoHibernate getTaskDeptItemDao() {
		return taskDeptItemDao;
	}

	public void setTaskDeptItemDao(TaskDeptItemDaoHibernate taskDeptItemDao) {
		this.taskDeptItemDao = taskDeptItemDao;
	}

	public List getClientList() {
		return clientList;
	}

	public void setClientList(List clientList) {
		this.clientList = clientList;
	}

	public List getModelList() {
		return modelList;
	}

	public void setModelList(List modelList) {
		this.modelList = modelList;
	}

	public List getProcDocClassList() {
		return procDocClassList;
	}

	public void setProcDocClassList(List procDocClassList) {
		this.procDocClassList = procDocClassList;
	}

	public List getProcModeList() {
		return procModeList;
	}

	public void setProcModeList(List procModeList) {
		this.procModeList = procModeList;
	}

	public List getDocList() {
		return docList;
	}

	public void setDocList(List docList) {
		this.docList = docList;
	}

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public List getHistoryList() {
		return historyList;
	}

	public void setHistoryList(List historyList) {
		this.historyList = historyList;
	}

	public File getAttach() {
		return attach;
	}

	public void setAttach(File attach) {
		this.attach = attach;
	}

	public String getAttachFileName() {
		return attachFileName;
	}

	public void setAttachFileName(String attachFileName) {
		this.attachFileName = attachFileName;
	}

	public String getAttachContentType() {
		return attachContentType;
	}

	public void setAttachContentType(String attachContentType) {
		this.attachContentType = attachContentType;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public MailEngine getMailEngine() {
		return mailEngine;
	}

	public void setMailEngine(MailEngine mailEngine) {
		this.mailEngine = mailEngine;
	}

	public UserDaoHibernate getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDaoHibernate userDao) {
		this.userDao = userDao;
	}

	public List<String> getDeptSelected() {
		return deptSelected;
	}

	public void setDeptSelected(List<String> deptSelected) {
		this.deptSelected = deptSelected;
	}

	public RuleRecipientDaoHibernate getRuleRecipientDao() {
		return ruleRecipientDao;
	}

	public void setRuleRecipientDao(RuleRecipientDaoHibernate ruleRecipientDao) {
		this.ruleRecipientDao = ruleRecipientDao;
	}

	public List<DictData> getRecipients() {
		return recipients;
	}

	public void setRecipients(List<DictData> recipients) {
		this.recipients = recipients;
	}

}
