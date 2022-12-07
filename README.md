## 강유진 기술 과제 (다우기술 Web Application 개발 직무)
<hr />

# 서버 구동 방법
```shell
$ git clone https://github.com/zrefind/daou-assignment.git
$ cd daou-assignment
$ ./gradlew clean build
$ cd build/libs
$ java -jar daou-assignment-0.0.1-SNAPSHOT.jar -Djava.net.preferIPv4Stack=true
```
이 애플리케이션은 application.properties 파일에서 접근 허용 IP를 지정합니다.
- 접근 허용 IP를 IPv4로 사용하기 위해 애플리케이션을 실행할 때 `-Djava.net.preferIPv4Stack=true` 옵션을 적용합니다.
- 접근 허용 IP를 IPv6로 사용하려면 `-Djava.net.preferIPv6Stack=true` 옵션을 적용하거나 해당 옵션 없이 실행하십시오.
    - `java -jar daou-assignment-0.0.1-SNAPSHOT.jar -Djava.net.preferIPv6Stack=true`
    - `java -jar daou-assignment-0.0.1-SNAPSHOT.jar`
- 소스 원본은 `127.0.0.1` `0:0:0:0:0:0:0:1` IPv4, IPv6가 허용 IP로 적용되어 있습니다.

# 애플리케이션 필수 설정
> application.properties 파일에서 설정

| 옵션            | 설명                                | 예시                                                                      | 비고                                                                   |
|:--------------|:----------------------------------|:------------------------------------------------------------------------|:---------------------------------------------------------------------|
| `target.dir`  | 스케줄러가 읽을 파일이 있는 디렉토리 경로           | `Linux`: /opt/daou/files<br>`Windows`: C:\\\Users\\\user\\\daou\\\files | 이 설정값에 해당하는 경로가 애플리케이션을 실행하는 로컬 머신에 반드시 존재해야 합니다.                    |
| `target.file` | 스케줄러가 읽을 파일명                      | sample.csv                                                              | src/main/resources 하위에 테스트에서 사용한 `sample.csv` `sample.txt` 파일이 있습니다. |
| `allowed.ip`   | 접근을 허용할 IP - 콤마(,)를 통해 구분         | 127.0.0.1,0:0:0:0:0:0:0:1                                               ||
|`log.config.dir`| REST API가 생성하는 로그 파일이 저장될 디렉토리 경로 | `Linux`: /Users/kang/logs<br>`Windows`: C:\\\Users\\\user\\\logs        ||

# API 명세

### 인증 관련

| 메서드  | URI               | 설명   | 출력 포맷  | 입력 예시                                                                                   | 비고  |
|:-----|:------------------|:-----|:------:|:----------------------------------------------------------------------------------------|:----|
| POST | /api/auth/sign-up | 회원가입 |  JSON  | { "email": "tester@test.com", "password": "1q2w3e4r!", "passwordConfirm": "1q2w3e4r!" } ||
| POST | /api/auth/sign-in | 로그인  |  JSON  | { "email": "tester@test.com", "password": "1q2w3e4r!" }                                 ||

### 결산 관련

| 메서드    | URI                                         | 설명         | 출력 포맷 | 입력 예시                                                                                                                                    | 비고                                                                                   |
|:-------|:--------------------------------------------|:-----------|:-----:|:-----------------------------------------------------------------------------------------------------------------------------------------|:-------------------------------------------------------------------------------------|
| GET    | /api/settlement/search/{factor}/{from}/{to} | 시간대별 항목 조회 | JSON  | `factor`: newbie(가입자) / bolter(탈퇴자) / payment(결제 금액) / used(사용 금액) / sales(매출 금액) 중 택1<br>`from`: 조회할 시간대의 시작 시간<br>`to`: 조회할 시간대의 종료 시간 | 1. `factor` `from` `to` 모두 필수값입니다.<br> 2. `from` `to` 값은 `yyyyMMddHH` 형식으로 입력해야 합니다. |
| POST   | /api/settlement/enrollment                  | 시간대별 항목 입력 | JSON  | { "time": "2022113000", "newbie": 1, "bolter": 2, "payment": 3, "used": 4, "sales": 5 }                                                  | `time` 값은 `yyyyMMddHH` 형식으로 입력해야 하며 필수값입니다.                                          |
| PUT    | /api/settlement/correction                  | 시간대별 항목 수정 | JSON  | { "time": "2022113000", "newbie": 1, "bolter": 2, "payment": 3, "used": 4, "sales": 5 }                                                  | `time` 값은 `yyyyMMddHH` 형식으로 입력해야 하며 필수값입니다.                                          |
| DELETE | /api/settlement/elimination                 | 시간대별 항목 삭제 | JSON  | { "time": "2022113000" }                                                                                                                 | `time` 값은 `yyyyMMddHH` 형식으로 입력해야 하며 필수값입니다.                                          |