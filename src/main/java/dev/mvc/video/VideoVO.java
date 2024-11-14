package dev.mvc.video;

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
public class VideoVO {
  
  private String iframeHtml = "";
  
  private String mid = "";
  /**비디오 번호*/
  private Integer videono = 0;

  /**비디오 제목 */
  private String videotitle = "";

  private String url = "";
    
  private String descripe = "";
  
  /**등록일*/
  private Date video_date;
  

  private String mname;
  
  /**관리자 번호 */
  private Integer managerno = 0;

  
}
