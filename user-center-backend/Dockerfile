FROM openjdk:8-jdk-alpine
MAINTAINER ayang

WORKDIR /app
ADD user-center-backend-0.0.1-SNAPSHOT.jar user-center-backend.jar


CMD ["java","-jar","/app/user-center-backend.jar", "--spring.profiles.active=prod"]