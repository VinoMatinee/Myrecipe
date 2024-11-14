package dev.mvc.spice;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

// CREATE TABLE SPICE(
// 		SPICENO                       		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
// 		MANAGERNO                     		NUMBER(10)		 NULL ,
// 		SPICENAME                     		VARCHAR2(20)		 NOT NULL,
// 		SPICECONT                     		CLOB    		 NOT NULL,
// 		SPICEIMG                      		VARCHAR2(200)		 NULL ,
// 		SPICESAVED                    		VARCHAR2(200)		 NULL ,
// 		SPICETHUMB                    		VARCHAR2(200)		 NULL ,
// 		SPICESIZE                     		NUMBER(10)		 NULL ,
// 		SPICEPRICE                    		NUMBER(20)		 NOT NULL,
// 		URL                           		CLOB    		 NOT NULL,
// 		SPICEDATE                     		DATE		 NOT NULL,
// 		good                          		NUMBER(10)		 DEFAULT 0		 NOT NULL,
//   FOREIGN KEY (MANAGERNO) REFERENCES MANAGER (MANAGERNO)
// );

@Getter @Setter
public class SpiceVO {

  private int spiceno;

  private int managerno;

  private String spicename;

  private String spicecont;

  private MultipartFile fileMF = null;

  private String size_label = "";

  private String spiceimg = "";

  private String spicesaved = "";

  private String spicethumb = "";

  private long spicesize = 0;

  private int spiceprice;

  private String url;

  private String spicedate;

  private int good;

}
