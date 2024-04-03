package com.alterdekim.javabot;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


@Data
@Configuration
public class TelegramConfig {
    @Value("${bot.botToken}") private String botToken;
    @Value("${bot.botLogin}") private String botLogin;
    @Value("${bot.master}") private String masterId;
}
