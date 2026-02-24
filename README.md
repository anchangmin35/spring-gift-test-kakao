# spring-gift-test

## 실행 방법

### 단위/행동 테스트 (H2, Docker 불필요)

```bash
./gradlew test
```

RestAssured 기반 행동 테스트를 H2 인메모리 DB로 실행합니다.

### Cucumber BDD 테스트 (PostgreSQL + Docker)

```bash
./gradlew cucumberTest
```

Docker Compose가 자동으로 PostgreSQL을 시작하고, Cucumber 시나리오를 실행한 뒤 정리합니다.

### 테스트 구성

| 명령어 | DB | 대상 | 설명 |
|---|---|---|---|
| `./gradlew test` | H2 | RestAssured 테스트 (7개) | Docker 불필요, 빠른 피드백 |
| `./gradlew cucumberTest` | PostgreSQL | Cucumber 시나리오 (7개) | Docker 필요, Production Parity |

| 종류 | 위치 |
|---|---|
| Cucumber Feature 파일 | `src/test/resources/features/` |
| Step Definitions | `src/test/java/gift/cucumber/` |
| RestAssured 테스트 | `src/test/java/gift/` |

### 요구사항

- Java 21
- Gradle Wrapper 포함 (별도 설치 불필요)
- Docker Desktop (cucumberTest 실행 시 필요)