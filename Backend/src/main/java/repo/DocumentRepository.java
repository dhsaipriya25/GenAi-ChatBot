package com.bot.loginAndRegisterationApp.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bot.loginAndRegisterationApp.model.Documents;


public interface DocumentRepository extends JpaRepository<Documents, String> {

    List<Documents> findByUserUserName(String userName);

    List<Documents> findByUserUserNameAndApplicationName(String userName, String applicationName);
}
