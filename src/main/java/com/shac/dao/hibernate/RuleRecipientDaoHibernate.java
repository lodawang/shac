package com.shac.dao.hibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;

import com.shac.model.DictData;
import com.shac.model.IssueRule;
import com.shac.model.RuleRecipient;

@Repository("ruleRecipientDao")
public class RuleRecipientDaoHibernate extends GenericDaoHibernate<RuleRecipient,String>{

	public RuleRecipientDaoHibernate() {
		super(RuleRecipient.class);
	}

	@Override
	public DetachedCriteria getCriteria(Map filterMap) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(RuleRecipient.class);
		return detachedCriteria;
	}
	
	public void deleteByRule(String ruleId){
		Session session = getSession();
		String hql = "delete from RuleRecipient where issueRule.id = ?";
		Query query = session.createQuery(hql);
		query.setString(0, ruleId);
		query.executeUpdate();
		releaseSession(session);
	}
	
	public List<DictData> findRecipientsByRule(String ruleId){
		String hql = "from RuleRecipient where issueRule.id = ?";
		List<RuleRecipient> list = getHibernateTemplate().find(hql, new Object[]{ruleId});
		List<DictData> result = new ArrayList();
		for(RuleRecipient rr:list){
			if(rr.getWorkshop()==null){
				result.add(rr.getFactory());
			}else{
				result.add(rr.getWorkshop());
			}
		}
		return result;
	}
	
	public List<DictData> findRecipientsByRule(IssueRule rule){
		String hql = "from IssueRule where (docType is null or docType='' or docType=?) and (procMode is null or procMode='' or procMode=?) and (procDocClass is null or procDocClass='' or procDocClass=?) and (processIn is null or processIn='' or processIn=?)";
		List<IssueRule> list = getHibernateTemplate().find(hql, new Object[]{rule.getDocType(),rule.getProcMode(),rule.getProcDocClass(),rule.getProcessIn()});
		List<DictData> result = new ArrayList();
		for(IssueRule r:list){
			result.addAll(findRecipientsByRule(r.getId()));
		}
		return result;
	}

}
