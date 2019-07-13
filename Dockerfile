FROM openjdk:11
EXPOSE 8080
ARG JAR_FILE=build/libs/ServerSideKotlin-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} application.jar
ENV JAVA_OPTS=""
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar application.jar"]