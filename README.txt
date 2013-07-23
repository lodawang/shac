#################
	README
#################

************
	环境
************

VPN
=========
用户名 ：yaoyujun 
密码 ： y12345 
IP ：61.152.126.62

服务器
=========
[测试机] 10.124.88.70 dbadmin Dps12345
[WEB]    10.124.88.174  shac\admin-dps  Dps12345
[DB]     10.124.88.173 

ORACLE
=========
oracle SID:orcl  用户:sys,system,sysman,shac/shacdoc9

TOMCAT
=========
tomcat shac/saicshac

FTP
=========
ftp admin shacdpftp
ftp york shacdp9
ftp tcuser shac010tc

************
SWFTOOLS
************

pdf2swf.exe  A0+4.pdf   A0-4.swf -f -T 9 -t -s poly2bitmap


**************
与TC接口相关
**************

为tc创建的oracle用户
====================
GRANT CONNECT TO TCSHAC;
ALTER USER TCSHAC DEFAULT ROLE NONE;
grant create session to tcshac;
grant select,insert,update,delete on shac.sc_tc_issuetask to tcshac;


========
20120518
========
 * 工艺文档的编号输入 倒数第2,3位分别对应工艺方式 和 工艺文档分类
 * 新增技术协议文档类型
 * 5月1号之后录入的文件，须批量生成生产状态水印
 * 查询时不区分大小写，通配
 