FROM gradle:jdk-21-and-22-alpine AS build
WORKDIR /business-service/iam
COPY build.gradle .
COPY settings.gradle .
COPY gradle ./gradle
COPY .env .
COPY gradle.properties .
RUN gradle dependencies --no-daemon
COPY src ./src
RUN gradle clean build -x test --no-daemon

FROM eclipse-temurin:21-jre-alpine
COPY --from=build ./business-service/iam/build/libs/*.jar iam.jar
ENTRYPOINT ["java", "-jar", "iam.jar"]