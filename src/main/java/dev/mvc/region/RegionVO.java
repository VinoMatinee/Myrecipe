package dev.mvc.region;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

//CREATE TABLE region(
//    regiono                             NUMBER(10)       NOT NULL       PRIMARY KEY,
//    regionname                          VARCHAR2(20)       NULL ,
//    MANAGERNO                           NUMBER(10)       NULL ,
//FOREIGN KEY (MANAGERNO) REFERENCES MANAGER (MANAGERNO)
//);


@Setter @Getter
public class RegionVO {
  
  
 
  private Integer regiono = 0;

 
  private String regionname = "";
  
  private String mname;
  
 
  private Integer managerno = 0;

  
}
