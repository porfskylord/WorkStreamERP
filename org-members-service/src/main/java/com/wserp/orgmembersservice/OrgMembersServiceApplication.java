package com.wserp.orgmembersservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.wserp.orgmembersservice", "com.wserp.common"})
public class OrgMembersServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrgMembersServiceApplication.class, args);
    }

}
