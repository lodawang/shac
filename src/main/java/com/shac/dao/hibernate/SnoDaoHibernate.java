package com.shac.dao.hibernate;

import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;

import com.shac.model.Sno;


@Repository("snoDao")
public class SnoDaoHibernate extends GenericDaoHibernate<Sno, String>{
	public SnoDaoHibernate() {
		super(Sno.class);
	}
	@Override
	public DetachedCriteria getCriteria(Map filterMap) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 获得编号
	 */
	public void saveSno(Sno sno){
		  getHibernateTemplate().save(sno);
	}
}
