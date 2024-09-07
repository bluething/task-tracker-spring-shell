package io.github.bluething.playground.spring.boot.tasktracker;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class AppConfig {
    @Bean
    OutputFormatter outputFormatter(){
        return new OutputFormatter();
    }
}
