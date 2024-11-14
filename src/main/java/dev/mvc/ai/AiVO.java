  package dev.mvc.ai;
  
  import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Max;
  import lombok.Getter;
  import lombok.Setter;
  
  //CREATE TABLE AI_SEARCH(
  //    SEARCHNO                          NUMBER(10)     NOT NULL    PRIMARY KEY,
  //    IMG_SEARCH                        VARCHAR2(100)    NULL ,
  //    IMG_SEARCH_SAVE                   VARCHAR2(100)    NULL ,
  //    IMG_SEARCH_THUMB                  VARCHAR2(100)    NULL ,
  //    IMG_SEARCH_SIZE                   NUMBER(10)     NULL ,
  //    TEXT_SEARCH                       CLOB(500)    NULL ,
  //    ACCOUNTNO                         NUMBER(10)     NULL ,
  //    ANSWERNO                          NUMBER(10)     NULL ,
  //  FOREIGN KEY (ACCOUNTNO) REFERENCES ACCOUNT (ACCOUNTNO)
  //);
  
  @Setter @Getter
  public class AiVO {
    /** ai 에게 질문 한 번호 */
   
    
    private Integer searchno=0;
    
    private String file1 = "";
    
    private MultipartFile file1MF = null;
    
    /** 질문한 이미지 */
    private String img_search = "";
    
    /** 질문한 이미지 저장 */
    private String img_search_save = "";
    
    /** 질문한 이미지 섬네일 */
    private String img_search_thumb = "";
    
    /** 저장한 이미지 사이즈 */
    private long img_search_size = 0;
    
    /** 질문한 텍스트 */
    private String text_search = "";
    
    /** 유저 번호 */
    private Integer accountno = 1;
    
    /** 답변 번호 */
    private Integer answerno = 1;
    
    private String rdate = "";
    
    
    
    
    
    
  }
