FROM openjdk:15

WORKDIR /app

COPY ["build/libs/backend-0.0.1-SNAPSHOT.jar", "./"]

CMD [ "java", "-jar", "backend-0.0.1-SNAPSHOT.jar"]
