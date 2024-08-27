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
# 마이크로 서비스 별 주요 기능 및 특징

## **Spring Cloud Eureka 서버 및 Gateway 구축**

- 서비스 디스커버리 및 API Gateway에서 인증, 라우팅 역할 수행

## **Spring Cloud Config 서버 구축**
- Spring Cloud Config를 통해 모든 서비스의 설정 정보를 중앙 저장소(Git 등)에서 관리함으로써 일관성을 유지하고, 설정 변경 이력을 체계적으로 추적할 수 있습니다. 이를 통해 설정 정보의 관리와 배포가 간편해지며, 설정 변경 시 서비스 재배포 없이도 적용할 수 있습니다.

## CI/CD 파이프라인 구축

- Jenkins와 Docker를 활용하여 CI/CD 파이프라인 구축





