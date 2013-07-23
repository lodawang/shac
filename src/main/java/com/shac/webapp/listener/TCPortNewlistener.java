package com.shac.webapp.listener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.TimerTask;

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
import com.shac.dao.hibernate.DictDataDaoHibernate;
import com.shac.dao.hibernate.IssueTaskDaoHibernate;
import com.shac.dao.hibernate.IssueTaskTCDaoHibernate;
import com.shac.dao.hibernate.TCHeartDaoHibernate;
import com.shac.dao.hibernate.TCIssueTaskDaoHibernate;
import com.shac.dao.hibernate.TaskDeptItemDaoHibernate;
import com.shac.dao.hibernate.TaskDeptItemTCDaoHibernate;
import com.shac.model.DictData;
import com.shac.model.IssueTask;
import com.shac.model.IssueTaskTC;
import com.shac.model.TCIssueTask;
import com.shac.model.TaskDeptItem;
import com.shac.model.TaskDeptItemTC;
import com.shac.util.Constants;

public class TCPortNewlistener extends TimerTask{
	
	// 判断是否已经在启动，其实不用，spring好像会自己判断是否运行
		private static boolean isRunning = false;
		
		private TCIssueTaskDaoHibernate tcIssueTaskDao;
		
		private TCHeartDaoHibernate tcHeartDao;
		
		private IssueTaskTCDaoHibernate issueTaskTCDao;	
		
		private DictDataDaoHibernate dictDataDao;
		
		private TaskDeptItemTCDaoHibernate taskDeptItemTCDao;

		public TaskDeptItemTCDaoHibernate getTaskDeptItemTCDao() {
			return taskDeptItemTCDao;
		}

		public void setTaskDeptItemTCDao(TaskDeptItemTCDaoHibernate taskDeptItemTCDao) {
			this.taskDeptItemTCDao = taskDeptItemTCDao;
		}

		public DictDataDaoHibernate getDictDataDao() {
			return dictDataDao;
		}

		public void setDictDataDao(DictDataDaoHibernate dictDataDao) {
			this.dictDataDao = dictDataDao;
		}

		public IssueTaskTCDaoHibernate getIssueTaskTCDao() {
			return issueTaskTCDao;
		}

		public void setIssueTaskTCDao(IssueTaskTCDaoHibernate issueTaskTCDao) {
			this.issueTaskTCDao = issueTaskTCDao;
		}

		public TCHeartDaoHibernate getTcHeartDao() {
			return tcHeartDao;
		}

		public void setTcHeartDao(TCHeartDaoHibernate tcHeartDao) {
			this.tcHeartDao = tcHeartDao;
		}
	
		public TCIssueTaskDaoHibernate getTcIssueTaskDao() {
			return tcIssueTaskDao;
		}

		public void setTcIssueTaskDao(TCIssueTaskDaoHibernate tcIssueTaskDao) {
			this.tcIssueTaskDao = tcIssueTaskDao;
		}

		@Override
		public void run() {
			if (!isRunning) {
				isRunning = true;
				System.out.println("SpringTCPort 定时启动");
				try {
					dealTC();				
					tcHeart();
				}catch (Exception e) {
					System.out.println("SpringTCPort 有错误!");
					e.printStackTrace();
				}
				System.out.println("SpringTCPort 定时结束");
				isRunning = false;
			}
		}
		
		
		private void dealTC() {
			// 扫描表SC_TC_ISSUETASK，条件（TASKSTATUS='0'）
			HashMap filter = new HashMap();
			filter.put("taskStatus", "0");
			List<TCIssueTask> tc = tcIssueTaskDao.findByAll(filter, null);
			for (TCIssueTask dealtc : tc) {
				try {
					boolean result = false;
					// 把数据搬到临时表			
					result = copyto(dealtc);					
					// 成功运行
					if (result) {
						tcIssueTaskDao.updateTCIssueTaskStatus(dealtc.getId(), "1",
								"");
					}
				} catch (Exception e) {
					tcIssueTaskDao.updateTCIssueTaskStatus(dealtc.getId(), "0",
							e.getMessage());
				}
			      
			}
		}
		
		/**
		 * 把tc的记录搬到正式表中
		 * 
		 * @param dealtc
		 */
		private boolean copyto(TCIssueTask dealtc) {
			boolean res_flag = false;
			IssueTaskTC newissue = new IssueTaskTC();
			newissue.setPartid(dealtc.getPartid());
			newissue.setDocType(dealtc.getDoctype());
			newissue.setDocDate(dealtc.getDocDate());
			newissue.setVeroftc(dealtc.getVeroftc());
			newissue.setTechDocClass(dealtc.getTechdocclass());
			newissue.setDrawingNumb(dealtc.getDrawingNumb());
			newissue.setDrawingPage(dealtc.getDrawingPage());
			newissue.setDrawingSize(dealtc.getDrawingSize());
			newissue.setAssembly(dealtc.getAssembly());
			newissue.setAssembTitle(dealtc.getAssembTitle());
			boolean tmp_AssembOrnot = false;
			if(dealtc.getAssembOrnot()!=null&&dealtc.getAssembOrnot().equalsIgnoreCase("Y")){
				tmp_AssembOrnot = true;
			}
			newissue.setAssembOrnot(tmp_AssembOrnot);
			newissue.setClient(dealtc.getClient());
			newissue.setModelCode(dealtc.getModelCode().replaceAll("EPSILON-Ⅱ", "EPSILON-II"));		
			newissue.setCltPartNumb(dealtc.getCltPartNumb());
			newissue.setProcessIn(dealtc.getProcessIn());
			
			//显示文件先处理掉
			if (dealtc.getAttachFile() != null && dealtc.getAttachFile() != "") {
				String localpath = System.getProperty("search.root") + "\\"
						+ dealtc.getAttachFile();
				File tcfile = new File(localpath);
				if (!tcfile.exists()) {
					tcIssueTaskDao.updateTCIssueTaskStatus(dealtc.getId(), "0",
							"文件不存在");
					return res_flag;
				}
				
				String Filename = tcfile.getName();
				// 转换pdftoswf,如果是图纸
				if (dealtc.getDoctype().equalsIgnoreCase("0")) {
					// 是否是pdf文件
					if (Filename.toUpperCase().endsWith("PDF")) {
						boolean res_pdfswf = pdftoswf(localpath, dealtc);
						if (res_pdfswf != true) {
							tcIssueTaskDao.updateTCIssueTaskStatus(dealtc.getId(),
									"0", "转换水印出错");
							return res_flag;
						}
						newissue.setViewFile(dealtc.getAttachFile()
								.replaceAll("pdf", "swf").replaceAll("PDF", "swf"));
					}
				}
				
				newissue.setAttachFile(dealtc.getAttachFile());
			}
			//运用原来的status字段来保留新增还是更新
			newissue.setStatus(dealtc.getUpdatestatus());
			if(dealtc.getUpdatestatus().equalsIgnoreCase("1")){
				newissue.setPreviousitem(dealtc.getPreviousitem());
				newissue.setPreviousassem(dealtc.getPreviousassem());
			}
			newissue.setTaskType("0");
			newissue.setTaskStatus("0");
			newissue.setHistory("0");
			newissue.setUpdated("0");
			Date date = new Date();
			newissue.setCreateTime(date);
			//未处理
			newissue.setDealstatus("0");
			IssueTaskTC tempissuetask = null;
			// 检查原来的表中是否已经有记录
			if (checkalreadyin(dealtc, newissue.getDocVersion())) {
				tcIssueTaskDao.updateTCIssueTaskStatus(dealtc.getId(), "0",
						"重复记录,表内原来已有该记录");
				return res_flag;
			} else {
				tempissuetask = issueTaskTCDao.save(newissue);
			}
			
			if (tempissuetask != null) {
				// 分发
				if (distribute(tempissuetask, dealtc)) {
					res_flag = true;
				} else {
					tcIssueTaskDao.updateTCIssueTaskStatus(dealtc.getId(), "0",
							"分发失败");
					return res_flag;
				}
			}
			return res_flag;
		}
		
		/**
		 * pdf转换swf
		 * 
		 * @return
		 */
		private boolean pdftoswf(String filepath, TCIssueTask dealtc) {
			boolean flag = false;

			System.out.println("加水印并转换");
			String pdfPath = filepath;
			File pdfFile = new File(pdfPath);
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

				PdfReader reader = new PdfReader(pdfPath, null);
				PdfStamper writer = new PdfStamper(reader, os);
				// Image image = Image.getInstance(ServletActionContext
				// .getServletContext().getRealPath("/img")
				// + "/mark_security.png");
				Image image = Image.getInstance(System.getProperty("search.root")
						+ "/img" + "/mark_security.png");

				int pageSize = reader.getNumberOfPages();
				BaseFont base = null;
				// base = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI,
				// BaseFont.NOT_EMBEDDED);
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
					content.showTextAligned(Element.ALIGN_CENTER, "机密文件", x, y, 35);// 35度倾斜
					content.endText();
					// 正式生产，试生产
					content.beginText();
					content.setColorFill(BaseColor.RED);
					content.setFontAndSize(base, fontSize);
					content.showTextAligned(Element.ALIGN_CENTER,
							dealtc.getProcessIn(), x, y - fontSize - 20, 35);// 35度倾斜
					content.endText();

					content.restoreState();
					// 文字水印-end
				}
				reader.close();
				writer.close();

				try {
					// -s poly2bitmap 加了文字水印的pdf转换成swf后打印出来的水印文字不符合要求（颜色较深），改成-s
					// bitmap后解决
					Process process = Runtime
							.getRuntime()
							.exec("C:\\SWFTools\\pdf2swf.exe "
									+ waterMarkFileName
									+ " "
									+ pdfFile.getParent()
									+ File.separator
									+ fileName
									+ ".swf  -f -T 9 -t -s bitmap -s zoom=100 -s languagedir=C:\\xpdf\\xpdf-chinese-simplified");
					flag = true;
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(e);
				}

			} catch (IOException e) {
				e.printStackTrace();
			} catch (DocumentException e) {
				e.printStackTrace();
			}

			return flag;
		}
		
		
		/**
		 * 数组元素去重复
		 * @param a
		 * @return
		 */
		public  String[] array_unique(String[] a) {  
			   // array_unique  
			    List<String> list = new LinkedList<String>();  
			    for(int i = 0; i < a.length; i++) {  
			        if(!list.contains(a[i])) {  
			            list.add(a[i]);  
			        }  
			    }  
			    return (String[])list.toArray(new String[list.size()]);  
		}  
		
		/**
		 * 分发部门
		 * 
		 * @param res
		 * @return
		 */
		private boolean distribute(IssueTaskTC res, TCIssueTask dealtc) {
			// TODO Auto-generated method stub
			boolean flag = false;
	        //技术文档分发默认的部门
			String defaultissue = "";
			if(dealtc.getDoctype().equalsIgnoreCase("1")){
				String techclass = dealtc.getTechdocclass();
				if(techclass!=null&&techclass.length()>0){
					HashMap filter = new HashMap();
					filter.put("dictType", Constants.DICTTYPE_TECHDOCCLASS);
					filter.put("dictValue", techclass);
					List<DictData> temp = dictDataDao.findByAll(filter, null);
					if (temp.size() > 0) {
						if(temp.get(0).getIssue()!=null){
							defaultissue = temp.get(0).getIssue();
						}		
					} 
				}
			}
			
			if ((dealtc.getIssueto() != null && dealtc.getIssueto().length() > 0)||dealtc.getDoctype().equalsIgnoreCase("1")) {
				String finalissue = "";
				if(dealtc.getIssueto() == null){
					finalissue = defaultissue;
				}else{
					finalissue = defaultissue + dealtc.getIssueto();
				}
				String[] depselect = array_unique(finalissue.split(","));
				for (int i = 0; i < depselect.length; i++) {
					String dept = depselect[i];
					if(dept!=null){
						// 这里去查找中文在数据字典的id
						HashMap filter = new HashMap();
						filter.put("dictType", Constants.DICTTYPE_REGION);
						filter.put("dictValue", dept);
						List<DictData> temp = dictDataDao.findByAll(filter, null);
						DictData data = null;
						if (temp.size() > 0) {
							data = temp.get(0);
						} else {
							// 如果字典表中不包含该单位
							data = new DictData();
							data.setDictType(Constants.DICTTYPE_REGION);
							data.setDictValue(dept);
							data.setStatus(Constants.TRUE_STRING);
							data = dictDataDao.save(data);
						}
						TaskDeptItemTC deptItem = new TaskDeptItemTC();
						deptItem.setCreateTime(new Date());
						deptItem.setDept(data);
						deptItem.setStatus("2");//仅作标记 0:已发放未签收 1:已签收
						deptItem.setHistory("0");
						deptItem.setTask(res);
						taskDeptItemTCDao.saveTaskDeptItemTC(deptItem);
					}
				}
				//issueTaskDao.updateIssueTaskStatus(res.getId(), "1");
				flag = true;
			}else{
				flag = true;
			}
			return flag;
		}
		
		/**
		 * 是否已存在表内
		 * 
		 * @param dealtc
		 * @return
		 */
		private boolean checkalreadyin(TCIssueTask dealtc, String docversion) {
			// TODO Auto-generated method stub
			boolean flag = false;
			HashMap filter = new HashMap();
			HashMap orderMap = new HashMap();
			filter.put("eqPartid", dealtc.getPartid());
			filter.put("eqAssembly", dealtc.getAssembly());			
			filter.put("veroftc", dealtc.getVeroftc());
			List taskList;
			taskList = issueTaskTCDao.findByAll(filter, orderMap);
			if (taskList.size() > 0) {
				flag = true;
			}

			return flag;
		}
		
		
		
		
		/**
		 * 心跳记录
		 * 
		 */
		private void tcHeart() {
			// TODO Auto-generated method stub
			tcHeartDao.updateTCHeart();
		}

}
