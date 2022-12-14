/*
 * Copyright (c) 2017, 2020 Oracle and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.helidon.demo.todos.backend;

import java.io.IOException;
import java.util.List;
import java.util.logging.LogManager;

import io.dekorate.docker.annotation.DockerBuild;
import io.dekorate.helm.annotation.HelmChart;
import io.dekorate.helm.annotation.ValueReference;
import io.dekorate.knative.annotation.KnativeApplication;
import io.dekorate.kubernetes.annotation.*;
import io.dekorate.tekton.annotation.TektonApplication;
import io.helidon.config.Config;
import io.helidon.microprofile.server.Server;

import static io.helidon.config.ConfigSources.classpath;
import static io.helidon.config.ConfigSources.environmentVariables;
import static io.helidon.config.ConfigSources.file;

/**
 * Main class to start the service.
 */
@DockerBuild(group = "ammbra", image = "docker.io/ammbra/helidon-examples-todo-backend:2.5.3")
@KubernetesApplication( ports = @Port(name = "http", containerPort = 8854, hostPort = 8854),
        mounts = @Mount(name = "config-volume", path = "/conf"),
        configMapVolumes = @ConfigMapVolume(configMapName = "helidon-examples-todo-backend", volumeName = "config-volume"))
@KnativeApplication(name = "ktodo-app-backend", ports = @Port(name = "http", containerPort = 8854),
        mounts = @Mount(name = "config-volume", path = "/conf"),
        configMapVolumes = @ConfigMapVolume(configMapName = "helidon-examples-todo-backend", volumeName = "config-volume"))
@HelmChart(name = "htodo-app-backend", values = @ValueReference(property = "name", paths = "metadata.name", value = "htodo-app-backend"))
@TektonApplication(sourceWorkspaceClaim = @PersistentVolumeClaim(name = "claim-mine", matchLabels = @Label(key = "foo", value = "bar")))
public final class Main {

    /**
     * Cannot be instantiated.
     */
    private Main() {
    }

    /**
     * Application main entry point.
     *
     * @param args command line arguments
     * @throws IOException if an error occurred while reading logging
     * configuration
     */
    public static void main(final String[] args) throws IOException {

        // load logging configuration
        LogManager.getLogManager().readConfiguration(
                Main.class.getResourceAsStream("/logging.properties"));

        Config config = buildConfig();

        // as we need to use custom filter
        // we need to build Server with custom config
        Server server = Server.builder()
                .config(config)
                .build();

        server.start();
    }

    /**
     * Load the configuration from all sources.
     * @return the configuration root
     */
    static Config buildConfig() {
        return Config.builder()
                .sources(List.of(
                        environmentVariables(),
                        // expected on development machine
                        // to override props for dev
                        file("dev.yaml").optional(),
                        // expected in k8s runtime
                        // to configure testing/production values
                        file("prod.yaml").optional(),
                        // in jar file
                        // (see src/main/resources/application.yaml)
                        classpath("application.yaml")))
                 // support for passwords in configuration
//                .addFilter(SecureConfigFilter.fromConfig())
                .build();
    }
}
