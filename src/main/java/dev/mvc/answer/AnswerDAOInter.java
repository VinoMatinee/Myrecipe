package dev.mvc.answer;

import java.util.ArrayList;
import java.util.Map;

public interface AnswerDAOInter {
  
  /**
   * 유저 전용 리스트
   * @param accountno
   * @return
   */
  public ArrayList <AnswerVO> list(int accountno);
  
  /**
   * 관리자 전용 리스트
   * @return
   */
  public ArrayList <AnswerVO> list_all();
  
  /**
   * 관리자용 답변 검색 카운트
   * @param word
   * @return
   */
  public int list_all_search_count(String word);
  
  /**
   * 관리자용 질문 검색 + 페이징
   * @param map
   * @return
   */
  public ArrayList<AnswerVO> list_all_search_paging(Map <String, Object> map);
  
  /**
   * 유저용 질문 검색 + 페이징
   * @param map
   * @return
   */
  public ArrayList<AnswerVO> list_search_paging(Map <String, Object> map);
  
  /**
   * 유저용 답변 검색 카운트
   * @param word
   * @return
   */
  public int list_search_count(Map <String, Object> map);

}
