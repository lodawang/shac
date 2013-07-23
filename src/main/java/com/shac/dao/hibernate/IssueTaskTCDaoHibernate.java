package com.shac.dao.hibernate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.shac.model.IssueTaskTC;
import com.shac.model.User;
import com.shac.util.Constants;

@Repository("issueTaskTCDao")
public class IssueTaskTCDaoHibernate extends GenericDaoHibernate<IssueTaskTC, String>{
	public IssueTaskTCDaoHibernate() {
		super(IssueTaskTC.class);
	}
	
	@Override
	public DetachedCriteria getCriteria(Map filterMap) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(IssueTaskTC.class);
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
		
		if(isNotEmpty(filterMap.get("dealstatus"))){
			detachedCriteria.add(Restrictions.eq("dealstatus", (String)filterMap.get("dealstatus")));		
		}
		
		
		return detachedCriteria;
	}

	/**
	 * 更新结果
	 * 	 
	 */
	public void updateTCIssueTaskStatus(String id,String result,String faillog) {
		// TODO Auto-generated method stub
		Session session = getSession();
		if(faillog.length()>200){
			faillog = faillog.substring(0, 100);
		}
		
		String hql = "update IssueTaskTC set dealstatus=?,dealtime=sysdate,faillog=? where id = ?";
		Query query = session.createQuery(hql);
		query.setString(0, result);
		query.setString(1, faillog);
		query.setString(2, id);
		query.executeUpdate();
		releaseSession(session);
	}
}
