package dev.mvc.crudcate;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

// CREATE TABLE CRUDCATE(
// 		CRUDCATENO                    		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
// 		NAME                          		VARCHAR2(30)		 NOT NULL,
// 		NAMESUB                       		VARCHAR2(30)		 NOT NULL,
// 		NAMESUBSUB                    		VARCHAR2(30)		 NOT NULL,
// 		CNT                           		NUMBER(7)		 NOT NULL,
// 		CRUDATE                       		DATE		 NOT NULL,
// 		SEQNO                         		NUMBER(5)		 NOT NULL,
// 		VISIBLE                       		CHAR(1)		 DEFAULT 'N'		 NOT NULL,
// 		ACCOUNTNO                     		NUMBER(10)		 NULL ,
// 		MANAGERNO                     		NUMBER(10)		 NULL ,
//   FOREIGN KEY (ACCOUNTNO) REFERENCES ACCOUNT (ACCOUNTNO),
//   FOREIGN KEY (MANAGERNO) REFERENCES MANAGER (MANAGERNO)
// );

@Setter @Getter
public class CrudcateVOMenu {
  /** 대분류 */
  private String name;

  /** 중분류 */
  ArrayList<CrudcateVO> list_namesubs;
}
