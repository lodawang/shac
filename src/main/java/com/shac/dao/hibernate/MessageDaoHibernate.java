package com.shac.dao.hibernate;

import com.shac.model.DictData;
import com.shac.model.Message;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

@Repository("messageDao")
public class MessageDaoHibernate extends GenericDaoHibernate<Message, String>
{
  public MessageDaoHibernate()
  {
    super(Message.class);
  }

  public void updateMessage(Message message)
  {
    getHibernateTemplate().update(message);
  }

  public DetachedCriteria getCriteria(Map filterMap)
  {
    DetachedCriteria detachedCriteria = 
      DetachedCriteria.forClass(Message.class);
    detachedCriteria.createAlias("task", "task");
    if (isNotEmpty(filterMap.get("querypartid"))) {
      detachedCriteria.add(Restrictions.ilike("task.partid", (String)filterMap.get("querypartid"), MatchMode.ANYWHERE));
    }

    if (isNotEmpty(filterMap.get("dept"))) {
      DictData dept = new DictData();
      dept.setId((String)filterMap.get("dept"));
      detachedCriteria.add(Restrictions.eq("dept", dept));
    }
    if (isNotEmpty(filterMap.get("tskid")))
    {
      detachedCriteria.add(Restrictions.eq("task.id", (String)filterMap.get("tskid")));
    }

    if ((isNotEmpty(filterMap.get("begindate"))) && 
      (isNotEmpty(filterMap.get("enddate")))) {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      Date s_date = null;
      Date e_date = null;
      try {
        s_date = sdf.parse((String)filterMap.get("begindate"));
        e_date = sdf.parse((String)filterMap.get("enddate"));
      }
      catch (ParseException e) {
        e.printStackTrace();
      }
      detachedCriteria.add(Restrictions.ge("createTime", s_date));
      detachedCriteria.add(Restrictions.le("createTime", e_date));
    }

    return detachedCriteria;
  }
}