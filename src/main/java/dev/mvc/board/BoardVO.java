package dev.mvc.board;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

// CREATE TABLE BOARD(
//     BOARDNO        NUMBER(10)       NOT NULL PRIMARY KEY,
//     ACCOUNTNO      NUMBER(10)       NULL,
//     MANAGERNO      NUMBER(10)       NULL,
//     CRUDCATENO     NUMBER(10)       NULL,
//     BTITLE         VARCHAR2(20)     NOT NULL,
//     BCONTENT       CLOB             NOT NULL,
//     BDATE          DATE             NOT NULL,
//     BPASSWD        VARCHAR2(200)    NOT NULL,
//     BTAG           VARCHAR2(100)    NULL,
//     BPHOTO         VARCHAR2(100)    NULL,
//     BPHOTOSAVED    VARCHAR2(100)    NULL,
//     BTHUMB         VARCHAR2(100)    NULL,
//     BSIZE          NUMBER(10)       DEFAULT 0 NULL,
//     BREPLYCNT      NUMBER(10)       DEFAULT 0 NULL,
//     BVIEW          NUMBER(7)        DEFAULT 0 NULL,
//     BRECOM         NUMBER(7)        DEFAULT 0 NULL,
//     BYOUTUBE       VARCHAR2(1000)   NULL,
//     BVISIBLE       CHAR(1)          DEFAULT 'N' NOT NULL,
//     TAGNO          NUMBER(10)       NULL,
//     FOREIGN KEY (ACCOUNTNO) REFERENCES ACCOUNT (ACCOUNTNO),
//     FOREIGN KEY (MANAGERNO) REFERENCES MANAGER (MANAGERNO),
//     FOREIGN KEY (CRUDCATENO) REFERENCES CRUDCATE (CRUDCATENO)
// );


@Setter @Getter
public class BoardVO {
  /** 게시판 번호 */
  private int boardno;

  /** 관리자 번호 */
  private int managerno;

  /** 유저 번호 */
  private int accountno;

  /** 카테고리 번호 */
  private int crudcateno;

  /** 게시판 제목 */
  private String btitle;

  /** 게시판 내용 */
  private String bcontent;

  /** 게시판 등록일 */
  private String bdate;

  /** 게시판 비밀번호 */
  private String bpasswd;

  /** 게시판 태그 */
  private String btag;

  /** 이미지 파일 */
  private MultipartFile bphotoNF = null;
  /** 메인 이미지 크기 단위 */
  private String bsize_label = "";

  /** 메인 이미지 */
  private String bphoto = "";

  /** 실제 저장된 메인 이미지 */
  private String bphotosaved = "";;

  /** 메인 이미지 썸네일 */
  private String bthumb = "";;

  /* 메인 이미지 파일 사이즈 */
  private long bsize = 0;

  /** 게시판 댓글수 */
  private int breplycnt;

  /** 게시판 조회수 */
  private int bview;

  /** 게시판 추천수 */
  private int brecom;

  /** 게시판 유튜브 */
  private String byoutube;

  /** 게시판 출력모드 */
  private String bvisible = "Y";

  /** 분류 번호 */
  private int tagno;

  
}
