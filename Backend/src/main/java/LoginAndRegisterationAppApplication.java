package com.bot.loginAndRegisterationApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.bot.loginAndRegisterationApp")
public class LoginAndRegisterationAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoginAndRegisterationAppApplication.class, args);
	}

}
