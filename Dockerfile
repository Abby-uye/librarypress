FROM maven:3.8.7 as build
COPY . .
RUN mvn -B clean package -Dskiptests from openjdk:21
COPY --from=build ./target/*.jar app.jar
EXPOSE 8585
ENTRYPOINT ["java","-jar","app.jar"]