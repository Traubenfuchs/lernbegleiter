FROM maven:latest
RUN mvn clean package -DskipTests

FROM openjdk:12
VOLUME /tmp
COPY target/app.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]