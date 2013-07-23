package com.shac.util;

public final class Constants {
	 
	 //文档类型-图纸
	 public static final String DOC_DOCTYPE_DRAW = "0";
	 //文档类型-技术文件
	 public static final String DOC_DOCTYPE_TECH = "1";
	 //文档类型-工艺文档
	 public static final String DOC_DOCTYPE_PROC = "2";
	//文档类型-导入的工艺文档
     public static final String BATCH_DOC_DOCTYPE_PROC = "3";
	
	 //图档上传路径
	 public static final String  DOCU_UPLOAD_PATH = "outnewsFile";
	 
	 //字典类型之厂部
	 public static final String DICTTYPE_REGION = "dept";
	 //图幅
	 public static final String DICTTYPE_DRAWINGSIZE = "drawsize";
	 //技术文件类型
	 public static final String DICTTYPE_TECHDOCCLASS = "techdoc";
	 //客户
	 public static final String DICTTYPE_CLIENT = "client";
	 //车型
	 public static final String DICTTYPE_MODEL = "model";
	 //工艺文档分类
	 public static final String DICTTYPE_PROCDOCCLASS = "procdoc";
	 //工艺方式
	 public static final String DICTTYPE_PROCMODE = "procmode";
	 //车间科室
	 public static final String DICTTYPE_WORKSHOP = "workshop";
	 
	 //启用|是|open
	 public static final String TRUE_STRING = "1";
	 //禁用|否|close
	 public static final String FALSE_STRING = "0";
	 
	 
	 //ISSUETASK 状态
	 public static final String ISSUE_TASKTYPE_TC = "0";
	 public static final String ISSUE_TASKTYPE_MANU = "1";
	 public static final String ISSUE_TASK_WAIT = "0";
	 public static final String ISSUE_TASK_RELEASE = "1";
	 
	 
	 
	 //发送任务标记-草稿
	 public static final String TASK_DRAFT = "0";
	 //发送任务标记-进入队列等待发送
	 public static final String TASK_WAIT = "2";
	 //发送任务标记-已发送
	 public static final String TASK_SUCCESS = "1";
	 
	 
	 
	 //HttpSession 变量之用户区域
	 public static final String SESSION_USER_REGION = "SESSION_USER_REGION";
	 //HttpSession 变量之用户区域
	 public static final String SESSION_USER_ID = "SESSION_USER_ID";
	 //HttpSession 自定义菜单
	 public static final String SESSION_USER_MENU = "SESSION_USER_MENU";
	 
	 
	 //角色名—超管
	 public static final String ROLE_ADMIN = "ROLE_ADMIN";
	 //角色名-授权可登录用户
	 public static final String ROLE_USER = "ROLE_USER";
	 //角色名—接收用户
	 public static final String ROLE_RECV = "ROLE_RECV";
	 //角色名—接收用户--厂部管理员（可执行接受、打印操作）
	 public static final String ROLE_RECVADMIN = "ROLE_RECVADMIN";
	 //角色名-接收用户--车间管理员（可签收车间收到的文件）
	 public static final String ROLE_RECVADMIN_WORKSHOP = "ROLE_RECVADMIN_WS";
	 
	 
	 
	 //文档发放操作员的登录ID
	 public static final String USER_RELEASEOP_LOGINID = "super";
	 
	 //导入文件类型
	 public static final String DOC_STATUS = "D";
	 
	 public static final boolean SEND_RECEIVE_FEEDBACK = false;
     
	 //中心部门
	 public static final String [] DEFAULT_DEPIDS = {"5","4","3","15","8afcd82e36e9803c0136ec35ed0b0003","8afcd8c632e94c6d0132faf249ac00e0","8afcd82e36e9803c0136ec3714060008","8afcd82e36e9803c0136ec36be260006"};
	 
}
