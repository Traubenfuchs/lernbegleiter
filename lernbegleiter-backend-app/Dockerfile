FROM openjdk:14
VOLUME /tmp
ARG DEPENDENCY=target/dependency
COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java","--enable-preview","-Xmx225m","-XX:+UseG1GC","-XX:+UseStringDeduplication","-cp","app:app/lib/*","at.technikumwien.lernbegleiter.LernbegleiterApplication"]