FROM --platform=$TARGETPLATFORM openjdk:17.0.1

ARG TARGETPLATFORM
ARG BUILDPLATFORM

WORKDIR /usr/myapp

COPY target/*.jar myapp.jar

CMD ["java","-jar","myapp.jar"]