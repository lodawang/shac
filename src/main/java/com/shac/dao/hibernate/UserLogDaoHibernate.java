package com.shac.dao.hibernate;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.shac.model.UserLog;


@Repository("userLogDao")
public class UserLogDaoHibernate extends GenericDaoHibernate<UserLog, String>{

	public UserLogDaoHibernate() {
		super(UserLog.class);
	}

	@Override
	public DetachedCriteria getCriteria(Map filterMap) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(UserLog.class);
		detachedCriteria.createAlias("user", "user");
		if(isNotEmpty(filterMap.get("user.loginID"))){
			detachedCriteria.add(Restrictions.like("user.loginID",(String)filterMap.get("user.loginID"),MatchMode.ANYWHERE));
		}
		if(isNotEmpty(filterMap.get("dateBegin"))){
			detachedCriteria.add(Restrictions.ge("loginTime",(Date)filterMap.get("dateBegin")));
		}
		if(isNotEmpty(filterMap.get("dateEnd"))){
			Date dateEnd = (Date)filterMap.get("dateEnd");
			Calendar cal = Calendar.getInstance();
			cal.setTime(dateEnd);
			cal.set(Calendar.HOUR_OF_DAY, 23);
			cal.set(Calendar.MINUTE, 59);
			cal.set(Calendar.SECOND, 59);
			detachedCriteria.add(Restrictions.le("loginTime",cal.getTime()));
		}
		return detachedCriteria;
	}

}
