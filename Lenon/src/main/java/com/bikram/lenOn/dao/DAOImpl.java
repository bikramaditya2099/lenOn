package com.bikram.lenOn.dao;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.bikram.lenOn.bean.CustomerBean;
import com.bikram.lenOn.bean.CustomerHistory;
import com.bikram.lenOn.bean.CustomerHistoryResponse;
import com.bikram.lenOn.bean.CustomerResponse;
import com.bikram.lenOn.bean.CustomerTransaction;
import com.bikram.lenOn.bean.LoginBean;
import com.bikram.lenOn.bean.UserBean;
import com.bikram.lenOn.exception.ErrorHandlerEnum;
import com.bikram.lenOn.exception.LenOnException;
import com.bikram.lenOn.util.HibernateUtil;
import com.bikram.lenOn.util.Response;

public class DAOImpl implements DAO{

	private final Logger LOGGER = Logger.getLogger(DAOImpl.class);
	private static final String PASSWORD="password";
	private static final String MOBILE="mobile";
	private static final String COLOR="color";
	private static final String AMOUNT="amount";
	private static final String CUSTOMER_NAME="name";
	private static final String CUSTOMER="customer";
	private static final String CUSTOMER_LENDER="customer.lender";
	private static final String LENDER="lender";
	private static final String LENDER_ID="lender.id";
	@Override
	public Object createLogin(UserBean bean) throws LenOnException {
		Session session=HibernateUtil.getNewSession();
		Criteria criteria=session.createCriteria(UserBean.class);
		criteria.add(Restrictions.and(Restrictions.eq(MOBILE, bean.getMobile()), Restrictions.eq(PASSWORD, bean.getPassword())));
		List<UserBean> list=criteria.list();
		LOGGER.info("creating User : "+bean.getfName());
		if(list.size()>0){
			session.close();
			throw new LenOnException(ErrorHandlerEnum.USER_ALREADY_EXIST);
		}
		else{
			criteria.add(Restrictions.eq(MOBILE, bean.getMobile()));
			List<UserBean> list1=criteria.list();
			if(list1.size()>0){
				session.close();
				throw new LenOnException(ErrorHandlerEnum.USER_ALREADY_EXIST);
			}
			else{
				Transaction tx=session.beginTransaction();
				session.save(bean);
				tx.commit();
				session.close();
				return new Response(ErrorHandlerEnum.SIGNUP_SUCCESS, bean.getMobile());
			}
		}
		
	}

	@Override
	public Object Login(LoginBean bean) throws LenOnException{
		LOGGER.info("Logging in with user : "+bean.getUserName());
		Session session=HibernateUtil.getNewSession();
		Criteria criteria=session.createCriteria(UserBean.class);
		criteria.add(Restrictions.and(Restrictions.eq(MOBILE, bean.getUserName()), Restrictions.eq(PASSWORD, bean.getPassword())));
		List<UserBean> list=criteria.list();
		if(list.size()>0){
			session.close();
			return new Response(ErrorHandlerEnum.LOGIN_SUCCESS, Base64.getEncoder().encodeToString(String.valueOf(bean.getUserName()+":"+bean.getPassword()).getBytes()));
		}
		else{
			session.close();
			throw new LenOnException(ErrorHandlerEnum.LOGIN_FAILED);
		}
	}

	@Override
	public Object addCustomer(CustomerBean bean,String token) throws LenOnException {
		validateUser(token);
		LOGGER.info("Adding Customer : "+bean.getName());
		try {
			Session session=HibernateUtil.getNewSession();
			Criteria criteria=session.createCriteria(CustomerBean.class);
			criteria.add(Restrictions.eq(MOBILE, bean.getMobile()));
			List<CustomerBean> list=criteria.list();
			Transaction tx=session.beginTransaction();
			if(list.size()>0){
				throw new LenOnException(ErrorHandlerEnum.CUSTOMER_ALREADY_EXISTS);
			}
			else{
				UserBean userBean=getUserByToken(token);
				bean.setLender(userBean);
				double amt=bean.getAmount();
				bean.setAmount(0);			
				addCredit(bean, amt, token);
			}
			tx.commit();
			session.close();
			return new Response(ErrorHandlerEnum.CUSTOMER_SUCCESFULLY_ADDED, "");
		} catch (HibernateException e) {
			LOGGER.error(e);
			throw new LenOnException(ErrorHandlerEnum.ERROR);
		}
	}

	@Override
	public void validateUser(String token) throws LenOnException{
		LOGGER.info("Validating user");
		Session session=HibernateUtil.getNewSession();
		String actualToken=new String(Base64.getDecoder().decode(token.getBytes()));
		String userName=actualToken.split(":")[0];
		String password=actualToken.split(":")[1];
		Criteria criteria=session.createCriteria(UserBean.class);
		criteria.add(Restrictions.and(Restrictions.eq(MOBILE, userName), Restrictions.eq(PASSWORD,password)));
		List<UserBean> list=criteria.list();
		if(list.size()<=0){
			session.close();
			throw new LenOnException(ErrorHandlerEnum.INVALID_USER);
		}
		
		session.close();
	}

	@Override
	public Object addDebit(CustomerBean bean, double amt,String token) throws LenOnException {
		validateUser(token);
		LOGGER.info("Adding debit amount :");
		Session session=HibernateUtil.getNewSession();
		CustomerTransaction transaction=new CustomerTransaction();
		transaction.setDebit(amt);
		transaction.setCredit(0);
		if(bean.getAmount()<amt)
			throw new LenOnException(ErrorHandlerEnum.DEBIT_FAILURE);
		bean.setAmount(bean.getAmount()-amt);
		bean.setLastupdated(new Date());
		transaction.setCustomer(bean);
		Transaction tx=session.beginTransaction();
		session.save(transaction);
		tx.commit();
		session.close();
		return new Response(ErrorHandlerEnum.SUCCESSFULLY_ADDED_DEBIT, "");
		
	}

	@Override
	public Object addCredit(CustomerBean bean, double amt,String token) throws LenOnException {
		validateUser(token);
		LOGGER.info("Adding Credit amount :");
		Session session=HibernateUtil.getNewSession();
		CustomerTransaction transaction=new CustomerTransaction();
		transaction.setDebit(0);
		transaction.setCredit(amt);
		bean.setAmount(bean.getAmount()+amt);
		transaction.setCustomer(bean);
		Transaction tx=session.beginTransaction();
		session.save(transaction);
		tx.commit();
		session.close();
		return new Response(ErrorHandlerEnum.SUCCESSFULLY_ADDED_CREDIT, "");
	}

	@Override
	public CustomerBean getCustomerById(String mobile) throws LenOnException {
		Session session=HibernateUtil.getNewSession();
		Criteria criteria=session.createCriteria(CustomerBean.class);
		criteria.add(Restrictions.eq(MOBILE, mobile));
		List<CustomerBean> list=criteria.list();
		if(list.size()>0){
			return list.get(0);
		}
		else{
			throw new LenOnException(ErrorHandlerEnum.NO_SUCH_USER);
		}
	}

	@Override
	public UserBean getUserByToken(String token) throws LenOnException {
		LOGGER.info("Getting User Information");
		Session session=HibernateUtil.getNewSession();
		String actualToken=new String(Base64.getDecoder().decode(token.getBytes()));
		String userName=actualToken.split(":")[0];
		String password=actualToken.split(":")[1];
		Criteria criteria=session.createCriteria(UserBean.class);
		criteria.add(Restrictions.and(Restrictions.eq(MOBILE, userName), Restrictions.eq(PASSWORD,password)));
		List<UserBean> list=criteria.list();
		if(list.size()<=0){
			session.close();
			throw new LenOnException(ErrorHandlerEnum.INVALID_USER);
		}
		else{
			session.close();
			return list.get(0);
		}
	}

	@Override
	public List<CustomerResponse> getAllCustomer(String token) throws LenOnException {
		List<CustomerResponse> customerResponses=new ArrayList<>();
		UserBean bean=getUserByToken(token);
		LOGGER.info("Getting User Information");
		Session session=HibernateUtil.getNewSession();
		Criteria criteria=session.createCriteria(CustomerBean.class,CUSTOMER);
		criteria.createAlias(CUSTOMER_LENDER, LENDER);
		criteria.add(Restrictions.eq(LENDER_ID, bean.getId()));
		ProjectionList projectionList=Projections.projectionList(); 
		Projection projection1 = Projections.property(MOBILE);
		Projection projection2 = Projections.property(COLOR);
		Projection projection3 = Projections.property(AMOUNT);
		Projection projection4 = Projections.property(CUSTOMER_NAME);
		projectionList.add(projection1);
		projectionList.add(projection2);
		projectionList.add(projection3);
		projectionList.add(projection4);
		criteria.setProjection(projectionList);
		List customerBeans=criteria.list();
		Iterator it=customerBeans.iterator();
		 while(it.hasNext())
		 {
		 Object ob[] = (Object[])it.next();
		CustomerResponse response=new CustomerResponse();
		response.setMobile((String) ob[0]);
		response.setColor((String) ob[1]);
		response.setAmount((double) ob[2]);
		response.setName((String) ob[3]);
		customerResponses.add(response);
		 }
		
		return customerResponses;
	}

	@Override
	public CustomerHistoryResponse getCustomerHistory(String mobile, String token) throws LenOnException {
		validateUser(token);
		CustomerHistoryResponse customerHistoryResponse=new CustomerHistoryResponse();
		CustomerBean customerBean=getCustomerById(mobile);
		customerHistoryResponse.setAmount(customerBean.getAmount());
		customerHistoryResponse.setColor(customerBean.getColor());
		customerHistoryResponse.setMobile(customerBean.getMobile());
		customerHistoryResponse.setName(customerBean.getName());
		Session session=HibernateUtil.getNewSession();
		Criteria criteria=session.createCriteria(CustomerTransaction.class,"history");
		criteria.createAlias("history.customer", "customer");
		criteria.add(Restrictions.eq("customer.id", customerBean.getId()));
		ProjectionList projectionList=Projections.projectionList();
		Projection projection1 = Projections.property("credit");
		Projection projection2 = Projections.property("debit");
		Projection projection3 = Projections.property("createdOn");
		projectionList.add(projection1);
		projectionList.add(projection2);
		projectionList.add(projection3);
		criteria.setProjection(projectionList);
		List customerHistoryData=criteria.list();
		Iterator it=customerHistoryData.iterator();
		List<CustomerHistory> customerHistories=new ArrayList<CustomerHistory>();
		 while(it.hasNext())
		 {
		 Object ob[] = (Object[])it.next();
		CustomerHistory customerHistory=new CustomerHistory();
		customerHistory.setCredit((double) ob[0]);
		customerHistory.setDebit((double) ob[1]);
		customerHistory.setDate((Date) ob[2]);
		customerHistories.add(customerHistory);
		 }
		 customerHistoryResponse.setHistory(customerHistories);
		 return customerHistoryResponse;
	}
	
	
	
	
	

}
