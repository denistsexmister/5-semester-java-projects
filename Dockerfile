FROM openjdk:21-alpine
COPY target/cardealership-0.0.1-SNAPSHOT.jar cardealership-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "cardealership-0.0.1-SNAPSHOT.jar"]