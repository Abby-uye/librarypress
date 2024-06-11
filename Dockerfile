FROM maven:3.8.7 as build
COPY . .
RUN mvn -B clean package -Dskiptests
FROM openjdk:17-jdk-slim
COPY --from=build ./target/*.jar app.jar
EXPOSE 8585
ENTRYPOINT ["java","-jar","app.jar"]