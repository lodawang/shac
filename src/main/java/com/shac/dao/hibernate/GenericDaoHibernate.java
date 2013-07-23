package com.shac.dao.hibernate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.shac.dao.GenericDao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

/**
 * 	GenericDaoHibernate
 */
public abstract class GenericDaoHibernate<T, PK extends Serializable> extends HibernateDaoSupport implements GenericDao<T, PK> {
    /**
     * Log variable for all child classes. Uses LogFactory.getLog(getClass()) from Commons Logging
     */
    protected final Log log = LogFactory.getLog(getClass());
    private Class<T> persistentClass;

    /**
     * Constructor that takes in a class to see which type of entity to persist.
     * Use this constructor when subclassing.
     *
     * @param persistentClass the class type you'd like to persist
     */
    public GenericDaoHibernate(final Class<T> persistentClass) {
        this.persistentClass = persistentClass;
    }
    
    @Autowired
    @Required
    public void setSuperSessionFactory(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<T> getAll() {
        return getHibernateTemplate().loadAll(this.persistentClass);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<T> getAllDistinct() {
        Collection result = new LinkedHashSet(getAll());
        return new ArrayList(result);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public T get(PK id) {
        T entity = (T) getHibernateTemplate().get(this.persistentClass, id);

        if (entity == null) {
            log.warn("Uh oh, '" + this.persistentClass + "' object with id '" + id + "' not found...");
            throw new ObjectRetrievalFailureException(this.persistentClass, id);
        }

        return entity;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public boolean exists(PK id) {
        T entity = (T) getHibernateTemplate().get(this.persistentClass, id);
        return entity != null;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public T save(T object) {
        return (T) getHibernateTemplate().merge(object);
    }

    /**
     * {@inheritDoc}
     */
    public void remove(PK id) {
    	getHibernateTemplate().delete(this.get(id));
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<T> findByNamedQuery(String queryName, Map<String, Object> queryParams) {
        String[] params = new String[queryParams.size()];
        Object[] values = new Object[queryParams.size()];
        
        int index = 0;
        for (String s : queryParams.keySet()) {
            params[index] = s;
            values[index++] = queryParams.get(s);
        }

        return getHibernateTemplate().findByNamedQueryAndNamedParam(queryName, params, values);
    }
    
    //extend
    
    abstract public DetachedCriteria getCriteria(Map filterMap);
    
    public void orderCriteria(DetachedCriteria criteria, Map orderMap) {
		if(orderMap!=null && !orderMap.isEmpty()){
			Iterator iter = orderMap.keySet().iterator();
			while(iter.hasNext()){
				String fieldName = (String)iter.next();
				String orderType = (String)orderMap.get(fieldName);
				//支持一层嵌套
				if ("asc".equalsIgnoreCase(orderType)) {
                    criteria.addOrder(Order.asc(fieldName));
                } else {
                    criteria.addOrder(Order.desc(fieldName));
                }
			}
		}
	}
    
    protected boolean isNotEmpty(Object obj){
        if(obj == null)
            return false;
        return !"".equals(obj);
    }
    
    
    public Long getCount(Map filterMap){
    	Session session = getSession();
    	DetachedCriteria detachedCriteria = getCriteria(filterMap);
    	Criteria criteria = detachedCriteria.getExecutableCriteria(session);
    	Long count = (Long)criteria.setProjection(Projections.rowCount()).uniqueResult();
    	releaseSession(session);
    	return count;
    }
    
    public List findByAll(Map filterMap,Map orderMap){
    	DetachedCriteria criteria = getCriteria(filterMap);
    	orderCriteria(criteria, orderMap);
    	return getHibernateTemplate().findByCriteria(criteria);
    }
    
    public List findBy(Map filterMap,Map orderMap,int firstResult, int maxResults){
    	DetachedCriteria criteria = getCriteria(filterMap);
    	orderCriteria(criteria, orderMap);
    	return getHibernateTemplate().findByCriteria(criteria, firstResult, maxResults);
    }
}
