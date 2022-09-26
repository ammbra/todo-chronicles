FROM maven:3.6.3-jdk-11 AS MVN_BUILD

LABEL version 1.0 \
      description "Image for custom greeting application"

COPY . /code
RUN echo 'building the application' > rm -Rf /code/target && \
	cd /code/ && chmod +x mvnw && \
    mvn clean package

FROM openjdk:11-jre

COPY --from=MVN_BUILD /code/target/*.jar /app.jar
COPY --from=MVN_BUILD /code/entrypoint.sh /entrypoint.sh

RUN groupadd -r appuser && useradd -r -g appuser appuser
RUN chmod +x /entrypoint.sh
USER appuser

CMD ["/entrypoint.sh"]