FROM maven:3.8.7 as build
COPY ./The_library_press .
RUN mvn -B clean package -Dskiptests
FROM openjdk:17
COPY --from=build ./The_library_press/target/*.jar app.jar
EXPOSE 8585
ENTRYPOINT ["java","-jar","app.jar"]
