FROM openjdk:13
VOLUME /tmp
COPY target/app.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","--enable-preview","-Xmx200m","-XX:+UseG1GC","-XX:+UseStringDeduplication","-jar","/app.jar"]
