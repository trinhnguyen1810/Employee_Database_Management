package com.employee.demo.event.listener;

import com.employee.demo.entity.User;
import com.employee.demo.event.RegistrationCompleteEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import java.util.UUID;
import com.employee.demo.service.UserService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {

    @Autowired
    private UserService userService;

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        // Create Verificication Token for User w Link and
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        userService.saveVerficationTokenForUser(token,user);

        //send email to user
        String url = event.getApplicationUrl() + "/verifyRegistration?="+token;
        log.info("Click the link to verify your account:{}",url);

    }
}
