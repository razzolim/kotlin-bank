FROM gradle:7.4.1 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build

FROM amazoncorretto:11-alpine-jdk
WORKDIR /app
COPY --from=build /home/gradle/src/build/libs/kotlin-0.0.1.jar .
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "kotlin-0.0.1.jar"]