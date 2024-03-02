# blue 8088
# green 8089

#1 컨테이너 확인 (띄워지면 값 출력)
IS_BLUE_ON=$(sudo docker-compose -p market-blue -f docker-compose.blue.yml ps --services --filter "status=running")

if [ -n "$IS_BLUE_ON" ]; then
        # 2-1. blue가 띄워져 있다면
        echo "** 8089 포트 GREEN 컨테이너 실행"
                sudo docker-compose -p market-green -f /home/ubuntu/emarket/docker-compose.green.yml up -d
                BEFORE_NAME="blue"
                AFTER_NAME="green"
                BEFORE_PORT=8088
                AFTER_PORT=8089
else
        # 2-2. blue가 띄워지지 않은 경우
                echo "** 8088 포트 BLUE 컨테이너 실행"
                sudo docker-compose -p market-blue -f /home/ubuntu/emarket/docker-compose.blue.yml up -d
                BEFORE_NAME="green"
                AFTER_NAME="blue"
                BEFORE_PORT=8089
                AFTER_PORT=8088

fi

echo "** ${AFTER_NAME} server up(port:${AFTER_PORT})"

# 헬스체크
for cnt in {0..20}
do
        echo "** 서버 상태 확인중(${cnt}/20)";

        UP=$(curl -s http://127.0.0.1:${AFTER_PORT}/actuator/health)
        if [ "${UP}" != "up" ]
                then
                        sleep 10
                        continue
                else
                        break
        fi
done

if [ $cnt -eq 20 ]
then
        echo "** 서버가 정상적으로 구동되지 않았습니다."
        exit 1
fi

# 3 nginx 변경
sudo sed -i "s/${BEFORE_PORT}/${AFTER_PORT}/" /etc/nginx/conf.d/service-url-market.inc
sudo nginx -s reload
echo "** 배포 성공!"

# 4 기존 서버 종료
echo "$BEFORE_NAME server down(port:${BEFORE_PORT})"
sudo docker-compose -p market-${BEFORE_NAME} -f docker-compose.${BEFORE_NAME}.yml down

# 5. 사용하지 않는 이미지를 모두 삭제한다
sudo docker image prune -a -f
