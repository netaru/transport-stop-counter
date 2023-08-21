FROM maven:3.9.3-eclipse-temurin-11 as builder

RUN mkdir -p /usr/src/app/transport-stop-counter-service
WORKDIR /usr/src/app
COPY swagger-specification/ /usr/src/app/transport-stop-counter-service/swagger-specification
COPY pom.xml /usr/src/app
COPY src /usr/src/app/src
RUN mvn clean install

FROM eclipse-temurin:11
COPY --from=builder /usr/src/app/target/transport-stop-counter-service-1.0-SNAPSHOT.jar /opt/app/runtime.jar
COPY config.yml /opt/app
WORKDIR /opt/app
CMD [ "java", "-jar", "runtime.jar", "server", "config.yml" ]
EXPOSE 8080
