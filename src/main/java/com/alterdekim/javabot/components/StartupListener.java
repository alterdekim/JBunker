package com.alterdekim.javabot.components;

import com.alterdekim.javabot.dto.UserDTO;
import com.alterdekim.javabot.entities.GameTheme;
import com.alterdekim.javabot.entities.TextDataVal;
import com.alterdekim.javabot.service.GameThemeService;
import com.alterdekim.javabot.service.TextDataValService;
import com.alterdekim.javabot.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.games.Game;

import java.util.UUID;


@Slf4j
@Component
public class StartupListener {

    @Autowired
    private UserService userService;

    @Autowired
    private GameThemeService gameThemeService;

    @Autowired
    private TextDataValService textDataValService;


    private static final String ADMIN_USERNAME = "admin";
    private static final String DEFAULT_THEME = "Default";

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if( userService.findByUsername(ADMIN_USERNAME) != null ) return;
        String pwd = UUID.randomUUID().toString();
        log.info("Your admin account password is: {}", pwd);
        userService.saveUser(new UserDTO(ADMIN_USERNAME, pwd));
        gameThemeService.saveGameTheme(
                new GameTheme(
                        textDataValService.save(
                                new TextDataVal(DEFAULT_THEME)
                        ).getId()
                )
        );
    }
}
