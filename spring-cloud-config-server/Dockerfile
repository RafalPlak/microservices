FROM openjdk:11-oracle
EXPOSE 8888
ADD ./target/app.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]