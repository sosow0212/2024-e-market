FROM amazoncorretto:17-alpine-jdk
ARG JAR_FILE=./build/libs/market-batch-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Dspring.profiles.active=prod", "-Duser.timezone=Asia/Seoul", "-jar", "/app.jar"]
