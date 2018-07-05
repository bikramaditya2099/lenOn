package com.bikram.lenOn.dao;

import com.bikram.lenOn.bean.LoginBean;
import com.bikram.lenOn.bean.UserBean;
import com.bikram.lenOn.exception.LenOnException;

public interface DAO {
void createLogin(UserBean bean) throws LenOnException;
String Login(LoginBean bean) throws LenOnException;
}
