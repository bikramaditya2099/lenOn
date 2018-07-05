package com.bikram.lenOn.util;

import org.apache.log4j.Logger;

import com.bikram.lenOn.bean.UserBean;
import com.bikram.lenOn.dao.DAO;
import com.bikram.lenOn.dao.DAOImpl;
import com.bikram.lenOn.exception.LenOnException;

public class CreateDefault {
	private static final Logger LOGGER = Logger.getLogger(CreateDefault.class);
	public static void createDefaultUser()
	{
		UserBean bean=new UserBean();
		bean.setfName("test");
		bean.setlName("user");
		bean.setMobile("9583214762");
		bean.setPassword("test123");
		DAO dao=new DAOImpl();
		try {
			dao.createLogin(bean);
		} catch (LenOnException e) {
			LOGGER.error(e);
		}
	}
}
