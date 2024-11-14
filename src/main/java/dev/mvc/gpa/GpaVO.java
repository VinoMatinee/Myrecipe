package dev.mvc.gpa;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

//CREATE TABLE GPA(
//    GPANO                             NUMBER(10)     NOT NULL    PRIMARY KEY,
//    GPASCORE                          INTEGER     NOT NULL ,
//    GPADATE                           DATE  DEFAULT SYSDATE NOT NULL,
//    ACCOUNTNO                         NUMBER(10)     NULL ,
//    BOARDNO                           NUMBER(10)     NOT NULL ,
//  FOREIGN KEY (ACCOUNTNO) REFERENCES ACCOUNT (ACCOUNTNO),
//  FOREIGN KEY (BOARDNO) REFERENCES BOARD (BOARDNO)
//);

@Setter @Getter
public class GpaVO {
  
  
  /**유저 아이디 */
  private String btitle = "";
  
  private String aid = "";
  
 private double avg_gpascore = 0;

  /**별점 번호*/
  private Integer gpano = 0;

  /**별점 점수 */
  private Integer gpascore = 0;

  /**등록일*/
  private Date gpadate;
  

  /**유저 번호 */
  private Integer accountno = 0;

  /**게시판 번호 */
  private Integer boardno = 0;
  
}
