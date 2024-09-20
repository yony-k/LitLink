# 🔖 LitLink

LitLink는 좋아하는 책을 저장하고, 감상을 작성하여 다른 사람과 공유할 수 있는 책 정보 공유 서비스입니다.

# 📑 목차
### [Quick Start](#quick-start)
### [1. 프로젝트 개요](#1-프로젝트-개요)
- [⚙️기술 스택](#️-기술-스택)
- [✔️ 요구사항](#️-요구사항)
### [2. 프로젝트 관리](#2-프로젝트-관리)
- [🗓️ 일정(2024.08.04~2024.08.11)](#️-일정2024091220240920)
### [3. 기술 문서](#3-기술-문서)
- [📄 API 명세서](#-api-명세서)
### [4. 기능 구현](#4-기능-구현)
- [⭐ Authentication](#-Authentication)
- [⭐ Book](#-Book)
- [⭐ Note](#-Note)
### [5. 트러블 슈팅](#5-트러블-슈팅)

## Quick Start

1. Redis, Postgres 설치가 필요합니다.
2. Postgres DB에 어플리케이션에 사용될 DB, 사용자를 생성해줍니다.
3. 제공된 application.propertis 파일의 DB_URL, DB_USERNAME, DB_PASSWORD 에 위에서 생성한 DB의 이름, 사용자 계정의 이름, 사용자 계정의 비밀번호를 넣어줍니다.
4. 제공된 application.propertis 파일의 KAKAO_CLIENT, KAKAO_SECRET 에 카카오 로그인 때 사용할 카카오 계정의 정보를 넣어줍니다.
5.  제공된 application.propertis 파일의 CLIENT_ID, CLIENT_SECRET 에 책 검색 API 호출 때 사용할 네이버 계정의 정보를 넣어줍니다.
6. **src\main\resources** 경로 아래에 **application.properties** 파일을 넣어줍니다. </br>
7. 아래 명령어를 차례로 터미널에 입력합니다.

```bash
./gradlew clean build

./gradlew bootRun
```

</br>

## 1. 프로젝트 개요

### ⚙️ 기술 스택
![java](https://img.shields.io/badge/Java-17-blue?logo=java)
![spring-boot](https://img.shields.io/badge/SpringBoot-3.3.3-6DB33F?logo=springboot)
![postgresql](https://img.shields.io/badge/PostgreSQL-16.4-blue?logo=postgresql)
![redis](https://img.shields.io/badge/Redis-7.4.0-FF4438?logo=redis)

### ✔️ 요구사항

#### Authentication

- 카카오 소셜 회원가입/로그인 기능 (REST)

#### Book

- 로그인한 사용자는 책에 다음과 같은 기능을 이용할 수 있습니다.
    1. 책 검색 및 저장 기능
        - 책 검색은 Naver API를 사용하세요. (REST)
        - 검색된 책 중 사용자가 선택한 책을 데이터베이스에 저장합니다. (GraphQL)
    2. 저장한 책에 좋아요를 할 수 있는 기능 (GraphQL)
    3. 저장한 책 정보를 외부 공유할 수 있는 기능 (REST)
        - JWT 토큰을 사용합니다.
        - 공유를 통해 타 사용자/비로그인 사용자는 공유자가 저장한 책 정보 및 해당 책에 공유자가 작성한 노트를 열람할 수 있습니다.
        - 열람 만료 기한은 공유 시점으로부터 10분 후 입니다.

#### Note

- 로그인한 사용자는 자신이 저장한 책에 다음과 같은 기능을 이용할 수 있습니다.
    - 노트 CRUD (GraphQL)
        - 사용자는 자신이 저장한 책에 여러 개의 노트를 작성할 수 있으며, 노트 작성 및 열람 권한은 책을 저장한 사용자 당사자에게만 있습니다.
        - 노트 삭제는 Soft Delete로 구현합니다.
        - 노트는 최근 작성 순으로 정렬합니다.
- 외부 공유 기능을 통해 접근한 사용자는 공유자가 작성한 노트도 열람할 수 있습니다.

</br>

## 2. 프로젝트 관리

### 🗓️ 일정(2024.09.12~2024.09.20)

| 날짜 | 활동 |
| --- | --- |
| 24.09.12 (수) | 요구사항 분석 |
| 24.09.13 (목) | 엔티티 설계 |
| 24.09.17 (화) | 엔티티 생성 및 DB 구성 |
| 24.09.18 (수) | 스프링 시큐리티 적용 및 카카오 소셜 로그인 기능 구현 |
| 24.09.19 (목) | 네이버 책 검색 API 적용 및 책 관련 RESTful API, GraphQL API 개발 |
| 24.09.20 (금) | 노트 관련 GraphQL API 개발 및 README 작성 |

<details>
<summary><strong>작업 사이클</strong></summary>

1. 이슈 생성
2. 브랜치 생성
3. 코드 작성
4. PR 생성
5. dev 브랜치로 Merge
</details>

<details>
<summary><strong>이슈 관리</strong></summary>
<img src=https://github.com/user-attachments/assets/81b45ddf-fb31-486b-895e-e95a9dfa9c82>
</details>

<details>
<summary><strong>컨벤션</strong></summary>

- **Branch**
    - **전략**

      | Branch Type | Description |
            | --- | --- |
      | `dev` | 주요 개발 branch, `main`으로 merge 전 거치는 branch |
      | `feature` | 할 일 issue 등록 후 branch 생성 및 작업 |

    - **네이밍**
        - `{header}/#{issue number}`
        - 예) `feat/#1`

- **커밋 메시지 규칙**
    ```bash
    > [HEADER] : 기능 요약
    
    - [CHORE]: 내부 파일 수정
    - [FEAT] : 새로운 기능 구현
    - [ADD] : FEAT 이외의 부수적인 코드 추가, 라이브러리 추가, 새로운 파일 생성 시
    - [FIX] : 코드 수정, 버그, 오류 해결
    - [DEL] : 쓸모없는 코드 삭제
    - [DOCS] : README나 WIKI 등의 문서 개정
    - [MOVE] : 프로젝트 내 파일이나 코드의 이동
    - [RENAME] : 파일 이름의 변경
    - [MERGE]: 다른 브렌치를 merge하는 경우
    - [STYLE] : 코드가 아닌 스타일 변경을 하는 경우
    - [INIT] : Initial commit을 하는 경우
    - [REFACTOR] : 로직은 변경 없는 클린 코드를 위한 코드 수정
    
    ex) [FEAT] 게시글 목록 조회 API 구현
    ex) [FIX] 내가 작성하지 않은 리뷰 볼 수 있는 버그 해결
    ```

- **Issue**
    ```bash
    🔗 Description
    <!-- 진행할 작업을 설명해주세요 -->
    
    🔗 To-do
    <!-- 작업을 수행하기 위해 해야할 태스크를 작성해주세요 -->
    [ ] todo1
    
    🔗 ETC
    <!-- 특이사항 및 예정 개발 일정을 작성해주세요 -->
    ```

- **PR**
    - **규칙**
        - branch 작업 완료 후 PR 보내기
        - 항상 local에서 충돌 해결 후 remote에 올리기
        - 충돌 확인 후 문제 없으면 merge
        -  merge
      ```bash
          > [MERGE] {브랜치이름}/{#이슈번호}
          ex) [MERGE] setting/#1
      ```
    - **Template**
      ```bash
      🔗 Description
      <!-- 진행할 작업을 설명해주세요 -->
      
      🔗 To-do
      <!-- 작업을 수행하기 위해 해야할 태스크를 작성해주세요 -->
      [ ] todo1
      
      🔗 ETC
      <!-- 특이사항 및 예정 개발 일정을 작성해주세요 -->
      ```
</details>

</br>

## 3. 기술 문서

### 📄 API 명세서

| API 명칭 | HTTP 메서드 | 엔드포인트 | 설명 |
| --- | --- | --- | --- |
| **책 검색** | GET | `/api/book` | 책 검색 후 목록을 받아볼 수 있습니다. |
| **회원탈퇴** | POST | `/api/members` | 회원탈퇴를 합니다. |
| **refreshToken 재발급** | DELETE | `/api/members` | refreshToken을 재발급합니다. |
| **책 공유 기능으로 조회** | GET | `/api/bookmark` | 책 공유 기능을 통해 책 정보 및 노트를 조회합니다. |
| **책 공유** | POST | `/api/bookmark` | 북마크를 공유합니다. |
| **refreshToken 재발급** | DELETE | `/api/members` | refreshToken을 재발급합니다. |
| **saveBookMark** | POST | `/graphql` | 책 정보를 저장합니다. |
| **likeBookMark** | POST | `/graphql` | 북마크에 좋아요를 표시합니다. |
| **deleteBookMark** | POST | `/graphql` | 특정 북마크를 삭제합니다. |
| **getBookMarks** | POST | `/graphql` | 사용자의 모든 북마크를 조회합니다. |
| **getBookMark** | POST | `/graphql` | 특정 북마크를 조회합니다. |
| **saveNote** | POST | `/graphql` | 노트를 생서합니다. |
| **updateNote** | POST | `/graphql` | 노트를 수정합니다. |
| **deleteNote** | POST | `/graphql` | 노트를 삭제합니다. |
| **getNotes** | POST | `/graphql` | 특정 북마크에 속한 모든 노트를 조회합니다. |
| **getNote** | POST | `/graphql` | 특정 노트를 조회합니다. |


<details>
<summary><strong>ERD</strong></summary>
<img src=https://github.com/user-attachments/assets/8edf646d-a590-47da-9351-dc469c94aab9>
</details>

<details>
<summary><strong>디렉토리 구조</strong></summary>

```bash
D:.
├─main
│  ├─java
│  │  └─com
│  │      └─yonyk
│  │          └─litlink
│  │              │  LitLinkApplication.java
│  │              │
│  │              ├─domain
│  │              │  ├─book
│  │              │  │  └─controller
│  │              │  │          BookController.java
│  │              │  │
│  │              │  ├─bookmark
│  │              │  │  ├─controller
│  │              │  │  │      BookMarkController.java
│  │              │  │  │
│  │              │  │  ├─dto
│  │              │  │  │  ├─request
│  │              │  │  │  └─response
│  │              │  │  │          BookMarkDTO.java
│  │              │  │  │          ShareBookMarkDTO.java
│  │              │  │  │
│  │              │  │  ├─entity
│  │              │  │  │      BookMark.java
│  │              │  │  │
│  │              │  │  ├─graphql
│  │              │  │  │      BookMarkGraphqlController.java
│  │              │  │  │
│  │              │  │  ├─redis
│  │              │  │  │      ShareToken.java
│  │              │  │  │      ShareTokenRepository.java
│  │              │  │  │
│  │              │  │  ├─repository
│  │              │  │  │      BookMarkRepository.java
│  │              │  │  │
│  │              │  │  └─service
│  │              │  │          BookMarkService.java
│  │              │  │
│  │              │  ├─member
│  │              │  │  ├─controller
│  │              │  │  │      MemberController.java
│  │              │  │  │
│  │              │  │  ├─dto
│  │              │  │  │      dummy.txt
│  │              │  │  │
│  │              │  │  ├─entity
│  │              │  │  │  │  Member.java
│  │              │  │  │  │
│  │              │  │  │  └─enums
│  │              │  │  │          MemberRole.java
│  │              │  │  │
│  │              │  │  ├─exception
│  │              │  │  │      dummy.txt
│  │              │  │  │
│  │              │  │  ├─repository
│  │              │  │  │      MemberRepository.java
│  │              │  │  │
│  │              │  │  └─service
│  │              │  │          JwtService.java
│  │              │  │          MemberService.java
│  │              │  │
│  │              │  └─note
│  │              │      ├─dto
│  │              │      │  ├─request
│  │              │      │  │      CreateNoteDTO.java
│  │              │      │  │      UpdateNoteDTO.java
│  │              │      │  │
│  │              │      │  └─response
│  │              │      │          NoteDTO.java
│  │              │      │
│  │              │      ├─entity
│  │              │      │      Note.java
│  │              │      │
│  │              │      ├─exception
│  │              │      │      dummy.txt
│  │              │      │
│  │              │      ├─graphql
│  │              │      │      NoteGraphqlController.java
│  │              │      │
│  │              │      ├─repository
│  │              │      │      NoteRepository.java
│  │              │      │
│  │              │      └─service
│  │              │              NoteService.java
│  │              │
│  │              └─global
│  │                  ├─common
│  │                  │  │  BaseEntity.java
│  │                  │  │
│  │                  │  └─book
│  │                  │      ├─dto
│  │                  │      │  ├─request
│  │                  │      │  │      BookSerchDTO.java
│  │                  │      │  │
│  │                  │      │  └─response
│  │                  │      │          BookDTO.java
│  │                  │      │          BookSerchResult.java
│  │                  │      │
│  │                  │      ├─entity
│  │                  │      │      Book.java
│  │                  │      │
│  │                  │      ├─repository
│  │                  │      │      BookRepository.java
│  │                  │      │
│  │                  │      └─service
│  │                  │              BookAPIService.java
│  │                  │
│  │                  ├─config
│  │                  │      JacksonConfig.java
│  │                  │      RedisConfig.java
│  │                  │      SpringSecurityConfig.java
│  │                  │
│  │                  ├─error
│  │                  │  │  CustomException.java
│  │                  │  │  CustomExceptionHandler.java
│  │                  │  │
│  │                  │  └─exceptionType
│  │                  │          BookExceptionType.java
│  │                  │          BookMarkExceptionType.java
│  │                  │          CommonExceptionType.java
│  │                  │          ExceptionType.java
│  │                  │          GraphqlExceptionHandler.java
│  │                  │          NoteExceptionType.java
│  │                  │          SecurityExceptionType.java
│  │                  │
│  │                  └─security
│  │                      ├─details
│  │                      │      OAuth2UserDTO.java
│  │                      │      PrincipalDetails.java
│  │                      │
│  │                      ├─dto
│  │                      │      JwtDTO.java
│  │                      │
│  │                      ├─filter
│  │                      │      JwtAuthorizationFilter.java
│  │                      │
│  │                      ├─handler
│  │                      │      CustomAccessDeniedHandler.java
│  │                      │      CustomAuthenticationEntryPoint.java
│  │                      │      CustomLogoutHandler.java
│  │                      │      OAuth2SuccessHandler.java
│  │                      │      SecurityResponseHandler.java
│  │                      │
│  │                      ├─redis
│  │                      │      RefreshToken.java
│  │                      │      RefreshTokenRepository.java
│  │                      │
│  │                      ├─service
│  │                      │      CustomOAuth2UserService.java
│  │                      │
│  │                      └─util
│  │                              CookieProvider.java
│  │                              JwtProvider.java
│  │
│  └─resources
│      │  application.properties
│      │  application.yml
│      │
│      ├─graphql
│      │      schema.graphqls
│      │
│      ├─static
│      │      favicon.ico
│      │
│      └─templates
│              dummy.txt
│
└─test
    └─java
        └─com
            └─yonyk
                └─litlink
                        LitLinkApplicationTests.java
```

</details>

</br>

## 4. 기능 구현

### ⭐ Authentication

- 스프링 시큐리티와 OAuth2 이용 사용자 회원가입 및 로그인 기능 구현
- 스프링 시큐리티 내 핸드러를 커스텀하여 전역 예외 처리
- 대칭키 방식을 이용한 JWT 생성 및 발급
- accessToken은 헤더, refreshToken은 쿠키에 넣어서 반환
- refreshToken은 서버의 Redis에 저장

<details>
    <summary>구현 의도</summary>
    <div>
        <div><strong>스프링 시큐리티</strong></div>
        <div>스프링 시큐리티는 사용자 인증/인가 처리를 간편하게 처리해주는 프레임워크로 OAuth2 프로토콜을 사용할 수 있는 필터 또한 구현되어있습니다. OAuth2 로그인을 구현해야하는 프로젝트에 사용하기 알맞다고 생각하였기에 스프링 시큐리티를 적용했습니다.</div>
        <div><strong>JWT</strong></div>
        <div>로그인 한 사용자만 요청 가능한 API가 존재하기에 사용자 인가 관리가 필수 였습니다. 그 중 인가 관리 방법 중 보안성이 가장 높은 JWT 방식을 선택했고 사용자 경험 측면에서 로그인을 자주 하지 않는 편이 바람직할 것 같아 refreshToken을 함께 발급하도록 구현했습니다.</div>
        <div><strong>Redis</strong></div>
        <div>refreshToken은 서버에 저장되어있어야하지만 자주 요청 되는 자원입니다. 매 요청마다 DB에 접근하는 것은 비효율적이라고 판단해 비교적 접근이 쉽고 빠른 인메모리 방식의 데이터 저장소인 Redis에 저장하도록 했습니다.</div>
    </div>
</details>
<details>
    <summary>구현 코드</summary>
    <div>
        <a href="https://github.com/yony-k/LitLink/tree/dev/src/main/java/com/yonyk/litlink/global/security" target="_blank">스프링 시큐리티 패키지</a></br>
    </div>
</details>

---

### ⭐ Book

책 검색, 공유 제외 기본적으로 로그인 한 사용자만 이용가능하도록 개발하였습니다.

- 네이버 책 검색 API를 사용한 책 목록 조회 기능 구현(REST)
- 책 저장 기능 구현(GraphQL)
- 저장된 책 목록/단건 조회 기능 구현(GraphQL)
- 저장된 책에 좋아요 선택 기능 구현(GraphQL)
- 저장된 책 공유 기능 구현(GraphQL)
- 저장된 책 삭제 기능 구현(GraphQL)

<details>
    <summary>구현 의도</summary>
    <div>
        <div><strong>네이버 책 검색 API</strong></div>
        <div>사용자가 원하는 책의 정보만 DB에 저장되길 원했기에 대용량의 도서정보 DB 자체가 필요하지는 않았습니다. 그래서 네이버 책 검색 API를 이용해 검색어에 해당하는 도서정보만 받아본 후 그 중 사용자가 저장을 원하는 도서의 ISBN을 이용해 다시 API를 호출하고 해당 정보를 저장하는 방식을 택했습니다. </div>
        <div><strong>좋아요</strong></div>
        <div>사용자가 책을 저장하면 Book 테이블에 책의 정보가 저장되고, BookMark 테이블에 사용자의 북마크 정보가 저장되는 방식입니다. Book 테이블에는 어플리케이션 사용자들이 해당 책에 좋아요를 누른 횟수가 기록되고 BookMark 테이블에는 사용자가 해당 책에 좋아요 표시를 했는지 여부가 boolean 타입으로 기록됩니다. 이 어플리케이션에는 공유 기능이 있어 다른 사람들도 어플리케이션에 저장된 책의 정보를 볼 수 있는데 그때 해당 책이 어플리케이션 사용자들에게 얼마나 인기가 있는지 알 수 있게 해주면 좋을 것 같아 이렇게 설계를 해보았습니다.
        사용자가 자신의 북마크에 좋아요 요청을 하면 BookMark 테이블의 좋아요 표시 컬럼의 값이 true가 되고 동시에 Book 테이블의 좋아요 기록 컬럼의 숫자가 1 증가하는 방식입니다.</div>
        <div><strong>공유</strong></div>
        <div>사용자가 특정 북마크의 공유를 요청하면 JWT 이 포함된 공유 링크를 생성해서 반환하는 방식으로 구현했습니다. 해당 링크로 요청이 들어오면 REST API로 처리되며 책의 정보, 사용자가 해당 책에 생성한 노트를 모두 받아볼 수 있게 했습니다.</div>
    </div>
</details>
<details>
    <summary>구현 코드</summary>
    <div>
        <a href="https://github.com/yony-k/LitLink/tree/dev/src/main/java/com/yonyk/litlink/global/common/book" target="_blank">책 검색 API 패키지</a></br>
        <a href="https://github.com/yony-k/LitLink/blob/dev/src/main/java/com/yonyk/litlink/domain/book/controller/BookController.java" target="_blank">책 공유 기능</a></br>
        <a href="https://github.com/yony-k/LitLink/tree/dev/src/main/java/com/yonyk/litlink/domain/bookmark" target="_blank">BookMark 패키지</a></br>
    </div>
</details>

---

### ⭐ Note

기본적으로 로그인 한 사용자만 이용가능하도록 개발하였습니다.

- Note CRUD 구현

<details>
    <summary>구현 코드</summary>
    <div>
        <a href="https://github.com/yony-k/LitLink/tree/dev/src/main/java/com/yonyk/litlink/domain/note" target="_blank">Note 패키지</a></br>
    </div>
</details>

</br>

## 5. 트러블 슈팅

<details>
    <summary>GraphQL 예외처리</summary>
    <div>
        <div><strong>문제상황</strong></div>
        <div>GraphQL 컨트롤러 및 서비스에서 발생하는 예외가 전역 예외 처리기에서 처리되지 않는 상황이 발생했다. 예외 발생 시 클라이언트에서 500 오류가 노출되는 상황이 반복되었다.</div>
        <div><strong>원인</strong></div>
        <div>기존에 선언해놓은 전역 예외 처리기는 REST 요청에서 발생하는 예외만 처리할 수 있었다. GraphQL에서 전역 예외 처리를 하려면 또 다른 예외 처리 핸들러를 구현해야했다.</div>
        <div><strong>해결</strong></div>
        <div>각 예외처리 메소드에 @GraphQlExceptionHandler 어노테이션을 붙인 예외 처리 핸들러 클래스를 만들어서 서버를 구동하니 정상적으로 GraphQL 쿼리 진행과정 중 발생한 예외가 처리되었다.</div>
    </div>
</details>