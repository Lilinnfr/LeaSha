FROM eclipse-temurin:17.0.12_7-jdk-focal
WORKDIR /app
COPY target/ls-back-0.0.1-SNAPSHOT.jar leasha.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","leasha.jar"]