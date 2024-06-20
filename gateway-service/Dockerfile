FROM openjdk:17-ea-11-jdk-slim
VOLUME /tmp

COPY build/libs/gateway-service-0.0.1-SNAPSHOT.jar gateway-service.jar
ENTRYPOINT ["java", "-jar", "gateway-service.jar", "--spring.profiles.active=prod"]
