package com.shac.dao.hibernate;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.shac.model.DictData;
import com.shac.model.Role;
import com.shac.model.User;
import com.shac.util.Constants;

@Repository("userDao")
public class UserDaoHibernate extends GenericDaoHibernate<User, String>{

	public UserDaoHibernate() {
		super(User.class);
	}

	@Override
	public DetachedCriteria getCriteria(Map filterMap) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(User.class);
		if(isNotEmpty(filterMap.get("utype"))){
			detachedCriteria.add(Restrictions.eq("utype", (String)filterMap.get("utype")));
		}
		if(isNotEmpty(filterMap.get("status"))){
			detachedCriteria.add(Restrictions.eq("status", (String)filterMap.get("status")));
		}
		if(isNotEmpty(filterMap.get("qstatus"))){
			detachedCriteria.add(Restrictions.eq("status", (String)filterMap.get("qstatus")));
		}
		if(isNotEmpty(filterMap.get("name"))){
			detachedCriteria.add(Restrictions.like("name", (String)filterMap.get("name"),MatchMode.ANYWHERE));
		}
		if(isNotEmpty(filterMap.get("loginID"))){
			detachedCriteria.add(Restrictions.like("loginID", (String)filterMap.get("loginID"),MatchMode.ANYWHERE));
		}
		if(isNotEmpty(filterMap.get("qname"))){
			detachedCriteria.add(Restrictions.disjunction().add(Restrictions.like("loginID", (String)filterMap.get("qname"),MatchMode.ANYWHERE)).add(Restrictions.like("name", (String)filterMap.get("qname"),MatchMode.ANYWHERE)));;
		}
		if(isNotEmpty(filterMap.get("qregionid"))){
			String regionId = (String)filterMap.get("qregionid");
			DictData region = new DictData();
			region.setId(regionId);
			detachedCriteria.add(Restrictions.eq("region", region));
		}
		return detachedCriteria;
	}
	
	public User findByLoginID(String loginID){
		String queryString = "from User user where user.loginID=?";
		List list = getHibernateTemplate().find(queryString,new Object[]{loginID});
		if(list!=null && list.size()==1){
			return (User)list.get(0);
		}else{
			return null;
		}
	}
	
	/**
	 * rwd = '1' ：厂部管理员
	 * is_engineer is null or is_engineer ='0' : 非工程师角色
	 * email 非空
	 * 以上条件避免取到DR*的用户作为总部通知签收的接收者
	 * @param deptId
	 * @return
	 */
	public User findWriteUserByDept(String deptId){
		String queryString = "from User user where user.region.id=? and user.rwd='1' and (user.is_engineer is null or user.is_engineer='0') and user.email is not null";
		List list = getHibernateTemplate().find(queryString,new Object[]{deptId});
		if(list!=null && list.size()>0){
			return (User)list.get(0);
		}else{
			return null;
		}
	}
	
	public String saveUser(User user){
		return (String)getHibernateTemplate().save(user);
	}
	
	public void updateUser(User user){
		getHibernateTemplate().update(user);
	}
	
	public void updateUserStatus(String status,String id){
		Session session = getSession();
		String hql = "update User set status=? where id = ?";
		Query query = session.createQuery(hql);
		query.setString(0, status);
		query.setString(1, id);
		query.executeUpdate();
		releaseSession(session);
	}
	
	public void updateUserPassword(String password,String id){
		Session session = getSession();
		String hql = "update User set passWord=? where id = ?";
		Query query = session.createQuery(hql);
		query.setString(0, password);
		query.setString(1, id);
		query.executeUpdate();
		releaseSession(session);
	}
	
	public List<Role> findRolesByUid(String uid){
		String queryString = "select user.roles from User user where user.id=?";
		List list = getHibernateTemplate().find(queryString,new Object[]{uid});
		return list;
	}
	
	/**
	 * 查找厂部的厂部管理员
	 * @param deptId
	 * @return
	 */
	public User findFactoryAdminUser(String deptId){
		String hql = "select user from User user inner join user.roles role where user.region.id=? and role.code=?";
		List list = getHibernateTemplate().find(hql,new Object[]{deptId,Constants.ROLE_RECVADMIN});
		User ret = null;
		if(list!=null && list.size()>0){
			ret = (User)list.get(0);
		}
		return ret;
	}
	
	/**
	 * 
	 * @param deptId
	 * @return
	 */
	public User findWorkshopAdminUser(String workshopId){
		String hql = "select user from User user inner join user.roles role where user.workshop.id=? and role.code=?";
		List list = getHibernateTemplate().find(hql,new Object[]{workshopId,Constants.ROLE_RECVADMIN_WORKSHOP});
		User ret = null;
		if(list!=null && list.size()>0){
			ret = (User)list.get(0);
		}
		return ret;
	}
	
	

}
