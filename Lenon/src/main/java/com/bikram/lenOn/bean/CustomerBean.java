package com.bikram.lenOn.bean;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "customer_table_t")
public class CustomerBean {
	@Id
	@GeneratedValue
	private long id;
	private String name;
	private String mobile;
	private double amount;
	private String color="#04AABC";
	private Date lastupdated=new Date();
	@ManyToOne(cascade = CascadeType.ALL)
	private UserBean lender;
	
	
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public Date getLastupdated() {
		return lastupdated;
	}
	public void setLastupdated(Date lastupdated) {
		this.lastupdated = lastupdated;
	}
	public UserBean getLender() {
		return lender;
	}
	public void setLender(UserBean lender) {
		this.lender = lender;
	}
	
}
