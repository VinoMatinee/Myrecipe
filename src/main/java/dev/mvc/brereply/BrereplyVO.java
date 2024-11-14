package dev.mvc.brereply;

import org.springframework.web.multipart.MultipartFile;

import dev.mvc.breply.BreplyVO;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

// CREATE TABLE BREREPLY(
// 		BREREPLYNO                      		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
// 		BREREPLYCONT                  		CLOB		 NOT NULL,
// 		BREREPLYDATE                  		DATE		 NOT NULL,
// 		BREPLYNO                      		NUMBER(10)		 NULL ,
// 		ACCOUNTNO                     		NUMBER(10)		 NULL ,
// 		BREREPLYIMG                   		VARCHAR2(100)		 NULL ,
// 		BREREPLYSAVE                  		VARCHAR2(100)		 NULL ,
// 		BREREPLYTHUMB                 		VARCHAR2(100)		 NULL ,
// 		BREREPLYSIZE                  		NUMBER(10)		 NULL ,
//    BREREPLYPASSWD                      VARCHAR2(200)		 NOT NULL,
//   FOREIGN KEY (BREPLYNO) REFERENCES BREPLY (BREPLYNO),
//   FOREIGN KEY (ACCOUNTNO) REFERENCES ACCOUNT (ACCOUNTNO)
// );

@Setter @Getter
public class BrereplyVO {

  /** 대댓글 번호 */
  private int brereplyno;

  /** 대댓글내용 */
  @NotEmpty(message="내용을 입력해주세요.")
  private String brereplycont="";

  /** 이미지 파일 */
  private MultipartFile fileMF = null;

  /** 메인 이미지 크기 단위, 파일 크기 */
  private String size_label = "";

  /** 대댓글 이미지 */
  private String brereplyimg = "";

  /** 대댓글 이미지 미리보기 */
  private String brereplysave = "";

  /** 대댓글 이미지 썸네일 */
  private String brereplythumb = "";

  /** 대댓글 이미지 크기 */
  private long brereplysize = 0;

  /** 대댓글 등록일 */
  private String brereplydate;

  /** 대댓글 비밀번호 */
  @NotEmpty(message="비밀번호를 설정해주세요.")
  private String brereplypasswd;

  /** 댓글 번호 */
  private int breplyno;

  /** 회원 번호 */
  private int accountno;

  /** 관리자 번호 */
  private int managerno;
}
