package com.bikram.lenOn.dao;

import java.util.List;

import com.bikram.lenOn.bean.CustomerBean;
import com.bikram.lenOn.bean.CustomerHistoryResponse;
import com.bikram.lenOn.bean.CustomerResponse;
import com.bikram.lenOn.bean.LoginBean;
import com.bikram.lenOn.bean.UserBean;
import com.bikram.lenOn.exception.LenOnException;

public interface DAO {
void createLogin(UserBean bean) throws LenOnException;
Object Login(LoginBean bean) throws LenOnException;
void validateUser(String token) throws LenOnException;
Object addCustomer(CustomerBean bean,String token) throws LenOnException;
Object addDebit(CustomerBean bean,double amt,String token) throws LenOnException;
Object addCredit(CustomerBean bean,double amt,String token) throws LenOnException;
CustomerBean getCustomerById(String mobile) throws LenOnException;
UserBean getUserByToken(String token) throws LenOnException;
List<CustomerResponse> getAllCustomer(String token) throws LenOnException;
CustomerHistoryResponse getCustomerHistory(String mobile,String token) throws LenOnException;
}
