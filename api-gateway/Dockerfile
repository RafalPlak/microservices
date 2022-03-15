FROM openjdk:11-oracle
WORKDIR "./usr/app"
EXPOSE 8080
ADD ./target/app.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]