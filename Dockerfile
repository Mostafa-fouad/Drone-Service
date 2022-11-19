# First stage: complete build environment
FROM maven:3.6.3-openjdk-11 AS builder

# add pom.xml and source code
ADD ./pom.xml pom.xml
ADD ./src src/

# package jar
RUN mvn package -Pdeploy -DskipTests

# Second stage
FROM openjdk:11

# copy jar from the first stage
COPY --from=builder target/drone-0.0.1-SNAPSHOT.jar /Drone-Task/target/drone-app.jar

EXPOSE 8080

CMD ["java", "-Dspring.profiles.active=docker", "-jar", "/Drone-Task/target/drone-app.jar"]