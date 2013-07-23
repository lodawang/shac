package com.shac.dao.hibernate;

import java.util.Date;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;

import com.shac.model.TCHeart;


@Repository("tcHeartDao")
public class TCHeartDaoHibernate extends GenericDaoHibernate<TCHeart, String>{
	public TCHeartDaoHibernate() {
		super(TCHeart.class);
	}
	@Override
	public DetachedCriteria getCriteria(Map filterMap) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 更新心跳表
	 * 
	 */
	public void updateTCHeart(){
		Session session = getSession();
		String hql = "update TCHeart set doTime=current_timestamp()";
		//String hql = "update TCHeart set doTime=?";
		Query query = session.createQuery(hql);	
		//query.setDate(0, new Date());
		
		query.executeUpdate();
		releaseSession(session);		
	}

}
