FROM gradle:jdk17 AS builder
RUN mkdir -p /source
WORKDIR /source
COPY . .
RUN gradle build --no-daemon

FROM openjdk:17.0.2-slim-buster
RUN mkdir /app
WORKDIR /app
COPY --from=builder /source/build/libs/fram-challenge-0.0.1-SNAPSHOT.jar .
EXPOSE 8080
ENTRYPOINT java -jar fram-challenge-0.0.1-SNAPSHOT.jar --spring.profiles.active=demo
