package com.shac.dao.hibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.shac.model.DictData;
import com.shac.model.IssueRule;
import com.shac.model.IssueTask;

@Repository("issueRuleDao")
public class IssueRuleDaoHibernate extends GenericDaoHibernate<IssueRule,String>{

	public IssueRuleDaoHibernate() {
		super(IssueRule.class);
	}

	@Override
	public DetachedCriteria getCriteria(Map filterMap) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(IssueRule.class);
		if(isNotEmpty(filterMap.get("docType"))){
			detachedCriteria.add(Restrictions.eq("docType", filterMap.get("docType")));
		}
		return detachedCriteria;
	}
	
	public List findByDocument(IssueTask docu){
		String hql = "from IssueRule where (docType is null or docType='' or docType=?) and (procMode is null or procMode='' or procMode=?) and (procDocClass is null or procDocClass='' or procDocClass=?) and (processIn is null or processIn='' or processIn=?)";
		List<IssueRule> list = getHibernateTemplate().find(hql, new Object[]{docu.getDocType(),docu.getProcMode(),docu.getProcDocClass(),docu.getProcessIn()});
		return list;
	}

}
