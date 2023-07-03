FROM openjdk:11-jdk

WORKDIR /app

COPY ./build/libs/placepic_core-0.0.1-SNAPSHOT.jar /app

ENTRYPOINT ["java", "-jar", "/app/placepic_core-0.0.1-SNAPSHOT.jar"]