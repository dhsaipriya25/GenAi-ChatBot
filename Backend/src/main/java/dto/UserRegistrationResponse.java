package com.bot.loginAndRegisterationApp.dto;

import org.springframework.stereotype.Component;

@Component
public class UserRegistrationResponse {
    private String userName;
    
    public UserRegistrationResponse() {}

	public UserRegistrationResponse(String userName) {
		super();
		this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
    
    

}