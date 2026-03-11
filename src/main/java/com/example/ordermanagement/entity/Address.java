package com.example.ordermanagement.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


@Entity
@Table(name="address")
public class Address {
    @Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

    @OneToMany(fetch=FetchType.EAGER,cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
			name="users",
			joinColumns= {@JoinColumn(name="id",referencedColumnName="ID")},
			inverseJoinColumns= {@JoinColumn(name="ROLE_ID",referencedColumnName="ID")})
	private List<User> user_id=new ArrayList<>();
    
    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String city;

    @Column(nullable = true)
    private String postalCode;
    
}