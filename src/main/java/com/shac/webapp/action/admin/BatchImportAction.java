package com.shac.webapp.action.admin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Result;
import org.doomdark.uuid.UUIDGenerator;
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
import com.shac.dao.hibernate.TaskDeptItemDaoHibernate;
import com.shac.dao.hibernate.UserDaoHibernate;
import com.shac.dao.hibernate.ZnoDaoHibernate;
import com.shac.model.DictData;
import com.shac.model.IssueTask;
import com.shac.model.TCIssueTask;
import com.shac.model.TaskDeptItem;
import com.shac.model.User;
import com.shac.model.Zno;
import com.shac.util.Constants;
import com.shac.util.Page;

public class BatchImportAction extends ActionSupport implements Preparable {
	public static final UUIDGenerator uuidmaker = UUIDGenerator.getInstance();

	// 分页参数
	private Integer totalCount;
	private Page pager;
	private Integer page = new Integer(1);

	// 下拉选项
	private List clientList;
	private List modelList;
	private List procDocClassList;// 工艺文档分类
	private List procModeList;// 工艺方式
	private List<DictData> seleteddep;

	// 默认初始值
	private String initclient;
	private String initprocDocClass;
	private String initmodelCode;
	private String initprocMode;
	private String initprocessIn;
	private String inittaskdep;
	private String initcltPartNumb;
	private String initassembTitle;
	private String[] initseleteddep;

	// 数据库处理
	private IssueTaskDaoHibernate issueTaskDao;
	private DictDataDaoHibernate dictDataDao;
	private UserDaoHibernate userDao;
	private ZnoDaoHibernate znoDao;
	private TaskDeptItemDaoHibernate taskDeptItemDao;
	// 数据主体
	private String id;
	private IssueTask docu;

	// 上传文件
	private transient File attach;
	private transient String attachFileName;
	private transient String attachContentType;

	// 显示列表
	private List docList;

	// 查询值
	private String upfile;
	
	//默认发放部门
	private List<DictData> recipients;
	
	private List<String>  deptSelected;
	
	//导入的时间
	private String pickdate;
	
	private String hiddenpickdate;
	
	private String flagmessage;
	
	public String getFlagmessage() {
		return flagmessage;
	}

	public void setFlagmessage(String flagmessage) {
		this.flagmessage = flagmessage;
	}

	@Action(value = "batchlist", results = { @Result(name = "success", location = "batchlist.jsp") })
	public String list() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		User user = userDao.findByLoginID(userDetails.getUsername());
		// 初始化选择值
		clientList = dictDataDao.findByDictType(Constants.DICTTYPE_CLIENT);
		modelList = dictDataDao.findByDictType(Constants.DICTTYPE_MODEL);
		procDocClassList = dictDataDao
				.findByDictType(Constants.DICTTYPE_PROCDOCCLASS);
		procModeList = dictDataDao.findByDictType(Constants.DICTTYPE_PROCMODE);
		
		seleteddep = dictDataDao.findChildren(user.getRegion().getId());
        
		// 用户只能看到自己写入的
		
		// 显示列表值
		HttpServletRequest request = ServletActionContext.getRequest();
		Map filterMap = WebUtils.getParametersStartingWith(request, "docu.");
		Map orderMap = new HashMap();
		filterMap.put("user", user);
		filterMap.put("history", "0");
		//filterMap.put("docType", Constants.BATCH_DOC_DOCTYPE_PROC);
		filterMap.put("status", Constants.DOC_STATUS);
		
		if (upfile != null) {
			filterMap.put("attachFile", upfile);
		}

		orderMap.put("createTime", "desc");
		docList = issueTaskDao.findBy(filterMap, orderMap,
				(page.intValue() - 1) * 10, 10);
		totalCount = issueTaskDao.getCount(filterMap).intValue();

		return SUCCESS;
	}

	@Action(value = "tobatch", results = { @Result(name = "success", location = "editbatch.jsp") })
	public String tobatch() {
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User currentUser = userDao.findByLoginID(userDetails.getUsername());
		// 选中默认值
		clientList = dictDataDao.findByDictType(Constants.DICTTYPE_CLIENT);
		modelList = dictDataDao.findByDictType(Constants.DICTTYPE_MODEL);
		procDocClassList = dictDataDao
				.findByDictType(Constants.DICTTYPE_PROCDOCCLASS);
		procModeList = dictDataDao.findByDictType(Constants.DICTTYPE_PROCMODE);
		
		recipients = dictDataDao.findChildren(currentUser.getRegion().getId());
        
		if (id != null) {
			docu = issueTaskDao.get(id);
			String depselected = docu.getTaskdep();
			if(depselected!=null && depselected.length()>0){
				String []  depselects = depselected.split(",");
				for(DictData rcp:recipients){
					for(String seldep:depselects){
						if(rcp.getDictValue().equalsIgnoreCase(seldep)){
							rcp.setSent(Constants.FALSE_STRING);
						}
					}
				}
			}
		} else {
			docu = new IssueTask();
			docu.setClient(initclient);
			docu.setProcDocClass(initprocDocClass);
			docu.setModelCode(initmodelCode);
			docu.setProcessIn(initprocessIn);
			docu.setProcMode(initprocMode);
			docu.setTaskdep(inittaskdep);
			docu.setCltPartNumb(initcltPartNumb);
			docu.setAssembTitle(initassembTitle);
			//设置默认发放部门
			if(initseleteddep!=null){
				for(DictData rcp:recipients){
					for(String seldep:initseleteddep){
						if(rcp.getDictValue().equalsIgnoreCase(seldep)){
							rcp.setSent(Constants.FALSE_STRING);
						}
					}
				}
			}
			
		}
		return SUCCESS;
	}

	@Action(value = "savebatch", results = {
			@Result(name = "success", location = "batchlist.jsp"),
			@Result(name = "input", location = "editbatch.jsp") }, interceptorRefs = {
			@InterceptorRef(params = {
					"allowedTypes",
					"image/png,image/gif,image/jpeg,image/pjpeg,image/bmp,image/x-png,text/plain,application/msword,"
							+ "application/vnd.openxmlformats-officedocument.wordprocessingml.document,application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/pdf",
					"maximumSize", "102400000" }, value = "fileUpload"),
			@InterceptorRef("params"), @InterceptorRef("defaultStack") })
	public String savebatch() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		User user = userDao.findByLoginID(userDetails.getUsername());

		Date date = new Date();
		boolean isNew = docu.getId() == null || docu.getId().equals("");
		if (isNew) {
			docu.setCreateTime(date);
			docu.setTaskStatus(Constants.ISSUE_TASK_WAIT);
			docu.setTaskType(Constants.ISSUE_TASKTYPE_MANU);
			docu.setHistory("0");
			docu.setUpdated("0");
			docu.setDocVersion("001");
			// 这个有改变，保存的时候，文档id会去ZNO自动获取,并设置status状态为D,并增加用户信息
			docu.setStatus(Constants.DOC_STATUS);
			docu.setUser(user);

			Zno temp = new Zno();
			temp.setUser(user);
			temp.setNoTime(new Date());
			znoDao.saveZno(temp);
			String partid = "G" + String.format("%08d", temp.getId());
			docu.setPartid(partid);

			IssueTask foundDocu = issueTaskDao.findDocuByPartId(
					docu.getPartid(), docu.getAssembly());
			if (foundDocu != null) {
				addActionError("文档ID：[" + docu.getPartid() + "] 的记录 已存在");
				return INPUT;
			}
		}

		if (attach != null) {
			FileInputStream fis = null;
			FileOutputStream fos = null;
			try {
				fis = new FileInputStream(attach);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			Random r = new Random(date.getTime());
			String fileType = getAttachFileName().substring(
					getAttachFileName().lastIndexOf(".") + 1);
			String newFileNamePrefix = ServletActionContext.getRequest()
					.getSession().getId()
					+ r.nextInt(10000);
			String newFileName = newFileNamePrefix + "." + fileType;
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String dateDir = formatter.format(date);
			String tmp = File.separator + dateDir + File.separator;
			String uploadDir = ServletActionContext.getServletContext()
					.getRealPath("/" + Constants.DOCU_UPLOAD_PATH) + tmp;
			String viewPathPrefix = "/" + Constants.DOCU_UPLOAD_PATH + "/"
					+ dateDir + "/";
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

			// 水印处理+文件转换
			if (docu.getDocType().equals(Constants.BATCH_DOC_DOCTYPE_PROC)) {
				System.out.println("加水印并转换");

				String pdfPath = docu.getAttachFile();
				String realPath = ServletActionContext.getServletContext()
						.getRealPath(pdfPath);
				File pdfFile = new File(realPath);
				String fileFullName = pdfFile.getName();
				String fileName = fileFullName.substring(0,
						fileFullName.lastIndexOf("."));
				String waterMarkFileName = pdfFile.getParent() + File.separator
						+ fileName + "-sct.pdf";
				File waterMarkFile = new File(waterMarkFileName);
				FileOutputStream os = null;
				try {
					os = new FileOutputStream(waterMarkFile);
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}

				try {
					PdfReader reader = new PdfReader(realPath, null);
					PdfStamper writer = new PdfStamper(reader, os);
					Image image = Image.getInstance(ServletActionContext
							.getServletContext().getRealPath("/img")
							+ "/mark_security.png");
					int pageSize = reader.getNumberOfPages();
					BaseFont base = null;
					// base = BaseFont.createFont(BaseFont.HELVETICA,
					// BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);
					base = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",
							BaseFont.NOT_EMBEDDED);
					for (int i = 1; i <= pageSize; i++) {
						Rectangle rectangle = reader.getPageSizeWithRotation(i);
						float x = rectangle.getWidth() / 2;
						float y = rectangle.getHeight() / 2;
						int fontSize = (int) (x * 0.1);

						PdfContentByte content = writer.getOverContent(i);
						content.saveState();
						// 透明处理
						PdfGState gs = new PdfGState();
						gs.setFillOpacity(0.1f);// 透明度
						content.setGState(gs);

						// 图片水印-start
						// image.setAbsolutePosition(0,0);
						// content.addImage(image);
						// 图片水印-end

						// 文字水印-start
						content.beginText();
						content.setColorFill(BaseColor.RED);
						content.setFontAndSize(base, fontSize);
						content.showTextAligned(Element.ALIGN_CENTER, "机密文件",
								x, y, 35);// 35度倾斜
						content.endText();

						// 正式生产，试生产
						content.beginText();
						content.setColorFill(BaseColor.RED);
						content.setFontAndSize(base, fontSize);
						content.showTextAligned(Element.ALIGN_CENTER,
								docu.getProcessIn(), x, y - fontSize - 20, 35);// 35度倾斜
						content.endText();

						content.restoreState();
						// 文字水印-end
					}
					reader.close();
					writer.close();

					try {
						// -s poly2bitmap
						// 加了文字水印的pdf转换成swf后打印出来的水印文字不符合要求（颜色较深），改成-s bitmap后解决
						Process process = Runtime
								.getRuntime()
								.exec("C:\\SWFTools\\pdf2swf.exe "
										+ waterMarkFileName
										+ " "
										+ pdfFile.getParent()
										+ File.separator
										+ fileName
										+ ".swf  -f -T 9 -t -s bitmap -s zoom=100 -s languagedir=C:\\xpdf\\xpdf-chinese-simplified");
					} catch (Exception e) {
						e.printStackTrace();
						System.out.println(e);
					}

				} catch (IOException e) {
					e.printStackTrace();
				} catch (DocumentException e) {
					e.printStackTrace();
				}
				docu.setViewFile(pdfPath.substring(0, pdfPath.lastIndexOf("/"))
						+ "/" + fileName + ".swf");
			}
			// 水印处理+文件转换
			
			if(!isNew){
				//当附件重新上传后,置空printFile 和 adminPrintFile,
				//点击 纸质提示打印版本 和 普通打印版本会重新生成相应文件
				docu.setPrintFile(null);
				docu.setAdminPrintFile(null);
			}
		}

		// sysUID
		if (isNew) {
			String sysUID = uuidmaker.generateTimeBasedUUID().toString()
					.replaceAll("-", "");
			docu.setSysUID(sysUID);
		}

		//默认发放部门
		String defaultdep = "";
		if(deptSelected!=null){
			for(String dept:deptSelected){
				defaultdep = defaultdep + dept + ",";
			}
		}
		docu.setTaskdep(defaultdep);
		
		issueTaskDao.save(docu);

		// 初始化选择值
		clientList = dictDataDao.findByDictType(Constants.DICTTYPE_CLIENT);
		modelList = dictDataDao.findByDictType(Constants.DICTTYPE_MODEL);
		procDocClassList = dictDataDao
				.findByDictType(Constants.DICTTYPE_PROCDOCCLASS);
		procModeList = dictDataDao.findByDictType(Constants.DICTTYPE_PROCMODE);
		seleteddep = dictDataDao.findChildren(user.getRegion().getId());
		
		//设置默认发放部门
		if(initseleteddep!=null){
			String[] temp_select = initseleteddep[0].split(",");
			for(DictData rcp:seleteddep){			
				for(String seldep:temp_select){
					if(rcp.getDictValue().equalsIgnoreCase(seldep.trim())){
						rcp.setSent(Constants.FALSE_STRING);
					}
				}
			}
		}
		
		Map filterMap = new HashMap();
		Map orderMap = new HashMap();
		filterMap.put("user", user);
		filterMap.put("history", "0");
		//filterMap.put("docType", Constants.BATCH_DOC_DOCTYPE_PROC);
		filterMap.put("status", Constants.DOC_STATUS);
		orderMap.put("createTime", "desc");
		docList = issueTaskDao.findBy(filterMap, orderMap,
				(page.intValue() - 1) * 10, 10);
		totalCount = issueTaskDao.getCount(filterMap).intValue();
		return SUCCESS;
	}
    
	/**
	 * 导入转正式
	 * 
	 */
	@Action(value = "tooff", results = { @Result(name = "success", location = "official.jsp") })
	public String toofficial(){		
		//查询过来的
		if(pickdate!=null&&pickdate!=""){
			Map filterMap = new HashMap();
			Map orderMap = new HashMap();
			filterMap.put("history", "0");
			filterMap.put("pickdate", pickdate);
			//filterMap.put("docType", Constants.BATCH_DOC_DOCTYPE_PROC);
			filterMap.put("status", Constants.DOC_STATUS);
			orderMap.put("createTime", "desc");
			docList = issueTaskDao.findBy(filterMap, orderMap,
					0, 10000);
			hiddenpickdate=pickdate;
		}
		return SUCCESS;
	}
	
	/**
	 *  处理重复数据
	 */
	@Action(value = "clearoff", results = { @Result(name = "success", location = "official.jsp") })
	public String clearoff(){	
		
		taskDeptItemDao.removeErroWorkshipData();
		taskDeptItemDao.removeErroCityData();
		flagmessage = "清理完成";
		return SUCCESS;
	}
	
	
	/**
	 * 批量转化正式
	 * @return
	 */
	@Action(value = "dooff", results = { @Result(name = "success", location = "official.jsp") })
	public String dooff(){
		
		Map filterMap = new HashMap();
		Map orderMap = new HashMap();
		filterMap.put("history", "0");
		filterMap.put("pickdate", hiddenpickdate);
		filterMap.put("status", Constants.DOC_STATUS);
		orderMap.put("createTime", "desc");
		docList = issueTaskDao.findBy(filterMap, orderMap,
				0, 10000);
		pickdate=hiddenpickdate;
		
		List<IssueTask> docListit = docList;
		
		for(IssueTask it : docListit){
		   if(it.getDocType().equalsIgnoreCase(Constants.BATCH_DOC_DOCTYPE_PROC) && it.getAttachFile()!=null){
			//找到批量转的所有记录,第一步修改当前记录状态,第二部插入发放部门
				it.setDocType(Constants.DOC_DOCTYPE_PROC);	    
		    //先模拟，中心一次发放,接受单位就是导入用户的单位，然后是发给该单位的厂部管理员
				User factoryDR = it.getUser();
				TaskDeptItem deptItem = new TaskDeptItem();
				deptItem.setCreateTime(new Date());
				deptItem.setDept(factoryDR.getRegion());
				deptItem.setHistory("0");
				deptItem.setTask(it);
				deptItem.setStatus("1");//直接签收掉
				deptItem.setIssueType("1");//表示需要二次发放,该标记确保厂部普通用户不会看到重复记录
				//是否重记录了
				if(!checkalreadyin(deptItem,0)){
					taskDeptItemDao.saveTaskDeptItem(deptItem);
					//签收--归档历史版本，签收最新版本
					taskDeptItemDao.updateHistoryBySysUID(it, factoryDR.getRegion().getId(),Constants.ROLE_RECVADMIN,null);
				}
				
				it.setTaskStatus("1");
				
				if(it.getTaskdep() != null && it.getTaskdep().length() > 1){
					String [] taskdepselected = it.getTaskdep().split(",");	
					List<DictData> bumens = dictDataDao.findChildren(factoryDR.getRegion().getId());
				    //在模拟部门2次发放	
					for(String dept:taskdepselected){
						for(DictData bumen:bumens){	
							if(bumen.getDictValue().equalsIgnoreCase(dept)){				
								TaskDeptItem deptItembumen = new TaskDeptItem();
								deptItembumen.setCreateTime(new Date());
								deptItembumen.setDept(factoryDR.getRegion());
								deptItembumen.setWorkshop(bumen);
								deptItembumen.setStatus("1");
								deptItembumen.setHistory("0");
								deptItembumen.setTask(it);
								if(!checkalreadyin(deptItembumen,1)){
									taskDeptItemDao.saveTaskDeptItem(deptItembumen);					
									taskDeptItemDao.updateHistoryBySysUID(it, factoryDR.getRegion().getId(),Constants.ROLE_RECVADMIN_WORKSHOP,bumen.getId());
								}
							}
						}
					}
					
				}
			    
				//八个中心部门发放
				if(Constants.DEFAULT_DEPIDS !=null && Constants.DEFAULT_DEPIDS.length>0){
					for (String dep : Constants.DEFAULT_DEPIDS) {
						DictData depdict = null;
						// 这里去查找中文在数据字典的id
						HashMap filter = new HashMap();
						filter.put("dictType", Constants.DICTTYPE_REGION);
						filter.put("dictID", dep);
						List<DictData> temp = dictDataDao.findByAll(filter,
								null);
						if (temp.size() > 0) {
							depdict = temp.get(0);
							TaskDeptItem tempdepitem = new TaskDeptItem();
							tempdepitem.setCreateTime(new Date());
							tempdepitem.setDept(depdict);
							tempdepitem.setHistory("0");
							tempdepitem.setTask(it);
							tempdepitem.setStatus("1");//直接签收掉						
							//是否重记录了
							if(!checkalreadyin(tempdepitem,0)){
								taskDeptItemDao.saveTaskDeptItem(tempdepitem);
								//签收--归档历史版本，签收最新版本
								taskDeptItemDao.updateHistoryBySysUID(it, depdict.getId(),Constants.ROLE_RECVADMIN,null);
							}
						}																	
					}										
				}
				
				
				issueTaskDao.updateTask(it);
		   }	
		}
		
		docList = docListit;
		
		return SUCCESS;
	}
	
	/**
	 * 是否已存在表内
	 * 
	 * @param dealtc
	 * @return
	 */
	private boolean checkalreadyin(TaskDeptItem deptItem,int fl) {
		// TODO Auto-generated method stub
		boolean flag = false;
		HashMap filter = new HashMap();
		HashMap orderMap = new HashMap();
		filter.put("taskid", deptItem.getTask().getId());
		if(fl == 0){
			filter.put("dept", deptItem.getDept().getId());
		}
		if(fl == 1){
			filter.put("item_workshop", deptItem.getWorkshop());
		}
		List taskList;
		taskList = taskDeptItemDao.findByAll(filter, orderMap);
		if (taskList.size() > 0) {
			flag = true;
		}

		return flag;
	}
	
	
	/**
	 * 删除 没有发放过、没有升级过的文档方可删除
	 * 
	 * @return
	 */
	@Action(value = "removeBatchProcDoc", results = { @Result(name = "success", location = "batchlist.do", type = "redirect") })
	public String remove() {
		issueTaskDao.remove(id);
		return SUCCESS;
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

	public String getInitclient() {
		return initclient;
	}

	public void setInitclient(String initclient) {
		this.initclient = initclient;
	}

	public String getInitprocDocClass() {
		return initprocDocClass;
	}

	public void setInitprocDocClass(String initprocDocClass) {
		this.initprocDocClass = initprocDocClass;
	}

	public String getInitmodelCode() {
		return initmodelCode;
	}

	public void setInitmodelCode(String initmodelCode) {
		this.initmodelCode = initmodelCode;
	}

	public String getInitprocMode() {
		return initprocMode;
	}

	public void setInitprocMode(String initprocMode) {
		this.initprocMode = initprocMode;
	}

	public String getInitprocessIn() {
		return initprocessIn;
	}

	public void setInitprocessIn(String initprocessIn) {
		this.initprocessIn = initprocessIn;
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

	public UserDaoHibernate getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDaoHibernate userDao) {
		this.userDao = userDao;
	}

	public ZnoDaoHibernate getZnoDao() {
		return znoDao;
	}

	public void setZnoDao(ZnoDaoHibernate znoDao) {
		this.znoDao = znoDao;
	}

	public String getInittaskdep() {
		return inittaskdep;
	}

	public void setInittaskdep(String inittaskdep) {
		this.inittaskdep = inittaskdep;
	}

	public List getDocList() {
		return docList;
	}

	public void setDocList(List docList) {
		this.docList = docList;
	}

	public String getUpfile() {
		return upfile;
	}

	public void setUpfile(String upfile) {
		this.upfile = upfile;
	}

	public List<DictData> getRecipients() {
		return recipients;
	}

	public void setRecipients(List<DictData> recipients) {
		this.recipients = recipients;
	}
	
	

	public List<String> getDeptSelected() {
		return deptSelected;
	}

	public void setDeptSelected(List<String> deptSelected) {
		this.deptSelected = deptSelected;
	}

	
	
	public String getInitcltPartNumb() {
		return initcltPartNumb;
	}

	public void setInitcltPartNumb(String initcltPartNumb) {
		this.initcltPartNumb = initcltPartNumb;
	}

	public String getInitassembTitle() {
		return initassembTitle;
	}

	public void setInitassembTitle(String initassembTitle) {
		this.initassembTitle = initassembTitle;
	}

	public String getPickdate() {
		return pickdate;
	}

	public void setPickdate(String pickdate) {
		this.pickdate = pickdate;
	}

	
	
	public String getHiddenpickdate() {
		return hiddenpickdate;
	}

	public void setHiddenpickdate(String hiddenpickdate) {
		this.hiddenpickdate = hiddenpickdate;
	}

	public String[] getInitseleteddep() {
		return initseleteddep;
	}

	public void setInitseleteddep(String[] initseleteddep) {
		this.initseleteddep = initseleteddep;
	}

	public List<DictData> getSeleteddep() {
		return seleteddep;
	}

	public void setSeleteddep(List<DictData> seleteddep) {
		this.seleteddep = seleteddep;
	}

	public void prepare() throws Exception {
		// TODO Auto-generated method stub

	}
	
	

	public TaskDeptItemDaoHibernate getTaskDeptItemDao() {
		return taskDeptItemDao;
	}

	public void setTaskDeptItemDao(TaskDeptItemDaoHibernate taskDeptItemDao) {
		this.taskDeptItemDao = taskDeptItemDao;
	}

	public void prepareSavebatch() throws Exception {
		clientList = dictDataDao.findByDictType(Constants.DICTTYPE_CLIENT);
		modelList = dictDataDao.findByDictType(Constants.DICTTYPE_MODEL);
		procDocClassList = dictDataDao
				.findByDictType(Constants.DICTTYPE_PROCDOCCLASS);
		procModeList = dictDataDao.findByDictType(Constants.DICTTYPE_PROCMODE);
		if (!docu.getId().equals("")) {
			docu = issueTaskDao.get(docu.getId());
		}
	}

}
