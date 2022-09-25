# todo-chronicles

Make sure you have Java11 installed before running the code in this repository:

```java
$ java -version
```

This repository contains 3 projects:
* _example-todo-app-project_ which is a slightly modified version of [TODO Demo Application](https://github.com/helidon-io/helidon/tree/main/examples/todo-app) part of the Helidon examples.
This project has 2 modules, both using Dekorate to generate Kubernetes manifest files:
  * _helidon-examples-todo-frontend_ 
  * _helidon-examples-todo-backend_
* _todo-app_quarkus_ is a slightly modified version of [TODO Application with Quarkus](https://github.com/quarkusio/todo-demo-app).
* _todo_app_springboot_ is a modified version of [TODO Application with Spring Boot](https://github.com/microsoft/spring-todo-app). The version in this repo uses a different Java version,
an H2 database and way to persist its data.

## How to run the Helidon examples

You can run both Helidon example projects by going to _todo_app_helidon_ folder:

```shell
$ cd todo-app-helidon
```

1. If you would like to generate Kubernetes/Knative or Helm charts using the existing Docker image, 
please comment the Tekton dependency in the each _pom.xml_:

```xml 
<!--        <dependency>-->
<!--            <groupId>io.dekorate</groupId>-->
<!--            <artifactId>tekton-annotations</artifactId>-->
<!--            <version>${dekorate-annotations.version}</version>-->
<!--        </dependency>-->
```

Then run:
```shell
$ mvn package  -Ddekorate.build=false -Ddekorate.push=false
```
The above command will generate:
 * the Kubernetes manifests (_kubernetes.json_ and _kubernetes.yml_) under:
  _todo-app-helidon/backend/target/classes/META-INF/dekorate_ for the backend module or
  _todo-app-helidon/frontend/target/classes/META-INF/dekorate_ for the frontend module.


  * the Knative manifests (_knative.json_ and _knative.yml_) under:
  _todo-app-helidon/backend/target/classes/META-INF/dekorate_ for the backend module or 
  _todo-app-helidon/frontend/target/classes/META-INF/dekorate_ for the frontend module.


  * the Helm manifests under:
  _todo-app-helidon/backend/target/classes/META-INF/dekorate/helm_ for the backend module or
  _todo-app-helidon/frontend/target/classes/META-INF/dekorate/helm_ for the frontend module.

2. If you would like to generate Tekton resources, comment the Knative and Helm Dekorate dependencies present in the _pom.xml_ files: 

```xml 
<!--        <dependency>-->
<!--            <groupId>io.dekorate</groupId>-->
<!--            <artifactId>knative-annotations</artifactId>-->
<!--            <version>${dekorate-annotations.version}</version>-->
<!--        </dependency>-->
<!--        <dependency>-->
<!--            <groupId>io.dekorate</groupId>-->
<!--            <artifactId>helm-annotations</artifactId>-->
<!--            <version>${dekorate-annotations.version}</version>-->
<!--        </dependency>-->
```
Uncomment the following dependency (if commented in previous use case):

```xml 
        <dependency>
            <groupId>io.dekorate</groupId>
            <artifactId>tekton-annotations</artifactId>
            <version>${dekorate-annotations.version}</version>
        </dependency>
```

And now run:

```shell
$ mvn package  -Ddekorate.build=false -Ddekorate.push=false
```

The above command will generate the Tekton manifests under:
* _todo-app-helidon/backend/target/classes/META-INF/dekorate_ for the backend module or
* _todo-app-helidon/frontend/target/classes/META-INF/dekorate_ for the frontend module.

## How to run the Quarkus examples

You can run the Quarkus example project by going to _todo_app_quarkus_ folder:

```shell
cd todo-app-quarkus
```

If you run the following command:

```shell
mvn clean package
```

you will obtain the following under _todo-app-quarkus/target_:

* _helm_ folder containing the Helm charts generated for the app.
* _kubernetes_ folder containing _knative.json_ and _knative.yml_ manifests.

If you would like to generate Kubernetes manifests, 
please comment the following line in _todo-app-quarkus/src/main/resources/application.properties_ :

```properties
quarkus.kubernetes.deployment-target=knative
```
and run again `mvn clean package`. 
This will result in generating _todo-app-quarkus/target/kubernetes/kubernetes.json_
and _todo-app-quarkus/target/kubernetes/kubernetes.yml_

## How to run the SpringBoot examples

You can run both SpringBoot example projects by going to _todo_app_springboot_ folder:

```shell
$ cd todo-app-springboot
```

1. If you would like to generate Kubernetes/Knative or Helm charts using the existing Docker image,
   please comment the Tekton dependency in the _pom.xml_:

```xml 
<!--        <dependency>-->
<!--            <groupId>io.dekorate</groupId>-->
<!--            <artifactId>tekton-annotations</artifactId>-->
<!--            <version>${dekorate-annotations.version}</version>-->
<!--        </dependency>-->
```

Then run:
```shell
$ mvn package  -Ddekorate.build=false -Ddekorate.push=false
```
The above command will generate:
* the Kubernetes manifests (_kubernetes.json_ and _kubernetes.yml_) under
  _todo-app-springboot/target/classes/META-INF/dekorate_.


* the Knative manifests (_knative.json_ and _knative.yml_) under _todo-app-springboot/target/classes/META-INF/dekorate_.


* the Helm manifests under:
  _todo-app-springboot/target/classes/META-INF/dekorate/helm_.

2. If you would like to generate Tekton resources, comment the Knative and Helm Dekorate dependencies present in the _pom.xml_ files:

```xml 
<!--        <dependency>-->
<!--            <groupId>io.dekorate</groupId>-->
<!--            <artifactId>knative-annotations</artifactId>-->
<!--            <version>${dekorate-annotations.version}</version>-->
<!--        </dependency>-->
<!--        <dependency>-->
<!--            <groupId>io.dekorate</groupId>-->
<!--            <artifactId>helm-annotations</artifactId>-->
<!--            <version>${dekorate-annotations.version}</version>-->
<!--        </dependency>-->
```
Uncomment the following dependency (if commented in previous use case):

```xml 
        <dependency>
            <groupId>io.dekorate</groupId>
            <artifactId>tekton-annotations</artifactId>
            <version>${dekorate-annotations.version}</version>
        </dependency>
```

And now run:

```shell
$ mvn package  -Ddekorate.build=false -Ddekorate.push=false
```

The above command will generate the Tekton manifests under _todo-app-springboot/target/classes/META-INF/dekorate_.
