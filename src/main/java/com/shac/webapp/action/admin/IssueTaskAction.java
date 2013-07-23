package com.shac.webapp.action.admin;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.opensymphony.xwork2.ActionSupport;
import com.shac.dao.hibernate.DictDataDaoHibernate;
import com.shac.dao.hibernate.IssueTaskDaoHibernate;
import com.shac.dao.hibernate.IssueTaskTCDaoHibernate;
import com.shac.dao.hibernate.TaskDeptItemDaoHibernate;
import com.shac.dao.hibernate.TaskDeptItemTCDaoHibernate;
import com.shac.dao.hibernate.UserDaoHibernate;
import com.shac.model.DictData;
import com.shac.model.IssueTask;
import com.shac.model.IssueTaskTC;
import com.shac.model.TaskDeptItem;
import com.shac.model.TaskDeptItemTC;
import com.shac.model.User;
import com.shac.service.MailEngine;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.doomdark.uuid.UUID;
import org.doomdark.uuid.UUIDGenerator;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.util.WebUtils;

public class IssueTaskAction extends ActionSupport {
	public static final UUIDGenerator uuidmaker = UUIDGenerator.getInstance();
	private List<IssueTask> taskList;
	private List<IssueTaskTC> tasktcList;
	private String tcflag;
	private IssueTaskDaoHibernate issueTaskDao;
	private IssueTaskTCDaoHibernate issueTaskTCDao;
	private IssueTask task;
	private IssueTaskTC taskTC;
	private List<DictData> deptList;
	private List<DictData> workshopList;
	private DictDataDaoHibernate dictDataDao;
	private TaskDeptItemDaoHibernate taskDeptItemDao;
	private TaskDeptItemTCDaoHibernate taskDeptItemTCDao;
	private List<String> deptSelected;
	private MailEngine mailEngine;
	private UserDaoHibernate userDao;
	private String id;
	private String deptId;
	private IssueTask docu;
	private String erroattachflag;

	public String getTcflag() {
		return this.tcflag;
	}

	public void setTcflag(String tcflag) {
		this.tcflag = tcflag;
	}

	public List<IssueTaskTC> getTasktcList() {
		return this.tasktcList;
	}

	public void setTasktcList(List<IssueTaskTC> tasktcList) {
		this.tasktcList = tasktcList;
	}

	public IssueTaskTCDaoHibernate getIssueTaskTCDao() {
		return this.issueTaskTCDao;
	}

	public void setIssueTaskTCDao(IssueTaskTCDaoHibernate issueTaskTCDao) {
		this.issueTaskTCDao = issueTaskTCDao;
	}

	public IssueTaskTC getTaskTC() {
		return this.taskTC;
	}

	public void setTaskTC(IssueTaskTC taskTC) {
		this.taskTC = taskTC;
	}

	public String getErroattachflag() {
		return this.erroattachflag;
	}

	public void setErroattachflag(String erroattachflag) {
		this.erroattachflag = erroattachflag;
	}

	public IssueTask getDocu() {
		return this.docu;
	}

	public void setDocu(IssueTask docu) {
		this.docu = docu;
	}

	public TaskDeptItemTCDaoHibernate getTaskDeptItemTCDao() {
		return this.taskDeptItemTCDao;
	}

	public void setTaskDeptItemTCDao(
			TaskDeptItemTCDaoHibernate taskDeptItemTCDao) {
		this.taskDeptItemTCDao = taskDeptItemTCDao;
	}

	@Action(value = "listTCManaDocuNew", results = { @org.apache.struts2.convention.annotation.Result(name = "success",

	location = "issueTaskTCList.jsp") })
	public String tcnewlist() {
		HttpServletRequest request = ServletActionContext.getRequest();
		Map filterMap = WebUtils.getParametersStartingWith(request, "docu.");
		Map orderMap = new HashMap();
		filterMap.put("dealstatus", "0");

		orderMap.put("createTime", "desc");
		this.tasktcList = this.issueTaskTCDao.findByAll(filterMap, orderMap);

		for (IssueTaskTC iss : this.tasktcList) {
			if ((iss.getDocType().equalsIgnoreCase("0"))
					&& (iss.getAttachFile() != null) && (!iss.getAttachFile

					().toUpperCase().endsWith("PDF"))) {
				iss.setErroattach("1");
			}

			if ((iss.getStatus().equalsIgnoreCase("1")) && (!checkIfInTC(iss))) {
				iss.setMissup("1");
			}

		}

		return "success";
	}

	@Action(value = "listTCManaDocu", results = { @org.apache.struts2.convention.annotation.Result(name = "success",

	location = "issueTaskList.jsp") })
	public String tclist() {
		HttpServletRequest request = ServletActionContext.getRequest();
		Map filterMap = WebUtils.getParametersStartingWith(request, "docu.");
		Map orderMap = new HashMap();
		filterMap.put("taskType", "0");
		filterMap.put("taskStatus", "0");

		orderMap.put("createTime", "desc");
		this.taskList = this.issueTaskDao.findByAll(filterMap, orderMap);

		for (IssueTask iss : this.taskList) {
			if ((iss.getDocType().equalsIgnoreCase("0"))
					&& (iss.getAttachFile() != null) && (!iss.getAttachFile

					().toUpperCase().endsWith("PDF"))) {
				iss.setErroattach("1");
			}
		}

		return "success";
	}

	@Action(value = "issueTaskList", results = { @org.apache.struts2.convention.annotation.Result(name = "success",

	location = "issueTaskTCList.jsp") })
	public String list() {
		Map filterMap = new HashMap();
		Map orderMap = new HashMap();
		filterMap.put("dealstatus", "0");
		orderMap.put("createTime", "desc");
		this.tasktcList = this.issueTaskTCDao.findByAll(filterMap, orderMap);

		for (IssueTaskTC iss : this.tasktcList) {
			if ((iss.getDocType().equalsIgnoreCase("0"))
					&& (iss.getAttachFile() != null) && (!iss.getAttachFile

					().toUpperCase().endsWith("PDF"))) {
				iss.setErroattach("1");
			}

			if ((iss.getStatus().equalsIgnoreCase("1")) && (!checkIfInTC(iss))) {
				iss.setMissup("1");
			}

		}

		return "success";
	}

	private boolean checkIfInTC(IssueTaskTC dealtc) {
		boolean flag = false;

		HashMap filter = new HashMap();
		HashMap orderMap = new HashMap();
		filter.put("eqPartid", dealtc.getPreviousitem());
		filter.put("eqAssembly", dealtc.getPreviousassem());
		filter.put("history", "0");
		filter.put("docType", dealtc.getDocType());

		List taskList = this.issueTaskDao.findByAll(filter, orderMap);
		if (taskList.size() > 0) {
			flag = true;
		}

		if ((!flag) && (dealtc.getVeroftc() != null)) {
			HashMap filtertc = new HashMap();
			HashMap orderMaptc = new HashMap();
			filtertc.put("eqPartid", dealtc.getPreviousitem());
			filtertc.put("eqAssembly", dealtc.getPreviousassem());
			filtertc.put("docType", dealtc.getDocType());

			List<IssueTaskTC> taskListtc = this.issueTaskTCDao.findByAll(
					filtertc, orderMaptc);

			for (IssueTaskTC t : taskListtc) {
				if (t.getVeroftc() != null) {
					try {
						int a = Integer.parseInt(t.getVeroftc());
						int b = Integer.parseInt(dealtc.getVeroftc());
						if (a + 1 == b)
							flag = true;
					} catch (Exception e) {
						System.out.println("转换出错");
					}
				}
			}

		}

		return flag;
	}

	@Action(value = "issueTaskListOld", results = { @org.apache.struts2.convention.annotation.Result(name = "success",

	location = "issueTaskList.jsp") })
	public String listold() {
		Map filterMap = new HashMap();
		Map orderMap = new HashMap();
		filterMap.put("taskType", "0");
		filterMap.put("taskStatus", "0");
		orderMap.put("createTime", "desc");
		this.taskList = this.issueTaskDao.findByAll(filterMap, orderMap);

		for (IssueTask iss : this.taskList) {
			if ((iss.getDocType().equalsIgnoreCase("0"))
					&& (iss.getAttachFile() != null) && (!iss.getAttachFile

					().toUpperCase().endsWith("PDF"))) {
				iss.setErroattach("1");
			}
		}

		return "success";
	}

	@Action(value="viewTaskTC", results={@org.apache.struts2.convention.annotation.Result(name="success", 

location="docTaskReciverTC.jsp")})
  public String viewTC()
  {
    if (this.id != null) {
      this.taskTC = ((IssueTaskTC)this.issueTaskTCDao.get(this.id));
      if ((this.taskTC.getDocType().equalsIgnoreCase("0")) && (this.taskTC.getAttachFile() != null) && (!

this.taskTC.getAttachFile().toUpperCase().endsWith("PDF"))) {
        this.erroattachflag = "1";
      }
      this.deptList = this.dictDataDao.findByDictType("dept");
      this.workshopList = this.dictDataDao.findByDictType("workshop");
      Map filterMap = new HashMap();
      filterMap.put("taskid", this.taskTC.getId());
      Map orderMap = new HashMap();
      List<TaskDeptItemTC> taskDepts = this.taskDeptItemTCDao.findByAll(filterMap, orderMap);
      List dellist = new ArrayList();
      for (DictData data : this.deptList) {
        if (data.getScope() != null) {
          if ((this.taskTC.getDocType().equals("0")) || (this.taskTC.getDocType().equals("1"))) {
            if (data.getScope().equals("2")) {
              dellist.add(data);
              continue;
            }
          } else if ((this.taskTC.getDocType().equals("2")) && 
            (data.getScope().equals("1"))) {
            dellist.add(data);
            continue;
          }
        }

        for (TaskDeptItemTC item : taskDepts) {
          if ((item.getWorkshop() == null) && (item.getDept().getId().equals(data.getId()))) {
            data.setSent(item.getStatus());
            data.setSignTime(item.getSignTime());
          }
        }
      }
      this.deptList.removeAll(dellist);

      for(DictData dt:workshopList){
			for(TaskDeptItemTC item:taskDepts){
				if(item.getWorkshop()!=null && item.getWorkshop().getId().equals(dt.getId())){
					dt.setSent(item.getStatus());
					dt.setSignTime(item.getSignTime());
					dt.setDestroyTime(item.getDestroyTime());
				}
			}
		}	
    }

    return "success";
  }

	@Action(value="viewTask", results={@org.apache.struts2.convention.annotation.Result(name="success", 

location="docTaskReciver.jsp")})
  public String view()
  {
    if (this.id != null) {
      this.task = ((IssueTask)this.issueTaskDao.get(this.id));
      if ((this.task.getDocType().equalsIgnoreCase("0")) && (this.task.getAttachFile() != null) && (!

this.task.getAttachFile().toUpperCase().endsWith("PDF"))) {
        this.erroattachflag = "1";
      }
      this.deptList = this.dictDataDao.findByDictType("dept");
      this.workshopList = this.dictDataDao.findByDictType("workshop");
      Map filterMap = new HashMap();
      filterMap.put("taskid", this.task.getId());
      Map orderMap = new HashMap();
      List<TaskDeptItem> taskDepts = this.taskDeptItemDao.findByAll(filterMap, orderMap);
      List dellist = new ArrayList();
      for (DictData data : this.deptList) {
        if (data.getScope() != null) {
          if ((this.task.getDocType().equals("0")) || (this.task.getDocType().equals("1"))) {
            if (data.getScope().equals("2")) {
              dellist.add(data);
              continue;
            }
          } else if ((this.task.getDocType().equals("2")) && 
            (data.getScope().equals("1"))) {
            dellist.add(data);
            continue;
          }
        }

        for (TaskDeptItem item : taskDepts) {
          if ((item.getWorkshop() == null) && (item.getDept().getId().equals(data.getId()))) {
            data.setSent(item.getStatus());
            data.setSignTime(item.getSignTime());
          }
        }
      }
      this.deptList.removeAll(dellist);

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

    return "success";
  }

	@Action(value = "issueDocu", results = { @org.apache.struts2.convention.annotation.Result(name = "success",

	location = "issueTaskList.do", type = "redirect") })
	public String issue() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		User currentUser = this.userDao
				.findByLoginID(userDetails.getUsername());
		if (this.deptSelected != null) {
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
				deptItem.setTask(this.task);
				User user = this.userDao.findWriteUserByDept(dept);
				this.task = ((IssueTask) this.issueTaskDao.get(this.task
						.getId()));
				if (user != null) {
					SimpleMailMessage mailMessage = new SimpleMailMessage();
					mailMessage.setFrom(currentUser.getEmail());
					mailMessage.setTo(user.getEmail());
					String subject = "";
					if (this.task.getUpdated().equals("1"))
						subject = "文档发放通知--" + this.task.getPartid() + "已升级";
					else {
						subject = "文档发放通知--" + this.task.getPartid();
					}
					mailMessage.setSubject(subject);
					String bodyText = "编号:" + this.task.getPartid() + "版本:"
							+ this.task.getDocVersion()
							+ "的文档已发放,请到技术文档发布系统中查收";
					mailMessage.setText(bodyText);
					this.mailEngine.sendAsync(mailMessage);
				}
				this.taskDeptItemDao.saveTaskDeptItem(deptItem);
			}

			defaulIssue(this.deptSelected, this.task.getId());
		}

		this.taskDeptItemDao.updateIssueItemStatus(this.task.getId(), "2", "0");
		this.issueTaskDao.updateIssueTaskStatus(this.task.getId(), "1");
		return "success";
	}

	public void defaulIssue(List<String> deptSelected, String tskid) {
		boolean flag = false;

		if ((deptSelected != null)
				&& (!deptSelected.contains("8afcd82e3d8ff425013dc34ec368126f"))) {
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
			deptItem.setTask(this.task);
			this.taskDeptItemDao.saveTaskDeptItem(deptItem);
		}
	}

	private boolean checkalreadyin(String deptID, String depitemId) {
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

	@Action(value = "issueDocuTC", results = {
			@org.apache.struts2.convention.annotation.Result(name = "success",

			location = "issueTCResult.jsp"),
			@org.apache.struts2.convention.annotation.Result(name = "main",

			location = "issueTaskList.do", type = "redirect") })
	public String issuetc() {
		this.taskTC = ((IssueTaskTC) this.issueTaskTCDao.get(this.taskTC
				.getId()));
		IssueTask newissue = new IssueTask();
		newissue.setPartid(this.taskTC.getPartid());
		newissue.setDocType(this.taskTC.getDocType());
		newissue.setDocDate(this.taskTC.getDocDate());
		newissue.setVeroftc(this.taskTC.getVeroftc());
		newissue.setTechDocClass(this.taskTC.getTechDocClass());
		newissue.setDrawingNumb(this.taskTC.getDrawingNumb());
		newissue.setDrawingPage(this.taskTC.getDrawingPage());
		newissue.setDrawingSize(this.taskTC.getDrawingSize());
		newissue.setAssembly(this.taskTC.getAssembly());
		newissue.setAssembTitle(this.taskTC.getAssembTitle());
		newissue.setAssembOrnot(this.taskTC.getAssembOrnot());
		newissue.setClient(this.taskTC.getClient());
		newissue.setModelCode(this.taskTC.getModelCode());
		newissue.setCltPartNumb(this.taskTC.getCltPartNumb());
		newissue.setProcessIn(this.taskTC.getProcessIn());
		newissue.setViewFile(this.taskTC.getViewFile());
		newissue.setAttachFile(this.taskTC.getAttachFile());
		newissue.setTaskType("0");
		newissue.setTaskStatus("0");
		newissue.setHistory("0");
		newissue.setUpdated(this.taskTC.getStatus());
		newissue.setCreateTime(this.taskTC.getCreateTime());

		IssueTask tempissuetask = null;
		IssueTask oldDocu;
		if (this.taskTC.getStatus().equalsIgnoreCase("1")) {
			if (ifUpdate(this.taskTC)) {
				this.tcflag = "临时表内有更老的版本，请先处理老版本!";
				return "success";
			}

			if (checkIfIn(this.taskTC)) {
				HashMap filter = new HashMap();
				HashMap orderMap = new HashMap();
				filter.put("eqPartid", this.taskTC.getPreviousitem());
				filter.put("eqAssembly", this.taskTC.getPreviousassem());
				filter.put("history", "0");
				filter.put("docType", this.taskTC.getDocType());

				List taskList = this.issueTaskDao.findByAll(filter, orderMap);
				oldDocu = (IssueTask) taskList.get(0);

				int count = this.issueTaskDao.getCountBySysUID(oldDocu
						.getSysUID());
				NumberFormat integerFormat = new DecimalFormat("000");
				String ver = integerFormat.format(count + 1);
				updateoldDOC(oldDocu);
				newissue.setDocVersion(ver);

				newissue.setSysUID(oldDocu.getSysUID());
			} else {
				newissue.setDocVersion("001");

				String sysUID = uuidmaker.generateTimeBasedUUID().toString()
						.replaceAll("-", "");
				newissue.setSysUID(sysUID);

				newissue.setMissup("1");
			}
		} else {
			newissue.setDocVersion("001");

			String sysUID = uuidmaker.generateTimeBasedUUID().toString()
					.replaceAll("-", "");
			newissue.setSysUID(sysUID);
		}

		if (checkalreadyin(this.taskTC, newissue.getDocVersion())) {
			this.tcflag = "重复记录,表内原来已有该记录";
			this.issueTaskTCDao.updateTCIssueTaskStatus(this.taskTC.getId(),
					"3", "重复记录,表内原来已有该记录");
			return "success";
		}
		tempissuetask = (IssueTask) this.issueTaskDao.save(newissue);

		if (tempissuetask != null) {
			if (!distribute(tempissuetask, this.taskTC)) {
				this.tcflag = "添加成功,发布失败,请到文档管理内重新发布";
				this.issueTaskTCDao.updateTCIssueTaskStatus(
						this.taskTC.getId(), "3", "分发失败");
				return "success";
			}
		}

		if (tempissuetask != null) {
			UserDetails userDetails = (UserDetails) SecurityContextHolder
					.getContext().getAuthentication().getPrincipal();
			User currentUser = this.userDao.findByLoginID(userDetails
					.getUsername());
			if (this.deptSelected != null) {
				for (String dept : this.deptSelected) {
					DictData data = new DictData();
					data.setId(dept);
					TaskDeptItem deptItem = new TaskDeptItem();
					deptItem.setCreateTime(new Date());
					deptItem.setDept(data);
					deptItem.setStatus("0");
					deptItem.setHistory("0");
					deptItem.setTask(tempissuetask);
					User user = this.userDao.findWriteUserByDept(dept);
					tempissuetask = (IssueTask) this.issueTaskDao
							.get(tempissuetask.getId());
					if (user != null) {
						SimpleMailMessage mailMessage = new SimpleMailMessage();
						mailMessage.setFrom(currentUser.getEmail());
						mailMessage.setTo(user.getEmail());
						String subject = "";
						if (tempissuetask.getUpdated().equals("1"))
							subject = "文档发放通知--" + tempissuetask.getPartid()
									+ "已升级";
						else {
							subject = "文档发放通知--" + tempissuetask.getPartid();
						}
						mailMessage.setSubject(subject);
						String bodyText = "编号:" + tempissuetask.getPartid()
								+ "版本:" + tempissuetask.getDocVersion()
								+ "的文档已发放,请到技术文档发布系统中查收";
						mailMessage.setText(bodyText);
						this.mailEngine.sendAsync(mailMessage);
					}
					this.taskDeptItemDao.saveTaskDeptItem(deptItem);
				}

				defaulIssue(this.deptSelected, tempissuetask.getId());
			}

			this.taskDeptItemDao.updateIssueItemStatus(tempissuetask.getId(),
					"2", "0");
			this.issueTaskDao.updateIssueTaskStatus(tempissuetask.getId(), "1");
		}

		this.tcflag = "添加成功,发布成功,请到文档管理内查看发布情况";
		this.issueTaskTCDao.updateTCIssueTaskStatus(this.taskTC.getId(), "1",
				"处理成功");

		return "main";
	}

	private boolean ifUpdate(IssueTaskTC temptc) {
		boolean flag = false;
		HashMap filter = new HashMap();
		HashMap orderMap = new HashMap();
		filter.put("eqPartid", temptc.getPreviousitem());
		filter.put("eqAssembly", temptc.getPreviousassem());
		filter.put("dealstatus", "0");
		filter.put("docType", temptc.getDocType());

		List<IssueTaskTC> tasktcList = this.issueTaskTCDao.findByAll(filter,
				orderMap);

		for (IssueTaskTC iss : tasktcList) {
			if ((iss.getVeroftc() != null)
					&& (iss.getVeroftc() != "")
					&& (temptc.getVeroftc() != null)
					&&

					(temptc.getVeroftc() != "")
					&& (Integer.parseInt(iss.getVeroftc()) < Integer
							.parseInt(temptc.getVeroftc()))) {
				flag = true;
				break;
			}

		}

		return flag;
	}

	private void updateoldDOC(IssueTask oldDocu) {
		this.issueTaskDao.updateIssueHisStatus(oldDocu.getId(), "1");
		if (oldDocu.getDocType().equals("0")) {
			String pdfPath = oldDocu.getAttachFile();

			String realPath = System.getProperty("search.root") + pdfPath;
			File pdfFile = new File(realPath);
			String fileFullName = pdfFile.getName();
			String fileName = fileFullName.substring(0,
					fileFullName.lastIndexOf("."));
			String securityFileName = pdfFile.getParent() + File.separator
					+ fileName + "-sct.pdf";
			String waterMarkFileName = pdfFile.getParent() + File.separator
					+ fileName + "-hst.pdf";
			File waterMarkFile = new File(waterMarkFileName);
			FileOutputStream os = null;
			try {
				os = new FileOutputStream(waterMarkFile);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			try {
				PdfReader reader = new PdfReader(securityFileName, null);
				PdfStamper writer = new PdfStamper(reader, os);

				Image image = Image.getInstance(System
						.getProperty("search.root")
						+ "/img"
						+ "/mark_history.png");

				int pageSize = reader.getNumberOfPages();
				BaseFont base = null;

				base = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",
						false);
				for (int i = 1; i <= pageSize; i++) {
					Rectangle rectangle = reader.getPageSizeWithRotation(i);
					float x = rectangle.getWidth() / 2.0F;
					float y = rectangle.getHeight() / 2.0F;
					int fontSize = (int) (x * 0.1D);

					PdfContentByte content = writer.getOverContent(i);
					content.saveState();

					PdfGState gs = new PdfGState();
					gs.setFillOpacity(0.1F);
					content.setGState(gs);

					content.beginText();
					content.setColorFill(BaseColor.RED);
					content.setFontAndSize(base, fontSize);
					content.showTextAligned(1, "历史版本", x, y - (fontSize + 20)
							* 2, 35.0F);
					content.endText();

					content.restoreState();
				}

				reader.close();
				writer.close();
				try {
					Process process = Runtime
							.getRuntime()
							.exec("C:\\SWFTools\\pdf2swf.exe "
									+ waterMarkFileName
									+ " "
									+ pdfFile.getParent()
									+ File.separator
									+ fileName
									+ "-hst.swf  -f -T 9 -t -s bitmap -s languagedir=C:\\xpdf\\xpdf-chinese-simplified");
				} catch (Exception e) {
					Process process;
					e.printStackTrace();
					System.out.println(e);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (DocumentException e) {
				e.printStackTrace();
			}
			this.issueTaskDao.updateIssueTaskViewFile(oldDocu.getId(),
					pdfPath.substring(0, pdfPath.lastIndexOf("/")) + "/"
							+ fileName + "-hst.swf");
		}
	}

	private boolean checkIfIn(IssueTaskTC dealtc) {
		boolean flag = false;
		if (dealtc.getStatus().equalsIgnoreCase("1")) {
			HashMap filter = new HashMap();
			HashMap orderMap = new HashMap();
			filter.put("eqPartid", dealtc.getPreviousitem());
			filter.put("eqAssembly", dealtc.getPreviousassem());
			filter.put("history", "0");
			filter.put("docType", dealtc.getDocType());

			List taskList = this.issueTaskDao.findByAll(filter, orderMap);
			if (taskList.size() > 0)
				flag = true;
		} else {
			flag = true;
		}
		return flag;
	}

	private boolean distribute(IssueTask res, IssueTaskTC dealtc) {
		boolean flag = false;

		Map filterMap = new HashMap();
		filterMap.put("taskid", this.taskTC.getId());
		Map orderMap = new HashMap();
		List<TaskDeptItemTC> taskDepts = this.taskDeptItemTCDao.findByAll(
				filterMap, orderMap);

		for (TaskDeptItemTC item : taskDepts) {
			TaskDeptItem deptItem = new TaskDeptItem();
			deptItem.setCreateTime(new Date());
			deptItem.setDept(item.getDept());
			deptItem.setStatus("2");
			deptItem.setHistory("0");
			deptItem.setTask(res);
			this.taskDeptItemDao.saveTaskDeptItem(deptItem);
		}

		flag = true;

		return flag;
	}

	private boolean checkalreadyin(IssueTaskTC dealtc, String docversion) {
		boolean flag = false;
		HashMap filter = new HashMap();
		HashMap orderMap = new HashMap();
		filter.put("eqPartid", dealtc.getPartid());
		filter.put("eqAssembly", dealtc.getAssembly());
		filter.put("docVersion", docversion);
		filter.put("veroftc", dealtc.getVeroftc());

		List taskList = this.issueTaskDao.findByAll(filter, orderMap);
		if (taskList.size() > 0) {
			flag = true;
		}

		return flag;
	}

	@Action(value = "undoIssue", results = { @org.apache.struts2.convention.annotation.Result(name = "success",

	location = "viewTask.do?id=${id}", type = "redirect") })
	public String undoIssue() {
		this.taskDeptItemDao.removeItemByTaskAndDept(this.task.getId(),
				this.deptId);
		this.id = this.task.getId();
		return "success";
	}

	@Action(value = "undoIssueTC", results = { @org.apache.struts2.convention.annotation.Result(name = "success", location = "viewTaskTC.do?id=${id}", type = "redirect") })
	public String undoIssueTC() {
		this.taskDeptItemTCDao.removeItemByTaskAndDept(this.taskTC.getId(),
				this.deptId);
		this.id = this.taskTC.getId();
		return "success";
	}

	@Action(value = "delTcTask", results = { @org.apache.struts2.convention.annotation.Result(name = "success",location = "issueTaskList.do", type = "redirect") })
	public String delTcTask() {
		IssueTask del_tesk = (IssueTask) this.issueTaskDao.get(this.id);
		HashMap filter = new HashMap();
		HashMap orderMap = new HashMap();

		filter.put("sysUID", del_tesk.getSysUID());

		filter.put("history", "1");

		int histdocversion = Integer.valueOf(del_tesk.getDocVersion())
				.intValue();
		NumberFormat integerFormat = new DecimalFormat("000");
		if (histdocversion > 1) {
			String ver = integerFormat.format(histdocversion - 1);
			filter.put("docVersion", ver);
		}
		filter.put("docType", del_tesk.getDocType());

		List taskList = this.issueTaskDao.findByAll(filter, orderMap);
		if (taskList.size() > 0) {
			IssueTask oldissue = (IssueTask) taskList.get(0);
			String viewfile = "";
			if (oldissue.getViewFile() != null) {
				viewfile = oldissue.getViewFile().replaceAll("-hst", "");
			}
			this.issueTaskDao.updateItemByTCTask(oldissue.getId(), viewfile);
		}

		this.taskDeptItemDao.removeItemByTask(this.id);
		this.issueTaskDao.remove(this.id);
		return "success";
	}

	@Action(value = "delTcTaskNew", results = { @org.apache.struts2.convention.annotation.Result(name = "success",

	location = "issueTaskList.do", type = "redirect") })
	public String delTcTaskNew() {
		this.taskDeptItemTCDao.removeItemByTask(this.id);
		this.issueTaskTCDao.remove(this.id);
		return "success";
	}

	@Action(value = "delTask", results = { @org.apache.struts2.convention.annotation.Result(name = "success",

	location = "listManaDocu.do", type = "redirect") })
	public String delTask() {
		this.issueTaskDao.remove(this.id);
		return "success";
	}

	public List<IssueTask> getTaskList() {
		return this.taskList;
	}

	public void setTaskList(List<IssueTask> taskList) {
		this.taskList = taskList;
	}

	public IssueTaskDaoHibernate getIssueTaskDao() {
		return this.issueTaskDao;
	}

	public void setIssueTaskDao(IssueTaskDaoHibernate issueTaskDao) {
		this.issueTaskDao = issueTaskDao;
	}

	public IssueTask getTask() {
		return this.task;
	}

	public void setTask(IssueTask task) {
		this.task = task;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public DictDataDaoHibernate getDictDataDao() {
		return this.dictDataDao;
	}

	public void setDictDataDao(DictDataDaoHibernate dictDataDao) {
		this.dictDataDao = dictDataDao;
	}

	public List<String> getDeptSelected() {
		return this.deptSelected;
	}

	public void setDeptSelected(List<String> deptSelected) {
		this.deptSelected = deptSelected;
	}

	public TaskDeptItemDaoHibernate getTaskDeptItemDao() {
		return this.taskDeptItemDao;
	}

	public void setTaskDeptItemDao(TaskDeptItemDaoHibernate taskDeptItemDao) {
		this.taskDeptItemDao = taskDeptItemDao;
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

	public List<DictData> getDeptList() {
		return this.deptList;
	}

	public void setDeptList(List<DictData> deptList) {
		this.deptList = deptList;
	}

	public String getDeptId() {
		return this.deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public List<DictData> getWorkshopList() {
		return this.workshopList;
	}

	public void setWorkshopList(List<DictData> workshopList) {
		this.workshopList = workshopList;
	}
}