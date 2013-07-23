package com.shac.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "sc_role")
public class Role implements java.io.Serializable,GrantedAuthority{
	
	
	private String id;
    private String code;
	private Set<Menu> roleMenus;
    private Set<User> users;
    private String name;
    private Date createTime;
    
    
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
	
	@Column(name="code",length=20)
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	@Column(name="name",length=50)
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "createTime", nullable = false, length = 19)
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "sc_menurole",
            joinColumns = { @JoinColumn(name = "roleid") },
            inverseJoinColumns = @JoinColumn(name = "menuid")
    )
	public Set<Menu> getRoleMenus() {
		return roleMenus;
	}
	public void setRoleMenus(Set roleMenus) {
		this.roleMenus = roleMenus;
	}
	
	@ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "sc_roleuser",
            joinColumns = { @JoinColumn(name = "roleid") },
            inverseJoinColumns = @JoinColumn(name = "userid")
    )
	public Set<User> getUsers() {
		return users;
	}
	public void setUsers(Set users) {
		this.users = users;
	}
	
	@Transient
	public String getAuthority() {
		
		return code;
	}
    
}
