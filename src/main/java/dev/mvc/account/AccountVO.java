package dev.mvc.account;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class AccountVO {
  /*
    ACCOUNTNO                         NUMBER(10)     NOT NULL    PRIMARY KEY,
    AID                               VARCHAR2(50)     NOT NULL,
    APASSWD                           VARCHAR2(200)    NOT NULL,
    ANAME                             VARCHAR2(30)     NOT NULL,
    APROFILE_IMG                      VARCHAR2(100)    NOT NULL,
    APROFILE_IMGSAVE                    VARCHAR2(100)    NOT NULL,
    APROFILE_THUM                     VARCHAR2(100)    NOT NULL,
    APROFILE_SIZE                     NUMBER(10)     NULL ,
    ZIPCODE     VARCHAR(5)        NULL, -- 우편번호, 12345
    ADDRESS1                          VARCHAR2(80)     DEFAULT '-'     NULL ,
    ADDRESS2                          VARCHAR2(80)     DEFAULT '-'     NULL ,
    ATEL                              CHAR(30)     NOT NULL,
    ADATE                             DATE     NOT NULL,
    AGRADE                            NUMBER(2)    NOT NULL
  */

    /** 회원 번호 */
    private int accountno;
    /** 아이디(이메일) */
    private String aid = "";
    /** 패스워드 */
    private String apasswd = "";
    /** 회원 성명 */
    private String aname = "";
    /** 전화 번호 */
    private String atel = "";
    /** 우편 번호 */
    private String zipcode = "";
    /** 주소 1 */
    private String address1 = "";
    /** 주소 2 */
    private String address2 = "";
    /** 가입일 */
    private String adate = "";
    /** 등급 */
    private int agrade = 0;

    
    
    // 프로필 이미지 업로드
    // -----------------------------------------------------------------------------------

    private MultipartFile aprofile_imgMF = null;
    /** 메인 이미지 크기 단위, 파일 크기 */
    private String aprofile_size_label = "";
    /** 메인 이미지 */
    private String aprofile_img = "";
    /** 실제 저장된 메인 이미지 */
    private String aprofile_imgsave = "";
    /** 메인 이미지 preview */
    private String aprofile_thum = "";
    /** 메인 이미지 크기 */
    private long aprofile_size = 0;
    
    // 회원 로그인 로그
    
    /** 로그인 번호 **/
    private int loginno;
    /** IP */
    private String ip = "";
    /** 로그인 날짜 */
    private String ldata = "";

    
}

