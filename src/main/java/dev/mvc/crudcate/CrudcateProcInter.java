package dev.mvc.crudcate;

import java.util.ArrayList;
import java.util.Map;

public interface CrudcateProcInter {
  /**
   * 등록
   * 
   */
  public int create(CrudcateVO crudcateVO);

  /**
   * 전체 목록
   * @return
   */
  public ArrayList<CrudcateVO> list_all();

  /**
   * 조회 
   * @param crudcateno
   * @return
   */
  public CrudcateVO read(int crudcateno);

  /**
   * 수정
   * @param crudcateVO
   * @return
   */
  public int update(CrudcateVO crudcateVO);

  /**
   * 삭제
   * @param crudcateno
   * @return
   */
  public int delete(int crudcateno);

  /**
   * 출력 순서 조절
   * @param crudcateno
   * @return
   */
  public int seqno_forward(int crudcateno);

  /**
   * 출력 순서 조절
   * @param crudcateno
   * @return
   */
  public int seqno_backward(int crudcateno);

  /**
   * 출력 보이기
   * @param crudcateno
   * @return
   */
  public int visible_y(int crudcateno);

  /**
   * 출력 숨기기
   * @param crudcateno
   * @return
   */
  public int visible_n(int crudcateno);

  /**
   * 대분류 리스트 출력
   * @return
   */
  public ArrayList<CrudcateVO> list_all_name_y();

  /**
   * 중분류 리스트 출력
   * @param name
   * @return
   */
  public ArrayList<CrudcateVO> list_all_namesub_y(String name);

  /** 메뉴 */
  public ArrayList<CrudcateVOMenu> menu();

  /**
   * 리스트 검색
   * @param word
   * @return
   */
  public ArrayList<CrudcateVO> list_search(String word);

  /**
   * 리스트 검색, 페이징
   * @param word
   * @param now_page
   * @param record_per_page
   * @return
   */
  public ArrayList<CrudcateVO> list_search_paging(String word, int now_page, int record_per_page);

  /**
   * 리스트 검색 카운터
   * @param word
   * @return
   */
  public int list_search_count(String word);

  /** 페이징 박스 */
  public String pagingBox(int now_page, String word, String list_file, int search_count, int record_per_page, int page_per_blocK);
}