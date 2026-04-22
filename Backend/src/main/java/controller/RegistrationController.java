package com.bot.loginAndRegisterationApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bot.loginAndRegisterationApp.dto.GetUserDetails;
import com.bot.loginAndRegisterationApp.dto.UserRegistrationResponse;
import com.bot.loginAndRegisterationApp.model.UserRegistration;
import com.bot.loginAndRegisterationApp.repo.UserRegistrationRepository;
import com.bot.loginAndRegisterationApp.service.IUserRegistrationService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/userRegistration")
public class RegistrationController {

 
  @Autowired
  private IUserRegistrationService iUserRegistrationService;
  
  @Autowired
  private UserRegistrationRepository urr;

    @PostMapping("/register")
    public ResponseEntity<UserRegistrationResponse> userRegistrationSave(@RequestBody UserRegistration userRegistration){
        return new ResponseEntity<>(iUserRegistrationService.userRegistrationSave(userRegistration), HttpStatus.CREATED);

    }
    
    @PutMapping("/updateInterviewer")
    public ResponseEntity<UserRegistrationResponse> updateInterviewer(@RequestBody UserRegistration userRegistration){
        return new ResponseEntity<>(iUserRegistrationService.userRegistrationSave(userRegistration), HttpStatus.CREATED);

    }

    @GetMapping("/getRegisteredUserDetails/{userName}")
    public ResponseEntity<GetUserDetails> findUserDetailsByUserName(@PathVariable("userName") String userName){
        return new ResponseEntity<>(iUserRegistrationService.findUserDetailsByUserName(userName), HttpStatus.OK);
    }
    
    @GetMapping("/email/{email}")
    public ResponseEntity<UserRegistration> findByEmail(@PathVariable("email") String email){
    	System.out.println(email);
    	
    	return new ResponseEntity<>(iUserRegistrationService.findByEmail(email), HttpStatus.OK);
    }
    
}
