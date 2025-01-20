# 베이스 이미지로 OpenJDK 사용
FROM openjdk:23-jdk

# 작업 디렉토리를 설정 (경로를 수정)
WORKDIR /ploytechcourse

# JAR 파일을 Docker 이미지로 복사
COPY build/libs/ploytechcourse.jar app.jar

# 컨테이너 시작 시 실행할 명령어
ENTRYPOINT ["java", "-jar", "app.jar"]
