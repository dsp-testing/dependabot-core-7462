FROM maven:3.8.3-openjdk-17 AS builder
ENV HOME=/app
RUN mkdir -p $HOME
WORKDIR $HOME
ADD backend/pom.xml $HOME
RUN mvn verify --fail-never
ADD ./backend $HOME
RUN mvn package -DskipTests

FROM mockoon/cli AS mock-backend
# FROM node:14-alpine as mock-backend

# RUN npm install -g @mockoon/cli@2.3.0
COPY backend/mockoon-portail-formation.json ./mockoon-portail-formation.json
# Do not run as root.
# RUN adduser --shell /bin/sh --disabled-password --gecos "" mockoon
# RUN chown -R mockoon ./mockoon-portail-formation.json
# USER mockoon
ENV DATA=./mockoon-portail-formation.json
EXPOSE 8080
ENTRYPOINT ["mockoon-cli", "start", "--hostname", "0.0.0.0", "--daemon-off", "--data", "./mockoon-portail-formation.json", "--container"]
# Usage: docker run -p <host_port>:<container_port> mockoon-test

FROM openjdk:17-alpine AS dev-mode
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT exec java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005 -jar app.jar

FROM openjdk:17-alpine
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT exec java -jar app.jar
