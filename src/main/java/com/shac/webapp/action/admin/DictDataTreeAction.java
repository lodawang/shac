package com.shac.webapp.action.admin;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;

import com.opensymphony.xwork2.ActionSupport;
import com.shac.dao.hibernate.DictDataDaoHibernate;
import com.shac.dao.hibernate.RuleRecipientDaoHibernate;
import com.shac.model.DictData;
import com.shac.util.Constants;


public class DictDataTreeAction extends ActionSupport{
	private DictDataDaoHibernate dictDataDao;
	private RuleRecipientDaoHibernate ruleRecipientDao;
	private String ruleId;
	
	@Action(value="loadOrgsIssueRuleTree")
	public void loadUserTreeForConfig() throws IOException, JSONException{

		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
		List<DictData> ruleRecipients = ruleRecipientDao.findRecipientsByRule(ruleId);
		List organizations = dictDataDao.findByDictType(Constants.DICTTYPE_REGION);
		Iterator<DictData> iter = organizations.iterator();

		JSONArray array = new JSONArray();
		
		while(iter.hasNext()){
			DictData org = iter.next();
			JSONObject tree = new JSONObject();
			tree.put("id", org.getId());
			if(org.getParent()==null){
				tree.put("pId", 0);
			}else{
				tree.put("pId", org.getParent().getId());
			}
			
			for(DictData r:ruleRecipients){
				if(r.getId().equals(org.getId())){
					tree.put("checked", true);
					break;
				}
			}
			tree.put("name", org.getDictValue());
			tree.put("open", true);
			tree.put("nocheck", true);
			
			
			List<DictData> children = dictDataDao.findChildren(org.getId());
			Iterator<DictData> childIter = children.iterator();
			JSONArray childArray = new JSONArray();
			while(childIter.hasNext()){
				DictData childOrg = childIter.next();
				JSONObject childTree = new JSONObject();
				childTree.put("id", childOrg.getId());
				if(childOrg.getParent()==null){
					childTree.put("pId", 0);
				}else{
					childTree.put("pId", childOrg.getParent().getId());
				}
				
				for(DictData r:ruleRecipients){
					if(r.getId().equals(childOrg.getId())){
						childTree.put("checked", true);
						break;
					}
				}
				childTree.put("name", childOrg.getDictValue());
				childArray.add(childTree);
			}
			tree.put("children", childArray);
			array.add(tree);
		}
		
		response.getWriter().write(array.toString());
	}

	public DictDataDaoHibernate getDictDataDao() {
		return dictDataDao;
	}

	public void setDictDataDao(DictDataDaoHibernate dictDataDao) {
		this.dictDataDao = dictDataDao;
	}

	public String getRuleId() {
		return ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}

	public RuleRecipientDaoHibernate getRuleRecipientDao() {
		return ruleRecipientDao;
	}

	public void setRuleRecipientDao(RuleRecipientDaoHibernate ruleRecipientDao) {
		this.ruleRecipientDao = ruleRecipientDao;
	}
	
}
