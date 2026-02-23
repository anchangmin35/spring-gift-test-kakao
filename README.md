# spring-gift-test

## 실행 방법

### 전체 테스트 실행

```bash
./gradlew test
```

기존 RestAssured 테스트와 Cucumber BDD 시나리오가 함께 실행됩니다.

### 테스트 구성

| 종류 | 위치 | 설명 |
|---|---|---|
| Cucumber Feature 파일 | `src/test/resources/features/` | 한글 Gherkin 시나리오 (7개) |
| Step Definitions | `src/test/java/gift/cucumber/` | Feature 파일의 스텝 구현 |
| RestAssured 테스트 | `src/test/java/gift/` | 기존 행동 기반 테스트 (7개) |

### 요구사항

- Java 21
- Gradle Wrapper 포함 (별도 설치 불필요)