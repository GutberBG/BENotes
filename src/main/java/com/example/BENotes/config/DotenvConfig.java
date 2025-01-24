package com.example.BENotes.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DotenvConfig {
    private static final Dotenv dotenv = Dotenv.configure().load();

    public static String get(String key) {
        return dotenv.get(key);
    }
}
