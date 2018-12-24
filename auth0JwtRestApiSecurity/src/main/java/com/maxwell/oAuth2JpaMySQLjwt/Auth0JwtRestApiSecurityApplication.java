package com.maxwell.oAuth2JpaMySQLjwt;

import java.io.File;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.maxwell.oAuth2JpaMySQLjwt.controller.UserController;

@SpringBootApplication
@ComponentScan({"com.maxwell.oAuth2JpaMySQLjwt","controller"}) //this is so that the controller package is scanned coz we need to use it
public class Auth0JwtRestApiSecurityApplication {

	//for generating the bean that is injected in the user controller
	@Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
	 
	public static void main(String[] args) {
		//creating the folder for files to be uploadable
		new File(UserController.uploadDirectory).mkdir();
		SpringApplication.run(Auth0JwtRestApiSecurityApplication.class, args);
	}
}
