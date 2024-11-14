package dev.mvc.spice;

import java.util.ArrayList;

public interface SpiceProcInter {

  /**
   * 향신료 등록
   * @param spiceVO
   * @return
   */
  public int create(SpiceVO spiceVO);

  /**
   * 향신료 목록
   * @return
   */
  public ArrayList<SpiceVO> list();

  /**
   * 향신료 조회
   * @param spiceno
   * @return
   */
  public SpiceVO read(int spiceno);

  /**
   * 향신료 수정
   * @param spiceVO
   * @return
   */
  public int update(SpiceVO spiceVO);

  /**
   * 향신료 삭제
   * @param spiceno
   * @return
   */
  public int delete(int spiceno);

  public int good_up(int spiceno);

  public int good_down(int spiceno);

  /**
   * 페이징
   * @param map
   * @return
   */
  public ArrayList<SpiceVO> list_search_paging(String word, int now_page, int record_per_page);

  /**
   * 검색 레코드 수
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
