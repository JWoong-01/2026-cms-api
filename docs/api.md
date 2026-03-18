# REST API 문서

## 기본 정보

- Base URL: `http://localhost:8080`
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- 인증 방식: JWT Bearer Token

보호된 API를 호출할 때는 아래 헤더가 필요합니다.

```http
Authorization: Bearer {accessToken}
```

## 인증 API

### 1. 로그인

- Method: `POST`
- Path: `/api/auth/login`
- 설명: 사용자명과 비밀번호로 로그인하고 JWT access token을 발급합니다.

요청 예시

```json
{
  "username": "user1",
  "password": "user1234"
}
```

응답 예시 `200 OK`

```json
{
  "accessToken": "eyJ...",
  "tokenType": "Bearer",
  "expiresIn": 3600
}
```

실패 예시 `401 Unauthorized`

```json
{
  "status": 401,
  "error": "Unauthorized",
  "message": "Invalid username or password",
  "timestamp": "2026-03-19T02:00:00"
}
```

### 2. 현재 로그인 사용자 조회

- Method: `GET`
- Path: `/api/auth/me`
- 설명: 현재 토큰 기준 로그인한 사용자 정보를 조회합니다.
- 인증 필요: 예

응답 예시 `200 OK`

```json
{
  "id": 2,
  "username": "user1",
  "role": "USER"
}
```

## 콘텐츠 API

### 1. 콘텐츠 생성

- Method: `POST`
- Path: `/api/contents`
- 설명: 새로운 콘텐츠를 생성합니다.
- 인증 필요: 예

요청 예시

```json
{
  "title": "첫 번째 콘텐츠",
  "description": "콘텐츠 내용입니다."
}
```

응답 예시 `201 Created`

```json
{
  "id": 4,
  "title": "첫 번째 콘텐츠",
  "description": "콘텐츠 내용입니다.",
  "viewCount": 0,
  "createdBy": "user1",
  "lastModifiedBy": "user1",
  "createdDate": "2026-03-19T02:00:00",
  "lastModifiedDate": "2026-03-19T02:00:00"
}
```

### 2. 콘텐츠 목록 조회

- Method: `GET`
- Path: `/api/contents`
- 설명: 콘텐츠 목록을 페이징하여 조회합니다.
- 인증 필요: 예

쿼리 파라미터

- `page`: 페이지 번호, 0부터 시작
- `size`: 페이지 크기

호출 예시

```http
GET /api/contents?page=0&size=10
```

응답 예시 `200 OK`

```json
{
  "items": [
    {
      "id": 1,
      "title": "Welcome",
      "viewCount": 0,
      "createdBy": "user1",
      "createdDate": "2026-03-19T02:00:00",
      "lastModifiedDate": "2026-03-19T02:00:00"
    }
  ],
  "page": 0,
  "size": 10,
  "totalElements": 3,
  "totalPages": 1
}
```

### 3. 콘텐츠 상세 조회

- Method: `GET`
- Path: `/api/contents/{id}`
- 설명: 단일 콘텐츠의 상세 정보를 조회합니다.
- 인증 필요: 예

응답 예시 `200 OK`

```json
{
  "id": 1,
  "title": "Welcome",
  "description": "Seeded content by user1",
  "viewCount": 0,
  "createdBy": "user1",
  "lastModifiedBy": "user1",
  "createdDate": "2026-03-19T02:00:00",
  "lastModifiedDate": "2026-03-19T02:00:00"
}
```

### 4. 콘텐츠 수정

- Method: `PUT`
- Path: `/api/contents/{id}`
- 설명: 콘텐츠를 수정합니다.
- 인증 필요: 예
- 권한 조건: 작성자 본인 또는 `ADMIN`

요청 예시

```json
{
  "title": "수정된 제목",
  "description": "수정된 내용입니다."
}
```

응답 예시 `200 OK`

```json
{
  "id": 1,
  "title": "수정된 제목",
  "description": "수정된 내용입니다.",
  "viewCount": 0,
  "createdBy": "user1",
  "lastModifiedBy": "user1",
  "createdDate": "2026-03-19T02:00:00",
  "lastModifiedDate": "2026-03-19T02:10:00"
}
```

### 5. 콘텐츠 삭제

- Method: `DELETE`
- Path: `/api/contents/{id}`
- 설명: 콘텐츠를 삭제합니다.
- 인증 필요: 예
- 권한 조건: 작성자 본인 또는 `ADMIN`

응답 예시 `204 No Content`

## 공통 에러 응답

모든 에러 응답은 아래 형식으로 반환합니다.

```json
{
  "status": 403,
  "error": "Forbidden",
  "message": "Only the author or ADMIN can modify/delete this content",
  "timestamp": "2026-03-19T02:00:00"
}
```

주요 상태 코드

- `400 Bad Request`: 요청값 검증 실패
- `401 Unauthorized`: 인증 실패 또는 토큰 없음
- `403 Forbidden`: 권한 없음
- `404 Not Found`: 대상 콘텐츠 없음

## 권한 정책

- 인증되지 않은 사용자는 콘텐츠 API에 접근할 수 없습니다.
- `USER`는 자신이 작성한 콘텐츠만 수정/삭제할 수 있습니다.
- `ADMIN`은 모든 콘텐츠를 수정/삭제할 수 있습니다.

## 테스트 계정

- `admin / admin1234` : `ADMIN`
- `user1 / user1234` : `USER`
- `user2 / user1234` : `USER`
