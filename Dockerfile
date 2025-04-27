# 1. 사용할 베이스 이미지 지정 (Java 21 런타임)
FROM eclipse-temurin:21-jdk-jammy

# 2. 작업 디렉토리 생성
WORKDIR /app

# 3. 빌드된 .jar 파일을 컨테이너 내부로 복사
COPY build/libs/well-c-code-git-backend-0.0.1-SNAPSHOT.jar app.jar

# 4. 서버 실행 명령
ENTRYPOINT ["java", "-jar", "app.jar"]
