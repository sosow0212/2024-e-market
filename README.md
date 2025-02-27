# E-market (24.01 ~ 05)

## 목차

1. [프로젝트 소개](#프로젝트-소개)
2. [개발 내용](#개발-내용)
    - [기능 개발](#기능-개발)
    - [아키텍처](#아키텍처)
3. [인프라](#인프라)
4. [테스트 및 커버리지](#테스트)

---    

## 프로젝트 소개

<p>
(1인 프로젝트)

아래 설문을 바탕으로 교내 중고 거래 서비스의 필요함을 인지하고 기획하게 된 대학생을 위한 교내 중고거래 플랫폼입니다.

학교와 제휴를 통해 쿠폰을 지급하고 교내 서적 및 필요한 비품을 저렴하게 구매할 수 있습니다.

<img width="592" alt="스크린샷 2024-03-13 오후 8 44 22" src="https://github.com/sosow0212/2024-electronic-market/assets/63213487/0e01419e-841b-4373-befd-550660d435d9">


</p>

--- 

## 개발 내용

### 기능 개발

- [x] 테스트 격리 유틸 생성 및 REST Docs Helper
  생성 ([해당 과정 포스팅 보러가기](https://blog.naver.com/PostView.naver?blogId=sosow0212&logNo=223315262688&categoryNo=152&parentCategoryNo=152&from=thumbnailList))
- [x] 회원가입 및
  로그인 ([해당 과정 포스팅 및 고민한 점 보러가기](https://blog.naver.com/PostView.naver?blogId=sosow0212&logNo=223317675973&categoryNo=152&parentCategoryNo=152&from=thumbnailList))
- [x] 회원가입 이메일 전송 비동기 구현 및 미전송 메일 처리해주는 기능
  구현 ([해당 과정 보러가기](https://blog.naver.com/PostView.naver?blogId=sosow0212&logNo=223322476947&categoryNo=152&parentCategoryNo=152&from=thumbnailList))
- [x] 회원 간 커뮤니티 기능 생성 및 동시성
  처리 ([다양한 동시성 처리 방법 보러가기](https://blog.naver.com/PostView.naver?blogId=sosow0212&logNo=223328710499&categoryNo=152&parentCategoryNo=152&from=thumbnailList))
- [x] 쿠폰 기능 생성 및 금액 할인 도메인 서비스 구현
- [x] 마켓에 물품을 올리고 구매할 수 있는 기능 구현
- [x] 멀티모듈 분리 -> API, Batch 서버 ([멀티모듈로 분리한 이유](https://blog.naver.com/sosow0212/223401237314))
    - [x] 기존 이벤트를 Redis pub/sub으로 변경 ([Redis Pub/Sub로 이벤트 수신하기](https://blog.naver.com/sosow0212/223408645604))
    - [x] batch-server의 스케일아웃 환경에서 스케줄링 작업이 중복되는 문제 해결 ([스케줄링 중복 문제 해결](https://blog.naver.com/sosow0212/223408645604))

---

## 아키텍처

- **24.04.16 기준 서버 아키텍처**
    - 현재 SPOF 지점 : MySQL, Redis (개선 예정)
    - <img width="1532" alt="image" src="https://github.com/sosow0212/2024-mju-market/assets/63213487/23a96925-4dbb-40ca-a41b-910a4bc7ac8a">

---

### 인프라

- [x] Github Actions를 이용한 CI 환경
  생성 ([해당 과정 포스팅 보러가기](https://blog.naver.com/PostView.naver?blogId=sosow0212&logNo=223314392944&categoryNo=152&parentCategoryNo=152&from=thumbnailList))
- [x] 인프라 환경 구축하기 1차
    - [x] Jenkins를 이용한 CD 환경
      구축 ([아키텍처 및 해당 과정 보러가기](https://blog.naver.com/PostView.naver?blogId=sosow0212&logNo=223369462311&categoryNo=152&parentCategoryNo=152&from=thumbnailList))
    - [x] Prometheus, Grafana 이용한 모니터링
      구축 ([아키텍처 및 해당 과정 보러가기](https://blog.naver.com/PostView.naver?blogId=sosow0212&logNo=223369973401&categoryNo=161&parentCategoryNo=152&viewDate=&currentPage=1&postListTopCurrentPage=1&from=thumbnailList&userTopListOpen=true&userTopListCount=5&userTopListManageOpen=false&userTopListCurrentPage=1))
    - [x] Docker 이미지로 서버를 작동하도록 변경 및 무중단배포
      적용 ([새로운 아키텍처와 해당 과정 보러가기](https://blog.naver.com/PostView.naver?blogId=sosow0212&logNo=223371181808&categoryNo=161&parentCategoryNo=152&viewDate=&currentPage=1&postListTopCurrentPage=1&from=thumbnailList&userTopListOpen=true&userTopListCount=5&userTopListManageOpen=false&userTopListCurrentPage=1)))

<img width="1758" alt="image" src="https://github.com/sosow0212/2024-mju-market/assets/63213487/b2507a61-b6ca-45d1-bf08-704b66807069">

---

## 테스트

- 24.04.16 기준 테스트 227개 및 커버리지 80% 이상

<img width="838" alt="image" src="https://github.com/sosow0212/2024-mju-market/assets/63213487/89d4ccd8-f881-4a4b-adfc-a4b3d8ce9029">

<img width="655" alt="image" src="https://github.com/sosow0212/2024-mju-market/assets/63213487/967f8cfb-dd71-475d-821d-6767c87169d6">
