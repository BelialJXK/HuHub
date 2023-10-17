# Build stage
FROM maven:3.6.3-jdk-11 as build
WORKDIR /app
COPY . .
RUN mvn clean compile package

ENV TARGET_PATH=bang-center-start
# Deploy stage
FROM docker:dind
RUN apk add maven
COPY --from=build /root/.m2/repository /root/.m2/repository
COPY --from=build /app/bang-center-start/target/*.jar app.jar
CMD ["dockerd-entrypoint.sh", "java", "-jar", "app.jar"]
