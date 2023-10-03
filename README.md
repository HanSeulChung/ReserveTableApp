# ReserveTableApp

## 사용한 skill
<li>Windows</li>
<li>Java 11</li>
<li>Gradle 7.2</li>
<li>IntelliJ 2023.1.4</li>

<li> Spring boot 2.5.6 </li>
<li> JDK : Oracle Open JDK 20.0.1 </li>
<li> MariaDB </li>
<li> Spring Data JPA </li>
<li> Redis </li>
<li> Junit5 </li>
<li> jsonwebtoken 0.9.1 </li>
<li> apache.commons 4.3 </li>
<li> Lombok </li>
<li> Swagger 3.0.0 </li>


## 프로젝트 개요
이 프로젝트는 사용자들이 식당이나 점포를 이용하기 전에 미리 예약을 할 수 있는 서비스를 개발하는 것을 목표로 합니다. 

예약 기능을 통해 사용자들은 방문 시간을 미리 정하고 대기 없이 식사나 서비스를 즐길 수 있어 더욱 편리한 이용이 가능합니다.

또한 가게 점주도 들어온 예약을 승인 및 거절을 할 수 있어 가게 운영에 어려움 없이 이용할 수 있습니다.

## 프로젝트 엔티티
![image](https://github.com/HanSeulChung/ReserveTableApp/assets/94779505/cb82aa17-c7a8-4dc3-ba8b-ab294ba78ba5)


## API 문서
이 프로젝트는 다음과 같은 API 엔드포인트를 제공합니다.
### 공통 인증
#### 점주 회원가입
POST /auth/owner/signup
<li>요청 예시</li>
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
<li>요청 예시</li>

#### 사용자 회원가입
POST auth/signup
<li>요청 예시</li>
```json
{
"userId": "user",
"userName": "사용자",
"phone": "010-0101-1111",
"email": "이메일@naver.com",
"password": "비밀번호"
}
```

#### 사용자 로그인
POST auth/signin
<li>요청 예시</li>

----------
### 점주 
#### 가게 등록
POST /auth/owner/register/store

#### 가게 수정
POST /auth/owner/update/store

#### 가게 삭제
DELETE /auth/owner/register/store

#### 가게 조회
가게주인 아이디로 가게 조회 기능
GET auth/owner/read/store/?ownerId=onwer1


#### 사용자 예약 조회
/auth/owner/reservation/{ownerId}/{storeId}
GET /auth/owner/reservation/onwer1/1

#### 사용자 예약 승인
/auth/owner/reservation/approve?reservationId=1

#### 사용자 예약 거절
/auth/owner/reservation/refuse?reservationId=1


#### 리뷰 조회
#### 리뷰 삭제


--------

### 사용자
#### 예약 등록
#### 예약 수정
#### 예약 삭제
#### 예약 조회

#### 키오스크에서 자신의 예약 조회
승인된 예약만 조회 가능
GET kiosk/user1?storeId=1

#### 키오스크 도착
예약이 승인되었을 경우에만 가능
POST /kiosk/arrive/?reservationId=1


#### 리뷰 등록
POST /register/review?reservationId=1

#### 리뷰 수정
POST /update/review?reviewId=3

#### 리뷰 삭제
DELETE /delete/review?reviewId=1

#### 리뷰 조회
GET /get/review?reviewId=1

#### 리뷰 전체 조회

