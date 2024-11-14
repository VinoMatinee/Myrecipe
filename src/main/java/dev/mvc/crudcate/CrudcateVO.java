package dev.mvc.crudcate;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

// CREATE TABLE CRUDCATE(
// 		CRUDCATENO                    		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
// 		NAME                          		VARCHAR2(30)		 NOT NULL,
// 		NAMESUB                       		VARCHAR2(30)		 NOT NULL,
// 		NAMESUBSUB                    		VARCHAR2(30)		 NOT NULL,
// 		CNT                           		NUMBER(7)		 NOT NULL,
// 		CRUDATE                       		DATE		 NOT NULL,
// 		SEQNO                         		NUMBER(5)		 NOT NULL,
// 		VISIBLE                       		CHAR(1)		 DEFAULT 'N'		 NOT NULL,
// 		ACCOUNTNO                     		NUMBER(10)		 NULL ,
// 		MANAGERNO                     		NUMBER(10)		 NULL ,
//   FOREIGN KEY (ACCOUNTNO) REFERENCES ACCOUNT (ACCOUNTNO),
//   FOREIGN KEY (MANAGERNO) REFERENCES MANAGER (MANAGERNO)
// );


@Setter @Getter
public class CrudcateVO {
  /** 게시판 카테고리 번호 */
  private Integer crudcateno;

  /** 대분류명 */
  @NotEmpty(message="대분류명은 필수 입력 항목입니다.")
  private String name;

  /** 중분류명 */
  @NotEmpty(message="중분류명은 필수 입력 항목입니다.")
  private String namesub;

  /** 소분류명 */
  @NotEmpty(message="소분류명은 필수 입력 항목입니다.")
  private String namesubsub;

  /** 관련자료수 */
  @NotNull(message="관련자료수는 필수 입력 항목입니다.")
  private Integer cnt;

  /** 등록일 */
  private String crudate;

  /** 출력순서 */
  @NotNull(message="출력 순서는 필수 입력 항목입니다.")
  private Integer seqno=1;

  /** 출력모드 */
  @NotEmpty(message="출력 모드는 필수 입력 항목입니다.")
  @Pattern(regexp="^[YN]$", message="Y 또는 N만 입력 가능합니다.")
  private String visible = "N";

  /** 유저번호 */
  /** 관리자번호 */
}
