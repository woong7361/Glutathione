# 서비스 소개

쇼핑몰 프로젝트

# 📌프로젝트 결과물
**[[Admin Page]] : (http://210.114.19.32:8085/)**
TODO admin id & password

**[[Client Page]] : (http://210.114.19.32:8090/)**
TODO client id & password

# 📜 개발 환경

- 개발 운영체제 - Linux Ubuntu
- 기반 프레임워크 - Java Spring Boot
- MSA 프레임워크 - Spring cloud, gateway, eureka, config, feign client
- 기타 프레임워크 - JPA, QueryDsl, Spring Secuirty, ...
- 데이터베이스 - Mysql
- 로그관리 - ELK (Elastic Search & Logstash & Kibana)
- CI/CD - Jenkins & Github Webhook
- 서버 - 자체서버 1대 & AWS EC2 3대  

# 🚨 시스템 아키텍쳐
![쇼핑몰_아키텍처 drawio (3)](https://github.com/user-attachments/assets/0681f364-a5db-4c0b-bcee-06b00f40fba4)


(TODO 대표 사진 추가 필요!)
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
**TODO jenkins CI/CD branch별로 따로 작성된것 스크린샷 추가**

**TODO jenkins url 추가**
계정 정보
- username: admin
- password: 1234

## 로그 관리
### MSA로 인해 분산된 로그 때문에 에러나 상황파확이 어려워져 통합적인 로그관리의 필요성이 높아졌습니다. 따라서 traceId를 활용해 로그 트레이싱 환경을 구축하였습니다.
- Micrometer Tracing(traceId 제공)과 ELK를 활용하여 분산 로그 트레이싱 환경 구축

**TODO ELK 스크린샷 추가**
**TODO jenkins url 추가**
