package dev.mvc.week;

import java.util.ArrayList;
import java.util.HashMap;

public interface WeekDAOInter {
  
  /**
   * 생성
   * @param weekVO
   * @return
   */
  public int create(WeekVO weekVO);

  /**
   * 주간 선택 메뉴
   * @param accountno
   * @return
   */
  public ArrayList<WeekVO> list_wds(int accountno);

  /**
   * 전체 목록
   * @param map
   * @return
   */
  public ArrayList<WeekVO> list_all(HashMap<String, Object> map);

  /**
   * 삭제
   * @param map
   * @return
   */
  public int delete(HashMap<String, Object> map);

  /**
   * 수정
   * @param weekVO
   * @return
   */
  public int update(WeekVO weekVO);

}
