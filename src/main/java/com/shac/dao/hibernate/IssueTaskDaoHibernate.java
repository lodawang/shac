package com.shac.dao.hibernate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.Session;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.shac.model.DictData;
import com.shac.model.IssueTask;
import com.shac.model.User;
import com.shac.util.Constants;

@Repository("issueTaskDao")
public class IssueTaskDaoHibernate extends GenericDaoHibernate<IssueTask, String>{

	public IssueTaskDaoHibernate() {
		super(IssueTask.class);
	}

	@Override
	public DetachedCriteria getCriteria(Map filterMap) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(IssueTask.class);
		if(isNotEmpty(filterMap.get("taskType"))){
			detachedCriteria.add(Restrictions.eq("taskType", (String)filterMap.get("taskType")));
		}
		if(isNotEmpty(filterMap.get("docType"))){
			detachedCriteria.add(Restrictions.eq("docType", (String)filterMap.get("docType")));
		}
		if(isNotEmpty(filterMap.get("status"))){
			detachedCriteria.add(Restrictions.eq("status", (String)filterMap.get("status")));
		}
		
		if(isNotEmpty(filterMap.get("client"))){
			detachedCriteria.add(Restrictions.eq("client", (String)filterMap.get("client")));
		}
		if(isNotEmpty(filterMap.get("modelCode"))){
			detachedCriteria.add(Restrictions.eq("modelCode", (String)filterMap.get("modelCode")));
		}
		if(isNotEmpty(filterMap.get("cltPartNumb"))){
			detachedCriteria.add(Restrictions.ilike("cltPartNumb", (String)filterMap.get("cltPartNumb"),MatchMode.ANYWHERE));
		}
		if(isNotEmpty(filterMap.get("eqcltPartNumb"))){
			detachedCriteria.add(Restrictions.eq("cltPartNumb",(String)filterMap.get("cltPartNumb")));
		}
		if(isNotEmpty(filterMap.get("processIn"))){
			detachedCriteria.add(Restrictions.eq("processIn", (String)filterMap.get("processIn")));
		}
		if(isNotEmpty(filterMap.get("techDocClass"))){
			detachedCriteria.add(Restrictions.eq("techDocClass", (String)filterMap.get("techDocClass")));
		}
		if(isNotEmpty(filterMap.get("procDocClass"))){
			detachedCriteria.add(Restrictions.eq("procDocClass", (String)filterMap.get("procDocClass")));
		}
		if(isNotEmpty(filterMap.get("procMode"))){
			detachedCriteria.add(Restrictions.eq("procMode", (String)filterMap.get("procMode")));
		}
		if(isNotEmpty(filterMap.get("drawingNumb"))){
			//detachedCriteria.add(Restrictions.eq("drawingNumb", (String)filterMap.get("drawingNumb")));
			detachedCriteria.add(Restrictions.ilike("drawingNumb", (String)filterMap.get("drawingNumb"),MatchMode.ANYWHERE));
		}
		if(isNotEmpty(filterMap.get("docVersion"))){
			detachedCriteria.add(Restrictions.eq("docVersion", (String)filterMap.get("docVersion")));
		}
		if(isNotEmpty(filterMap.get("drawingSize"))){
			detachedCriteria.add(Restrictions.eq("drawingSize", (String)filterMap.get("drawingSize")));
		}
		if(isNotEmpty(filterMap.get("partid"))){
			String querypartid = (String)filterMap.get("partid");
			detachedCriteria.add(Restrictions.ilike("partid", querypartid.trim(),MatchMode.ANYWHERE));
		}
		if(isNotEmpty(filterMap.get("eqPartid"))){
			detachedCriteria.add(Restrictions.eq("partid",(String)filterMap.get("eqPartid")));
		}
		if(isNotEmpty(filterMap.get("history"))){
			detachedCriteria.add(Restrictions.eq("history", (String)filterMap.get("history")));
		}
		if(isNotEmpty(filterMap.get("taskStatus"))){
			detachedCriteria.add(Restrictions.eq("taskStatus", (String)filterMap.get("taskStatus")));
		}
		if(isNotEmpty(filterMap.get("assembly"))){
			//detachedCriteria.add(Restrictions.eq("assembly", (String)filterMap.get("assembly")));
			detachedCriteria.add(Restrictions.ilike("assembly", (String)filterMap.get("assembly"),MatchMode.ANYWHERE));
		}
		if(isNotEmpty(filterMap.get("eqAssembly"))){
			detachedCriteria.add(Restrictions.eq("assembly", (String)filterMap.get("eqAssembly")));
		}
		if(isNotEmpty(filterMap.get("emptyAssembly"))){
			detachedCriteria.add(Restrictions.or(Restrictions.isNull("assembly"),Restrictions.eq("assembly", "")));
		}
		if(isNotEmpty(filterMap.get("assembOrnot"))){
			detachedCriteria.add(Restrictions.eq("assembOrnot", new Boolean((String)filterMap.get("assembOrnot"))));
		}
		if(isNotEmpty(filterMap.get("veroftc"))){
			detachedCriteria.add(Restrictions.eq("veroftc", (String)filterMap.get("veroftc")));
		}
		if(isNotEmpty(filterMap.get("assembTitle"))){
			detachedCriteria.add(Restrictions.ilike("assembTitle", (String)filterMap.get("assembTitle"),MatchMode.ANYWHERE));
		}
		if(isNotEmpty(filterMap.get("drawAndTech"))){
			detachedCriteria.add(Restrictions.or(Restrictions.eq("docType", Constants.DOC_DOCTYPE_DRAW),Restrictions.eq("docType", Constants.DOC_DOCTYPE_TECH)));
		}
		if(isNotEmpty(filterMap.get("sysUID"))){
			detachedCriteria.add(Restrictions.eq("sysUID",(String)filterMap.get("sysUID")));
		}
		if(isNotEmpty(filterMap.get("user"))){
			detachedCriteria.add(Restrictions.eq("user", (User)filterMap.get("user")));
		}
		if(isNotEmpty(filterMap.get("pickdate"))){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date s_date = null;
			Date e_date = null;
			try {
				s_date = (Date)sdf.parse((String)filterMap.get("pickdate"));
				e_date =   new   Date(s_date.getTime()   +   1000   *   60   *   60   *   24);   
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
			detachedCriteria.add(Restrictions.ge("createTime",s_date));
			detachedCriteria.add(Restrictions.le("createTime",e_date));
		}
		
		
		if(isNotEmpty(filterMap.get("attachFile"))){
			String temp = (String)filterMap.get("attachFile");
			if(temp.equalsIgnoreCase("1")){
				detachedCriteria.add(Restrictions.isNotNull("attachFile"));
			}else{
				detachedCriteria.add(Restrictions.isNull("attachFile"));
			}	
		}
		
		if(isNotEmpty(filterMap.get("dept"))){			
			if(isNotEmpty(filterMap.get("querywork"))){							
			  detachedCriteria.createAlias("depitem", "tdt").setFetchMode("tdt", FetchMode.JOIN).add(Restrictions.eq("tdt.workshop.id", (String)filterMap.get("querywork")));
			}else{
			  detachedCriteria.createAlias("depitem", "tdt").setFetchMode("tdt", FetchMode.JOIN).add(Restrictions.eq("tdt.dept.id", (String)filterMap.get("dept"))).add(Restrictions.isNull("tdt.workshop"));
			}
		}
		
		if(isNotEmpty(filterMap.get("oldimport"))){
			detachedCriteria.add(Restrictions.eq("beta",(String)filterMap.get("oldimport")));
		}
		
		
		return detachedCriteria;
	}
	/**
	 * 更新发放状态
	 * @param taskid
	 * @param status
	 */
	public void updateIssueTaskStatus(String taskid,String status){
		Session session = getSession();
		String hql = "update IssueTask set taskStatus=? where id = ?";
		Query query = session.createQuery(hql);
		query.setString(0, status);
		query.setString(1, taskid);
		query.executeUpdate();
		releaseSession(session);
	}
	
	/**
	 * 更新附件
	 * @param taskid
	 * @param attach
	 */
	public void updateIssueTaskAttach(String taskid,String attach){
		Session session = getSession();
		String hql = "update IssueTask set attachFile=? where id = ?";
		Query query = session.createQuery(hql);
		query.setString(0, attach);
		query.setString(1, taskid);
		query.executeUpdate();
		releaseSession(session);
	}
	
	/**
	 * 更新查看文件
	 * @param taskid
	 * @param attach
	 */
	public void updateIssueTaskViewFile(String taskid,String viewFile){
		Session session = getSession();
		String hql = "update IssueTask set viewFile=? where id = ?";
		Query query = session.createQuery(hql);
		query.setString(0, viewFile);
		query.setString(1, taskid);
		query.executeUpdate();
		releaseSession(session);
	}
	
	/**
	 * 更新查看文件
	 * @param taskid
	 * @param attach
	 */
	public void updatePrintFile(String id,String printFile){
		Session session = getSession();
		String hql = "update IssueTask set adminPrintFile=? where id = ?";
		Query query = session.createQuery(hql);
		query.setString(0, printFile);
		query.setString(1, id);
		query.executeUpdate();
		releaseSession(session);
	}
	
	/**
	 * 更新查看文件
	 * @param taskid
	 * @param attach
	 */
	public void updateOrdinaryPrintFile(String id,String printFile){
		Session session = getSession();
		String hql = "update IssueTask set printFile=? where id = ?";
		Query query = session.createQuery(hql);
		query.setString(0, printFile);
		query.setString(1, id);
		query.executeUpdate();
		releaseSession(session);
	}
	
	public String saveIssueTask(IssueTask task){
		 return (String)getHibernateTemplate().save(task);
	}
	

	
	/**
	 * 更新历史状态
	 * @param taskid
	 * @param status
	 */
	public void updateIssueHisStatus(String taskid,String status){
		Session session = getSession();
		String hql = "update IssueTask set history=? where id = ?";
		Query query = session.createQuery(hql);
		query.setString(0, status);
		query.setString(1, taskid);
		query.executeUpdate();
		releaseSession(session);
	}
	
	public IssueTask findDocuByPartId(String partId,String assembly){
		String hql;
		List list;
		if(assembly==null||assembly.trim().equals("")){
			hql = "from IssueTask where partid =? and (assembly is null or assembly='')";
			list = getHibernateTemplate().find(hql, new Object[]{partId});
		}else{
			hql = "from IssueTask where partid =? and assembly = ?";
			list = getHibernateTemplate().find(hql, new Object[]{partId,assembly});
		}
		IssueTask docu = null;
		if(list!=null && list.size()>0){
			docu = (IssueTask)list.get(0);
		}
		return docu;
	}
	
	public int getCountByPartIdOnly(String partId){
		String hql = "select count(*) from IssueTask where partid =? and taskStatus in('0','1')";
		List list = getHibernateTemplate().find(hql, new Object[]{partId});
		int count = 0;
		if(list!=null && list.size()>0){
			count = ((Long)list.get(0)).intValue();
		}
		return count;
	}
	
	public int getCountByPartId(String partId,String assembly){
		List list;
		if(assembly==null || assembly.trim().equals("")){
			String hql = "select count(*) from IssueTask where partid =? and (assembly is null or assembly='') and taskStatus in('0','1')";
			list = getHibernateTemplate().find(hql, new Object[]{partId});
		}else{
			String hql = "select count(*) from IssueTask where partid =? and assembly =? and taskStatus in('0','1')";
			list = getHibernateTemplate().find(hql, new Object[]{partId,assembly});
		}
		int count = 0;
		if(list!=null && list.size()>0){
			count = ((Long)list.get(0)).intValue();
		}
		return count;
	}
	
	public int getCountBySysUID(String sysUID){
		String hql = "select count(*) from IssueTask where sysUID = ? and taskStatus in('0','1')";
		List list = getHibernateTemplate().find(hql, new Object[]{sysUID});
		int count = 0;
		if(list!=null && list.size()>0){
			count = ((Long)list.get(0)).intValue();
		}
		return count;
	}
	
	/**
	 * TC升级版本删除老版本恢复
	 * @param taskId
	 * @param viewfile
	 */
	public void updateItemByTCTask(String taskId,String viewfile){
		Session session = getSession();
		String hql = "update IssueTask set history=? , viewFile=? where id=?";
		Query query = session.createQuery(hql);
		query.setString(0, "0");
		query.setString(1, viewfile);
		query.setString(2, taskId);
		query.executeUpdate();
		releaseSession(session);
	}
    
	/**
	 *  批量测试转正式
	 */
	public void updateTask(IssueTask task){
		getHibernateTemplate().update(task);
	}
}
