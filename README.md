# 서비스 소개
글루따띠온은 MSA 구조를 이용한 B2C 형태의 쇼핑몰 사이트이며 원하는 제품을 검색하고 담아 구매할 수있는 사이트입니다.

# 프로젝트 결과물
**[[Admin Page]](http://210.114.19.32:8085/)**
- 계정 (id & password)
  - tester | tester

**[[Client Page]](http://210.114.19.32:8090/)**
- 계정 (id & password)
  - admin | 1234
# 개발 환경

- 개발 운영체제 - Linux Ubuntu
- 기반 프레임워크 - Java Spring Boot
- MSA 프레임워크 - Spring cloud, gateway, eureka, config, feign client
- 기타 프레임워크 - JPA, QueryDsl, Spring Secuirty, ...
- 데이터베이스 - Mysql
- 로그관리 - ELK (Elastic Search & Logstash & Kibana)
- CI/CD - Jenkins & Github Webhook
- 서버 - 자체서버 1대 & AWS EC2 3대  

# 시스템 아키텍쳐
![쇼핑몰_아키텍처 drawio (3)](https://github.com/user-attachments/assets/0681f364-a5db-4c0b-bcee-06b00f40fba4)


**(TODO 대표 사진 추가 필요!)**
# 마이크로 서비스 별 주요 기능

## **Spring Cloud Eureka 서버 및 Gateway 구축**
- 서비스 디스커버리 및 API Gateway에서 인증, 라우팅 역할 수행
  - API Gateway에서 발급한 JWT를 인증하는 과정을 거쳐 인증된 사용자만이 접근할 수 있도록 하고있으며, 회원, 비회원, 관리자가 접근할 수 있는 API를 제한하여 라우팅을 진행하고 있습니다.

## **Spring Cloud Config 서버 구축**
- Spring Cloud Config를 통해 모든 서비스의 설정 정보를 중앙 저장소(Git 등)에서 관리함으로써 일관성을 유지하고, 설정 변경 이력을 체계적으로 추적할 수 있습니다. 이를 통해 설정 정보의 관리와 배포가 간편해지며, 설정 변경 시 서비스 재배포 없이도 적용할 수 있습니다.

## **회원의 정보를 처리하는 서비스(user-service)**
### 사용자 기능 (spring security $ JWT token)
- 회원가입&로그인
- 아이디 비밀번호 찾기
- 회원 정보 관리
  - 내 정보 수정, 삭제
  - 패아머니 조회, 구매 내역, 좋아요 내역 
- 관리자 로그인

## **상품과 관련된 내용을 처리하는 서비스(product service)**
### 상품 관리
- 상품 등록, 수정, 삭제
- 상품 타입&스타일(해쉬 태그) 등록&취소
- 각 상품에 대한 문의 답변 작성
### 상품 조회
- 상품 검색(상품명, 내용, 타입, 스타일)
- 상품 상세조회
- 상품 찜 확인&취소
- 상품 문의 작성
### 배너 관리
- 배너 등록, 수정, 삭제
- 배너 조회

## **상품 결재와 관련된 내용을 처리하는 서비스(order service)**
### 장바구니
- 장바구니 추가, 삭제, 조회
### 결재 
- 페이머니 추가
- 장바구니 결재, 단일 품목 결재
### 쿠폰 
- 쿠폰 등록&삭제
- 쿠폰 발급
### 대시보드 구성
- 매출액 & 판매수량
- 결재를 많이한 회원 순위(VIP 관리)
- 가장 많이 팔린 상품 지표

  
# 서비스 인프라 개요
## CI/CD 파이프라인 구축
### MSA를 사용하다보니 배포와 테스트할 케이스가 많아져 CI/CD의 필요성이 더욱 높아져 각 서비스별로 Jenkins를 활용하여 CI/CD를 구축하게 되었습니다.
- Jenkins와 Docker를 활용하여 CI/CD 파이프라인 구축
  - github hook을 사용하여 branch가 push될때 가동 되도록 구축
  - 각 서비스별 파이프라인을 따로 구축하여 서비스별로 CI/CD가 가능하게끔 구성
    
- **TODO jenkins CI/CD branch별로 따로 작성된것 스크린샷 추가**

**[[**jenkins 살펴보기!!**]](http://3.36.58.192:8080/)**
- 계정 정보
  - username: admin
  - password: 1234

## 로그 관리
### MSA로 인해 분산된 로그 때문에 에러나 상황파확이 어려워져 통합적인 로그관리의 필요성이 높아졌습니다. 따라서 traceId를 활용해 로그 트레이싱 환경을 구축하였습니다.
- Micrometer Tracing(traceId 제공)과 ELK를 활용하여 분산 로그 트레이싱 환경 구축

- **TODO ELK 스크린샷 추가**
- **[**elastic 살펴보기!!**](http://211.218.223.120:5601/app/discover#)**


# 서비스 주요 특징 & 트러블 슈팅
## 분산 트랜잭션 관리
### order-service에서 결재시 product-service의 product 수량을 감소 시켜주어야하는 상황에서 에러 발생시 트랜잭션 롤백 처리가 안되는 문제 발생
- 결재 및 상품 수정(상품 수량 감소)에서 SAGA Choreography 패턴 적용
- 로컬 트랜잭션에서 에러 발생시 보상 트랜잭션을 통해 데이터 일관성 유지
- Message Que(Kafka)를 활용한 이벤트 기반 비동기 통신으로 서비스간 결합도를 낮춤
  - 다른 서비스의 트랜잭션 실패를 대기할 일이 없으므로 빠른 처리 가능 

**TODO 자세한 사항은 펼쳐보기**

## 선착순 쿠폰 발급시 동시성 제어및 시스템 부하 관리
### 100명에게 선착순 쿠폰을 발행한다고 할때 100명이 넘는 인원들에게 발행이 되는 문제가 발생
1. DB Lock을 통한 해결 (비관적 Lock)
   - 스크린샷
   - 성능 & 분산환경에서 불가능
2. redis를 이용한 해결
   - redis가 single thread로 작동되는 것을 이용하여 해결

### 위의 문제를 해결하다보니 단기간에 접속이 많아져 DB CPU이용률이 높아지는 문제 발생
- message que인 kafka를 통해 데이터 흐름의 양을 제어
- kafka는 DB에 비해 sacle out도 쉽게 가능

**TODO 자세한 사항은 살펴보기**


## DB와 이미지 파일 동기화문제 해결
### 상품 혹은 배너와같은 이미지가 포함된 컬럼을 삭제할때 이미지파일은 DB 트랜잭션에 포함이 되지 않는 문제 발생
- 이미지를 DB 컬럼과 같이 삭제해버리면 rollback이 안되므로 컬럼에 삭제처리만을 해준다.
- 일정시간마다(linux crontab) 각 서비스의 database를 돌면서 삭제된지 일정기간이 지난 컬럼을 찾는다.
  - mysql 원격접속 & .sql 파일 실행
- 검색된 컬럼에 쓰여있는 경로로 이동하여 이미지 파일을 삭제한다.
  - crontab이 돌아가는 서버와 이미지가 저장되는 서버가 다르기에 ssh를 사용하여 원격으로 삭제한다.

**TODO 자세한 사항은 살펴보기**

## MSA 환경으로 인해 발생하는 Cost 최소화
### 현재 개발 PC1, 서버 PC1, EC2 free tier 2 => 개발PC를 제외한 서버 2대가 존재하는데 로컬 서버PC의 메모리 사용량이 90%가 넘어서 죽는 문제 발생
1. sacle out
  - 가장 간단하게 해결이 가능하지만 비용이 없는 free tier를 무한정 만들수는 없기에 1대만 추가하였다.
2. 운영체제 최적화
  - ubuntu desktop -> ubuntu server
3. docker 메모리제한
4. jvm heap 제한
5. 경량화 SQL -> SQL Lite

**TODO 자세한 사항은 펼쳐보기**

- 현재상황 
![image](https://github.com/user-attachments/assets/fae8338a-32fb-4ee8-9e66-ce94619b1c3b)

* 의사결정 사항

|  요구사항  |  선택지  |  설명  |
| ---- | ---- | ---- |
| Message Queue | Kafka VS RabbitMQ | MQ는 Kafka를 사용하였습니다. Kafka는 마이크로서비스 간의 느슨한 결합을 유지하고, 대용량 트래픽을 처리하기 위해 사용되었습니다. RabbitMQ는 Kafka에 비해 신뢰성과 보안성이 좋지만 현재 서비스에서 선착순 쿠폰 발행과같은 대용량 데이터 처리가 필요하고 Kafka에 비해 sacle out이 힘들다는 단점이 있어 Kafka를 선택하였습니다. |
| 이벤트 스케줄링 | spring Scheduler VS Crontab | 이벤트 스케줄러는 linux crontab을 선택하였습니다. spring scheduler는 기존의 프레임워크 위에서 작성되기에 작성이 편리하고, spring내에서 돌아가기에 log를 통합적으로 남기기에도 좋은 장점이있지만 scheduler가 필요한 서비스가 하나가 아니기에 코드가 파편화된다는 단점이 있습니다. 따로 스케줄러 서비스를 만들 수 있지만 현재 cost가 매우 부족한 상황에서는 힘들기에, 큰 비용을 사용하지 않고 운영체제 위에서 돌아가는 linux crontab을 사용하여 스케줄링을 관리하였습니다. |
| 데이터베이스 | MySQL VS SQL Lite | 데이터베이스는 Mysql을 선택하였습니다. MSA 환경을 혼자 구축하려다보니 점점 cost를 중점적으로 보기 시작하였고, cost를 최대한 줄이기 위하여 DB를 SQL Lite로 경량화하는 방식도 구현해 보았는데 동시 쓰기가 되지않아 선착순 쿠폰 발행시 속도가 많이 느려져 결국 MySQL을 사용하기로 결정하였습니다. |

