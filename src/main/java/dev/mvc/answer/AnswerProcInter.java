package dev.mvc.answer;

import java.util.ArrayList;
import java.util.Map;

public interface AnswerProcInter {

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
   * 유저용 답변 검색 카운트
   * @param word
   * @return
   */
  public int list_search_count(Map <String, Object> map);
  
  /**
   * 질문 검색 + 페이징
   * @param word
   * @param now_page
   * @param record_per_page
   * @return
   */
  public ArrayList<AnswerVO> list_all_search_paging(String word, int now_page, int record_per_page); 
  
  public ArrayList<AnswerVO> list_search_paging(int movieno, String word, int now_page, int record_per_page);
  
  public String pagingBox(int accountno, int now_page, String word, String list_file, int search_count, int record_per_page, int page_per_blocK);
  
  public String userpagingBox(int accountno, int now_page, String word, String list_file, int search_count, int record_per_page, int page_per_blocK);
}
