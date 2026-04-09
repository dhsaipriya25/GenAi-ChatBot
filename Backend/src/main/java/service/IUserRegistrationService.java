package com.bot.loginAndRegisterationApp.service;

import com.bot.loginAndRegisterationApp.dto.GetUserDetails;
import com.bot.loginAndRegisterationApp.dto.UserRegistrationResponse;
import com.bot.loginAndRegisterationApp.model.UserLogin;
import com.bot.loginAndRegisterationApp.model.UserRegistration;


public interface IUserRegistrationService {
	
	public UserRegistrationResponse userRegistrationSave( UserRegistration userRegistration);
	public GetUserDetails findUserDetailsByUserName(String userName);
	public UserLogin findByUsername(String email);
	public UserRegistration findByEmail(String email);
}
