package com.bot.loginAndRegisterationApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
//import org.bson.json.JsonObject;
import org.json.JSONObject;
import com.bot.loginAndRegisterationApp.dto.GetUserResponse;
import com.bot.loginAndRegisterationApp.model.UserLogin;
import com.bot.loginAndRegisterationApp.service.UserRegistrationServiceImpl;

@CrossOrigin(origins = "*")
@Controller
public class LoginController {
	
	@Autowired
	private UserRegistrationServiceImpl userRegistrationServiceImpl;
	@Autowired
	private GetUserResponse getUserResponse;

	@PostMapping("/login")
	public ResponseEntity<?> loginUser(@RequestBody UserLogin userLogin) {
		try {
			userRegistrationServiceImpl.validateUserCredentials(userLogin);
			userRegistrationServiceImpl.validateUserPassword(userLogin);
		String token = userRegistrationServiceImpl.generateAccesToken(userLogin.getEmail());
		JSONObject j = new JSONObject();
		j.put("accesToken", token);
			return new ResponseEntity<>(j,HttpStatus.OK );
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
			
		}

}
	
}
