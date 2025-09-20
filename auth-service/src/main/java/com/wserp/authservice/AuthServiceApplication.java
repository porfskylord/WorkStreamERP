package com.wserp.authservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableFeignClients
public class AuthServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(PasswordEncoder passwordEncoder) {
        return (args) -> {
            System.out.println("BCrypt hash for 12345 : " + passwordEncoder.encode("Admin12345"));
            System.out.println("BCrypt hash for password123 : " + passwordEncoder.encode("User12345"));

        };
    }

}
