package com.example.ordermanagement.entity;

import java.util.ArrayList;
import java.util.List;

import com.example.ordermanagement.order.model.Cart;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="users")
public class User {
	private static final long serialVersionUID=1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable=false)
	private String name;
	@Column(nullable=false,unique=true)
	private String email;
	
	@Column(nullable=false)
	private String password;
	
	@ManyToMany(fetch=FetchType.EAGER,cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(
			name="users_roles",
			joinColumns= {@JoinColumn(name="USER_ID",referencedColumnName="ID")},
			inverseJoinColumns= {@JoinColumn(name="ROLE_ID",referencedColumnName="ID")})
	private List<Role> roles=new ArrayList<>();
	
	 @Transient
private List<Cart> cart;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}	
	
	public List<Cart> getCart() {
	return cart;
}

public void setCart(List<Cart> cart) {
	this.cart = cart;
}
	
}
