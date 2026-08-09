FROM asmeenray/jdk-17
ARG JAR_FILE
COPY target/*.jar app.jar
VOLUME /tmp
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
