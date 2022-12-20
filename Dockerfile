FROM maven:3.8.3-openjdk-17 as builder
ENV HOME=/app
RUN mkdir -p $HOME
WORKDIR $HOME
ADD pom.xml $HOME
RUN mvn verify --fail-never
ADD . $HOME
RUN mvn package -DskipTests

FROM openjdk:17-alpine as dev-mode
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT exec java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005 -jar app.jar

FROM openjdk:17-alpine
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT exec java -jar app.jar
