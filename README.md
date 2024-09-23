# 서비스 소개
글루따띠온은 MSA 구조를 이용한 B2C 형태의 쇼핑몰 사이트이며 원하는 제품을 검색하고 담아 구매할 수있는 사이트입니다.

# 프로젝트 결과물
**[[Client Page]](http://210.114.19.32:8090/)**
- 계정 (id & password)
  - admin | 1234

**[[Admin Page]](http://210.114.19.32:8085/)**
- 계정 (id & password)
  - tester | tester

# 개발 환경

- 개발 운영체제 - Linux Ubuntu
- 기반 언어 - JAVA(17)
- 기반 프레임워크 - Java Spring Boot(3.3.1)
- MSA 프레임워크 - Spring cloud, gateway, eureka, config, feign client
- 기타 프레임워크 - JPA, QueryDsl, Spring Secuirty, ...
- 데이터베이스 - MySql, Redis
- 기타 - ELK, Jenkins, Kafka
- 배포 서버 - desktop 1대(ubuntu server) + AWS EC2 3대  

# 시스템 아키텍쳐
![쇼핑몰_아키텍처 (1)](https://github.com/user-attachments/assets/48aa8b80-e53c-4873-956a-df06df761742)


# 마이크로 서비스 별 주요 기능
![image](https://github.com/user-attachments/assets/1dd64919-e587-40f0-a049-ab2fef12faf7)

## **Spring Cloud Eureka 서버 구축**
- 서비스 레지스트리및 서비스 디스커버리 역할 수행 
  - 마이크로서비스를 관리, 운영을 위한 기반 서비스의 주소와 유동적인 IP를 매핑하여 저장
  - 클라이언트가 여러 개의 마이크로서비스를 호출하기 위해 최적 경로를 찾아주는 라우팅 기능, 적절한 부하 분산을 위한 로드 밸런싱 기능을 제공한다.
 
## **Spring Cloud Gateway 서버 구축**
- API 라우팅 역할 수행
- 인증 및 인가 역할 수행
  - 발급한 JWT를 인증하는 과정을 거쳐 회원, 비회원, 관리자가 접근할 수 있는 API를 제한하는 인증 및 인가 역할을 수행한다.
 
## **Spring Cloud Config 서버 구축**
- Spring Cloud Config를 통해 모든 서비스의 설정 정보를 중앙 저장소(Git 등)에서 관리함으로써 일관성을 유지하고, 설정 변경 이력을 체계적으로 추적할 수 있습니다. 이를 통해 설정 정보의 관리와 배포가 간편해지며, 설정 변경 시 서비스 재배포 없이도 적용할 수 있습니다.

## **회원의 정보를 처리하는 서비스(user-service)**
### 사용자 기능 (spring security & JWT token)
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
- 가장 인기있는 제품 스타일별 5개씩 (인기순)
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
    
  ![image](https://github.com/user-attachments/assets/649dcfaf-a29b-40d1-9175-94cbff5a6db5)

**[[**jenkins 살펴보기!!**]](http://3.36.58.192:8080/)**
- 계정 정보
  - username: admin
  - password: 1234

## 로그 관리
### MSA로 인해 분산된 로그 때문에 에러나 상황파확이 어려워져 통합적인 로그관리의 필요성이 높아졌습니다. 따라서 traceId를 활용해 로그 트레이싱 환경을 구축하였습니다.
- Micrometer Tracing(traceId 제공)과 ELK를 활용하여 분산 로그 트레이싱 환경 구축

  ![image](https://github.com/user-attachments/assets/cf7f1548-c592-4909-9384-480650b271eb)
- **[**elastic 살펴보기!!**](http://211.218.223.120:5601/app/discover#)**


# 서비스 주요 특징 & 트러블 슈팅
## 분산 트랜잭션 관리
### 결재 서비스와 상품서비스간의 협력시 트랜잭션 처리의 문제가 발생
- order-service에서 결재시 product-service의 product 수량을 감소 시켜주어야하는 상황에서 에러 발생시 트랜잭션 롤백 처리가 안되는 문제 발생
- 결재 및 상품 수정(상품 수량 감소)에서 SAGA Choreography 패턴 적용
- 로컬 트랜잭션에서 에러 발생시 보상 트랜잭션을 통해 데이터 일관성 유지
- Message Que(Kafka)를 활용한 이벤트 기반 비동기 통신으로 서비스간 결합도를 낮춤
  - 다른 서비스의 트랜잭션 실패를 대기할 일이 없으므로 빠른 처리 가능 

<details>
  <summary>*<b>자세한 사항은 펼쳐보기</b>*</summary>
  
### 문제 상황
- 현재 order-service에서 결재를 요청받으면 결재처리를 진행후 product-service로 이동하여 product 수량을 감소시켜주어야한다. 이때 **product-service에서 에러**가 발생한다면 order-service에서 **rollback**은 어떻게 진행되어야 할까?
  ![원본](https://github.com/user-attachments/assets/49060813-6a13-4f49-bd1c-6e940a3029ad)
  

### 해결 방안
- 이 문제를 해결하기 위해 **SAGA Pattern**을 적용하기로 하였다.
  - SAGA Pattern: 마이크로 서비스에서 **데이터 일관성**을 관리하는 방법입니다. 각 서비스는 로컬 트랜잭션을 가지고 있으며, 해당 서비스 데이터를 업데이트하며 메시지 또는 이벤트를 발행해서, 다음 단계 트랜잭션을 호출하게 됩니다. 만약, 해당 프로세스가 실패하게 되면 데이터 정합성을 맞추기 위해 이전 트랜잭션에 대해 **보상 트랜잭션**을 실행합니다.
  - SAGA Pattern은 2가지로 **Orchestration based** 방식과 **Choreography** 방식이 있는데 현재 서비스 구성상 **Choreography** 방식을 채택하기로 하였다. 
    - (**Orchestration-Based Saga** 패턴은 모든 관리를 Manager가 호출하기 때문에 분산트랜잭션의 중앙 집중화가 이루어져 구현및 테스트가 쉽지만 트랜잭션 관리 서비스가 하나더 추가되기에 cost가 부족한 현재 서비스에는 적합하지 않다고 생각되었다.)
    - (**Choreography** 방식은 서비스끼리 직접적으로 통신하지 않고, 이벤트 Pub/Sub을 활용해서 통신하는 방식으로 프로세스를 진행하면서 장애가 나면 보상 트랜잭션 이벤트를 발행한다. 추가 서비스 구현이 필요하지 않아 간단하지만, 테스트나 디버깅이 어려운 단점이 있다.)
   
  - 정상 처리시
  ![정상처리](https://github.com/user-attachments/assets/e903de29-5573-42ed-828f-40c3c721de00)
  - 에러 발생시
  ![에러발생시](https://github.com/user-attachments/assets/bdd3a1b3-813c-417c-b03e-a4f410489d98)
  - 에러 발생시 kafka를 통해 롤백을 요청하는 이벤트를 발행하여 보상트랜잭션을 구현할 수 있다. 

### 적용 코드 
PS. 적용 코드부분에서는 client -> order-service -> product-service 대신 **client -> product-service -> order-service** 순으로 순서를 바꾸었다.

- product-service에서 상품수량을 줄이고 kafka에 주문 event를 발송한다.
```
// ProductService.Java
public void order(OrderRequestDto orderRequestDto) {
    reduceQuantity(orderRequestDto.getOrderProducts().stream()
            .map((product) -> new ReduceQuantityRequestDto(product.getProductId(), product.getQuantity()))
            .collect(Collectors.toList()));

    kafkaProducer.send("order-request", orderRequestDto);
}
```

- order-service에서 발송된 이벤트를 받아 처리한다. 만일 **에러가 발생하면** rollback 이벤트를 발행하여 보상트랜잭션을 준비한다.
```
// KafkaConsumer.java
@KafkaListener(topics = "order-request", groupId = "consumerGroupId")
public void updateQty(String kafkaMessage) {
    log.info("kafka Message: {}", kafkaMessage);

    try {
        ObjectMapper objectMapper = new ObjectMapper();
        OrderRequestDto orderRequestDto = objectMapper.readValue(kafkaMessage, OrderRequestDto.class);
        orderService.order(orderRequestDto, orderRequestDto.getMemberId());
    } catch (Exception e) {
        log.error("order error -> rollback required: {}", e.getMessage());
        kafkaProducer.send("order-rollback", kafkaMessage);
    }
}
```

- product-service에서 rollback 이벤트를 받아 보상트랜잭션을 수행한다.
```
@KafkaListener(topics = "order-rollback", groupId = "consumerGroupId")
public void updateQty(String kafkaMessage) {
    log.info("kafka Message: {}", kafkaMessage);

    try {
        ObjectMapper objectMapper = new ObjectMapper();
        OrderRequestDto orderRequestDto = objectMapper.readValue(kafkaMessage, OrderRequestDto.class);
        orderRequestDto.getOrderProducts().stream()
                .forEach(p -> {
                    productRepository.findById(p.getProductId())
                            .ifPresent(product -> product.addQuantity(p.getQuantity()));
                });

    } catch (Exception e) {
        log.error("order rollback 실패 관리자 문의 필요: message: {}", kafkaMessage);
    }
}
```

### 결과
- order-service에서 일부러 에러를 던지는 코드로 수정하였다.
```
// KafkaConsumer.java
@KafkaListener(topics = "order-request", groupId = "consumerGroupId")
public void updateQty(String kafkaMessage) {
    log.info("kafka Message: {}", kafkaMessage);

    try {
        ObjectMapper objectMapper = new ObjectMapper();
        OrderRequestDto orderRequestDto = objectMapper.readValue(kafkaMessage, OrderRequestDto.class);
        orderService.order(orderRequestDto, orderRequestDto.getMemberId());
        throw new RuntimeException("rollback");
    } catch (Exception e) {
        log.error("order error -> rollback required: {}", e.getMessage());
        kafkaProducer.send("order-rollback", kafkaMessage);
    }
}
```

- 그결과 order-service에서 rollback-event를 발행하였고 
![image](https://github.com/user-attachments/assets/d7641c26-af46-4822-abd2-7f2a8bdaaca7)
- product-service에서 product수량을 rollback하는 과정이 수행되었다.
![image](https://github.com/user-attachments/assets/09ecc288-12c3-40fb-ad85-90b53f588782)

</details>

## 선착순 쿠폰 발급시 동시성 제어및 시스템 부하 관리
### 쿠폰발급시 정해진 쿠폰 수량보다 더 많은 쿠폰이 발급되는 동시성 문제가 발생
1. DB Lock을 통한 해결 (비관적 Lock)
   - 스크린샷
   - 성능 & 분산환경에서 불가능
2. redis를 이용한 해결
   - redis가 single thread로 작동되는 것을 이용하여 해결

### 위의 문제를 해결하다보니 단기간에 접속이 많아져 DB CPU이용률이 높아지는 문제 발생
- message que인 kafka를 통해 데이터 흐름의 양을 제어
- kafka는 DB에 비해 sacle out도 쉽게 가능

<details>
  <summary>*<b>자세한 사항은 펼쳐보기</b>*</summary>

### 문제상황1
- 쿠폰발급시 정해진 쿠폰 수량보다 더 많은 쿠폰이 발급되는 동시성 문제가 발생
- ex. 100명에게 선착순 쿠폰을 발행한다고 할때 100명이 넘는 인원들에게 발행이 되는 문제가 발생

- 문제가 발생한 코드
```
// CouponService.java

public void issueLimitedCoupon(Long couponId, Long memberId) {
    Coupon coupon = couponRepository.findById(couponId)
            .orElseThrow(() -> new NotFoundException("coupon Not Found", couponId));
    long issuedCouponCount = MemberCouponRepository.countByCouponId(couponId)

    if (coupon.getQuantity() < issuedCouponCount) {
        throw new CouponException("쿠폰 수량 부족");
    }

    MemberCoupon memberCoupon = MemberCoupon.builder()
            .memberId(memberId)
            .isUsed(false)
            .build();
    memberCoupon.setCouponId(couponId);

    memberCouponRepository.save(memberCoupon);
}
```
- 동시에 DB에 쓰기작업이 완료되지않고 동시에 조회작업이 들어와 문제가 발생한다.
- 결과: **100개 초과**의 쿠폰 발급 & 평균 **2.9초**의 처리속도
- ![image](https://github.com/user-attachments/assets/24de5e9c-1ec5-4520-bf42-c1249371f527)
- ![image](https://github.com/user-attachments/assets/78825834-01d0-4652-8d2d-adcfb56a2169)

### 해결방법 
1. **Lock으로 해결해보기(DB Lock)**
- 가장먼저 간단한 방법인 DB Lock을 적용하여 시험해보았다.
- 비관적 Lock을 사용하여 구현하였다.
```
    public void issueLimitedCoupon(Long couponId, Long memberId) {

        // @Lock(LockModeType.PESSIMISTIC_WRITE)를 추가해 조회문을 SelectForUpdate로 바꿔 비관적 Lock을 구현한다. 
        Coupon coupon = couponRepository.findByCouponIdForUpdate(couponId)
                .orElseThrow(() -> new CouponException("coupon Not Found", couponId));
        coupon.decreaseQuantity();  // 쿠폰 수량을 줄이고, 줄일 수 없다면 에러를 낸다.

        MemberCoupon memberCoupon = MemberCoupon.builder()
                .memberId(memberId)
                .isUsed(false)
                .build();
        memberCoupon.setCouponId(couponId);

        memberCouponRepository.save(memberCoupon);
    }
```

- 결과: **100개**의 쿠폰 발급 & 평균 **3.4초**의 처리속도
- DB Lock으로 인해 **처리 시간이 약 17% 증가** & 비관적 Lock은 **분산 환경에서 불가능**한 단점
  ![image](https://github.com/user-attachments/assets/1a7920ab-5181-4649-beca-831bdf9b620a)
  

2. **Redis를 중간에 도입하여 해결**
- Redis가 Redis 명령어를 single thread로 처리하는 방식을 이용
- 단순히 쿠폰의 개수만 세는 역할
  - 복잡한 분산 Lock 사용X
  - Redis의 원자적 연산인 increment를 사용하여 문제 해결
```
public void issueLimitedCoupon(Long couponId, Long memberId) {

    // redis의 increment연산 활용
    Long issuedCouponCount = countRepository.incrementCouponCount();
    Coupon coupon = couponRepository.findById(couponId)
            .orElseThrow(() -> new NotFoundException("coupon Not Found", couponId));
    if (coupon.getQuantity() < issuedCouponCount) {
        throw new CouponException("쿠폰 수량 부족");
    }

    MemberCoupon memberCoupon = MemberCoupon.builder()
            .memberId(memberId)
            .isUsed(false)
            .build();
    memberCoupon.setCouponId(couponId);

    memberCouponRepository.save(memberCoupon);
}
```
- 결과: **100개**의 쿠폰 발급 & 평균 **3.6초**의 처리속도
- 추가 네트워크 비용으로 인해 **처리 시간이 약 25% 증가**  
- ![image](https://github.com/user-attachments/assets/5e1c3abc-3248-4029-aa85-096a057d44e3)

### 결론
- Redis를 사용하는 방법이 8%정도의 시간이 더 걸리지만, 추후 분산환경으로 변경을 고려하여 Redis를 선택하였다. 

### 문제상황2
- 단기간의 급격한 트래픽 상승으로인해 **DB부하**
  - (TEST용 DB를 만들어서 사용하였다.)
- 결과: 최고 **CPU 사용율 88.61%**
![image](https://github.com/user-attachments/assets/ffae844e-546d-47b5-8dad-8720099aed64)


### 해결방법
- kafka를 사용한 처리량 조절
  - ex. 선착순 쿠폰 1000개를 발급일때
  - 비용이 높은 쓰기 작업을 DB에 바로 접근 대신 Message Queue에 저장 후 처리
```
...

// memberCouponRepository.save(memberCoupon);

// DB에 바로 접근하는 대신 kafka에 이벤트 발급 -> consumer에서 소비
kafkaProducer.send("issue_coupon", memberCoupon);
```
- 결과: 최고 **CPU 사용율 52.42%**
- ![image](https://github.com/user-attachments/assets/c65d852f-1f5d-46b4-b52d-bbe267499cce)

</details>

## DB와 이미지 파일 동기화문제 해결
### 상품 혹은 배너와같은 이미지가 포함된 컬럼을 삭제할때 이미지파일은 DB 트랜잭션에 포함이 되지 않는 문제 발생
- 이미지를 DB 컬럼과 같이 삭제해버리면 rollback이 안되므로 컬럼에 삭제처리만을 해준다.
- 일정시간마다(linux crontab) 각 서비스의 database를 돌면서 삭제된지 일정기간이 지난 컬럼을 찾는다.
  - mysql 원격접속 & .sql 파일 실행
- 검색된 컬럼에 쓰여있는 경로로 이동하여 이미지 파일을 삭제한다.
  - crontab이 돌아가는 서버와 이미지가 저장되는 서버가 다르기에 ssh를 사용하여 원격으로 삭제한다.

<details>
  <summary>*<b>자세한 사항은 펼쳐보기</b>*</summary>

### 문제상황
- ex. 제품을 삭제하는 도중 rollback이 된다면 이미 삭제된 이미지는 rollback이 안되잖아???
- 상품 혹은 배너와같은 이미지가 포함된 컬럼을 삭제할때 이미지파일은 DB 트랜잭션에 포함이 되지 않는 문제 발생

### 해결방법
- 따라서 이 프로젝트에서는 제품 이미지에 삭제됨 컬럼을 추가하였고, 실제 이미지는 즉시 삭제가아닌 추후삭제로 결정하였습니다.
```
// ProductImage.Java

@Entity
@DynamicInsert
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productImageId;

    ...

    @ColumnDefault(value = "false")
    private boolean isDelete;

    ...

}
```
- 추후삭제에 대한 고민은 일정기간이 지난 후(복구 가능 기간) 자동으로 DB를 검색해 일정기간이 지나고, isDelete=true인 이미지를 실제파일에서 삭제하려고 하였습니다.
- 스케줄링 도구에대해서는 spring scheduler와 linux crontab을 고민하였는데 현재 메모리 부족과 이미지들이 분산되어 저장되어있는 서비스 구조상 spring scehduler 를 사용하려면 새로운 스케줄러 서비스를 만들거나, 임의의 서비스에 어색한 코드들이 들어갈 수 있어 간편한 linux crontab을 사용하기로 결정하였습니다.

- **해결 순서**
1. 원격으로 mysql에 접속하여 각 데이터베이스의 삭제할 이미지파일의 경로를 불러오고 DB에서 삭제한다.
2. 삭제할 이미지파일의 경로로 원격으로 접속하여 이미지를 삭제한다.
3. crontab을 통해 일정기간마다 scheduling 설정을 해준다.

### 적용 코드
- DB에서 이미지파일의 경로 검색및 삭제
```
// product-image.sql - 제품 이미지 sql 예시

SELECT CONCAT(path, physical_name) FROM product_image WHERE isDelete ='true' AND modified_at < DATE_ADD(NOW(), INTERVAL -1 MONTH);
DELETE FROM product_image WHERE isDelete ='true' AND modified_at < DATE_ADD(NOW(), INTERVAL -1 MONTH);
```

- 원격으로 DB접근 후 ssh통신을 통해 파일 삭제
```
# delete-images.sh 

PWD=<비밀번호>
SERVER=(서버들...)
PORTS=(포트들...)
SQLS=(sql들...)
KEY_FILE=(EC2 key file들...)

for ((cnt=0 ; cnt < 2 ; cnt++));
do
mysql -u root -p${PWD} mydb -h ${SERVER[$cnt]} -P ${PORTS[$cnt]} < ${SQLS[$cnt]} | grep / | xargs ssh -i ${KEY_FILE} ubuntu@${SERVER[$cnt]} sudo rm
done
```

- 위의 프로세스를 crontab에 등록
```
# 매주 월요일 오전 1시에 test.sh 를 실행
0 1 * * 1 /home/script/test.sh
```

### 결과
- 위의 과정을 따로 실행시 결과 (**명령어를 rm 대신 echo로 실행**)
![image](https://github.com/user-attachments/assets/a95f293e-2868-4631-b177-99e8cbac08c0)

</details>



## MSA 환경으로 인해 발생하는 Cost 최소화
### 현재 개발 PC1, 서버 PC1, EC2 free tier 2 => 개발PC를 제외한 서버 2대가 존재하는데 로컬 서버PC의 메모리 사용량이 90%가 넘어서 죽는 문제 발생
- 시도해본 내용
1. sacle out
    - 가장 간단하게 해결이 가능하지만 비용이 없는 free tier를 무한정 만들수는 없기에 1대만 추가하였다.
2. 운영체제 최적화
    - ubuntu desktop -> ubuntu server
3. docker 메모리제한
4. jvm heap 제한

<details>
  <summary>*<b>자세한 사항은 펼쳐보기</b>*</summary>
  
### 문제 상황
  - local Server에서 4GB 메모리와 2GB 스왑메모리가 모두 사용중인 상태
  - 11개의 docker가 떠있지만 추가적으로 더 떠야하는 상태
![image](https://github.com/user-attachments/assets/fae8338a-32fb-4ee8-9e66-ce94619b1c3b)
![image](https://github.com/user-attachments/assets/d0e26b4a-c917-4669-80c8-6597920f8189)

### 해결방법
0. **swap 메모리 추가**
   - 기본적으로 서비스당 Ram에 필수적으로 떠있어야하는 메모리가 있기에 swap메모리를 2GB -> 4GB로 올렸지만 변치않음
1. **Scale out**
   - 서버가 먹통이된 상황이라 급하게 메모리수급을 위하여 새로운 EC2 freetier를 추가
   - 30%의 메모리 확보
  ![image](https://github.com/user-attachments/assets/d66e2a0a-8bae-4c51-b338-a7022f8de45a)
2. **운영체제 최적화**
  - ubuntu desktop을 ubuntu server로 전환(불필요한 부분인 GUI 제거)
  - 일반 메모리는 이전과 비슷하지만 swap메모리의 용량이 늘어남
  ![image](https://github.com/user-attachments/assets/6b1013b5-2b28-4a6a-b209-6c4dab3dac7e)
3. **docker 메모리제한**
  - 요청이 많이들어오지 않는다 가정하고 각 docker의 메모리를 제한하여 compact하게 사용하기로 해보았다.
  - ex. mysql 메모리를 200mb로 제한 -> swap메모리 자동으로 400mb 사용(2배)
  - 메모리 사용량 8% 감소, swap 메모리 사용량 16% 증가 -> 총 메모리사용량이 같으므로 무의미하다.
  - mysql의 권장사용 메모리량을 만족하지 못하여 실행하지 않는것으로 결정하였다.
  ![image](https://github.com/user-attachments/assets/ddbf9d0d-464d-4eca-ac0a-be04acd9863b)
4. **service jvm heap 제한**
  - java기반의 서비스의 jvm의 heap을 제한하여 메모리를 확보하려고 시도해보았다.
  - 아래와같이 512 -> 256으로 jvm heap을 제한하여봤지만 큰 영향은 없었다.
  ![image](https://github.com/user-attachments/assets/392cd360-0c6a-4d0f-9581-d22f26032829)

**결론**
- 현재 서비스들을 사용하기 위한 기본적인 메모리가 없기에 다른 방법들을 사용해도 힘든것같다.
- 결국 scale out을 하건 서비스들을 경량화하는 방향으로 가야할듯 하다. (ex. logstash -> fileBeats, Mysql -> SQL Lite) 

</details>

# 의사결정 사항

|  요구사항  |  선택지  |  설명  |
| ---- | ---- | ---- |
| Message Queue | Kafka VS RabbitMQ | MQ는 Kafka를 사용하였습니다. Kafka는 마이크로서비스 간의 느슨한 결합을 유지하고, 대용량 트래픽을 처리하기 위해 사용되었습니다. RabbitMQ는 Kafka에 비해 신뢰성과 보안성이 좋지만 현재 서비스에서 선착순 쿠폰 발행과같은 대용량 데이터 처리가 필요하고 Kafka에 비해 sacle out이 힘들다는 단점이 있어 Kafka를 선택하였습니다. |
| 이벤트 스케줄링 | spring Scheduler VS Crontab | 이벤트 스케줄러는 linux crontab을 선택하였습니다. spring scheduler는 기존의 프레임워크 위에서 작성되기에 작성이 편리하고, spring내에서 돌아가기에 log를 통합적으로 남기기에도 좋은 장점이있지만 scheduler가 필요한 서비스가 하나가 아니기에 코드가 파편화된다는 단점이 있습니다. 따로 스케줄러 서비스를 만들 수 있지만 현재 cost가 매우 부족한 상황에서는 힘들기에, 큰 비용을 사용하지 않고 운영체제 위에서 돌아가는 linux crontab을 사용하여 스케줄링을 관리하였습니다. |
| 데이터베이스 | MySQL VS SQL Lite | 데이터베이스는 Mysql을 선택하였습니다. MSA 환경을 혼자 구축하려다보니 점점 cost를 중점적으로 보기 시작하였고, cost를 최대한 줄이기 위하여 DB를 SQL Lite로 경량화하는 방식도 구현해 보았는데 동시 쓰기가 되지않아 선착순 쿠폰 발행시 속도가 많이 느려져 결국 MySQL을 사용하기로 결정하였습니다. |

