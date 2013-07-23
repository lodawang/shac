package com.shac.model;

import java.util.Collection;
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
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.struts2.json.annotations.JSON;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "sc_user")
public class User implements java.io.Serializable,UserDetails{
	
	
	private String id;
    private String name;
    private String sex;
    
    private String loginID;
    private String passWord;
    
    private String officePhone;
    private String mobile;
    private String email;
    private String priemail;
    
    //厂部
    private DictData region;
    //车间科室
    private DictData workshop;
    //
    private String officeTitle;
    //启用标记
    private String status;
    //读写删标记
    private String rwd;
    //
    private String utype;
    //
    private String cpType;
    //
    private Integer budget;
    //是否工艺工程师
    private String is_engineer;
    
    //是否可见发放单位
    private String issuesee;
 

	//email密码
    private String emailPwd;
    private Date createTime;
    //角色
    private Set<Role> roles;
    
    private Set<GrantedAuthority> authorities;
    
    @Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")   
	@Column(name = "id", nullable = false, length = 32)
	public String getId() {
		return id;
	}
    
    @Column(name = "name", length = 20)
	public String getName() {
		return name;
	}
    
    @Column(name = "sex", length = 2)
	public String getSex() {
		return sex;
	}
    
    @Column(name = "loginid", unique=true, length = 30)
	public String getLoginID() {
		return loginID;
	}
    
    @Column(name = "password", length = 32)
	public String getPassWord() {
		return passWord;
	}
    
    @Column(name = "officePhone", length = 20)
	public String getOfficePhone() {
		return officePhone;
	}
    
    @Column(name = "mobile", length = 20)
	public String getMobile() {
		return mobile;
	}
    
    @Column(name = "email",unique=true, length = 30)
	public String getEmail() {
		return email;
	}
    
    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
	@JoinColumn(name = "region")
	public DictData getRegion() {
		return region;
	}
	
	@Column(name = "officeTitle", length = 20)
	public String getOfficeTitle() {
		return officeTitle;
	}
	
	@Column(name = "u_status", length = 1)
	public String getStatus() {
		return status;
	}
	
	@Column(name = "createTime", nullable = false, length = 19)
	public Date getCreateTime() {
		return createTime;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public void setLoginID(String loginID) {
		this.loginID = loginID;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public void setOfficePhone(String officePhone) {
		this.officePhone = officePhone;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setRegion(DictData region) {
		this.region = region;
	}

	public void setOfficeTitle(String officeTitle) {
		this.officeTitle = officeTitle;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "sc_roleuser",
            joinColumns = { @JoinColumn(name = "userid") },
            inverseJoinColumns = @JoinColumn(name = "roleid")
    )
    @JSON(serialize=false)
	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	@Column(name = "rwd", length = 10)
	public String getRwd() {
		return rwd;
	}

	public void setRwd(String rwd) {
		this.rwd = rwd;
	}
	
	@Column(name = "utype", length = 2)
	public String getUtype() {
		return utype;
	}

	public void setUtype(String utype) {
		this.utype = utype;
	}
	
	@Column(name = "cpType", length = 2)
	public String getCpType() {
		return cpType;
	}

	public void setCpType(String cpType) {
		this.cpType = cpType;
	}
	
	@Column(name = "budget")
	public Integer getBudget() {
		return budget;
	}

	public void setBudget(Integer budget) {
		this.budget = budget;
	}
	
	@Column(name = "emailPwd", length = 16)
	public String getEmailPwd() {
		return emailPwd;
	}

	public void setEmailPwd(String emailPwd) {
		this.emailPwd = emailPwd;
	}
	
	@Column(name = "priemail", length = 30)
	public String getPriemail() {
		return priemail;
	}

	public void setPriemail(String priemail) {
		this.priemail = priemail;
	}
	
	public void setAuthorities(Set<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	@Transient
	public Collection<GrantedAuthority> getAuthorities() {
        return authorities;
	}
	@Transient
	public String getPassword() {
		return getPassWord();
	}
	@Transient
	public String getUsername() {
		return getLoginID();
	}
	@Transient
	public boolean isAccountNonExpired() {
		return true;
	}
	@Transient
	public boolean isAccountNonLocked() {
		return true;
	}
	@Transient
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@Transient
	public boolean isEnabled() {
		return status.equals("1");
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
	@JoinColumn(name = "workshop")
	public DictData getWorkshop() {
		return workshop;
	}

	public void setWorkshop(DictData workshop) {
		this.workshop = workshop;
	}
	
	@Column(name = "is_engineer", length = 1)
    public String getIs_engineer() {
		return is_engineer;
	}

	public void setIs_engineer(String is_engineer) {
		this.is_engineer = is_engineer;
	}
     
	@Column(name = "issuesee", length = 1)
	public String getIssuesee() {
		return issuesee;
	}

	public void setIssuesee(String issuesee) {
		this.issuesee = issuesee;
	}
    
}
