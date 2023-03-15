--#1. 회원 테이블 생성(구조만)
drop table member;

CREATE TABLE member (
    member_id    VARCHAR2(20),
    password     VARCHAR2(20) NOT NULL,
    name         VARCHAR2(30) NOT NULL,
    email        VARCHAR2(30) ,
    age          NUMBER(3)    NOT NULL,
    regdate      DATE DEFAULT SYSDATE
);


--#2. 제약조건 추가
ALTER TABLE member
  ADD ( CONSTRAINT member_id_pk   PRIMARY KEY(member_id),
        CONSTRAINT member_email_uk  UNIQUE (email));

--#3. 개발의 편의성을 위해 제약조건 비활성화
ALTER TABLE member
  DISABLE CONSTRAINT member_id_pk CASCADE
  DISABLE CONSTRAINT member_email_uk;

ALTER TABLE member
  ENABLE CONSTRAINT member_id_pk
  ENABLE CONSTRAINT member_email_uk;     

-- 회원 등록 테스트
INSERT INTO member(member_id, password, name, email, age)
VALUES ('bangry', '1111', '김기정', 'bangry@gmail.com', 30);

INSERT INTO member(member_id, password, name, email, age)
VALUES ('gildong', '1111', '홍길돌', 'gildong@gmail.com', 20);

COMMIT;

-- 전체 회원 조회 테스트
SELECT member_id, name, email, TO_CHAR(regdate, 'yyyy-MM-DD') regdate
FROM member
ORDER BY regdate DESC;

-- 아이디로 회원 조회(상세)
SELECT member_id, name, email, TO_CHAR(regdate, 'yyyy-MM-DD HH24:MI:SS') regdate, age
FROM member
WHERE member_id = 'bangry';

-- 아이디/비밀번호로 회원 조회(로그인)
SELECT member_id, name, email, TO_CHAR(regdate, 'yyyy-MM-DD HH24:MI:SS') regdate, age
FROM member
WHERE member_id = 'bangry' AND password = '1111';

-- 검색값에 따른 회원목록 조회
SELECT member_id, name, email, TO_CHAR(regdate, 'yyyy-MM-DD') regdate
FROM member
WHERE member_id = 'bangry' OR name LIKE '%길%';

        
-- 사용자 선택페이지 및 화면에 보여지는 목록개수 설정에 따른 사용자 목록 반환(페이징 처리)
SELECT member_id, name, password, email, regdate
FROM ( SELECT CEIL(ROWNUM / 10) page, member_id, name, password, email, regdate
       FROM   ( SELECT member_id, name, password, email, TO_CHAR(regdate, 'YYYY-MM-DD DAY') regdate
                FROM member
                ORDER  BY regdate DESC))
WHERE  page = 1;

-- 사용자 선택페이지, 조회 목록개수, 검색값에 따른 사용자 목록 반환(검색값에 따른 페이징 처리)
SELECT member_id, name, password, email, regdate
FROM ( SELECT CEIL(ROWNUM / 10) page, member_id, name, password, email, regdate
       FROM   ( SELECT member_id, name, password, email, TO_CHAR(regdate, 'YYYY-MM-DD DAY') regdate
                FROM member
                WHERE member_id = 'bangry' OR name LIKE '%길%'
                ORDER  BY regdate DESC))
WHERE  page = 1;

-- 전체 회원수 조회
SELECT COUNT(*) count
FROM member;

-- 검색값에 따른 회원수 조회 - 페이징 처리 시 필요
SELECT COUNT(*) count
FROM   member
WHERE member_id = 'bangry' OR name LIKE '%길%';