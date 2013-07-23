package com.shac.dao.hibernate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;


import com.shac.model.TCIssueTask;

@Repository("tcIssueTaskDao")
public class TCIssueTaskDaoHibernate extends GenericDaoHibernate<TCIssueTask, String>{
	public TCIssueTaskDaoHibernate() {
		super(TCIssueTask.class);
	}
	@Override
	public DetachedCriteria getCriteria(Map filterMap) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(TCIssueTask.class);
		if(isNotEmpty(filterMap.get("taskStatus"))){
			detachedCriteria.add(Restrictions.eq("taskStatus", (String)filterMap.get("taskStatus")));
		}
		
		
		return detachedCriteria;
	}
	
	/**
	 * 更新结果
	 * 	 
	 */
	public void updateTCIssueTaskStatus(String id,String result,String faillog){
		Session session = getSession();
		if(faillog.length()>200){
			faillog = faillog.substring(0, 100);
		}
		
		String hql = "update TCIssueTask set taskStatus=1 ,dealstatus=?,dealtime=sysdate,faillog=? where id = ?";
		Query query = session.createQuery(hql);
		query.setString(0, result);
		query.setString(1, faillog);
		query.setString(2, id);
		query.executeUpdate();
		releaseSession(session);
	}
	
	
	
}
