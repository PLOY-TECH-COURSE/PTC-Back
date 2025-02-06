FROM openjdk:23-jdk
WORKDIR /ploytechcourse
COPY ploytechcourse-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]