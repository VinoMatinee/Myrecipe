CREATE TABLE WEEK (
    weekno NUMBER(10)  NOT NULL PRIMARY KEY,
    ACCOUNTNO    NUMBER(10) NOT NULL,
    weekdates VARCHAR2(255) NOT NULL,
    monbreakfast VARCHAR2(255),
    tuebreakfast VARCHAR2(255),
    wedbreakfast VARCHAR2(255),
    thubreakfast VARCHAR2(255),
    fribreakfast VARCHAR2(255),
    satbreakfast VARCHAR2(255),
    sunbreakfast VARCHAR2(255),
    monlunch VARCHAR2(255),
    tuelunch VARCHAR2(255),
    wedlunch VARCHAR2(255),
    thulunch VARCHAR2(255),
    frilunch VARCHAR2(255),
    satlunch VARCHAR2(255),
    sunlunch VARCHAR2(255),
    mondinner VARCHAR2(255),
    tuedinner VARCHAR2(255),
    weddinner VARCHAR2(255),
    thudinner VARCHAR2(255),
    fridinner VARCHAR2(255),
    satdinner VARCHAR2(255),
    sundinner VARCHAR2(255),
    moncal VARCHAR2(255),
    tuecal VARCHAR2(255),
    wedcal VARCHAR2(255),
    thucal VARCHAR2(255),
    frical VARCHAR2(255),
    satcal VARCHAR2(255),
    suncal VARCHAR2(255),
    wdate DATE NOT NULL,
    FOREIGN KEY (ACCOUNTNO) REFERENCES ACCOUNT (ACCOUNTNO)
);

CREATE SEQUENCE WEEK_SEQ
    START WITH 1
    INCREMENT BY 1
    MAXVALUE 9999999999
    CACHE 2
    NOCYCLE;
    
Commit;