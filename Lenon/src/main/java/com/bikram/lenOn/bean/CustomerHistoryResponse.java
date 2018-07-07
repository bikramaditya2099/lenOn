package com.bikram.lenOn.bean;

import java.util.List;

public class CustomerHistoryResponse {

	private String mobile;
	private String name;
	private String color;
	private double amount;
	List<CustomerHistory> history;
	public String getMobile() {
		return mobile;
	}
	public String getName() {
		return name;
	}
	public String getColor() {
		return color;
	}

	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public List<CustomerHistory> getHistory() {
		return history;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public void setHistory(List<CustomerHistory> history) {
		this.history = history;
	}
	
}
