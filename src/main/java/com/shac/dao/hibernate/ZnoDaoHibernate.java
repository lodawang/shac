package com.shac.dao.hibernate;

import java.util.Date;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;

import com.shac.model.Zno;


@Repository("znoDao")
public class ZnoDaoHibernate extends GenericDaoHibernate<Zno, String>{
	
	public ZnoDaoHibernate() {
		super(Zno.class);
	}
	@Override
	public DetachedCriteria getCriteria(Map filterMap) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 获得编号
	 */
	public void saveZno(Zno zno){
		  getHibernateTemplate().save(zno);
	}
}
