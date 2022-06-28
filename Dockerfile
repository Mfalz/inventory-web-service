FROM openjdk:17-alpine

WORKDIR /usr/myapp

COPY target/*.jar myapp.jar

CMD ["java","-jar","myapp.jar"]