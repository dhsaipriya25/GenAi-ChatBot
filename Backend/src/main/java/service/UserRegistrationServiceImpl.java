package com.bot.loginAndRegisterationApp.service;


import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bot.loginAndRegisterationApp.dto.GetUserDetails;
import com.bot.loginAndRegisterationApp.dto.GetUserResponse;
import com.bot.loginAndRegisterationApp.dto.UserRegistrationResponse;
import com.bot.loginAndRegisterationApp.exception.UserCredentialsMisMatch;
import com.bot.loginAndRegisterationApp.exception.UserCredentialsNullException;
import com.bot.loginAndRegisterationApp.model.UserLogin;
import com.bot.loginAndRegisterationApp.model.UserRegistration;
import com.bot.loginAndRegisterationApp.repo.UserRegistrationRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class UserRegistrationServiceImpl implements IUserRegistrationService {

	private final UserRegistrationRepository userRegistrationRepository;
	private final UserRegistrationResponse userRegistrationResponse;
	private final GetUserDetails getUserDetails;
	@Autowired
	private GetUserResponse getUserResponse;

	@Autowired
	public UserRegistrationServiceImpl(UserRegistrationRepository userRegistrationRepository, UserRegistrationResponse userRegistrationResponse, GetUserDetails getUserDetails) {
		this.userRegistrationRepository = userRegistrationRepository;
		this.userRegistrationResponse = userRegistrationResponse;
		this.getUserDetails = getUserDetails;
	}

	// saving data in the database
	public UserRegistrationResponse userRegistrationSave(UserRegistration userRegistration) throws DataIntegrityViolationException {
		if (userRegistrationRepository.findByUserName(userRegistration.getUserName()) != null) {
			throw new DataIntegrityViolationException("username already exits with this name");
		}
		if (userRegistrationRepository.findByEmail(userRegistration.getEmail()) != null) {
			throw new DataIntegrityViolationException("duplicate email");
		}
		if (userRegistrationRepository.findByPhoneNumber(userRegistration.getPhoneNumber()) != null) {
			throw new DataIntegrityViolationException("duplicate phone number");
		}

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); // creating object
		String password = encoder.encode(userRegistration.getPassword()); //encoding the password
		userRegistration.setPassword(password);
		userRegistration.setId(generateUserId());
		userRegistrationRepository.save(userRegistration);
		String registeredUserName = userRegistration.getUserName();
		userRegistrationResponse.setUserName(registeredUserName);
		return userRegistrationResponse;
		

	}

	// get method to fetch user details based on user-name
	public GetUserDetails findUserDetailsByUserName(String userName) {
		UserRegistration userRegistration = userRegistrationRepository.findByUserName(userName);
		getUserDetails.setUserName(userRegistration.getUserName());
		getUserDetails.setFirstName(userRegistration.getFirstName());
		getUserDetails.setLastName(userRegistration.getLastName());
		getUserDetails.setEmail(userRegistration.getEmail());
		getUserDetails.setPhoneNumber(userRegistration.getPhoneNumber());
		return getUserDetails;
	}

	public UserLogin findByUsername(String email)  {
		UserRegistration user = userRegistrationRepository.findByEmail(email);
		UserLogin userLogin = new UserLogin(user.getEmail(), user.getPassword());
		return userLogin;
	}
	
	public void validateUserCredentials(UserLogin userLogin) {
		if (userLogin.getEmail() == null || userLogin.getPassword() == null) {
			throw new UserCredentialsNullException("Username and password cannot be null");
		}
	}

	public void validateUserPassword(UserLogin userLogin) {
		UserLogin userProfile = findByUsername(userLogin.getEmail());
		BCryptPasswordEncoder decode = new BCryptPasswordEncoder();
		if (!(decode.matches(userLogin.getPassword(),(userProfile.getPassword())))) {
			System.out.println("Provided Password"+ userLogin.getPassword());
			System.out.println("Stored Password"+ userProfile.getPassword());
			throw new UserCredentialsMisMatch("Invalid Password");

		}
	}
	
	public String generateAccesToken(String email) {
		Date currentDate = new Date();
		String accessToken = ""; 
		try {
		accessToken = Jwts.builder()
				.setIssuedAt(currentDate)				
				.claim("email", email)
				 .signWith(SignatureAlgorithm.HS256,"secret12345secret12345secret12345secret12345").compact() ;
		}
		catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
		return accessToken;
		
	}
	
	private static long generateUserId() {
		Random random = new Random();
		long userId  =random.nextLong();
		return Math.abs(userId);			
	}
	
	public UserRegistration findByEmail(String email) {
		// TODO Auto-generated method stub
		UserRegistration ur =userRegistrationRepository.findByEmail(email);
		System.out.println(ur);
//		System.out.println(ur.getEmail());
//		System.out.println(ur.getUserName());
		return ur;
//		return ur.getUserName();
	}
}

