package com.bikram.lenOn.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.bikram.lenOn.bean.LoginBean;
import com.bikram.lenOn.bean.UserBean;
import com.bikram.lenOn.exception.ErrorHandlerEnum;
import com.bikram.lenOn.exception.LenOnException;
import com.bikram.lenOn.util.HibernateUtil;

public class DAOImpl implements DAO{

	private final Logger LOGGER = Logger.getLogger(DAOImpl.class);
	private static final String PASSWORD="password";
	private static final String MOBILE="mobile";
	
	@Override
	public void createLogin(UserBean bean) throws LenOnException {
		Session session=HibernateUtil.getNewSession();
		Criteria criteria=session.createCriteria(UserBean.class);
		criteria.add(Restrictions.and(Restrictions.eq(MOBILE, bean.getMobile()), Restrictions.eq(PASSWORD, bean.getPassword())));
		List<UserBean> list=criteria.list();
		LOGGER.info("creating User : "+bean.getfName());
		if(list.size()>0)
			throw new LenOnException(ErrorHandlerEnum.USER_ALREADY_EXIST);
		else{
			criteria.add(Restrictions.eq(MOBILE, bean.getMobile()));
			List<UserBean> list1=criteria.list();
			if(list1.size()>0)
				throw new LenOnException(ErrorHandlerEnum.USER_ALREADY_EXIST);
			else{
				Transaction tx=session.beginTransaction();
				session.save(bean);
				tx.commit();
				session.close();
			}
		}
		
	}

	@Override
	public String Login(LoginBean bean) throws LenOnException{
		// TODO Auto-generated method stub
		return null;
	}

}
