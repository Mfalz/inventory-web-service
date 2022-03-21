FROM openjdk:17-alpine

WORKDIR /usr/myapp

ENV OWNER mfalz
ENV REPO inventory-web-service
ENV PACKAGE tbd
ENV VERSION tbd
ENV ARTIFACT tbd

RUN wget https://maven.pkg.github.com/${OWNER}/${REPO}/${PACKAGE}/${VERSION}/${ARTIFACT}.jar myapp.jar

CMD ["java","-jar","myapp.jar"]