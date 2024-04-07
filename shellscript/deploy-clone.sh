PROJECT_NAME=market
CURRENT_RUN_PID=$(pgrep -f ${PROJECT_NAME}-.*.jar | head -n 1)

if [ -z "$CURRENT_RUN_PID" ]; then
    echo "** 현재 해당 포트에 서버가 작동중이지 않습니다."
else
    echo "** 현재 실행중인 서버를 종료합니다. dead_pid : $CURRENT_RUN_PID"
    kill -15 $CURRENT_RUN_PID
fi

echo "\n** SpringBoot 환경변수 설정"
export ENCRYPT_KEY="market"

echo "\n** SpringBoot 애플리케이션을 실행합니다.\n"
JAR_NAME=$(ls | grep .jar | head -n 1)
sudo -E nohup java -jar -Dspring.profiles.active=prod -DENCRYPT_KEY=jasyptKey -Dserver.port=8089 /home/ubuntu/market-0.0.1-SNAPSHOT.jar &

