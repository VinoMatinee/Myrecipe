package dev.mvc.recipe_step;

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
public class Recipe_stepVO {
//  
//  CREATE TABLE recipe_step(
//      step_no                             NUMBER(10)       NOT NULL       PRIMARY KEY,
//      step_order                          NUMBER(10)       NULL ,
//      step_text                           VARCHAR2(255)       NULL ,
//      step_img                            VARCHAR2(100)       NULL ,
//      step_imgsaved                         VARCHAR2(100)       NULL ,
//      ssize                               NUMBER(10)       DEFAULT 0       NULL ,
//        thumb                              VARCHAR2(100)       NULL ,
//      foodno                              NUMBER(10)       NULL ,
//      MANAGERNO                           NUMBER(10)       NULL ,
//  FOREIGN KEY (foodno) REFERENCES regionfood (foodno),
//  FOREIGN KEY (MANAGERNO) REFERENCES MANAGER (MANAGERNO)
//);

  private String regionname = "";
 
  
  
  private Integer step_no = 0;

  private Integer step_order = 0;
 
  private String step_text = "";
  
  
  private MultipartFile sphotoNF = null;
  /** 메인 이미지 크기 단위 */
  private String ssize_label = "";
  
  private long ssize = 0;
  /** 메인 이미지 */
  private String step_img = "";
  /** 실제 저장된 메인 이미지 */
  private String thumb = "";
  
  private String step_imgsaved = "";
  
  
  private Integer foodno = 1;
  
  private String mname;
  
 
  private Integer managerno = 0;

  
}
