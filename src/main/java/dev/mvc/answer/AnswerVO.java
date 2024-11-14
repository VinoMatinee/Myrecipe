package dev.mvc.answer;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class AnswerVO {
  
//  CREATE TABLE AI_ANSWER(
//      ANSWERNO                            NUMBER(10)       NOT NULL       PRIMARY KEY,
//      TEXT_ANSWER                         CLOB       NULL ,
//      SEARCHNO                            NUMBER(10)       NULL ,
//  FOREIGN KEY (SEARCHNO) REFERENCES AI_SEARCH (SEARCHNO)
//);
  
  private int answerno;
  
  private String text_answer = "";
  
  private int searchno;
  
  private int accountno;
  
  private String aname;
  
  private String rdate = "";
  
  /** 질문한 이미지 */
  private String img_search = "";
  
  /** 질문한 이미지 저장 */
  private String img_search_save = "";
  
  /** 질문한 이미지 섬네일 */
  private String img_search_thumb = "";
  
  /** 저장한 이미지 사이즈 */
  private long img_search_size = 0;
  
  

}
