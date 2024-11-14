package dev.mvc.loginlog;


import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class LoginlogVO {
  /*
    LOGINNO                           NUMBER(10)     NOT NULL    PRIMARY KEY,
    IP                                VARCHAR2(15)     NOT NULL,
    LOGINDATE                         DATE     NOT NULL,
    ACCOUNTNO                         NUMBER(10)     NULL ,  --FK
        FOREIGN KEY (ACCOUNTNO) REFERENCES ACCOUNT (ACCOUNTNO)
  */
    
    // 회원 로그인 로그
    
    /** 로그인 번호 **/
    private int loginno;
    /** IP */
    private String ip = "";
    /** 로그인 날짜 */
    private String logindate = "";
    /** 회원 번호 **/
    private int accountno;

    
}

