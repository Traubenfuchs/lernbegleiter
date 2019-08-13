FROM maven:latest
COPY lernbegleiter-backend-app /usr/src/lernbegleiter/lernbegleiter-backend-app
COPY pom.xml /usr/src/lernbegleiter
RUN mvn -f /usr/src/lernbegleiter/pom.xml clean package -DskipTests

FROM openjdk:12
VOLUME /tmp
COPY --from=build /usr/src/lernbegleiter/lernbegleiter-backend-app/target/app.jar /usr/app/app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]