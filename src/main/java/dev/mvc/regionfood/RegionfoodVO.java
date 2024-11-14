package dev.mvc.regionfood;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

//CREATE TABLE region(
//    regiono                             NUMBER(10)       NOT NULL       PRIMARY KEY,
//    regionname                          VARCHAR2(20)       NULL ,
//    MANAGERNO                           NUMBER(10)       NULL ,
//FOREIGN KEY (MANAGERNO) REFERENCES MANAGER (MANAGERNO)
//);


@Setter @Getter
public class RegionfoodVO {
//  
//  CREATE TABLE regionfood(
//      foodno                              NUMBER(10)       NOT NULL       PRIMARY KEY,
//      foodtitle                           VARCHAR2(30)       NOT NULL,
//      foodimg_url                         VARCHAR2(10)       NULL ,
//      regiono                             NUMBER(10)       NULL ,
//      recipelist                          VARCHAR2(255)       NULL ,
//      cook_tool                           VARCHAR2(255)       NULL ,
//      difficulty                          NUMBER(10)       NULL ,
//      cooktime                            NUMBER(10)       NULL ,
//      MANAGERNO                           NUMBER(10)       NULL ,
//  FOREIGN KEY (regiono) REFERENCES region (regiono),
//  FOREIGN KEY (MANAGERNO) REFERENCES MANAGER (MANAGERNO)
//);
  private String regionname = "";
 
  private String fthumb = "";
  
  private Integer foodno = 0;

 private Integer step_order = 0;
 
 private String step_text = "";
  
  private String foodtitle = "";
  
  
  private MultipartFile fphotoNF = null;
  /** 메인 이미지 크기 단위 */
  private String fsize_label = "";
  
  private long fsize = 0;
  /** 메인 이미지 */
  private String foodimg_url = "";
  /** 실제 저장된 메인 이미지 */
  private String foodimg_urlsaved = "";
  
  private Integer regiono = 1;
  
  private String recipelist = "";
  
  private String cook_tool = "";
  
  private Integer difficulty = 0;
  
  private Integer cooktime = 0;
  
  private String mname;
  
 
  private Integer managerno = 0;

  
}
