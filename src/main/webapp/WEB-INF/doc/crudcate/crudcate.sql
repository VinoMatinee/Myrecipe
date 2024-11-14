/**********************************/
/* Table Name: 게시판 분류 */
/**********************************/

DROP TABLE CRUDCATE CASCADE CONSTRAINTS; -- 자식 무시하고 삭제 가능
DROP TABLE CRUDCATE;

DROP SEQUENCE CRUDCATE_SEQ;

CREATE TABLE CRUDCATE(
		CRUDCATENO                    		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		NAME                          		VARCHAR2(30)		 NOT NULL,
		NAMESUB                       		VARCHAR2(30)		 NOT NULL,
		NAMESUBSUB                    		VARCHAR2(30)		 NOT NULL,
		CNT                           		NUMBER(7)		 NOT NULL,
		CRUDATE                       		DATE		 NOT NULL,
		SEQNO                         		NUMBER(5)		 NOT NULL,
		VISIBLE                       		CHAR(1)		 DEFAULT 'N'		 NOT NULL,
		ACCOUNTNO                     		NUMBER(10)		 NULL ,
		MANAGERNO                     		NUMBER(10)		 NULL ,
  FOREIGN KEY (ACCOUNTNO) REFERENCES ACCOUNT (ACCOUNTNO),
  FOREIGN KEY (MANAGERNO) REFERENCES MANAGER (MANAGERNO)
);

COMMENT ON TABLE CRUDCATE is '게시판 분류';
COMMENT ON COLUMN CRUDCATE.CRUDCATENO is '카테고리번호';
COMMENT ON COLUMN CRUDCATE.NAME is '대분류명';
COMMENT ON COLUMN CRUDCATE.NAMESUB is '중분류명';
COMMENT ON COLUMN CRUDCATE.NAMESUBSUB is '소분류명';
COMMENT ON COLUMN CRUDCATE.CNT is '관련자료수';
COMMENT ON COLUMN CRUDCATE.CRUDATE is '등록일';
COMMENT ON COLUMN CRUDCATE.SEQNO is '출력순서';
COMMENT ON COLUMN CRUDCATE.VISIBLE is '출력모드';
COMMENT ON COLUMN CRUDCATE.ACCOUNTNO is '유저 번호';
COMMENT ON COLUMN CRUDCATE.MANAGERNO is '관리자번호';

-- SEQUENCE
CREATE SEQUENCE CRUDCATE_SEQ
    START WITH 1
    INCREMENT BY 1
    MAXVALUE 9999999999
    CACHE 2
    NOCYCLE;
    
-- CREATE    
INSERT INTO CRUDCATE(CRUDCATENO, name, namesub, namesubsub, cnt, crudate, seqno, visible)
VALUES(CRUDCATE_SEQ.nextval, '게시판', '질문', '-', 0, sysdate, 1, 'Y');

commit;

SELECT * FROM crudcate;