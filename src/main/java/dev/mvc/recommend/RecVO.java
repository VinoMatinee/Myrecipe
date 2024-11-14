package dev.mvc.recommend;

import lombok.Getter;
import lombok.Setter;

// CREATE TABLE RECOMMEND(
// 		RECONO                        		NUMBER(10)		 NULL 		 PRIMARY KEY,
// 		SPICENO                       		NUMBER(10)		 NULL ,
// 		ACCOUNTNO                     		NUMBER(10)		 NULL ,
// 		RECODATE                      		DATE		 NOT NULL,
//   FOREIGN KEY (SPICENO) REFERENCES SPICE (SPICENO),
//   FOREIGN KEY (ACCOUNTNO) REFERENCES ACCOUNT (ACCOUNTNO)
// );

@Getter @Setter
public class RecVO {

private int recono;

private int spiceno;

private int accountno;

private String recodate;
  
}
