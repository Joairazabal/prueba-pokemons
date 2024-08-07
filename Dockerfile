FROM openjdk:17-jdk-slim

COPY ./tecnica-0.0.1-SNAPSHOT.jar java-app.jar

CMD ["java", "-jar", "java-app.jar"]
