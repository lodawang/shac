package com.shac.dao.hibernate;

import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.shac.model.Advice;
import com.shac.model.IssueTask;
import com.shac.model.User;



@Repository("adviceDao")
public class AdviceDaoHibernate extends GenericDaoHibernate<Advice, String>{

	public AdviceDaoHibernate() {
		super(Advice.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	public DetachedCriteria getCriteria(Map filterMap) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Advice.class);
		if(isNotEmpty(filterMap.get("senderid"))){
			detachedCriteria.add(Restrictions.eq("senderid", (User)filterMap.get("senderid")));
		}
		
		if(isNotEmpty(filterMap.get("receiveridsuper"))){
			detachedCriteria.add(Restrictions.eq("receiverid", (User)filterMap.get("receiveridsuper")));
		}
		
		if(isNotEmpty(filterMap.get("rwd"))){
			detachedCriteria.add(Restrictions.or(Restrictions.eq("receiverid", (User)filterMap.get("rwd")), Restrictions.eq("senderid", (User)filterMap.get("rwd"))));
		}
		
		return detachedCriteria;
	}
    
	
	/**
	 * 更新状态
	 * @param taskid
	 * @param status
	 */
	public void updateStatus(String taskid,String status,String result){
		Session session = getSession();
		String hql = "update Advice set status=? , resultbake=? where id = ?";
		Query query = session.createQuery(hql);
		query.setString(0, status);
		query.setString(1, result);
		query.setString(2, taskid);
		query.executeUpdate();
		releaseSession(session);
	}
}
