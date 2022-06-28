FROM --platform=$BUILDPLATFORM openjdk:17.0.2
ARG TARGETPLATFORM
ARG BUILDPLATFORM

WORKDIR /usr/myapp

COPY target/*.jar myapp.jar

CMD ["java","-jar","myapp.jar"]