package com.bot.loginAndRegisterationApp.model;

//import jakarta.persistence.Entity;


public class UserLogin {
	private String email;
	private String password;

	public UserLogin(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}

