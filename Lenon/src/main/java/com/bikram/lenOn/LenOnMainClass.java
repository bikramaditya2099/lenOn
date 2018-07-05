package com.bikram.lenOn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

import com.bikram.lenOn.util.CreateDefault;
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class LenOnMainClass {

	  public static void main(String[] args) {
	        SpringApplication.run(LenOnMainClass.class, args);
	        CreateDefault.createDefaultUser();
	    }
}
