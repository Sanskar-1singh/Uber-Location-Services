package com.example.uberlocationservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringBootApplication
@EnableDiscoveryClient
public class UberLocationServicesApplication {

    public static void main(String[] args) {
        SpringApplication.run(UberLocationServicesApplication.class, args);
    }

}
