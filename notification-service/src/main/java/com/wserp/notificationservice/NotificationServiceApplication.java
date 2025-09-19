package com.wserp.notificationservice;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
public class NotificationServiceApplication {
	public static void main(String[] args) {
        SpringApplication.run(NotificationServiceApplication.class, args);
	}

}
