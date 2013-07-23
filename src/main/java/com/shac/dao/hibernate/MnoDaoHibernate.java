package com.shac.dao.hibernate;

import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;


import com.shac.model.Mno;
import com.shac.model.User;

@Repository("mnoDao")
public class MnoDaoHibernate extends GenericDaoHibernate<Mno, String>{
	public MnoDaoHibernate() {
		super(Mno.class);
	}
	@Override
	public DetachedCriteria getCriteria(Map filterMap) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Mno.class);
		
		if(isNotEmpty(filterMap.get("user"))){
			detachedCriteria.add(Restrictions.eq("user", (User)filterMap.get("user")));
		}
		// TODO Auto-generated method stub
		return detachedCriteria;
	}
	
	/**
	 * 获得编号
	 */
	public String saveMno(Mno mno){
		  return (String)getHibernateTemplate().save(mno);
	}
}
