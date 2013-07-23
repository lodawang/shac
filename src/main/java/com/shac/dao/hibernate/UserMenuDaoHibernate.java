package com.shac.dao.hibernate;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;

import com.shac.model.UserMenu;

@Repository("userMenuDao")
public class UserMenuDaoHibernate extends GenericDaoHibernate<UserMenu, String>{
	
	public UserMenuDaoHibernate() {
		super(UserMenu.class);
	}

	@Override
	public DetachedCriteria getCriteria(Map filterMap) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(UserMenu.class);
		return detachedCriteria;
	}
	
	public List listRoleMenu(String roleCode){
		String hql = "select role.roleMenus  from Role role where role.code = ?";
    	return getHibernateTemplate().find(hql,new Object[]{roleCode});
	}
	
	public List listMyMenu(String userId){
		String hql = "select userMenu.menu from UserMenu userMenu where userMenu.user.id=?";
    	return getHibernateTemplate().find(hql,new Object[]{userId});
	}
	
	public void removeUserMenu(String userId){
		Session session = getSession();
		String hql = "delete from UserMenu um where um.user.id=?";
		Query query = session.createQuery(hql);
		query.setString(0, userId);
		query.executeUpdate();
		releaseSession(session);
	}
}
