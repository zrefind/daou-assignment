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
해당 프로젝트는 application.properties 파일에서 접근 허용 IP를 지정합니다.
- 접근 허용 IP를 IPv4로 사용하기 위해 애플리케이션을 실행할 때 `-Djava.net.preferIPv4Stack=true` 옵션을 적용합니다.
- 접근 허용 IP를 IPv6로 사용하려면 `-Djava.net.preferIPv6Stack=true` 옵션을 적용하거나 해당 옵션 없이 실행하십시오.
    - `java -jar -Djava.net.preferIPv6Stack=true daou-assignment-0.0.1-SNAPSHOT.jar`
    - `java -jar daou-assignment-0.0.1-SNAPSHOT.jar`
- 소스 원본은 `127.0.0.1` `0:0:0:0:0:0:0:1` IPv4, IPv6가 허용 IP로 적용되어 있습니다.