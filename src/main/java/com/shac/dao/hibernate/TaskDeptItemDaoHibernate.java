package com.shac.dao.hibernate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.shac.model.DictData;
import com.shac.model.IssueTask;
import com.shac.model.TaskDeptItem;
import com.shac.util.Constants;

@Repository("taskDeptItemDao")
public class TaskDeptItemDaoHibernate extends GenericDaoHibernate<TaskDeptItem, String>{

	public TaskDeptItemDaoHibernate() {
		super(TaskDeptItem.class);
	}

	@Override
	public DetachedCriteria getCriteria(Map filterMap) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(TaskDeptItem.class);
		detachedCriteria.createAlias("task", "task");
		if(isNotEmpty(filterMap.get("task.processIn"))){
			detachedCriteria.add(Restrictions.eq("task.processIn",(String)filterMap.get("task.processIn")));
		}
		if(isNotEmpty(filterMap.get("dept"))){
			DictData dept = new DictData();
			dept.setId((String)filterMap.get("dept"));
			detachedCriteria.add(Restrictions.eq("dept",dept));
		}
		if(isNotEmpty(filterMap.get("taskid"))){
			IssueTask task = new IssueTask();
			task.setId((String)filterMap.get("taskid"));
			detachedCriteria.add(Restrictions.eq("task",task));
		}
		if(isNotEmpty(filterMap.get("status"))){
			detachedCriteria.add(Restrictions.eq("status",(String)filterMap.get("status")));
		}
		if(isNotEmpty(filterMap.get("history"))){
			detachedCriteria.add(Restrictions.eq("history",(String)filterMap.get("history")));
		}
		
		
		if(isNotEmpty(filterMap.get("docType"))){
			detachedCriteria.add(Restrictions.eq("task.docType", (String)filterMap.get("docType")));
		}
		if(isNotEmpty(filterMap.get("client"))){
			detachedCriteria.add(Restrictions.eq("task.client", (String)filterMap.get("client")));
		}
		if(isNotEmpty(filterMap.get("modelCode"))){
			detachedCriteria.add(Restrictions.eq("task.modelCode", (String)filterMap.get("modelCode")));
		}
		if(isNotEmpty(filterMap.get("cltPartNumb"))){
			//detachedCriteria.add(Restrictions.eq("task.cltPartNumb", (String)filterMap.get("cltPartNumb")));
			detachedCriteria.add(Restrictions.ilike("task.cltPartNumb", (String)filterMap.get("cltPartNumb"),MatchMode.ANYWHERE));
		}
		if(isNotEmpty(filterMap.get("processIn"))){
			detachedCriteria.add(Restrictions.eq("task.processIn", (String)filterMap.get("processIn")));
		}
		if(isNotEmpty(filterMap.get("techDocClass"))){
			detachedCriteria.add(Restrictions.eq("task.techDocClass", (String)filterMap.get("techDocClass")));
		}
		if(isNotEmpty(filterMap.get("procDocClass"))){
			detachedCriteria.add(Restrictions.eq("task.procDocClass", (String)filterMap.get("procDocClass")));
		}
		if(isNotEmpty(filterMap.get("procMode"))){
			detachedCriteria.add(Restrictions.eq("task.procMode", (String)filterMap.get("procMode")));
		}
		if(isNotEmpty(filterMap.get("drawingNumb"))){
			//detachedCriteria.add(Restrictions.eq("task.drawingNumb", (String)filterMap.get("drawingNumb")));
			detachedCriteria.add(Restrictions.ilike("task.drawingNumb", (String)filterMap.get("drawingNumb"),MatchMode.ANYWHERE));
		}
		if(isNotEmpty(filterMap.get("docVersion"))){
			detachedCriteria.add(Restrictions.eq("task.docVersion", (String)filterMap.get("docVersion")));
		}
		if(isNotEmpty(filterMap.get("drawingSize"))){
			detachedCriteria.add(Restrictions.eq("task.drawingSize", (String)filterMap.get("drawingSize")));
		}
		if(isNotEmpty(filterMap.get("partid"))){
			detachedCriteria.add(Restrictions.ilike("task.partid", (String)filterMap.get("partid"),MatchMode.ANYWHERE));
		}
		if(isNotEmpty(filterMap.get("assembly"))){
			//detachedCriteria.add(Restrictions.eq("task.assembly", (String)filterMap.get("assembly")));
			detachedCriteria.add(Restrictions.ilike("task.assembly", (String)filterMap.get("assembly"),MatchMode.ANYWHERE));
		}
		if(isNotEmpty(filterMap.get("assembOrnot"))){
			detachedCriteria.add(Restrictions.eq("task.assembOrnot", new Boolean((String)filterMap.get("assembOrnot"))));
		}
		if(isNotEmpty(filterMap.get("veroftc"))){
			detachedCriteria.add(Restrictions.eq("task.veroftc", (String)filterMap.get("veroftc")));
		}
		if(isNotEmpty(filterMap.get("assembTitle"))){
			//detachedCriteria.add(Restrictions.like("task.assembTitle", (String)filterMap.get("assembTitle"),MatchMode.ANYWHERE));
			detachedCriteria.add(Restrictions.ilike("task.assembTitle", (String)filterMap.get("assembTitle"),MatchMode.ANYWHERE));
		}
		
		if(isNotEmpty(filterMap.get("role"))){
			String role = (String)filterMap.get("role");
			if(role.equals(Constants.ROLE_RECVADMIN)){//厂部管理员,可看所有workshop为空的记录
				detachedCriteria.add(Restrictions.isNull("workshop"));
			}else if(role.equals(Constants.ROLE_RECV)){//厂部普通角色可看issueType为空 或者workshop 为自己车间的记录
				detachedCriteria.add(Restrictions.isNull("issueType"));
				DictData workshop = (DictData)filterMap.get("workshop");
				detachedCriteria.add(Restrictions.or(Restrictions.isNull("workshop"), Restrictions.eq("workshop", workshop)));
			}
		}
		
		if(isNotEmpty(filterMap.get("item_workshop"))){
			DictData workshop = (DictData)filterMap.get("item_workshop");
			detachedCriteria.add(Restrictions.eq("workshop",workshop));
		}
		
		//二级车间为空
		if(isNotEmpty(filterMap.get("nullitem_workshop"))){			
			detachedCriteria.add(Restrictions.isNull("workshop"));
		}

		//按发布时间查询
		if(isNotEmpty(filterMap.get("begindate"))&&isNotEmpty(filterMap.get("enddate"))){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date s_date = null;
			Date e_date = null;
			try {
				s_date = (Date)sdf.parse((String)filterMap.get("begindate"));
				e_date = (Date)sdf.parse((String)filterMap.get("enddate")); 
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
			detachedCriteria.add(Restrictions.ge("createTime",s_date));
			detachedCriteria.add(Restrictions.le("createTime",e_date));
		}
		
		if(isNotEmpty(filterMap.get("oldimport"))){
			detachedCriteria.add(Restrictions.eq("task.beta",(String)filterMap.get("oldimport")));
		}
		
		return detachedCriteria;
	}
	
	
	public void saveTaskDeptItem(TaskDeptItem item){
		getHibernateTemplate().save(item);
	}
	
	public void updateTaskDeptItem(TaskDeptItem item){
		getHibernateTemplate().update(item);
	}
	
	/**
	 * 更新接收状态
	 * @param taskid
	 * @param status
	 */
	@Deprecated
	public void updateStatus(String itemid,String status){
		Session session = getSession();
		String hql = "update TaskDeptItem set status=? where id = ?";
		Query query = session.createQuery(hql);
		query.setString(0, status);
		query.setString(1, itemid);
		query.executeUpdate();
		releaseSession(session);
	}
	
	/**
	 * 更新发放类型
	 * @param taskid
	 * @param status
	 */
	public void updateIssueType(String itemid,String type){
		Session session = getSession();
		String hql = "update TaskDeptItem set issueType=? where id = ?";
		Query query = session.createQuery(hql);
		query.setString(0, type);
		query.setString(1, itemid);
		query.executeUpdate();
		releaseSession(session);
	}
	
	/**
	 * 更新历史状态
	 * @param taskid
	 * @param status
	 */
	@Deprecated
	public void updateHistory(IssueTask task,String deptid){
		String hql = "select item from TaskDeptItem item where item.task.partid=? and item.task.assembly=? and item.dept.id =?";
		List<TaskDeptItem> list;
		if(task.getAssembly()==null || task.getAssembly().trim().equals("")){
			hql = "select item from TaskDeptItem item where item.task.partid=? and (item.task.assembly is null or item.task.assembly='') and item.dept.id =?";
			list  = getHibernateTemplate().find(hql, new Object[]{task.getPartid(),deptid});
		}else{
			list  = getHibernateTemplate().find(hql, new Object[]{task.getPartid(),task.getAssembly(),deptid});
		}
		Session session = getSession();
		for(TaskDeptItem item:list){
			String updHql = "update TaskDeptItem set history='1',status='1' where id = ?";
			Query query = session.createQuery(updHql);
			query.setString(0, item.getId());
			query.executeUpdate();
		}
		
		hql = "select max(item.task.docVersion) from TaskDeptItem item where item.task.partid=? and item.task.assembly=? and item.dept.id =?";
		List<String> mxList;
		if(task.getAssembly()==null || task.getAssembly().trim().equals("")){
			hql = "select max(item.task.docVersion) from TaskDeptItem item where item.task.partid=? and (item.task.assembly is null or item.task.assembly='') and item.dept.id =?";
			mxList = getHibernateTemplate().find(hql, new Object[]{task.getPartid(),deptid});
		}else{
			mxList = getHibernateTemplate().find(hql, new Object[]{task.getPartid(),task.getAssembly(),deptid});
		}
		String mxVerStr = mxList.get(0);
		System.out.println(mxVerStr);
		
		hql = "select task.id from IssueTask task where task.partid=? and task.assembly=? and task.docVersion=?";
		List<String> mxIdList;
		if(task.getAssembly()==null || task.getAssembly().trim().equals("")){
			hql = "select task.id from IssueTask task where task.partid=? and (task.assembly is null or task.assembly='') and task.docVersion=?";
			mxIdList = getHibernateTemplate().find(hql, new Object[]{task.getPartid(),mxVerStr});
		}else{
			mxIdList = getHibernateTemplate().find(hql, new Object[]{task.getPartid(),task.getAssembly(),mxVerStr});
		}
		String mxId = mxIdList.get(0);
		System.out.println(mxId);
		
		String updMxSql = "update TaskDeptItem set history='0',status='1' where task.id=? and dept.id=?";
		Query updMxQuery = session.createQuery(updMxSql);
		updMxQuery.setString(0, mxId);
		updMxQuery.setString(1, deptid);
		updMxQuery.executeUpdate();
		releaseSession(session);
	}
	
	/**
	 * 更新历史状态
	 * @param taskid
	 * @param status
	 */
	public void updateHistoryBySysUID(IssueTask task,String deptid,String role,String workshopId){
		String hql = "select item from TaskDeptItem item where item.task.sysUID=? and item.dept.id =?";
		List<TaskDeptItem> list = null;
		if(role.equals(Constants.ROLE_RECVADMIN)){
			hql += " and item.workshop is null";
			list  = getHibernateTemplate().find(hql, new Object[]{task.getSysUID(),deptid});
		}else if(role.equals(Constants.ROLE_RECVADMIN_WORKSHOP)){
			hql += " and item.workshop.id =?";
			list  = getHibernateTemplate().find(hql, new Object[]{task.getSysUID(),deptid,workshopId});
		}
		Session session = getSession();
		for(TaskDeptItem item:list){
			String updHql = "update TaskDeptItem set history='1',status='1' where id = ? and (status='0' or status='1')";
			Query query = session.createQuery(updHql);
			query.setString(0, item.getId());
			query.executeUpdate();
		}
		
		List<String> mxList = null;
		hql = "select max(item.task.docVersion) from TaskDeptItem item where item.task.sysUID=? and item.dept.id =?";
		if(role.equals(Constants.ROLE_RECVADMIN)){
			hql += " and item.workshop is null";
			mxList = getHibernateTemplate().find(hql, new Object[]{task.getSysUID(),deptid});
		}else if(role.equals(Constants.ROLE_RECVADMIN_WORKSHOP)){
			hql += " and item.workshop.id =?";
			mxList = getHibernateTemplate().find(hql, new Object[]{task.getSysUID(),deptid,workshopId});
		}
		String mxVerStr = mxList.get(0);
		
		hql = "select task.id from IssueTask task where task.sysUID=? and task.docVersion=?";
		List<String> mxIdList;
		mxIdList = getHibernateTemplate().find(hql, new Object[]{task.getSysUID(),mxVerStr});
		String mxId = mxIdList.get(0);
		
		String updMxSql = "update TaskDeptItem set history='0',status='1',signTime=? where task.id=? and dept.id=?";
		if(role.equals(Constants.ROLE_RECVADMIN)){
			updMxSql += " and workshop is null";
		}else if(role.equals(Constants.ROLE_RECVADMIN_WORKSHOP)){
			updMxSql += " and workshop.id = ?";
		}
		Query updMxQuery = session.createQuery(updMxSql);
		updMxQuery.setTimestamp(0, new Date());
		updMxQuery.setString(1, mxId);
		updMxQuery.setString(2, deptid);
		if(role.equals(Constants.ROLE_RECVADMIN_WORKSHOP)){
			updMxQuery.setString(3, workshopId);
		}
		updMxQuery.executeUpdate();
		releaseSession(session);
	}
	
	@Deprecated
	public List findMyLatestIssues(String partid,String deptId){
		String hql = "select item from TaskDeptItem item where item.task.partid=? and item.dept.id=? and item.status='0'";
		List list = getHibernateTemplate().find(hql, new Object[]{partid,deptId});
		return list;
	}
	
	public List findMyLatestIssuesBySysUID(String sysUID,String deptId,String role,DictData workshop){
		String hql = "select item from TaskDeptItem item where item.task.sysUID=? and item.dept.id=? and item.status='0'";
		List list = null;
		if(role.equals(Constants.ROLE_RECVADMIN)){
			hql +=" and item.workshop is null";
			list = getHibernateTemplate().find(hql, new Object[]{sysUID,deptId});
		}else if(role.equals(Constants.ROLE_RECV)){
			if(workshop!=null){
				hql +=" and item.workshop.id = ?";
				list = getHibernateTemplate().find(hql, new Object[]{sysUID,deptId,workshop.getId()});
			}else{
				hql +=" and item.workshop is null";
				list = getHibernateTemplate().find(hql, new Object[]{sysUID,deptId});
			}
		}
		 
		return list;
	}
	
	@Deprecated
	public List findMyHistoryIssues(String partid,String deptId){
		String hql = "select item from TaskDeptItem item where item.task.partid=? and item.dept.id=? and item.status='1' and item.history='1'";
		List list = getHibernateTemplate().find(hql, new Object[]{partid,deptId});
		return list;
	}
	
	public List findMyHistoryIssuesBySysUID(String sysUID,String deptId,String roleCode,DictData workshop){
		List list = null;
		String hql = "";
		if(roleCode.equals(Constants.ROLE_RECVADMIN)){
			hql = "select item from TaskDeptItem item where item.task.sysUID=? and item.dept.id=? and item.status='1' and item.history='1' and item.workshop is null";
			list = getHibernateTemplate().find(hql, new Object[]{sysUID,deptId});
		}else if(roleCode.equals(Constants.ROLE_RECV)){
			hql = "select item from TaskDeptItem item where item.task.sysUID=? and item.dept.id=? and item.status='1' and item.history='1' and item.issueType is null and (item.workshop is null or item.workshop=?)";
			list = getHibernateTemplate().find(hql, new Object[]{sysUID,deptId,workshop});
		}
		return list;
	}
	
	/**
	 * 0：未签收 2：已标记 的可以撤销，因为转厂的需求，已签收的也可收回（status=1）
	 * @param taskId
	 * @param deptId
	 */
	public void removeItemByTaskAndDept(String taskId,String deptId){
		Session session = getSession();
		String hql = "delete from TaskDeptItem item where item.task.id=? and item.dept.id=? and (item.status='0' or item.status='2' or item.status='1')";
		Query query = session.createQuery(hql);
		query.setString(0, taskId);
		query.setString(1, deptId);
		query.executeUpdate();
		releaseSession(session);
	}
	
	/**
	 * 将“已标记”状态标记为未签收
	 * @param taskId
	 * @param originStaus
	 * @param status
	 */
	public void updateIssueItemStatus(String taskId,String originStaus,String status){
		Session session = getSession();
		IssueTask task = new IssueTask();
		task.setId(taskId);
		String hql = "update TaskDeptItem item set item.status=? where item.task = ? and item.status=?";
		Query query = session.createQuery(hql);
		query.setString(0, status);
		query.setEntity(1, task);
		query.setString(2, originStaus);
		query.executeUpdate();
		releaseSession(session);
	}
	
	/**
	 * 根据文档id及厂部id，更新发送到车间的收发记录状态
	 * @param taskId
	 * @param deptId
	 * @param itemStatus
	 */
	public void updateItemStatusOfWorkshop(String taskId,String deptId,String itemStatus){
		Session session = getSession();
		String hql = "update TaskDeptItem item set item.status=? where item.task.id = ? and item.dept.id =? and item.workshop is not null";
		Query query = session.createQuery(hql);
		query.setString(0, itemStatus);
		query.setString(1, taskId);
		query.setString(2, deptId);
		query.executeUpdate();
		releaseSession(session);
	}
	
	/**
	 * 根据文档id和车间id更新发放接收记录状态
	 * 该发放目前只用于回收销毁状态,补充了destroyTime的写入
	 * @param taskId
	 * @param originStaus
	 * @param status
	 */
	public void updateStatusByTaskAndWorkshop(String taskId,String workshopId,String itemStatus){
		Session session = getSession();
		String hql = "update TaskDeptItem item set item.status=?,destroyTime=? where item.task.id = ? and item.workshop.id =?";
		Query query = session.createQuery(hql);
		query.setString(0, itemStatus);
		query.setTimestamp(1, new Date());
		query.setString(2, taskId);
		query.setString(3, workshopId);
		query.executeUpdate();
		releaseSession(session);
	}
	
	/**
	 * 0：未签收 2：已标记 的可以撤销
	 * @param taskId
	 * @param deptId
	 */
	public void removeItemByTaskAndWorkshop(String taskId,String deptId){
		Session session = getSession();
		String hql = "delete from TaskDeptItem item where item.task.id=? and item.workshop.id=? and (item.status='0' or item.status='2')";
		Query query = session.createQuery(hql);
		query.setString(0, taskId);
		query.setString(1, deptId);
		query.executeUpdate();
		releaseSession(session);
	}
	
	/**
	 * TC发布任务,直接取消任务
	 * @param taskId
	 */
	public void removeItemByTask(String taskId){
		Session session = getSession();
		String hql = "delete from TaskDeptItem item where item.task.id=?";
		Query query = session.createQuery(hql);
		query.setString(0, taskId);
		query.executeUpdate();
		releaseSession(session);
	}
	
	/**
	 * 删除重复二级部门
	 */
	public void removeErroWorkshipData(){
		Session session = getSession();		
		String sql = "delete from SC_TASKDEPTITEM  a where (a.TSKID,a.CITYID,a.WORKSHOP_ID) in (select TSKID ,CITYID ,WORKSHOP_ID from SC_TASKDEPTITEM group by TSKID ,CITYID ,WORKSHOP_ID  having count(*) > 1) and a.TSKID in (select ID from SC_ISSUETASK where STATUS = 'D' and DOCTYPE = '2') and rowid not in (select min(rowid) from SC_TASKDEPTITEM group by TSKID ,CITYID ,WORKSHOP_ID having count(*)>1)";
		Query query = session.createSQLQuery(sql);
		query.executeUpdate();
		releaseSession(session);
	}
	
	
	/**
	 * 删除重复厂
	 */
	public void removeErroCityData(){
		Session session = getSession();		
		String sql = "delete from SC_TASKDEPTITEM  a where (a.TSKID,a.CITYID) in (select TSKID ,CITYID  from SC_TASKDEPTITEM where WORKSHOP_ID is null group by TSKID ,CITYID   having count(*) > 1) and a.TSKID in (select ID from SC_ISSUETASK where STATUS = 'D' and DOCTYPE = '2')and WORKSHOP_ID is null and rowid not in (select min(rowid) from SC_TASKDEPTITEM where WORKSHOP_ID is null group by TSKID ,CITYID  having count(*)>1)";
		Query query = session.createSQLQuery(sql);
		query.executeUpdate();
		releaseSession(session);
	}

}
