package org.acme.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

//@KubernetesApplication(replicas = 2, serviceType = ServiceType.LoadBalancer)
public class TodoApplication {

    public static void main(String[] args) {
        SpringApplication.run(TodoApplication.class, args);
    }
}
