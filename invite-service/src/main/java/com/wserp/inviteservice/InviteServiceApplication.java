package com.wserp.inviteservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.wserp.inviteservice", "com.wserp.common"})
public class InviteServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InviteServiceApplication.class, args);
    }

}
