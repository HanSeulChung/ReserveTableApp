# ReserveTableApp

## 사용한 skill
<div align=left> 
  <img src="https://img.shields.io/badge/Windows-0078D6?style=for-the-badge&logo=windows&logoColor=white">
  <img src="https://img.shields.io/badge/IntelliJ_IDEA-000000?style=for-the-badge&logo=intellij-idea&logoColor=white">
  <img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white">
  <img src="https://img.shields.io/badge/JDK-Oracle_Open_JDK-007396?style=for-the-badge&logo=mariaDB&logoColor=white"> 
  <img src="https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white">
  </br>
  <img src="https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white"> 
  <img src="https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring&logoColor=white">
  <img src="https://img.shields.io/badge/Spring_Security-6DB33F?style=for-the-badge&logo=spring-security&logoColor=white">
  <img src="https://img.shields.io/badge/Spring_Data_JPA-6DB33?style=for-the-badge&logo=mariaDB&logoColor=white"> 
  </br>  
  <img src="https://img.shields.io/badge/mariaDB-003545?style=for-the-badge&logo=mariaDB&logoColor=white">
  <img src="https://img.shields.io/badge/Json_Web_Tokens-000000?style=for-the-badge&logo=json-web-tokens&logoColor=white"> 
  <img src="https://img.shields.io/badge/Lombok-BC4520?style=for-the-badge&logo=lombok&logoColor=white"> 
  <img src="https://img.shields.io/badge/html5-E34F26?style=for-the-badge&logo=html5&logoColor=white">
  </br>
  <img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white">
  <img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white">
</div>

## 프로젝트 개요
이 프로젝트는 사용자들이 식당이나 점포를 이용하기 전에 미리 예약을 할 수 있는 서비스를 개발하는 것을 목표로 합니다. 

예약 기능을 통해 사용자들은 방문 시간을 미리 정하고 대기 없이 식사나 서비스를 즐길 수 있어 더욱 편리한 이용이 가능합니다.

또한 가게 점주도 들어온 예약을 승인 및 거절을 할 수 있어 가게 운영에 어려움 없이 이용할 수 있습니다.

## 프로젝트 엔티티
![image](https://github.com/HanSeulChung/ReserveTableApp/assets/94779505/cb82aa17-c7a8-4dc3-ba8b-ab294ba78ba5)

## 타임리프 구현

![로그인](https://github.com/HanSeulChung/ReserveTableApp/assets/94779505/846f92fd-746b-493e-b8da-ce1b89a7a3d7)
* Spring Security를 이용해 메인페이지를 달리 할 수 있습니다.
  * 로그인 안했을 경우
  * Owner용 로그인
  * User용 로그인

* 공통
  * Owner 전용 회원가입, User 전용 회원가입
  * 로그인
  *  비밀번호 찾기 기능(회원가입 시 입력했던 이메일로 초기화 가능, 단 회원가입시 이메일 실존 유무 확인 안하므로 주의해야함)

* Owner 전용
![예약승인](https://github.com/HanSeulChung/ReserveTableApp/assets/94779505/f7c9eb49-0b11-4bfd-84af-c739be62635f)
  * 가게 추가, 삭제, 수정
  * 자신의 가게에 예약이 걸려있는 목록 확인 기능
  * 예약 승인, 거절 기능
    
* User 전용
![예약](https://github.com/HanSeulChung/ReserveTableApp/assets/94779505/5dc3535b-bd99-484b-bfa4-7e60e60c0ebd)
  * 추가된 모든 가게 목록
  * 예약 기능, 예약 삭제, 수정 기능(Owner가 예약 승인, 거절하지 않은 초기 예약 상태에서만 가능)


리뷰 작성 및 리뷰 확인은 추후 추가.

<details>
<summary><h2>API 문서</h2></summary>
  <div markdown=1>
    
이 프로젝트는 타임리프 구현 전에 다음과 같은 API 엔드포인트를 제공 했으며 이를 기반으로 간단한 타임리프 View를 추가하였습니다.


### 공통 인증
* 회원가입
  + 가입시 아이디와 이메일은 같은 경우 가입이 불가능 함
  + 비밀번호는 암호화된 상태로 database에 저장됩니다.
* 로그인
  + 로그인후 토큰 유효 시간은 1시간 입니다.
* 회원정보 수정(핸드폰 번호) > TODO
* 비밀번호 수정 > TODO

#### 점주 회원가입
POST /auth/owner/signup
* 요청 예시
```json
{
  "userId": "owner1",
  "userName": "가게주인1",
  "phone": "010-0101-1111",
  "email": "이메일@naver.com",
  "password": "비밀번호"
}
```


#### 점주 로그인
POST /auth/owner/signin
* 요청 예시
```json
{
  "userId": "owner1",
  "password": "비밀번호"
}
```

#### 사용자 회원가입
POST auth/signup
* 요청 예시
```json
{
  "userId": "user1",
  "userName": "사용자1",
  "phone": "010-0101-1111",
  "email": "user이메일@naver.com",
  "password": "비밀번호"
}
```

#### 사용자 로그인
POST auth/signin
* 요청 예시

```json
{
  "userId": "user1",
  "password": "비밀번호"
}
```

----------
### 점주 
#### 가게 등록
POST /auth/owner/register/store
* 요청 예시

```json
{
    "storeName" : "맛있는 삼겹살집",
    "phone" : "010-0000-0001",
    "addr" : "삼겹살집 주소1",
    "addrDetail" : "삼겹살집 상세 주소 1",
    "description" : "여기는 신선하고 맛있는 삼겹살을 판매하는 곳입니다."
}
```

#### 가게 수정
POST /auth/owner/update/store
* 요청 예시

```json
{
    "storeName" : "맛있는 삼겹살집",
    "phone" : "010-0000-0001",
    "addr" : "삼겹살집 주소1",
    "addrDetail" : "삼겹살집 상세 주소 수정1",
    "description" : "여기는 신선하고 맛있는 삼겹살을 판매하는 곳입니다."
}
```

#### 가게 삭제
DELETE /auth/owner/register/store

#### 가게 조회
+ 가게주인 아이디로 가게 조회 기능

GET auth/owner/read/store/?ownerId=onwer1

#### 사용자 예약 조회
/auth/owner/reservation/{ownerId}/{storeId}

GET /auth/owner/reservation/onwer1/1

#### 사용자 예약 승인
POST /auth/owner/reservation/approve?reservationId=1

#### 사용자 예약 거절
POST /auth/owner/reservation/refuse?reservationId=1


#### 리뷰 조회
#### 리뷰 삭제


--------

### 사용자
#### 가게 조회
* 가게 전체 조회

  GET /store/all

* 가게 이름으로 조회
  
  GET /store/search/storename/맛있는 삼겹살집

* 가게 주소로 조회

  GET /store/search/storeaddr/삼겹살집 주소1


#### 예약 등록
POST /reserve
* 요청 예시
```json
{
    "storeId" : 1,
    "storeName" : "맛있는 삼겹살집",
    "resDt" : "2023-10-02T21:50:00",
    "people" : 2
}
```

#### 예약 수정
+ 점주가 예약을 승인하거나 거절하지 않은 경우, WAITING일 경우에만 수정 가능

  POST /update/reservation?reservationId=1
* 요청 예시
```json
{
    "storeId" : 1,
    "storeName" : "맛있는 삼겹살집",
    "resDt" : "2023-10-02T21:50:00",
    "people" : 2
}
```

#### 예약 삭제
DELETE /delete/reservation?reservationId=1

#### 예약 조회
+ Mypage에서 조회

  GET /get/myreservations

#### 키오스크에서 자신의 예약 조회
+ 승인된 예약만 조회 가능

  GET kiosk/user1?storeId=1

#### 키오스크 도착
+ 예약이 승인되었을 경우에만 가능

  POST /kiosk/arrive/?reservationId=1


#### 리뷰 등록
POST /register/review?reservationId=1
* 요청 예시 
```json
{
    "score" : 5,
    "review" : "맛있었어요."
}
```

#### 리뷰 수정
POST /update/review?reviewId=3
* 요청 예시
```json
{
    "score" : 3,
    "review" : "맛은 있었지만, 간이 너무 강했습니다."
}
```

#### 리뷰 삭제
DELETE /delete/review?reviewId=1

#### 리뷰 조회
GET /get/review?reviewId=1

#### 리뷰 전체 조회
</div>
</details>

