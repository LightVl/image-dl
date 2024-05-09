FROM openjdk:21
WORKDIR /app
COPY /target/image-dl-0.0.1-SNAPSHOT.jar /app/image-dl.jar
ADD application.yml /app/application.yml
ENTRYPOINT ["java", "-Dspring.config.location=application.yml", "-jar", "image-dl.jar"]