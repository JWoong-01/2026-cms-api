# REST API Docs

## Authentication

모든 보호 API는 아래 헤더가 필요합니다.

```http
Authorization: Bearer {accessToken}
```

## 1. Login

`POST /api/auth/login`

Request

```json
{
  "username": "user1",
  "password": "user1234"
}
```

Response `200 OK`

```json
{
  "accessToken": "eyJ...",
  "tokenType": "Bearer",
  "expiresIn": 3600
}
```

## 2. Current User

`GET /api/auth/me`

Response `200 OK`

```json
{
  "id": 2,
  "username": "user1",
  "role": "USER"
}
```

## 3. Create Content

`POST /api/contents`

Request

```json
{
  "title": "My First Content",
  "description": "content body"
}
```

Response `201 Created`

```json
{
  "id": 4,
  "title": "My First Content",
  "description": "content body",
  "viewCount": 0,
  "createdBy": "user1",
  "lastModifiedBy": "user1",
  "createdDate": "2026-03-18T01:00:00",
  "lastModifiedDate": "2026-03-18T01:00:00"
}
```

## 4. Get Content List

`GET /api/contents?page=0&size=10`

Response `200 OK`

```json
{
  "items": [
    {
      "id": 1,
      "title": "Welcome",
      "viewCount": 0,
      "createdBy": "user1",
      "createdDate": "2026-03-18T01:00:00",
      "lastModifiedDate": "2026-03-18T01:00:00"
    }
  ],
  "page": 0,
  "size": 10,
  "totalElements": 3,
  "totalPages": 1
}
```

## 5. Get Content Detail

`GET /api/contents/{id}`

Response `200 OK`

```json
{
  "id": 1,
  "title": "Welcome",
  "description": "Seeded content by user1",
  "viewCount": 0,
  "createdBy": "user1",
  "lastModifiedBy": "user1",
  "createdDate": "2026-03-18T01:00:00",
  "lastModifiedDate": "2026-03-18T01:00:00"
}
```

## 6. Update Content

`PUT /api/contents/{id}`

Request

```json
{
  "title": "Updated Title",
  "description": "Updated description"
}
```

Response `200 OK`

```json
{
  "id": 1,
  "title": "Updated Title",
  "description": "Updated description",
  "viewCount": 0,
  "createdBy": "user1",
  "lastModifiedBy": "user1",
  "createdDate": "2026-03-18T01:00:00",
  "lastModifiedDate": "2026-03-18T01:10:00"
}
```

## 7. Delete Content

`DELETE /api/contents/{id}`

Response `204 No Content`

## Error Response

공통 에러 포맷은 아래와 같습니다.

```json
{
  "status": 403,
  "error": "Forbidden",
  "message": "Only the author or ADMIN can modify/delete this content",
  "timestamp": "2026-03-18T01:00:00"
}
```

## Authorization Rules

- 인증 필요: 모든 `/api/contents/**`, `/api/auth/me`
- `USER`: 본인 작성 콘텐츠만 수정/삭제 가능
- `ADMIN`: 모든 콘텐츠 수정/삭제 가능
