package dev.mvc.crudcate;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface CrudcateDAOInter {
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
   * 출력 순서 높임
   * @param crudcateno
   * @return
   */
  public int seqno_forward(int crudcateno);

  /**
   * 출력 순서 낮춤
   * @param crudcateno
   * @return
   */
  public int seqno_backward(int crudcateno);

  /**
   * 카테고리 공개
   * @param crudcateno
   * @return
   */
  public int visible_y(int crudcateno);

  /**
   * 카테고리 비공개
   * @param crudcateno
   * @return
   */
  public int visible_n(int crudcateno);

  /**
   * 대분류 출력 리스트
   * @return
   */
  public ArrayList<CrudcateVO> list_all_name_y();

  /**
   * 중분류 출력 리스트
   * @param name
   * @return
   */
  public ArrayList<CrudcateVO> list_all_namesub_y(String name);

  /**
   * 검색 리스트
   * @param word
   * @return
   */
  public ArrayList<CrudcateVO> list_search(String word);

  /**
   * 리스트 검색, 페이징
   * @param map
   * @return
   */
  public ArrayList<CrudcateVO> list_search_paging(Map<String, Object> map);

  /**
   * 리스트 검색 카운터
   * @param word
   * @return
   */
  public int list_search_count(String word);

  /**
   * 페이징 박스
   * @param now_page
   * @param word
   * @param list_file
   * @param search_count
   * @param record_per_page
   * @param page_per_blocK
   * @return
   */
  public String pagingBox(int now_page, String word, String list_file, int search_count, int record_per_page, int page_per_blocK);

}
