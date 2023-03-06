// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
package org.acme.example;

import io.dekorate.docker.annotation.DockerBuild;
import io.dekorate.kubernetes.annotation.KubernetesApplication;
import io.dekorate.kubernetes.annotation.ServiceType;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@DockerBuild(group = "ammbra", autoBuildEnabled = false, autoPushEnabled = false, image = "ammbra/spring-todo-app:1.0-SNAPSHOT")
@KubernetesApplication(replicas = 2, serviceType = ServiceType.LoadBalancer)
public class TodoApplication {

    public static void main(String[] args) {
        SpringApplication.run(TodoApplication.class, args);
    }
}
