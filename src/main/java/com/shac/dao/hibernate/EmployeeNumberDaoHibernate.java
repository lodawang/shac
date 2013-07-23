package com.shac.dao.hibernate;

import java.util.Map;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.shac.model.DictData;
import com.shac.model.EmployeeNumber;
import com.shac.model.User;

@Repository("employeeNumberDao")
public class EmployeeNumberDaoHibernate extends GenericDaoHibernate<EmployeeNumber, String>{

	public EmployeeNumberDaoHibernate() {
		super(EmployeeNumber.class);
	}

	@Override
	public DetachedCriteria getCriteria(Map filterMap) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(EmployeeNumber.class);
		
		if(isNotEmpty(filterMap.get("userid"))){
			User user = new User();
			user.setId((String)filterMap.get("userid"));
			detachedCriteria.add(Restrictions.eq("user",user));
		}
		
		if(isNotEmpty(filterMap.get("empNumber"))){
			detachedCriteria.add(Restrictions.eq("empNumber",(String)filterMap.get("empNumber")));
		}
		return detachedCriteria;
	}

}
