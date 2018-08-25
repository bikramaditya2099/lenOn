package com.bikram.lenOn.controller;




import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bikram.lenOn.bean.CustomerBean;
import com.bikram.lenOn.bean.LoginBean;
import com.bikram.lenOn.bean.TransactionRequestBean;
import com.bikram.lenOn.bean.UserBean;
import com.bikram.lenOn.dao.DAO;
import com.bikram.lenOn.dao.DAOImpl;
import com.bikram.lenOn.exception.LenOnException;

@Controller
public class LoginController {
	private final Logger LOGGER = Logger.getLogger(LoginController.class);
	@RequestMapping(value="/login",method=RequestMethod.POST,produces = "application/json")
	@ResponseBody
	public Object login(@RequestBody LoginBean bean)
	{
		LOGGER.info("Login Process initiated");
		DAO dao=new DAOImpl();
		try {
			return dao.Login(bean);
		} catch (LenOnException e) {
			LOGGER.error("Login Failed due to invalid credentials ");
			return e.toString();
		}
	}
	
	@RequestMapping(value="/addCustomer",method=RequestMethod.POST,produces = "application/json")
	@ResponseBody
	public Object addCustomer(@RequestBody CustomerBean bean,@RequestHeader(value="token") String token)
	{
		LOGGER.info("Adding Customer Process initiated");
		DAO dao=new DAOImpl();
		try {
			return dao.addCustomer(bean, token);
		} catch (LenOnException e) {
			LOGGER.error(e);
			return e.toString();
		}
	}
	
	@RequestMapping(value="/addDebit",method=RequestMethod.POST,produces = "application/json")
	@ResponseBody
	public Object addDebit(@RequestBody TransactionRequestBean bean,@RequestHeader(value="token") String token)
	{
		LOGGER.info("Adding Debit Process initiated");
		DAO dao=new DAOImpl();
		try {
			CustomerBean custBean=dao.getCustomerById(bean.getMobile());
			return dao.addDebit(custBean, bean.getAmt(), token);
		} catch (LenOnException e) {
			LOGGER.error(e);
			return e.toString();
		}
	}
	
	@RequestMapping(value="/addCredit",method=RequestMethod.POST,produces = "application/json")
	@ResponseBody
	public Object addCredit(@RequestBody TransactionRequestBean bean,@RequestHeader(value="token") String token)
	{
		LOGGER.info("Adding Credit Process initiated");
		DAO dao=new DAOImpl();
		try {
			CustomerBean custBean=dao.getCustomerById(bean.getMobile());
			return dao.addCredit(custBean, bean.getAmt(), token);
		} catch (LenOnException e) {
			LOGGER.error(e);
			return e.toString();
		}
	}
	
	@RequestMapping(value="/getAllCustomers",method=RequestMethod.GET,produces = "application/json")
	@ResponseBody
	public Object getAllCustomer(@RequestHeader(value="token") String token)
	{
		LOGGER.info("Loading All Customers");
		DAO dao=new DAOImpl();
		try {
			return dao.getAllCustomer(token);
		} catch (LenOnException e) {
			LOGGER.error(e);
			return e.toString();
		}
	}
	
	@RequestMapping(value="/getCustomerHistory/{id}",method=RequestMethod.GET,produces = "application/json")
	@ResponseBody
	public Object getCustomerHistory(@PathVariable("id") String mobile,@RequestHeader(value="token") String token)
	{
		LOGGER.info("Loading Customer History");
		DAO dao=new DAOImpl();
		try {
			return dao.getCustomerHistory(mobile, token);
		} catch (LenOnException e) {
			LOGGER.error(e);
			return e.toString();
		}
	}
	
	@RequestMapping(value="/signUp",method=RequestMethod.POST,produces = "application/json")
	@ResponseBody
	public Object signUp(@RequestBody UserBean bean)
	{
		LOGGER.info("SignUp Process Initiated");
		DAO dao=new DAOImpl();
		try {
			return dao.createLogin(bean);
		} catch (LenOnException e) {
			LOGGER.error(e);
			return e.toString();
		}
	}
}
