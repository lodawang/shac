package com.shac.dao.hibernate;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.jdom.Document;
import org.jdom.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.shac.model.DictData;
import com.shac.util.Constants;

@Repository("dictDataDao")
public class DictDataDaoHibernate extends GenericDaoHibernate<DictData, String>{
	
	
	public DictDataDaoHibernate() {
		super(DictData.class);
	}

	@Override
	public DetachedCriteria getCriteria(Map filterMap) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(DictData.class);
		if(isNotEmpty(filterMap.get("dictType"))){
			detachedCriteria.add(Restrictions.eq("dictType", filterMap.get("dictType")));
		}
		if(isNotEmpty(filterMap.get("status"))){
			detachedCriteria.add(Restrictions.eq("status", filterMap.get("status")));
		}
		if(isNotEmpty(filterMap.get("dictValue"))){
			detachedCriteria.add(Restrictions.eq("dictValue", filterMap.get("dictValue")));
		}
		if(isNotEmpty(filterMap.get("dictID"))){
			detachedCriteria.add(Restrictions.eq("id", filterMap.get("dictID")));
		}
		return detachedCriteria;
	}
	
	public List findByDictType(String dictType){
		 Map filterMap = new HashMap();
		 filterMap.put("dictType", dictType);
		 filterMap.put("status", Constants.TRUE_STRING);
		 DetachedCriteria criteria = getCriteria(filterMap);
		 Map orderMap = new HashMap();
		 orderMap.put("order","asc");
		 //orderMap.put("id","asc");
	     orderCriteria(criteria, orderMap);
	     return getHibernateTemplate().findByCriteria(criteria);
	}
	
	public void updateOrder(String id,String upOrDown){
		Session session = getSession();
		String hql = "";
		if(upOrDown.equals("up")){
			hql = "update DictData dd set dd.order=dd.order+1 where id = ?";
		}else if(upOrDown.equals("down")){
			hql = "update DictData dd set dd.order=dd.order-1 where id = ?";
		}
		Query query = session.createQuery(hql);
		query.setString(0, id);
		query.executeUpdate();
		releaseSession(session);
	}
	
	public void saveDictData(DictData data){
		getHibernateTemplate().save(data);
	}
	
	public void updateDictData(DictData data){
		getHibernateTemplate().update(data);
	}

	public List findChildren(String parentId){
		 String hql = "from DictData where parent.id=? order by order asc";
		 List list = getHibernateTemplate().find(hql, new Object[]{parentId});
		 return list;
	}

}
