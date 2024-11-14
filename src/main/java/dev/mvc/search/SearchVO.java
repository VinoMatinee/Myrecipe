package dev.mvc.search;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class SearchVO {

//  CREATE TABLE SEARCH(
//      SEARCHNO                            NUMBER(10)     NOT NULL    PRIMARY KEY,
//      WORD                              VARCHAR2(20)     NOT NULL,
//      CNT                             CLOB     NOT NULL
//  );

  private int searchno;

  private String word = "";

  private int cnt;
  
  private int num;

}
