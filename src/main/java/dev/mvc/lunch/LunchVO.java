package dev.mvc.lunch;

import lombok.Getter;
import lombok.Setter;

// CREATE TABLE L_RECOM (
//     L_NO       NUMBER(10)       NOT NULL PRIMARY KEY,
//     ACCOUNTNO  NUMBER(10)       NOT NULL,
//     L_MENU     VARCHAR2(255)    NOT NULL,
//     L_RECIPE   VARCHAR2(255)    NULL,
//     L_DATE     DATE             DEFAULT SYSDATE NOT NULL,
//     FOREIGN KEY (ACCOUNTNO) REFERENCES ACCOUNT (ACCOUNTNO)
// );

@Setter @Getter
public class LunchVO {

  /** 메뉴 추천 시퀀스 번호 */
  private int l_no;

  /** 회원 번호 */
  private int accoutno;

  /** 추천 메뉴 */
  private String l_menu;

  /** 추천 메뉴 레시피 */
  private String l_recipe;

  /** 등록일(생성일) */
  private String l_date;
}
