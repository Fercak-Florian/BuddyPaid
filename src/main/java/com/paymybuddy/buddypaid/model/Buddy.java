package com.paymybuddy.buddypaid.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import lombok.Data;

import javax.persistence.JoinColumn;
@Data
@Entity
@Table(name = "buddy")
public class Buddy /*extends User*/{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "user_id")
	private int userId;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;
	
	/*@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(name = "user_buddy", joinColumns = @JoinColumn(name = "buddy_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
	private User user;*/
	
	
	/*@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
	@JoinColumn(name = "id", insertable = false, updatable = false)
	private User user;*/
}
