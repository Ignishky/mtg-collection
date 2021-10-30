FROM openjdk:17-jdk-alpine
EXPOSE 8080
COPY build/libs/mtg-collection-0.0.1-SNAPSHOT.jar mtg-collection.jar
ENTRYPOINT ["java", "-jar", "/mtg-collection.jar"]