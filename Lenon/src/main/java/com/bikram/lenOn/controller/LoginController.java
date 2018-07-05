package com.bikram.lenOn.controller;




import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {
	private final Logger LOGGER = Logger.getLogger(LoginController.class);
	@RequestMapping("/login")
	@ResponseBody
	public String login()
	{
		System.out.println("COntroller executed");
		LOGGER.info("Entered Login Controller");
		return "Login Success";
	}
}
