# 2026 CMS API Assignment

간단한 CMS REST API 과제를 Spring Boot 4 기반으로 구현한 프로젝트입니다.

## Tech Stack

- Java 25
- Spring Boot 4.0.3
- Spring Security
- Spring Data JPA
- H2 Database
- Lombok

## 구현 내용

- JWT(access token only) 기반 로그인
- 콘텐츠 CRUD
- 콘텐츠 목록 페이징 조회
- 작성자 본인만 수정/삭제 가능
- `ADMIN` 사용자는 모든 콘텐츠 수정/삭제 가능
- 전역 예외 처리 및 공통 에러 응답
- H2 초기 스키마 구성 및 시드 데이터 자동 적재
- 인증/권한/콘텐츠 API 통합 테스트

## 패키지 구조

도메인 기준으로 `auth`, `content`, `user`를 나누고, 공통 관심사는 `common`에 배치했습니다.

```text
com.malgn
├─ auth
├─ content
├─ user
└─ common
```

## 인증 방식

인증은 JWT 기반으로 구현했습니다.

- `POST /api/auth/login`에서 사용자 인증 후 access token 발급
- 이후 보호된 API는 `Authorization: Bearer {token}` 헤더로 호출
- 과제 범위를 넘지 않도록 refresh token / blacklist / 재발급 로직은 구현하지 않았습니다

이 과제에서는 콘텐츠 권한 제어와 API 완성도를 우선하기 위해 `access token only` 구조로 제한했습니다.

## 테스트 계정

애플리케이션 시작 시 아래 계정이 자동 생성됩니다.

- `admin / admin1234` (`ADMIN`)
- `user1 / user1234` (`USER`)
- `user2 / user1234` (`USER`)

## 실행 방법

```bash
./gradlew bootRun
```

애플리케이션 실행 후 기본 포트는 `8080`입니다.

- API Base URL: `http://localhost:8080`
- H2 Console: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:test`
- Username: `sa`
- Password: 빈 값

## 테스트 방법

```bash
./gradlew test
```

## 주요 API

상세 명세는 [docs/api.md](/Users/yang/Downloads/2026-cms-api/docs/api.md) 에 정리했습니다.

- `POST /api/auth/login`
- `GET /api/auth/me`
- `POST /api/contents`
- `GET /api/contents?page=0&size=10`
- `GET /api/contents/{id}`
- `PUT /api/contents/{id}`
- `DELETE /api/contents/{id}`

## 권한 정책

- 인증되지 않은 사용자는 콘텐츠 API에 접근할 수 없습니다
- `USER`는 자신이 생성한 콘텐츠만 수정/삭제할 수 있습니다
- `ADMIN`은 모든 콘텐츠를 수정/삭제할 수 있습니다

## DB Schema

직접 정의한 테이블은 아래 두 개입니다.

- `users`
- `contents`

`contents`는 과제 요구 컬럼(`title`, `description`, `view_count`, `created_by`, `last_modified_by` 등)을 모두 포함합니다.

## 추가 구현 / 설계 포인트

- DTO를 분리해서 엔티티를 API 응답으로 직접 노출하지 않도록 구성했습니다
- 예외 응답을 일관된 JSON 포맷으로 통일했습니다
- 시드 데이터는 SQL 대신 `DataInitializer`에서 생성해 암호화된 비밀번호를 안전하게 준비했습니다
- Spring Security 7 / Boot 4 기준에 맞춰 stateless JWT 필터 체인을 구성했습니다

## 사용한 AI / 참고 자료

- OpenAI Codex(ChatGPT 계열 도구)를 사용해 구현 순서 정리, Spring Boot 4 호환 포인트 확인, 코드 작성/리팩터링 보조를 받았습니다
- 실제 구현은 로컬에서 직접 실행 및 테스트(`./gradlew test`)로 검증했습니다
