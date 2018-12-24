package com.maxwell.oAuth2JpaMySQLjwt.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="role_table")
public class Role {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	private String title;
	private String description; 
	
	@ManyToMany(fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	@JoinTable(
			name="role_permission",
			joinColumns=@JoinColumn(name="role_id",referencedColumnName="id"),
			inverseJoinColumns=@JoinColumn(name="permission_id",referencedColumnName="id")
			)
	private Set<Permission> permissions;
	
//	@ManyToMany(mappedBy="roles",fetch=FetchType.LAZY)
//	private Set<User> users;
//	
	
	public Role() {
		
	}
	
	
	public Role(String title, String description) {
		this.title = title;
		this.description = description;
	}


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}


	public Set<Permission> getPermissions() {
		return permissions;
	}


	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}


//	public Set<User> getUsers() {
//		return users;
//	}
//
//
//	public void setUsers(Set<User> users) {
//		this.users = users;
//	}
//	
	
}
