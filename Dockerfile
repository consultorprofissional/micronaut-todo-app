FROM gradle:6.5.0-jdk11 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon 


FROM adoptopenjdk/openjdk11-openj9:jdk-11.0.1.13-alpine-slim
COPY --from=build /home/gradle/src/build/libs/*.jar app.jar
EXPOSE 8080
CMD java -Dcom.sun.management.jmxremote -noverify ${JAVA_OPTS} -jar app.jar