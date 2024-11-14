package dev.mvc.week;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class WeekVO {
  
  /** 주간 식단 시퀀스 번호 */
  private int weekno;

  /** 회원 번호 */
  private int accountno;

  /** 기간 (일주일) */
  private String weekdates;

  /** 아침 메뉴 */
  private String monbreakfast;
  private String tuebreakfast;
  private String wedbreakfast;
  private String thubreakfast;
  private String fribreakfast;
  private String satbreakfast;
  private String sunbreakfast;

  /** 점심 메뉴 */
  private String monlunch;
  private String tuelunch;
  private String wedlunch;
  private String thulunch;
  private String frilunch;
  private String satlunch;
  private String sunlunch;

  /** 저녁 메뉴 */
  private String mondinner;
  private String tuedinner;
  private String weddinner;
  private String thudinner;
  private String fridinner;
  private String satdinner;
  private String sundinner;

  /** 칼로리 */
  private String moncal;
  private String tuecal;
  private String wedcal;
  private String thucal;
  private String frical;
  private String satcal;
  private String suncal;

  /** 등록일 */
  private String wdate;
}
