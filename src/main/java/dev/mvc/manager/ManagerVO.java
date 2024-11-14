package dev.mvc.manager;


import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class ManagerVO {
  /*
    MANAGERNO                         NUMBER(10)     NOT NULL    PRIMARY KEY,
    MID                           VARCHAR2(30)     NOT NULL UNIQUE,
    MPASSWD                           VARCHAR2(200)    NOT NULL,
    MNAME                             VARCHAR2(30)     NOT NULL,
    MDATE                             DATE     NOT NULL,
    MGRADE                            NUMBER(2)    NOT NULL
  */

    /** 회원 번호 */
    private int managerno;
    /** 아이디(이메일) */
    private String mid = "";
    /** 패스워드 */
    private String mpasswd = "";
    /** 회원 성명 */
    private String mname = "";
    /** 가입일 */
    private String mdate = "";
    /** 등급 */
    private int mgrade = 0;

}

