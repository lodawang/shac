package com.shac.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="sc_message")
public class Message
  implements Serializable
{
  private String id;
  private IssueTask task;
  private DictData dept;
  private String dotype;
  private Date createTime;
  private String taskStatus;

  @Id
  @GeneratedValue(generator="uuid")
  @GenericGenerator(name="uuid", strategy="uuid")
  @Column(name="id", nullable=false, length=32)
  public String getId()
  {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }
  @ManyToOne(fetch=FetchType.LAZY)
  @Fetch(FetchMode.JOIN)
  @JoinColumn(name="tskid")
  public IssueTask getTask() { return this.task; }

  public void setTask(IssueTask task)
  {
    this.task = task;
  }
  @ManyToOne(fetch=FetchType.LAZY)
  @Fetch(FetchMode.JOIN)
  @JoinColumn(name="cityid")
  public DictData getDept() { return this.dept; }

  public void setDept(DictData dept) {
    this.dept = dept;
  }

  public Date getCreateTime() {
    return this.createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public String getDotype() {
    return this.dotype;
  }

  public void setDotype(String dotype) {
    this.dotype = dotype;
  }

  public String getTaskStatus() {
    return this.taskStatus;
  }

  public void setTaskStatus(String taskStatus) {
    this.taskStatus = taskStatus;
  }
}