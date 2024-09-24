FROM java:8
MAINTAINER ayang

WORKDIR /app
ADD user-center-backend-0.0.1-SNAPSHOT.jar user-center-backend.jar
EXPOSE 9081

ENTRYPOINT  ["java","-jar","user-center-backend.jar", "--spring.profiles.active=prod"]