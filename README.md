## 강유진 기술 과제 (다우기술 Web Application 개발 직무)
<hr />

# 서버 구동 방법
```shell
$ git clone https://github.com/zrefind/daou-assignment.git
$ cd daou-assignment
$ ./gradlew clean build
$ cd build/libs
$ java -jar -Djava.net.preferIPv4Stack=true daou-assignment-0.0.1-SNAPSHOT.jar
```
이 애플리케이션은 application.properties 파일에서 접근 허용 IP를 지정합니다.
- 접근 허용 IP를 IPv4로 사용하기 위해 애플리케이션을 실행할 때 `-Djava.net.preferIPv4Stack=true` 옵션을 적용합니다.
- 접근 허용 IP를 IPv6로 사용하려면 `-Djava.net.preferIPv6Stack=true` 옵션을 적용하거나 해당 옵션 없이 실행하십시오.
    - `java -jar -Djava.net.preferIPv6Stack=true daou-assignment-0.0.1-SNAPSHOT.jar`
    - `java -jar daou-assignment-0.0.1-SNAPSHOT.jar`
- 소스 원본은 `127.0.0.1` `0:0:0:0:0:0:0:1` IPv4, IPv6가 허용 IP로 적용되어 있습니다.

# 애플리케이션 필수 설정
> application.properties 파일에서 설정

| 옵션            | 설명                                | 예시                        | 비고                                               |
|:--------------|:----------------------------------|:--------------------------|:-------------------------------------------------|
| `target.dir`  | 스케줄러가 읽을 파일이 있는 디렉토리 경로           | /opt/daou/files           | 이 설정값에 해당하는 경로가 애플리케이션을 실행하는 로컬 머신에 반드시 존재해야 합니다. |
| `target.file` | 스케줄러가 읽을 파일명                      | sample.csv                ||
| `allowed.ip`   | 접근을 허용할 IP - 콤마(,)를 통해 구분         | 127.0.0.1,0:0:0:0:0:0:0:1 ||
|`log.config.dir`| REST API가 생성하는 로그 파일이 저장될 디렉토리 경로 | /Users/kang/logs          ||