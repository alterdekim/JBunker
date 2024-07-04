package com.alterdekim.javabot.components;

import com.alterdekim.javabot.dto.UserDTO;
import com.alterdekim.javabot.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.UUID;


@Slf4j
@Component
public class StartupListener {

    @Autowired
    private UserService userService;

    private static final String ADMIN_USERNAME = "admin";

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if( userService.findByUsername(ADMIN_USERNAME) != null ) return;
        String pwd = UUID.randomUUID().toString();
        log.info("Your admin account password is: {}", pwd);
        userService.saveUser(new UserDTO(ADMIN_USERNAME, pwd));
    }
}
