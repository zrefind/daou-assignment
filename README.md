## 강유진 기술 과제 (다우기술 Web Application 개발 직무)
<hr />

# 애플리케이션 설정, 빌드, 구동

### 1. 프로젝트 소스 내려받기
```shell
$ git clone https://github.com/zrefind/daou-assignment.git
```

### 2. 애플리케이션 구동을 위한 필수 설정
> 애플리케이션을 정상적으로 빌드하고 구동하기 위해서 `application.properties` 파일에서 필수 옵션값을 설정해야 합니다.
```shell
$ cd daou-assignment
$ vim src/main/resources/application.properties
```
| 옵션명         | 설명                          | 예시                                                                | 비고                                                                                                                            |
|:------------|:----------------------------|:------------------------------------------------------------------|:------------------------------------------------------------------------------------------------------------------------------|
| server.port | 웹 서버 포트                     | 8080                                                              |                                                                                                                               |
| target.dir  | 스케줄러가 읽을 파일이 있는 디렉토리의 절대 경로 | `Linux`: /opt/daou/files<br>`Windows`: C:\\\Users\\\user\\\files  | 이 옵션값에 해당하는 경로가 반드시 존재해야 합니다                                                                                                  |
| target.file | 스케줄러가 읽을 파일명                | sample.csv                                                        | 1. 이 옵션값에 해당하는 파일이 `target.dir` 경로에 반드시 존재해야 합니다<br>2. src/main/resources 하위에 테스트에서 사용한<br>`sample.csv` `sample.txt` 파일이 있습니다 |
| allowed.ip  | 접근을 허용할 IP                  | 127.0.0.1,0:0:0:0:0:0:0:1                                         | 콤마(,)를 통해 각 IP를 구분하여 입력합니다                                                                                                    |
| log.dir     | API가 생성하는 로그를 저장할 파일의 절대 경로 | `Linux`: /Users/kang/logs<br>`Windows`: C:\\\Users\\\user\\\logs  ||

### 3. 애플리케이션 빌드 및 구동
```shell
$ ./gradlew clean build
$ cd build/libs
$ java -jar daou-assignment-0.0.1-SNAPSHOT.jar -Djava.net.preferIPv4Stack=true
```
1. 애플리케이션 빌드 시 테스트 코드 실행
   - 이 프로젝트는 총 35개의 테스트 코드가 작성되어 있습니다.
   - 성공적인 빌드를 위해 빌드 시 전체 테스트에서 `RateLimiterTest`는 수행되지 않습니다.
     - 전체 테스트 시 Bucket의 수를 확보하기 위해 `RateLimiterTest`의 테스트 코드는 `@Disabled` 어노테이션으로 비활성화되어 있습니다.
     - `@Disabled` 어노테이션을 제거하고 `RateLimiterTest`의 테스트 코드를 실행하면 성공적으로 수행됩니다.
2. 접근 허용 IP의 버전을 지정하기 위한 옵션
   - 접근 허용 IP를 IPv4로 사용하려면 애플리케이션을 실행할 때 `-Djava.net.preferIPv4Stack=true` 옵션을 적용합니다.
   - 접근 허용 IP를 IPv6로 사용하려면 애플리케이션을 실행할 때 `-Djava.net.preferIPv6Stack=true` 옵션을 적용합니다.
   - `application.properties` 파일의 `allowed.ip` 옵션의 값을 설정할 때 IPv4, IPv6 정보를 모두 입력하는 것을 권장합니다.
     - 소스를 처음 내려받으면 해당 옵션의 값이 `127.0.0.1,0:0:0:0:0:0:0:1`로 설정되어 있습니다.

### 4. H2 DB Console 접속 URL 및 테스트 데이터 관련 참고사항
   - H2 DB Console 접속 URL: `http://localhost:{server.port}/h2-console`
   - 애플리케이션이 구동되면 테스트 편의를 위한 데이터가 DB에 입력됩니다. (`src/main/resources/test.sql` 참조)
     - `MEMBER`
       - 계정/패스워드 정보가 `admin@test.com / 1q2w3e4r!`인 데이터가 추가됩니다.
       - API 테스트 시 해당 계정 정보로 사용자 인증을 할 수 있습니다.
     - `SETTLEMENT`
       - 2022년 11월 30일의 결산 데이터가 추가됩니다.

# API 명세

### 회원가입
| 메서드  | URI               | 출력 포맷 | 비고                                                           |
|:-----|:------------------|:-----:|:-------------------------------------------------------------|
| POST | /api/auth/sign-up | JSON  | 테스트 편의를 위해 `admin@test.com / 1q2w3e4r!` 회원정보가 DB에 저장되어 있습니다. |
```json lines
// 입력 예시
{
  "email": "tester@test.com",
  "password": "1q2w3e4r!",
  "passwordConfirm": "1q2w3e4r!"
}
// 응답 예시
{
  "responseType": "DONE",
  "email": "test@test.com",
  "jwt": null
}
```

### 로그인
| 메서드  | URI               | 출력 포맷 | 비고                                                           |
|:-----|:------------------|:-----:|:-------------------------------------------------------------|
| POST | /api/auth/sign-in | JSON  | 테스트 편의를 위해 `admin@test.com / 1q2w3e4r!` 회원정보가 DB에 저장되어 있습니다. |
```json lines
// 입력 예시
{
  "email": "admin@test.com",
  "password": "1q2w3e4r!"
}
// 응답 예시
{
  "responseType": "DONE",
  "email": "admin@test.com",
  "jwt": null
}
```

### 시간대별 결산 항목 조회
| 메서드 | URI                                         | 출력 포맷 | 비고                                                                                                                                                    |
|:----|:--------------------------------------------|:-----:|:------------------------------------------------------------------------------------------------------------------------------------------------------|
| GET | /api/settlement/search/{factor}/{from}/{to} | JSON  | 1. `factor` `from` `to` 모두 필수값입니다.<br>2. `factor`값은 newbie, bolter, payment, used, sales 중 택1하여 입력합니다.<br>3. `from` `to`는 `yyyyMMddHH` 형식으로 입력해야 합니다. |
```json lines
// 입력 예시
http://localhost:8080/api/settlement/search/newbie/2022113000/2022113010
// 응답 예시
{
  "responseType": "DONE",
  "time": null,
  "newbie": null,
  "bolter": null,
  "payment": null,
  "used": null,
  "sales": null,
  "searchResult": 310
}
```

### 시간대별 결산 항목 입력
| 메서드  | URI                        | 출력 포맷 | 비고                                          |
|:-----|:---------------------------|:-----:|:--------------------------------------------|
| POST | /api/settlement/enrollment | JSON  | `time` 값은 `yyyyMMddHH` 형식으로 입력해야 하며 필수값입니다. |
```json lines
// 입력 예시
{
  "time": "2022110100",
  "newbie": 1,
  "bolter": 2,
  "payment": 3,
  "used": 4,
  "sales": 5
}
// 응답 예시
{
  "responseType": "DONE",
  "time": "2022-11-01 00",
  "newbie": 1,
  "bolter": 2,
  "payment": 3,
  "used": 4,
  "sales": 5,
  "searchResult": null
}
```

### 시간대별 결산 항목 수정
| 메서드 | URI                        | 출력 포맷 | 비고                                          |
|:----|:---------------------------|:-----:|:--------------------------------------------|
| PUT | /api/settlement/correction | JSON  | `time` 값은 `yyyyMMddHH` 형식으로 입력해야 하며 필수값입니다. |
```json lines
// 입력 예시
{
  "time": "2022113000",
  "newbie": 1,
  "bolter": 2,
  "payment": 3,
  "used": 4,
  "sales": 5
}
// 응답 예시
{
  "responseType": "DONE",
  "time": "2022-11-30 00",
  "newbie": 1,
  "bolter": 2,
  "payment": 3,
  "used": 4,
  "sales": 5,
  "searchResult": null
}
```

### 시간대별 결산 항목 삭제
| 메서드    | URI                         | 출력 포맷 | 비고                                          |
|:-------|:----------------------------|:-----:|:--------------------------------------------|
| DELETE | /api/settlement/elimination | JSON  | `time` 값은 `yyyyMMddHH` 형식으로 입력해야 하며 필수값입니다. |
```json lines
// 입력 예시
{
  "time": "2022113000"
}
// 응답 예시
{
  "responseType": "DONE",
  "time": "2022-11-30 00",
  "newbie": 1,
  "bolter": 2,
  "payment": 3,
  "used": 4,
  "sales": 5,
  "searchResult": null
}
```