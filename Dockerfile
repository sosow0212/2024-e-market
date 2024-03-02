FROM amazoncorretto:17-alpine-jdk
ARG JAR_FILE=./build/libs/market-0.0.1-SNAPSHOT.jar
ARG ENCRYPT_KEY
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Dspring.profiles.active=prod", "-DENCRYPT_KEY=$ENCRYPT_KEY", "-Duser.timezone=Asia/Seoul"]
