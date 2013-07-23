package com.shac.dao.hibernate;

import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.shac.model.DictData;
import com.shac.model.IssueTask;
import com.shac.model.IssueTaskTC;
import com.shac.model.TaskDeptItem;

import com.shac.model.TaskDeptItemTC;
import com.shac.util.Constants;
@Repository("taskDeptItemTCDao")
public class TaskDeptItemTCDaoHibernate extends GenericDaoHibernate<TaskDeptItemTC, String>{
	
	public TaskDeptItemTCDaoHibernate() {
		super(TaskDeptItemTC.class);
	}
	
	@Override
	public DetachedCriteria getCriteria(Map filterMap) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(TaskDeptItemTC.class);
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
			IssueTaskTC task = new IssueTaskTC();
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
			detachedCriteria.add(Restrictions.eq("task.drawingNumb", (String)filterMap.get("drawingNumb")));
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
		
		return detachedCriteria;
	}
	
	public void saveTaskDeptItemTC(TaskDeptItemTC item){
		getHibernateTemplate().save(item);
	}
	
	
	/**
	 * TC发布任务,直接取消任务
	 * @param taskId
	 */
	public void removeItemByTask(String taskId){
		Session session = getSession();
		String hql = "delete from TaskDeptItemTC item where item.task.id=?";
		Query query = session.createQuery(hql);
		query.setString(0, taskId);
		query.executeUpdate();
		releaseSession(session);
	}

	public void removeItemByTaskAndDept(String taskid, String deptId) {
		Session session = getSession();
		String hql = "delete from TaskDeptItemTC item where item.task.id=? and item.dept.id=? and (item.status='0' or item.status='2' or item.status='1')";
		Query query = session.createQuery(hql);
		query.setString(0, taskid);
		query.setString(1, deptId);
		query.executeUpdate();
		releaseSession(session);
		
	}
}
