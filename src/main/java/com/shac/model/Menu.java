package com.shac.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "sc_menu")
public class Menu implements Serializable{

    private String id;
    private Integer seq;
    private String title;
    private String tip;
    private String image;
    private String forward;
    private String target;
    private Date createTime;
    private Menu parentMenu;
    private Set<Menu> childMenu = new HashSet(0);
    
    @Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")   
	@Column(name = "id", nullable = false, length = 32)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    @Column(name = "image", length = 100)
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    
    @Column(name = "forward", length = 100)
    public String getForward() {
        return forward;
    }

    public void setForward(String forward) {
        this.forward = forward;
    }
    
    @Column(name = "target", length = 100)
    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }
    
    @Column(name = "tip", length = 50)
    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    @Column(name = "title", length = 50)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    @Column(name = "seq")
    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }
    
    @Column(name = "createTime", nullable = false, length = 19)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "parent_id")
	public Menu getParentMenu() {
        return parentMenu;
    }
	
    public void setParentMenu(Menu parentMenu) {
        this.parentMenu = parentMenu;
    }
	
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "parentMenu")
	public Set<Menu> getChildMenu() {
	    return childMenu;
	}

    public void setChildMenu(Set childMenu) {
        this.childMenu = childMenu;
    }
}