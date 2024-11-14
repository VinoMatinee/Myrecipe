package dev.mvc.vocabulary;

import lombok.Getter;
import lombok.Setter;

//CREATE TABLE VOCABULARY(
//    VOCANO                            NUMBER(10)     NOT NULL    PRIMARY KEY,
//    VOCA                              VARCHAR2(20)     NOT NULL,
//    MEAN                              CLOB     NOT NULL,
//    MANAGERNO                         NUMBER(10)     NOT NULL,
//  FOREIGN KEY (MANAGERNO) REFERENCES MANAGER (MANAGERNO)
//);

@Setter @Getter
public class VocabularyVO {
  
  private int vocano;
  
  private String voca = "";
  
  private String mean;
  
  private int managerno;

}
