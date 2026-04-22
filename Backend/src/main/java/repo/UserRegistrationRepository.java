package com.bot.loginAndRegisterationApp.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bot.loginAndRegisterationApp.model.UserRegistration;


public interface UserRegistrationRepository extends JpaRepository<UserRegistration, Long> {

    UserRegistration findByUserName(String userName);

    UserRegistration findByEmail(String email);

    UserRegistration findByPhoneNumber(String phoneNumber);
}
