package com.bot.loginAndRegisterationApp.dto;

import org.springframework.context.annotation.Configuration;

@Configuration
public class GetUserResponse {
	private String email;
	
	public GetUserResponse(){}

	public GetUserResponse(String email) {
		super();
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}