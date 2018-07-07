package com.bikram.lenOn.bean;

import java.util.Date;

public class CustomerHistory {

	private double credit;
	private double debit;
	private Date date;
	public double getCredit() {
		return credit;
	}
	public double getDebit() {
		return debit;
	}
	public Date getDate() {
		return date;
	}
	public void setCredit(double credit) {
		this.credit = credit;
	}
	public void setDebit(double debit) {
		this.debit = debit;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
}
