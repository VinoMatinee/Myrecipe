package dev.mvc.breply;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

//CREATE TABLE BREPLY(
//    BREPLYNO                          NUMBER(10)     NOT NULL    PRIMARY KEY,
//    BREPLYCONT                        CLOB     NOT NULL,
//    BREPLYIMG                         VARCHAR2(100)    NULL ,
//    BREPLYSAVED                       VARCHAR2(100)    NULL ,
//    BREPLYTHUMB                       VARCHAR2(100)    NULL ,
//    BREPLYSIZE                        NUMBER(10)     NULL ,
//    BREPLYDATE                        DATE     NOT NULL,
//    BREPLYPASSWD                      VARCHAR2(200)    NOT NULL,
//    ACCOUNTNO                         NUMBER(10)     NULL ,
//    BOARDNO                           NUMBER(10)     NULL ,
//  FOREIGN KEY (BOARDNO) REFERENCES BOARD (BOARDNO)
//);

@Setter @Getter
public class BreplyVO {

  /** 댓글 번호 */
  private int breplyno;

  /** 댓글내용 */
  @NotEmpty(message="내용을 입력해주세요.")
  private String breplycont="";

  /** 이미지 파일 */
  private MultipartFile file1MF = null;

  /** 메인 이미지 크기 단위, 파일 크기 */
  private String size_label = "";

  /** 댓글 이미지 */
  private String breplyimg = "";

  /** 댓글 이미지 미리보기 */
  private String breplysaved = "";

  /** 댓글 이미지 썸네일 */
  private String breplythumb = "";

  /** 댓글 이미지 크기 */
  private long breplysize = 0;

  /** 댓글 등록일 */
  private String breplydate;

  /** 댓글 비밀번호 */
  @NotEmpty(message="비밀번호를 설정해주세요.")
  private String breplypasswd;

  private int accountno;

  private int managerno;

  private int boardno;
}
