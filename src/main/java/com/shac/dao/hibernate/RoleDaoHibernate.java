package com.shac.dao.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.shac.model.Role;

@Repository("roleDao")
public class RoleDaoHibernate extends GenericDaoHibernate<Role, String>{
	
	public RoleDaoHibernate() {
		super(Role.class);
	}

	@Override
	public DetachedCriteria getCriteria(Map filterMap) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Role.class);
		if(isNotEmpty(filterMap.get("code"))){
			detachedCriteria.add(Restrictions.eq("code", (String)filterMap.get("code")));
		}
		return detachedCriteria;
	}
	
	
	public Role getRoleByCode(String roleCode){
		Role role = null;
		Map filter = new HashMap();
		filter.put("code", roleCode);
		List list = findByAll(filter, null);
		if(list!=null && list.size()>0){
			role = (Role)list.get(0);
		}
		return role;
	}

}
