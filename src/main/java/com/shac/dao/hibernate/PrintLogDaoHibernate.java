package com.shac.dao.hibernate;

import java.util.Map;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;

import com.shac.model.PrintLog;

@Repository("printLogDao")
public class PrintLogDaoHibernate extends GenericDaoHibernate<PrintLog, String>{
	
	public PrintLogDaoHibernate() {
		super(PrintLog.class);
	}

	@Override
	public DetachedCriteria getCriteria(Map filterMap) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(PrintLog.class);
		return detachedCriteria;
	}

}
