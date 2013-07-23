package com.shac.dao.hibernate;

import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;

import com.shac.model.RoleUser;

@Repository("roleUserDao")
public class RoleUserDaoHibernate extends GenericDaoHibernate<RoleUser, String>{

	public RoleUserDaoHibernate() {
		super(RoleUser.class);
	}

	@Override
	public DetachedCriteria getCriteria(Map filterMap) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(RoleUser.class);
		return detachedCriteria;
	}
	
	public void deleRoleByUser(String userId){
		Session session = getSession();
		String hql = "delete from RoleUser where userId = ?";
		Query query = session.createQuery(hql);
		query.setString(0, userId);
		query.executeUpdate();
		releaseSession(session);
	}

}
