--#1. cookbook(요리책) 테이블 생성
CREATE TABLE cookbook (
  book_id     NUMBER(7),
  name        VARCHAR2(30)  NOT NULL,
  describe    VARCHAR2(200),
  author_id   VARCHAR2(20)
);

--#2. recipe(조리법) 테이블 생성
CREATE TABLE recipe (
  recipe_id      NUMBER(7),
  book_id        NUMBER(7),
  name           VARCHAR2(30)  NOT NULL,
  time           NUMBER(3),
  levels         NUMBER(1),
  ingredients    VARCHAR2(300),
  img_file_type  VARCHAR2(30),
  img_file_name  VARCHAR2(30),
  writer_id      VARCHAR2(20)  
);


--#3. recipe_procedure(조리법의 조리절차) 테이블 생성
CREATE TABLE recipe_procedure (
  recipe_id      NUMBER(7),
  seq_num        NUMBER(2),
  procedure      VARCHAR2(300) NOT NULL 
);

--#4.제약조건 추가
ALTER TABLE cookbook
  ADD ( CONSTRAINT cookbook_id_pk PRIMARY KEY(book_id),
        CONSTRAINT cookbook_author_id_fk FOREIGN KEY(author_id)  REFERENCES member(member_id));

ALTER TABLE recipe
  ADD ( CONSTRAINT recipe_id_pk          PRIMARY KEY(recipe_id),
        CONSTRAINT recipe_book_id_fk     FOREIGN KEY(book_id)    REFERENCES cookbook(book_id),
        CONSTRAINT recipe_writer_id_fk   FOREIGN KEY(writer_id)  REFERENCES member(member_id) );

ALTER TABLE recipe_procedure
  ADD ( CONSTRAINT recipe_id_fk          FOREIGN KEY(recipe_id)  REFERENCES recipe(recipe_id),
        CONSTRAINT recipe_id_seqnum_pk   PRIMARY KEY(recipe_id, seq_num) );

--#5. cookbook 시퀀스 생성
CREATE SEQUENCE cookbook_seq
START WITH 100
INCREMENT BY 10; 

--#6. 테스트 등록
INSERT INTO cookbook (book_id, name, describe, author_id)
VALUES (cookbook_seq.NEXTVAL, '한식매니아', '한식요리를 맛있게 만드는 방법을 설명합니다',  'bangry');

INSERT INTO cookbook (book_id, name, describe, author_id)
VALUES (cookbook_seq.NEXTVAL, '중식매니아', '중식요리를 맛있게 만드는 방법을 설명합니다',  'gildong');

commit;

-- 요리책 목록 조회(조인)
SELECT c.book_id book_id, c.name book_name, c.describe book_desc, m.name author_name 
FROM cookbook c
    JOIN member m
        ON c.author_id = m.member_id
ORDER BY book_id DESC;












