package dev.mvc.lunch;

import java.util.ArrayList;

public interface LunchProcInter {

  /**
   * 목록
   * @param accountno
   * @return
   */
  public ArrayList<LunchVO> list(int accountno);

  /**
   * 전체 목록
   * @return
   */
  public ArrayList<LunchVO> list_all();

  /**
   * 삭제
   * @param l_no
   * @return
   */
  public int delete(int l_no);
  
}