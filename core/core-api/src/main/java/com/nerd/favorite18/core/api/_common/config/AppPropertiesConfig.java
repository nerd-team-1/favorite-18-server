package com.nerd.favorite18.core.api._common.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "oauth2")
public class AppPropertiesConfig {
    private Admin admin;
    private User user;

    @Getter
    @Setter
    public static class Admin {
        private String subId;
        private String email;
    }

    @Getter
    @Setter
    public static class User {
        private String subId;
        private String email;
    }
}
