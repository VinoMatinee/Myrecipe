DROP TABLE ACCOUNT CASCADE CONSTRAINTS; 

CREATE TABLE ACCOUNT(
		ACCOUNTNO                     		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		AID                           		VARCHAR2(50)		 NOT NULL,
		APASSWD                       		VARCHAR2(200)		 NOT NULL,
		ANAME                         		VARCHAR2(30)		 NOT NULL,
		APROFILE_IMG                  		VARCHAR2(100)		 NULL,
        APROFILE_IMGSAVE                  	VARCHAR2(100)		 NULL,
        APROFILE_THUM                 		VARCHAR2(100)		 NULL,
		APROFILE_SIZE                 		NUMBER(10)		 DEFAULT 0 NULL,
        ZIPCODE     VARCHAR(5)        NULL, -- 우편번호, 12345
		ADDRESS1                      		VARCHAR2(80)		 DEFAULT '-'		 NULL ,
		ADDRESS2                      		VARCHAR2(80)		 DEFAULT '-'		 NULL ,
		ATEL                          		CHAR(30)		 NOT NULL,
		ADATE                         		DATE		 NOT NULL,
		AGRADE                        		NUMBER(2)		 NOT NULL
);

COMMENT ON TABLE ACCOUNT is '유저';
COMMENT ON COLUMN ACCOUNT.ACCOUNTNO is '유저 번호';
COMMENT ON COLUMN ACCOUNT.AID is '유저 아이디';
COMMENT ON COLUMN ACCOUNT.APASSWD is '유저 패스워드';
COMMENT ON COLUMN ACCOUNT.ANAME is '유저 이름';
COMMENT ON COLUMN ACCOUNT.APROFILE_IMG is '프로필 이미지';
COMMENT ON COLUMN ACCOUNT.APROFILE_IMGSAVE is '실제 저장된 프로필 이미지';
COMMENT ON COLUMN ACCOUNT.APROFILE_THUM is '프로필 썸네일';
COMMENT ON COLUMN ACCOUNT.APROFILE_SIZE is '프로필 파일 사이즈';
COMMENT ON COLUMN ACCOUNT.ZIPCODE is '우편번호';
COMMENT ON COLUMN ACCOUNT.ADDRESS1 is '주소1';
COMMENT ON COLUMN ACCOUNT.ADDRESS2 is '주소2';
COMMENT ON COLUMN ACCOUNT.ATEL is '유저 전화번호';
COMMENT ON COLUMN ACCOUNT.ADATE is '유저 가입일';
COMMENT ON COLUMN ACCOUNT.AGRADE is '유저 등급';


DROP SEQUENCE account_seq;

CREATE SEQUENCE account_seq
  START WITH 1              -- 시작 번호
  INCREMENT BY 1          -- 증가값
  MAXVALUE 9999999999 -- 최대값: 9999999 --> NUMBER(7) 대응
  CACHE 2                       -- 2번은 메모리에서만 계산
  NOCYCLE;                     -- 다시 1부터 생성되는 것을 방지
  
INSERT INTO account(accountno, aid, apasswd, aname, aprofile_img, aprofile_imgsave, aprofile_thum, aprofile_size, zipcode, address1, address2, atel, adate, agrade)
    VALUES (account_seq.nextval, 'admin1', '1234', '홍길동','space.jpg', 'space_1.jpg', 'space_t.jpg', 1000, '12345','서울시 종로구', '관철동',
    '000-0000-0000', sysdate, 15);

SELECT *
FROM account;

SELECT accountno, aid, apasswd, aname, aprofile_img, aprofile_imgsave, aprofile_thum, aprofile_size, zipcode, address1, address2, atel, adate, agrade
FROM account
ORDER BY agrade ASC, aid ASC;





SELECT COUNT(aid) as cnt
FROM account
WHERE aid=aid;


/**********************************/
/* Table Name: 관리자 */
/**********************************/
DROP TABLE MANAGER CASCADE CONSTRAINTS; 

CREATE TABLE MANAGER(
		MANAGERNO                     		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		MID                       		VARCHAR2(30)		 NOT NULL UNIQUE,
		MPASSWD                       		VARCHAR2(200)		 NOT NULL,
		MNAME                         		VARCHAR2(30)		 NOT NULL,
		MDATE                         		DATE		 NOT NULL,
		MGRADE                        		NUMBER(2)		 NOT NULL
);

COMMENT ON TABLE MANAGER is '관리자';
COMMENT ON COLUMN MANAGER.MANAGERNO is '관리자번호';
COMMENT ON COLUMN MANAGER.MID is '관리자 아이디';
COMMENT ON COLUMN MANAGER.MPASSWD is '관리자 비밀번호';
COMMENT ON COLUMN MANAGER.MNAME is '관리자 이름';
COMMENT ON COLUMN MANAGER.MDATE is '관리자 가입일';
COMMENT ON COLUMN MANAGER.MGRADE is '관리자 등급';


DROP SEQUENCE manager_seq;

CREATE SEQUENCE manager_seq
  START WITH 1              -- 시작 번호
  INCREMENT BY 1          -- 증가값
  MAXVALUE 9999999999 -- 최대값: 9999999 --> NUMBER(7) 대응
  CACHE 2                       -- 2번은 메모리에서만 계산
  NOCYCLE;                     -- 다시 1부터 생성되는 것을 방지
  
commit;

/**********************************/
/* Table Name: 로그인내역 */
/**********************************/

CREATE TABLE LOGINLOG(
		LOGINNO                       		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		IP                            		VARCHAR2(15)		 NOT NULL,
		LOGINDATE                     		DATE		 NOT NULL,
		ACCOUNTNO                     		NUMBER(10)		 NULL ,  --FK
        FOREIGN KEY (ACCOUNTNO) REFERENCES ACCOUNT (ACCOUNTNO)
);

COMMENT ON TABLE LOGINLOG is '로그인내역';
COMMENT ON COLUMN LOGINLOG.LOGINNO is '로그인번호';
COMMENT ON COLUMN LOGINLOG.IP is '접속아이피';
COMMENT ON COLUMN LOGINLOG.LOGINDATE is '로그인날짜';
COMMENT ON COLUMN LOGINLOG.ACCOUNTNO is '유저 번호';


DROP SEQUENCE log_seq;

CREATE SEQUENCE log_seq
  START WITH 1              -- 시작 번호
  INCREMENT BY 1          -- 증가값
  MAXVALUE 9999999999 -- 최대값: 9999999 --> NUMBER(7) 대응
  CACHE 2                       -- 2번은 메모리에서만 계산
  NOCYCLE;                     -- 다시 1부터 생성되는 것을 방지
  
